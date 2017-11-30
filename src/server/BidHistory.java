package server;

public class BidHistory{
	String bidderName;
	String itemName;
	double bid;
	
	public BidHistory(String bidderName, String itemName, double bid) {
		this.bidderName = bidderName;
		this.itemName = itemName;
		this.bid = bid;
	}
}