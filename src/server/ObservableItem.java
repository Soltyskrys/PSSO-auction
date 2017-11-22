package server;

import java.util.ArrayList;
import java.util.List;

public class ObservableItem extends Item{
	private List<IAuctionListener> listeners;
	
	public ObservableItem() {
		listeners = new ArrayList<>();
	}
	protected void notifyClients() {
	}
	
	public void registerListener(IAuctionListener listener) {
		listeners.add(listener);
	}
}
