package server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import server.IAuctionServer;

privileged public aspect AuctionHistory {
	private List<BidHistory>  AuctionServer.auctionHistory = new ArrayList<BidHistory>();
	
	public abstract double IAuctionServer.getUserCostsAspect(String username) throws RemoteException;
	
	public double AuctionServer.getUserCostsAspect(String username) throws RemoteException{
			double sum=0;
			for(BidHistory bid: auctionHistory) {
				if(bid.bidderName.equals(username))
					sum += bid.bid;
			}
			return sum;
     }
	
	void around(AuctionServer a): execution(void IAuctionServer.decrease()) && this(a){
		for(Iterator<ObservableItem> iter = a.items.iterator(); iter.hasNext();){
			ObservableItem item = iter.next();
			if(item.getRemainTime() > 0){
				item.decrementTime();
			}
			else {
				a.auctionHistory.add(new BidHistory(item.getCurrentBidderName(),item.getItemName(),item.getCurrentBid()));
				a.removeItem(iter);
			}
		}
	}
}


