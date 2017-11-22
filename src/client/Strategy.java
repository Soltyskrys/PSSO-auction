package client;

import java.rmi.RemoteException;

import server.IAuctionListener;
import server.Item;

public interface Strategy extends IAuctionListener{
	public void update(Item item) throws RemoteException;
}
