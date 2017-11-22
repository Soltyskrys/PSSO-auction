package server;

import java.rmi.RemoteException;

public interface IAuctionServer {
	 public void placeItemForBid(
			 String ownerName,
			 String itemName,
			 String itemDesc,
			 double startBid,
			 int auctionTime)  throws RemoteException;
	 
     public void bidOnItem(String bidderName, String itemName, double bid) throws RemoteException;
     
     public Item[] getItems() throws RemoteException; 
     
     public void registerListener(IAuctionListener al, String itemName) throws RemoteException;
}
