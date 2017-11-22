package server;

public class AuctionFactory {
	public IAuctionServer getAuctionServer() {
		return new AuctionServer();
	}
}
