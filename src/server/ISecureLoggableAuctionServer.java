package server;

import java.rmi.RemoteException;

public interface ISecureLoggableAuctionServer extends IAuctionServer {
	void authenticateUser(String username, String password) throws RemoteException;
}
