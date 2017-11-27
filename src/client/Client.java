package client;

import server.AuctionListener;
import server.IAuctionListener;
import server.IAuctionServer;
import server.Item;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Client {

	public Client() {

	}


	public void startNegotiation(Strategy strategy, Item item) {
		
	}

	public static void main(String args[]) throws RemoteException, NotBoundException {
		if (System.getSecurityManager() == null) System.setSecurityManager(new SecurityManager());
		Registry registry = LocateRegistry.getRegistry(args[0]);
		IAuctionServer server = (IAuctionServer) registry.lookup("AuctionServer");
		IAuctionListener aucListener = (IAuctionListener) UnicastRemoteObject.exportObject(new AuctionListener(), 0);

		(new Thread(new ProgramMenu(server, aucListener))).start();

	}


}
