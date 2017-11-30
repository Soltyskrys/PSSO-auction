package client;

import server.Strategy;
import server.IAuctionListener;
import server.Item;

import java.rmi.RemoteException;


public class Client implements IAuctionListener {

    private Strategy strategy;
    private String name;

    public Client(String name){
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
