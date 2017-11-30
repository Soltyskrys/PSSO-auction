package client;

import java.rmi.RemoteException;

public aspect ClientHistory {
	after(ProgramMenu a) : call(void ProgramMenu.performGetItems(..)) && this(a){
        try {
            double costs = a.server.getUserCostsAspect(a.username);
            System.out.println("Your costs are: "+costs);
         } catch (RemoteException e) {
         	System.out.println(e.getMessage());
         }
	}
}
