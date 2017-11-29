package server;

import java.rmi.RemoteException;

import server.Item;

public class LastMinuteBidStrategy implements Strategy {
	String newBidder;
	@Override
	public void update(Item item){
		// TODO Auto-generated method stub
		
	}
	public void setNewBidder(String newBidder) {
		this.newBidder = newBidder;
	}


}
