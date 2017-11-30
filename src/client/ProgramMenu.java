package client;

import server.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class ProgramMenu<T extends IAuctionServer> implements Runnable{

    Scanner reader;
    T server;
    Client aucListener;
    String username;

    public ProgramMenu(T server, Client a, String username){
        this.username = username;
        reader = new Scanner(System.in);
        reader.useLocale(Locale.US);
        this.server = server;
        this.aucListener = a;
    }

    public int showMenu(){

        System.out.println("1. Place item on bid");
        System.out.println("2. Bid on item");
        System.out.println("3. Register listener");
        System.out.println("4. Show items");
        System.out.println("5. Select strategy");

        int i;
        try {
        	i= reader.nextInt();
        } catch (InputMismatchException ex) {
        	i = 0;
        }
        return i;

    }

    public void startMenu(){
        int n = this.showMenu();

        switch (n) {
            case 1: {
                this.performPlaceItemForBid();
                break;
            }
            case 2: {
                this.performBidOnItem();
                break;
            }
            case 3: {
                this.performRegisterListener();
                break;
            }
            case 4: {
                this.performGetItems();
                break;
            }
            case 5: {
                this.performStrategySelection();
            }
            default: {
                System.out.println("Invalid option selected. Please try again");
            }
        }
    }
    protected void performStrategySelection() {
        System.out.println("1. Maximum Bid strategy");
        System.out.println("2. Last minute strategy");
        int strategyNumber = reader.nextInt();
        Strategy s;
        System.out.println("Insert item name");
        String itemName = reader.next();
        if (strategyNumber == 1) {
            System.out.println("Insert max bid");
            int maxBid = reader.nextInt();

            s = new MaximumBidStrategy(server);
            ((MaximumBidStrategy) s).setMaxBid(maxBid);
        } else if (strategyNumber == 2) {
            Item item = getItemBasedOnName(itemName);

            s = new LastMinuteBidStrategy(server, item, username);

        } else {
            System.out.println("Wrong number! Please select again");
            return;
        }
        try {
            server.registerListener(aucListener, itemName);
            aucListener.setStrategy(s);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    protected void performPlaceItemForBid(){

        System.out.println("Insert item name");
        String itemName = reader.next();

        System.out.println("Insert item description");
        String itemDescr = reader.next();

        System.out.println("Insert item startMenu bid");
        double startBid = reader.nextDouble();

        System.out.println("Insert item auction time");
        int auctionTime = reader.nextInt();
        

        try {
            server.placeItemForBid(username,
                    itemName,
                    itemDescr,
                    startBid,
                    auctionTime);
        } catch (RemoteException e) {
        	System.out.println(e.getMessage());
        }

    }

    protected void performBidOnItem(){

        System.out.println("Insert item name");
        String itemName = reader.next();

        System.out.println("Insert new bid");
        double bid = reader.nextDouble();

        try {
            server.bidOnItem(username, itemName, bid);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void performRegisterListener(){
        System.out.println("Insert item name");
        String itemName = reader.next();

        try {
            server.registerListener(this.aucListener, itemName);
        } catch (RemoteException e) {
        	System.out.println(e.getMessage());
        }
    }

    protected void performGetItems(){
        try {
            Item[] items = server.getItems();

            for(int i=0;i<items.length;i++){
                System.out.println(items[i].getItemName());
            }

        } catch (RemoteException e) {
        	System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        while(true){
            this.startMenu();
        }
    }

    protected Item getItemBasedOnName(String n){
        try {
            Item[] items = server.getItems();

            for(int i=0;i<items.length;i++){
                if(items[i].getItemName().equals(n)){
                    return items[i];
                }
            }

        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

	public static void main(String args[]) throws RemoteException, NotBoundException {
		if (System.getSecurityManager() == null) System.setSecurityManager(new SecurityManager());
		
		Registry registry = LocateRegistry.getRegistry(4555);

		Scanner reader = new Scanner(System.in);
		System.out.println("Please provide your name");
		String username = reader.next();

		IAuctionServer server = (IAuctionServer) registry.lookup("AuctionServer");
		Client client = new Client(username);
		UnicastRemoteObject.exportObject(client, 0);

		(new Thread(new ProgramMenu<IAuctionServer>(server, client, username))).start();
	}
}
