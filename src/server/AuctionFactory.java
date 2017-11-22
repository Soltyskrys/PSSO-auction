package server;

public class AuctionFactory {
	private IAuctionServer server;
	public synchronized IAuctionServer getAuctionServer() {
		if(server==null)
		{
			server = new AuctionServer();
		}
		return new AuctionServer();
	}
}
