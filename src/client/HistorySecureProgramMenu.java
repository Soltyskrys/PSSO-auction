package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.InputMismatchException;
import java.util.Scanner;

import server.IHistorySecureLoggableAuctionServer;

public class HistorySecureProgramMenu<T extends IHistorySecureLoggableAuctionServer> extends SecureProgramMenu<T> {

	public HistorySecureProgramMenu(T server, Client a, String username) {
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
        System.out.println("7. Get your costs summary");
        int i;
        try {
        	i= reader.nextInt();
        } catch (InputMismatchException ex) {
        	i = 0;
        }
        return i;
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
			} case 7: {
				performGetUserCostsSummary();
				break;
			}default: {
	        	System.out.println("Invalid option selected. Please try again");
	        }
        }
    }
	
	protected void performGetUserCostsSummary(){
        try {
           double costs = server.getUserCosts(username);
           System.out.println("Your costs are: "+costs);
        } catch (RemoteException e) {
        	System.out.println(e.getMessage());
        }
    }
	
	public static void main(String args[]) throws RemoteException, NotBoundException {
		if (System.getSecurityManager() == null) System.setSecurityManager(new SecurityManager());
		Registry registry = LocateRegistry.getRegistry(45555);
		IHistorySecureLoggableAuctionServer server = (IHistorySecureLoggableAuctionServer) registry.lookup("AuctionServer");

		Scanner reader = new Scanner(System.in);
		System.out.println("Please provide your name");
		String username = reader.next();

		Client client = new Client(username);
		UnicastRemoteObject.exportObject(client, 0);

		(new Thread(new HistorySecureProgramMenu<IHistorySecureLoggableAuctionServer>(server, client, username))).start();

	}

}
