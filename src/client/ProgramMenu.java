package client;

import server.IAuctionListener;
import server.IAuctionServer;
import server.Item;

import java.rmi.RemoteException;
import java.util.Scanner;

public class ProgramMenu implements Runnable{

    Scanner reader;
    IAuctionServer server;
    IAuctionListener aucListener;

    public ProgramMenu(IAuctionServer server, IAuctionListener a){
        reader = new Scanner(System.in);
        this.server = server;
        this.aucListener = a;
    }

    public int showMenu(){
        System.out.println("1. Place item on bid");
        System.out.println("2. Bid on item");
        System.out.println("3. Register listener");
        System.out.println("4. Show items");
        int i = reader.nextInt();

        return i;

    }

    public void startMenu(){
        int n = this.showMenu();
        if(n==1){
            this.performPlaceItemForBid();
        }
        else if(n==2){
            this.performBidOnItem();
        }
        else if(n==3){
            this.performRegisterListener();
        }
        else if(n==4){
            this.performGetItems();
        }
        else{
            System.out.println("Invalid option selected. Please try again");
        }

    }

    private void performPlaceItemForBid(){
        System.out.println("Insert owner name");
        String ownerName = reader.next();

        System.out.println("Insert item name");
        String itemName = reader.next();

        System.out.println("Insert item description");
        String itemDescr = reader.next();

        System.out.println("Insert item startMenu bid");
        double startBid = reader.nextDouble();

        System.out.println("Insert item auction time");
        int auctionTime = reader.nextInt();


        try {
            server.placeItemForBid(ownerName,
                    itemName,
                    itemDescr,
                    startBid,
                    auctionTime);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private void performBidOnItem(){
        System.out.println("Insert bidder name");
        String bidderName = reader.next();

        System.out.println("Insert item name");
        String itemName = reader.next();

        System.out.println("Insert new bid");
        double bid = reader.nextDouble();

        try {
            server.bidOnItem(bidderName, itemName, bid);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void performRegisterListener(){
        System.out.println("Insert item name");
        String itemName = reader.next();

        try {
            server.registerListener(this.aucListener, itemName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void performGetItems(){
        try {
            Item[] items = server.getItems();

            for(int i=0;i<items.length;i++){
                System.out.println(items[i].getItemName());
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true){
            this.startMenu();
        }
    }
}
