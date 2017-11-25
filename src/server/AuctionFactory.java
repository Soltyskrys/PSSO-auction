package server;

import java.rmi.RemoteException;

public class AuctionFactory {
	private IAuctionServer server;
	public synchronized IAuctionServer getAuctionServer() throws RemoteException {
		if(server==null)
		{
			server = new AuctionServer();
		}
		return new AuctionServer();
	}
}
