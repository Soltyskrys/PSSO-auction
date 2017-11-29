package client;

import java.rmi.RemoteException;

import server.IAuctionServer;
import server.Item;

public class MaximumBidStrategy implements Strategy{

	private int maxBid;
	private IAuctionServer auctionServer;
	private String newBidder;

	public MaximumBidStrategy(IAuctionServer auctionServer){
		this.auctionServer = auctionServer;
	}

	@Override
	public void update(Item item) throws RemoteException {
		if(item.getCurrentBid()+1 <= maxBid){
			auctionServer.bidOnItem(this.newBidder, item.getItemName(), item.getCurrentBid()+1);
		}
		
	}

	public int getMaxBid() {
		return maxBid;
	}

	public void setMaxBid(int maxBid) {
		this.maxBid = maxBid;
	}

	public void setNewBidder(String newBidder) {
		this.newBidder = newBidder;
	}
}
