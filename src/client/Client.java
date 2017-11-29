package client;

import server.AuctionListener;
import server.AuctionServer;
import server.IAuctionListener;
import server.IAuctionServer;
import server.Item;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client<T extends IAuctionServer>{

	protected T server;

	public void startNegotiation(Strategy strategy, Item item) {
		
	}

	public static void main(String args[]) throws RemoteException, NotBoundException {
		if (System.getSecurityManager() == null) System.setSecurityManager(new SecurityManager());
		Registry registry = LocateRegistry.getRegistry(args[0]);

		Scanner reader = new Scanner(System.in);
		System.out.println("Please provide your name");
		String username = reader.next();

		IAuctionServer server = (IAuctionServer) registry.lookup("AuctionServer");
		IAuctionListener aucListener = (IAuctionListener) UnicastRemoteObject.exportObject(new AuctionListener(username), 0);

		(new Thread(new ProgramMenu<IAuctionServer>(server, aucListener, username))).start();

	}


}
