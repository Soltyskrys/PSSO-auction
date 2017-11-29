package server;

import server.Strategy;

import java.rmi.RemoteException;

public class AuctionListener implements IAuctionListener {

    private Strategy strategy;
    private String name;

    public AuctionListener(String name){
        this.name = name;
        strategy = null;
    }

    @Override
    public void setStrategy(Strategy s){
        this.strategy = s;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void update(Item item) throws RemoteException {
        if (strategy!=null){
            strategy.setNewBidder(this.name);
            strategy.update(item);
        }

        if(item.getRemainTime() > 0){

            System.out.println("Item: " + item.getItemName() + " was updated. Current bid is " + item.getCurrentBid());
        }
        else {
            System.out.println("Item: " + item.getItemName() + ". Auction expired");
        }
    }

}
