package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class LoggableAuctionServer extends AuctionServer {

	protected FileLogger fileLogger;
	LoggableAuctionServer() throws RemoteException {
		super();
		fileLogger = new FileLogger("server.log");
	}
	
	@Override
	public void placeItemForBid(String ownerName, String itemName, String itemDesc, double startBid, int auctionTime)
			throws RemoteException {

		if(!checkIfItemAlreadyExists(itemName)) {
			fileLogger.log(ownerName+" placed item "+itemName+" on auction, start bid: "+startBid+" ,for "+auctionTime+" seconds");
			ObservableItem item = new ObservableItem(ownerName, itemName, itemDesc, startBid, auctionTime);
			items.add(item);
		}
		else{
			throw new RemoteException("Item already exists on auction");
		}
	}

	@Override
	public void bidOnItem(String bidderName, String itemName, double bid) throws RemoteException {
		fileLogger.log("User " + bidderName + " wants to make a bid on item " + itemName + " " + bid);
		super.bidOnItem(bidderName, itemName, bid);
	}
	
	@Override
	protected void setBidOnItem(String bidderName, String itemName, double bid, int i) {
		super.setBidOnItem(bidderName, itemName, bid, i);
		fileLogger.log("User " + bidderName + " made a bid on item " + itemName + " for bid: " + bid);
	}
	

	public static void main(String[] args) {
		if (System.getSecurityManager() == null) System.setSecurityManager(new SecurityManager());
		try {
			String name = "AuctionServer";
			LoggableAuctionServer engine = new LoggableAuctionServer();
			engine.placeItemForBid("Ala", "Rower", "Super rower", 1000.00, 3000);
			
		    Remote stub = UnicastRemoteObject.exportObject(engine, 0);

		    LocateRegistry.createRegistry(4555);
			Registry registry = LocateRegistry.getRegistry(4555);
			registry.rebind(name, stub);
			System.out.println("Server program bound");
			makeProgress(engine);
		} catch (Exception e) {
			System.err.println("Exc:");
			e.printStackTrace();
		}
	}

}
