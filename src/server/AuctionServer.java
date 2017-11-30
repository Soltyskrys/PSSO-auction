package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import client.Client;

public class AuctionServer implements IAuctionServer{

	protected  List<ObservableItem> items;

	AuctionServer() throws RemoteException{
		items = new ArrayList<>();
	}

	protected boolean checkIfItemAlreadyExists(String itemName){
		for(int i=0;i<items.size();i++){
			if(items.get(i).getItemName().equals(itemName))
				return true;
		}
		return false;
	}


	@Override
	public void placeItemForBid(String ownerName, String itemName, String itemDesc, double startBid, int auctionTime)
			throws RemoteException {

		if(!checkIfItemAlreadyExists(itemName)) {
			ObservableItem item = new ObservableItem(ownerName, itemName, itemDesc, startBid, auctionTime);
			items.add(item);
		}
		else{
			throw new RemoteException("Item already exists on auction");
		}
	}

	@Override
	public void bidOnItem(String bidderName, String itemName, double bid) throws RemoteException {

		System.out.println("User " + bidderName + "wants to make a bid on item " + itemName + " " + bid);
		for (int i=0;i<this.items.size();i++){
			if(items.get(i).getItemName().equals(itemName)){
				if(items.get(i).getCurrentBid() < bid) {
					setBidOnItem(bidderName, itemName, bid, i);
					break;
				}
				else{
					throw new RemoteException("Your bid is lower or equals currentBid");
				}
			}
		}
	}

	protected void setBidOnItem(String bidderName, String itemName, double bid, int i) {
		items.get(i).setCurrentBid(bid);
		items.get(i).setCurrentBidderName(bidderName);
		System.out.println("User " + bidderName + "made a bid on item " + itemName + " " + bid);
	}

	@Override
	public Item[] getItems() throws RemoteException {
		return this.items.toArray(new Item[0]);

	}

	@Override
	public void registerListener(IAuctionListener al, String itemName) throws RemoteException {
		for(int i=0;i<this.items.size();i++){
			if(this.items.get(i).getItemName().equals(itemName)){
				this.items.get(i).registerListener(al);
				break;
			}
		}
	}

	@Override
	public void decrease() {
		for(Iterator<ObservableItem> iter = this.items.iterator(); iter.hasNext();){
			ObservableItem item = iter.next();
			if(item.getRemainTime() > 0){
				item.decrementTime();
			}
			else {
				removeItem(iter);
			}
		}
	}
	
	protected void removeItem(Iterator<ObservableItem> iter) {
		iter.remove();
		System.out.println("Auction expired");
	}
	

	public static void main(String[] args) {
		if (System.getSecurityManager() == null) System.setSecurityManager(new SecurityManager());
		try {
			String name = "AuctionServer";
			IAuctionServer engine = new AuctionServer();
			Remote stub =  UnicastRemoteObject.exportObject(engine, 0);
			engine.placeItemForBid("Ala", "Rower", "Super rower", 1000.00, 3000);

			LocateRegistry.createRegistry(45555);
			Registry registry = LocateRegistry.getRegistry(45555);
			registry.rebind(name, stub);
			System.out.println("Server program bound");
			makeProgress(engine);
		} catch (Exception e) {
			System.err.println("Exc:");
			e.printStackTrace();
		}
	}

	protected static void makeProgress(IAuctionServer stub) throws InterruptedException, RemoteException {
		Item[] myItems;

		while(true){
			Thread.sleep(1000);
			myItems = stub.getItems();
			stub.decrease();
			for (int i=0; i<myItems.length;i++) {
				System.out.println(myItems[i].getItemName() + " "
						+ myItems[i].getRemainTime() + " "
						+ myItems[i].getCurrentBid() + " "
						+ myItems[i].getCurrentBidderName());
			}
		}
	}
}
