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
		// TODO Auto-generated method stub

	}

	@Override
	public Item[] getItems() throws RemoteException {
		return this.items.toArray(new Item[0]);

	}

	@Override
	public void registerListener(IAuctionListener al, String itemName) throws RemoteException {
		// TODO Auto-generated method stub

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
				Thread.sleep(500);
				myItems = stub.getItems();
				for (int i=0; i<myItems.length;i++) {
					System.out.println(myItems[i].getItemName());
				}
			}
		} catch (Exception e) {
			System.err.println("Exc:");
			e.printStackTrace();
		}
	}

}
