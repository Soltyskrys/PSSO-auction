package server;

import java.io.Serializable;

public class Item implements Serializable{
	private double currentBid;
	private String currentBidderName;
	private String description;
	private String name;
	private String owner;
	private volatile int remainTime;

	public Item(String ownerName, String itemName, String itemDesc, double startBid, int auctionTime){
		this.owner = ownerName;
		this.name = itemName;
		this.description = itemDesc;
		this.currentBid = startBid;
		this.remainTime = auctionTime;
	}

	public double getCurrentBid() {
		return this.currentBid;
	}

	public void setCurrentBid(double currBid){
		this.currentBid = currBid;
	}

	public String getCurrentBidderName() {

		return currentBidderName;
	}

	public void setCurrentBidderName(String currBidderName){
		this.currentBidderName = currBidderName;
	}

	public String getItemDesc() {
		return this.description;
	}

	public void setItemDescription(String desc){
		this.description = desc;
	}

	public String getItemName()
	{
		return this.name;
	}

	public void setItemName(String n){
		this.name = n;
	}

	public String getOwnerName()
	{
		return this.owner;
	}

	public void setOwnerName(String ownerName){
		this.owner = ownerName;
	}

	public synchronized int getRemainTime() {
		return this.remainTime;
	}

	public synchronized void setRemainTime(int remTime){
		this.remainTime = remTime;
	}

	public void decrementTime(){
		this.remainTime -= 1;
	}


}
