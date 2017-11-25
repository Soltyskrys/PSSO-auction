package server;

import java.util.ArrayList;
import java.util.List;

public class ObservableItem extends Item{
	private List<IAuctionListener> listeners;
	
	public ObservableItem(String ownerName, String itemName, String itemDesc, double startBid, int auctionTime) {
		super(ownerName, itemName, itemDesc, startBid, auctionTime);
		listeners = new ArrayList<>();
	}
	protected void notifyClients() {
	}
	
	public void registerListener(IAuctionListener listener) {
		listeners.add(listener);
	}
}
