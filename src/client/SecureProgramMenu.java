package client;

import java.rmi.RemoteException;
import java.util.InputMismatchException;

import server.AuctionServer;
import server.IAuctionListener;
import server.IAuctionServer;
import server.ISecureLoggableAuctionServer;
import server.SecureLoggableAuctionServer;

public class SecureProgramMenu <T extends ISecureLoggableAuctionServer> extends ProgramMenu<T> {

	public SecureProgramMenu(T server, IAuctionListener a, String username) {
		super(server, a, username);
	}
	
	@Override
	public int showMenu(){

        System.out.println("1. Place item on bid");
        System.out.println("2. Bid on item");
        System.out.println("3. Register listener");
        System.out.println("4. Show items");
		System.out.println("5. Select strategy");
        System.out.println("6. Authenticate");
        int i;
        try {
        	i= reader.nextInt();
        } catch (InputMismatchException ex) {
        	i = 0;
        }
        return i;
    }
	
	protected void authenticate() {
		System.out.println("Please provide password or type 'exit' to cancel");
		String password = reader.next();
		if (!password.equals("exit")) {
			try {
				server.authenticateUser(username, password);
			} catch (RemoteException e) {
				authenticate();
			}
		}
	}

	@Override
    public void startMenu(){
        int n = this.showMenu();
        switch(n) {
	        case 1:{
	        	this.performPlaceItemForBid();
	        	break;
	        } case 2:{
	        	this.performBidOnItem();
	        	break;
	        } case 3:{
	        	this.performRegisterListener();
	        	break;
	        } case 4:{
	        	this.performGetItems();
	        	break;
	        } case 5:{
	        	this.performStrategySelection();
	        	break;
	        } case 6: {
				this.authenticate();
				break;
			}default: {
	        	System.out.println("Invalid option selected. Please try again");
	        }
        }

    }

}
