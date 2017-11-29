package client;

import server.IAuctionListener;
import server.IAuctionServer;
import server.Item;

import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class ProgramMenu<T extends IAuctionServer> implements Runnable{

    Scanner reader;
    T server;
    IAuctionListener aucListener;
    String username;

    public ProgramMenu(T server, IAuctionListener a){
        reader = new Scanner(System.in);
        reader.useLocale(Locale.US);
        this.server = server;
        this.aucListener = a;
    }

    public int showMenu(){
    	if (username == null) {
    		System.out.println("Please provide your name");
    		String name = reader.next();
    		username = name;
    	} 
        System.out.println("1. Place item on bid");
        System.out.println("2. Bid on item");
        System.out.println("3. Register listener");
        System.out.println("4. Show items");
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
	        } default: {
	        	System.out.println("Invalid option selected. Please try again");
	        }
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
}
