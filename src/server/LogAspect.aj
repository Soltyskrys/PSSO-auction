package server;

import java.rmi.RemoteException;

public aspect LogAspect {
	private static FileLogger logger;
	

	after(String ownerName,String itemName,String itemDesc,double startBid,int auctionTime) : execution(void IAuctionServer.placeItemForBid(..)) && args(ownerName,itemName,itemDesc,startBid,auctionTime){
		Log(ownerName+" placed item "+itemName+" on auction, start bid: "+startBid+" ,for "+auctionTime+" seconds");
	}


	after(String bidderName,String itemName,double bid) : execution(void IAuctionServer.bidOnItem(..)) && args(bidderName,itemName,bid){
		Log("User " + bidderName + " wants to make a bid on item " + itemName + " " + bid);
	}
	
	private void Log(String message) {
		if(logger==null)
			logger = new FileLogger("server.log");
		logger.log(message);
	}
}
