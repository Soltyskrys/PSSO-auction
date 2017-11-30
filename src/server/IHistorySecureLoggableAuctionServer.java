package server;

import java.rmi.RemoteException;

public interface IHistorySecureLoggableAuctionServer extends ISecureLoggableAuctionServer{
	
	double getUserCosts(String username) throws RemoteException;
}
