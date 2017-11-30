package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;


public class LastMinuteBidStrategy implements Strategy {
    private String newBidder;
    private Timer timer;
    private Item item;

    private IAuctionServer auctionServer;

	private class SerialTimer extends Timer implements Serializable { }
    private int SECONDS_TO_THE_END = 20;

	public LastMinuteBidStrategy(IAuctionServer auctionServer, Item i, String username){
	    this.newBidder = username;
		this.auctionServer = auctionServer;
        System.out.println("Createing last minute strategy");
        this.item = i;
        update(i);
	}

    public void bid(){
	    System.out.println("Doing the bid!");
        try {
            if(!this.newBidder.equals(item.getCurrentBidderName()))
                auctionServer.bidOnItem(this.newBidder, item.getItemName(), 2*item.getCurrentBid());
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

	@Override
	public void update(Item i){
	    this.item = i;
        timer = new SerialTimer();
        if(this.item.getRemainTime() <= SECONDS_TO_THE_END){
            timer.schedule(new RemindTask(), 0);
        }
        else {
            int secToWait = this.item.getRemainTime() - SECONDS_TO_THE_END;
            timer.schedule(new RemindTask(), secToWait * 1000);
        }
	}

	public void setNewBidder(String newBidder) {
		this.newBidder = newBidder;
	}


    class RemindTask extends TimerTask implements Serializable {
        public void run() {
            bid();
        }
    }
}
