package server;

import java.rmi.RemoteException;

public class AuctionServer implements IAuctionServer{

	@Override
	public void placeItemForBid(String ownerName, String itemName, String itemDesc, double startBid, int auctionTime)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bidOnItem(String bidderName, String itemName, double bid) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Item[] getItems() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerListener(IAuctionListener al, String itemName) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
