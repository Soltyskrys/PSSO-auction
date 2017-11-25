package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAuctionListener extends Remote {
	public void update(Item item) throws RemoteException;
	public void setName(String name) throws RemoteException;
	public String getName() throws RemoteException;
}
