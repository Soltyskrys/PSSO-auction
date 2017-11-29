package server;
import java.io.Serializable;

import server.IAuctionListener;
import server.Item;

public interface Strategy extends Serializable{
	public void update(Item item);
	public void setNewBidder(String newBidder);

}
