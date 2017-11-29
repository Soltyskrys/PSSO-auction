package server;

import java.rmi.RemoteException;

public class AuctionListener implements IAuctionListener {

    public AuctionListener(){

    }

    @Override
    public void update(Item item) throws RemoteException {

        if(item.getRemainTime() > 0){
            System.out.println("Item: " + item.getItemName() + " was updated. Current bid is " + item.getCurrentBid());
        }
        else {
            System.out.println("Item: " + item.getItemName() + ". Auction expired");
        }
    }
}
