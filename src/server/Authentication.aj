package server;

import java.io.Console;
import java.util.Map;
import java.util.Set;
import client.ProgramMenu;

public aspect Authentication {
	
	protected Map<String, String> userPasswords;
	protected Set<String> authenticatedUsers;
	

	pointcut requiredAuthorization() : call(void IAuctionServer.placeItemForBid(..)) || call(void IAuctionServer.bidOnItem(..)) && this(ProgramMenu);


	before(): requiredAuthorization(){
		
	}
	
}

