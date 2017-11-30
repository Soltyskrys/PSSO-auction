package server;

import java.io.Console;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.aspectj.lang.JoinPoint;

import client.Client;
import client.ProgramMenu;
import java.util.Scanner;

public aspect Authentication {
	
	private Map<String, String> AuctionServer.userPasswords;
	private Set<String> AuctionServer.authenticatedUsers;
	

	pointcut callServer(ProgramMenu menu,IAuctionServer server) : this(menu) && target(server);
	
	void around(ProgramMenu menu,IAuctionServer server,String ownerName,String itemName,String itemDesc,double startBid,int auctionTime) throws RemoteException: args(ownerName,itemName,itemDesc,startBid,auctionTime) && callServer(menu,server) && call(void IAuctionServer.placeItemForBid(..)){
		try {
			authorize(menu,server);
			server.placeItemForBid(ownerName, itemName, itemDesc, startBid, auctionTime);
		}
		catch(RemoteException e) {
			System.out.println("Bad credentials");
		}
		
	}
	
	
	void around(ProgramMenu menu,IAuctionServer server,String bidderName,String itemName,double bid) throws RemoteException: args(bidderName,itemName,bid) && callServer(menu,server) && call(void IAuctionServer.bidOnItem(..)){
		try {
			authorize(menu,server);
			server.bidOnItem(bidderName, itemName, bid);
		}
		catch(RemoteException e) {
			System.out.println("Bad credentials");
		}
	}
	
	private void authorize(ProgramMenu menu,IAuctionServer server) throws RemoteException{
		System.out.println("Please provide password ");
		String password = new Scanner(System.in).next();
		System.out.println("Please provide username ");
		String username = new Scanner(System.in).next();
		server.authenticate(username, password);
	}
	 
	public abstract void IAuctionServer.authenticate(String username,String password) throws RemoteException;
	
	
	public void AuctionServer.authenticate(String username,String password) throws RemoteException{
		if(userPasswords==null) {
			userPasswords = new HashMap<String, String>();
			authenticatedUsers = new HashSet<String>();
		}
		
		if(!userPasswords.containsKey(username))
			userPasswords.put(username,password);
		
		if(!userPasswords.get(username).equals(password))
			throw new RemoteException("Wrong password");
		
		authenticatedUsers.add(username);
	}
}

