package server;

import server.Strategy;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAuctionListener extends Remote {
	public void update(Item item) throws RemoteException;
	public String getName() throws RemoteException;
	public void setStrategy(Strategy s) throws RemoteException;
}
