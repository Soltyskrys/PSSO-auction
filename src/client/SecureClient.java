package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import server.AuctionListener;
import server.AuctionServer;
import server.IAuctionListener;
import server.IAuctionServer;
import server.ISecureLoggableAuctionServer;
import server.SecureLoggableAuctionServer;

public class SecureClient<T extends ISecureLoggableAuctionServer> extends Client<ISecureLoggableAuctionServer> {

	public SecureClient() {
		super();
	}
	
	public static void main(String args[]) throws RemoteException, NotBoundException {
		if (System.getSecurityManager() == null) System.setSecurityManager(new SecurityManager());
		Registry registry = LocateRegistry.getRegistry(args[0]);
		ISecureLoggableAuctionServer server = (ISecureLoggableAuctionServer) registry.lookup("AuctionServer");

		Scanner reader = new Scanner(System.in);
		System.out.println("Please provide your name");
		String username = reader.next();

		IAuctionListener aucListener = (IAuctionListener) UnicastRemoteObject.exportObject(new AuctionListener(username), 0);

		(new Thread(new SecureProgramMenu(server, aucListener, username))).start();

	}

}
