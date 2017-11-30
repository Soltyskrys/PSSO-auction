package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SecureLoggableAuctionServer extends LoggableAuctionServer implements ISecureLoggableAuctionServer{

	
	protected Map<String, String> userPasswords;
	protected Set<String> authenticatedUsers;
	
	public SecureLoggableAuctionServer() throws RemoteException {
		super();
		userPasswords = new HashMap<>();
		userPasswords.put("adam", "aaa");
		userPasswords.put("basia", "bbb");
		userPasswords.put("zosia", "zzz");
		authenticatedUsers = new HashSet<>();
		authenticatedUsers.add("Ala");
	}
	
	public void authenticateUser(String username, String password) throws RemoteException {
		String userPassword = userPasswords.get(username);
		if (userPassword != null && userPassword.equals(password)) {
			authenticatedUsers.add(username);
			System.out.println("User "+username+" has been successfully authenticated");
		} else {
			throw new RemoteException("Authentication failed - try again");
		}
	}
	
	@Override
	public void placeItemForBid(String ownerName, String itemName, String itemDesc, double startBid, int auctionTime)
			throws RemoteException {
			if (authenticatedUsers.contains(ownerName)) {
				super.placeItemForBid(ownerName, itemName, itemDesc, startBid, auctionTime);
			}
			else throw new RemoteException("You must authenticate first to place item for a bid!");
	}

	@Override
	public void bidOnItem(String bidderName, String itemName, double bid) throws RemoteException {
		if (authenticatedUsers.contains(bidderName)) {
			super.bidOnItem(bidderName, itemName, bid);
		}
		else throw new RemoteException("You must authenticate first to bid on item!");
	}
	

	public static void main(String[] args) {
		if (System.getSecurityManager() == null) System.setSecurityManager(new SecurityManager());
		try {
			String name = "AuctionServer";
			SecureLoggableAuctionServer engine = new SecureLoggableAuctionServer();
			Remote stub = UnicastRemoteObject.exportObject(engine, 0);
			engine.placeItemForBid("Ala", "Rower", "Super rower", 1000.00, 3000);

			LocateRegistry.createRegistry(45555);
			Registry registry = LocateRegistry.getRegistry(45555);
			registry.rebind(name, stub);
			System.out.println("Server program bound");
			makeProgress(engine);
		} catch (Exception e) {
			System.err.println("Exc:");
			e.printStackTrace();
		}
	}

}
