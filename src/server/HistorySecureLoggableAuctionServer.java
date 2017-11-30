package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class HistorySecureLoggableAuctionServer extends SecureLoggableAuctionServer implements IHistorySecureLoggableAuctionServer{

	protected class BidHistoryEntry{
		String bidderName;
		String itemName;
		double bid;
		
		public BidHistoryEntry(String bidderName, String itemName, double bid) {
			this.bidderName = bidderName;
			this.itemName = itemName;
			this.bid = bid;
		}

	}
	protected List<BidHistoryEntry> bidHistory;
	
	public HistorySecureLoggableAuctionServer() throws RemoteException {
		super();
		authenticatedUsers.add("Stefan");
		bidHistory = new ArrayList<>();
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
				if (item.getCurrentBidderName() != null && !item.getCurrentBidderName().isEmpty()) {
					bidHistory.add(new BidHistoryEntry(item.getCurrentBidderName(),
							item.getItemName(), item.getCurrentBid()));
				}
			}
		}
	}


	@Override
	public double getUserCosts(String username) throws RemoteException {
		return bidHistory.stream()
			.filter(entry -> entry.bidderName.equals(username))
			.mapToDouble(entry -> entry.bid).sum();
	}
	
	public static void main(String[] args) {
		if (System.getSecurityManager() == null) System.setSecurityManager(new SecurityManager());
		try {
			String name = "AuctionServer";
			HistorySecureLoggableAuctionServer engine = new HistorySecureLoggableAuctionServer();
			Remote stub = UnicastRemoteObject.exportObject(engine, 0);
			engine.placeItemForBid("Ala", "Rower", "Super rower", 10.00, 50);
			engine.placeItemForBid("Ala", "Rower2", "Super rower", 10.00, 70);
			engine.placeItemForBid("Ala", "Rower3", "Super rower", 10.00, 100);
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


}
