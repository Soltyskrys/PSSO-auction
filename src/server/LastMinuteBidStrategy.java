package server;

import java.rmi.RemoteException;

import server.Item;



public class LastMinuteBidStrategy implements Strategy {
	String newBidder;
	private IAuctionServer auctionServer;

	public LastMinuteBidStrategy(IAuctionServer auctionServer){
		this.auctionServer = auctionServer;
	}

	@Override
	public void update(Item item){
		int timeRemain = item.getRemainTime();
		if(timeRemain <= 4 && timeRemain!=0){
			try {
				auctionServer.bidOnItem(this.newBidder, item.getItemName(), 2*item.getCurrentBid());
			} catch (RemoteException e) {
				System.out.println(e.getMessage());
			}
		}
		else if (timeRemain!=0){
			Thread t = new Thread(new LastMinuteTh (item, auctionServer, this.newBidder));
			t.start();
		}
		
	}

	public void setNewBidder(String newBidder) {
		this.newBidder = newBidder;
	}


}
