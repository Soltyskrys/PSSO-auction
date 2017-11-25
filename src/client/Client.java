package client;

import server.IAuctionServer;
import server.Item;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

	public Client() {

	}


	public void startNegotiation(Strategy strategy, Item item) {
		
	}

	public static void main(String args[]) throws RemoteException, NotBoundException {
		if (System.getSecurityManager() == null) System.setSecurityManager(new SecurityManager());
		Registry registry = LocateRegistry.getRegistry(args[0]);
		IAuctionServer server = (IAuctionServer) registry.lookup("AuctionServer");

		try {
			server.placeItemForBid("Ala",
					"Produkt",
					"Opis produktu",
					100.00,
					10000);
		} catch (RemoteException ex) {
			System.out.println("Something went wrong");
			System.out.println(ex.getMessage());
		}
	}


}
