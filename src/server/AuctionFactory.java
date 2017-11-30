package server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AuctionFactory<T extends IAuctionServer> {
    private static IAuctionServer server;

    public synchronized T getAuctionServer() throws RemoteException, NotBoundException {
        if (server == null) {
            Registry registry = LocateRegistry.getRegistry(45555);
            IAuctionServer server = (IAuctionServer) registry.lookup("AuctionServer");
        }
        return (T) server;
    }
}
