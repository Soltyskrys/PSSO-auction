package server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ObservableItem extends Item {
	private List<IAuctionListener> listeners;
	
	public ObservableItem(String ownerName, String itemName, String itemDesc, double startBid, int auctionTime) {
		super(ownerName, itemName, itemDesc, startBid, auctionTime);
		listeners = new ArrayList<>();
	}

	protected void notifyClients(){
		for(int i=0;i<listeners.size();i++){
			try {
				listeners.get(i).update(this);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	protected void notifyClients(String l){
		for(int i=0;i<listeners.size();i++){
			try {
				if(!listeners.get(i).getName().equals(l)) {
					listeners.get(i).update(this);
				}
				else{
					System.out.println("!!!!!!!!!!");
					System.out.println(l);
					System.out.println("!!!!!");
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setCurrentBidderName(String s){
		super.setCurrentBidderName(s);
		this.notifyClients(this.getCurrentBidderName());

	}

	public void registerListener(IAuctionListener listener) {
		listeners.add(listener);
	}

	public void decrementTime(){
		super.decrementTime();
		if(this.getRemainTime() == 0) {
			System.out.println("Notifying client about auction expiration!!!");
			this.notifyClients();
		}
	}
}
