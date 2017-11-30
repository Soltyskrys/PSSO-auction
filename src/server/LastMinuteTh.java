package server;

import java.rmi.RemoteException;


public class LastMinuteTh implements Runnable{
    Item item;
    String newBidder;
    private IAuctionServer auctionServer;
    public LastMinuteTh(Item i, IAuctionServer auctionServer, String bidder){
        this.item = i;
        this.newBidder = bidder;
        this.auctionServer = auctionServer;
    }

    public static int LAST_SEC = 4;

    @Override
    public void run() {
        try {
            while(true) {
                Thread.sleep(100);

                if(item.getRemainTime()<=LAST_SEC){
                    auctionServer.bidOnItem(this.newBidder, item.getItemName(), item.getCurrentBid() * 2);
                    break;
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}