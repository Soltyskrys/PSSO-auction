package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class AuctionServer implements IAuctionServer{

	private  List<ObservableItem> items;

	AuctionServer() throws RemoteException{
		items = new ArrayList<>();
	}

	private boolean checkIfItemAlreadyExists(String itemName){
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

		System.out.println("User " + bidderName + "make a bid on item " + itemName + " " + bid);

		for (int i=0;i<this.items.size();i++){
			if(items.get(i).getItemName().equals(itemName)){
				if(items.get(i).getCurrentBid() < bid) {
					items.get(i).setCurrentBidderName(bidderName);
					items.get(i).setCurrentBid(bid);
					break;
				}
				else{
					throw new RemoteException("Your bid is lower or equals currentBid");
				}


			}
		}

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
		for(int i=0;i<this.items.size();i++){
			if(this.items.get(i).getRemainTime() > 0){
				this.items.get(i).decrementTime();
			}
		}
	}

	public static void main(String[] args) {
		if (System.getSecurityManager() == null) System.setSecurityManager(new SecurityManager());
		try {
			String name = "AuctionServer";
			IAuctionServer engine = new AuctionServer();
			IAuctionServer stub = (IAuctionServer) UnicastRemoteObject.exportObject(engine, 0);
			stub.placeItemForBid("Ala", "Rower", "Super rower", 1000.00, 3000);

			Registry registry = LocateRegistry.getRegistry();
			registry.rebind(name, stub);
			System.out.println("Server program bound");
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
		} catch (Exception e) {
			System.err.println("Exc:");
			e.printStackTrace();
		}
	}
}
