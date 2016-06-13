package server.clientserver.listeners.newclientupdate;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import communication.ClientConnection;
import model.ClientUpdateMessage;
import server.clientserver.ServerMain;

@Path("/")
public class NewClientUpdate {
 
	@Path("/{n}")
	@GET
	@Produces("application/json")
	public String updateClientsInfo(@PathParam("n") int numClients) {		
		ServerMain serverMain = ServerMain.getInstance();
		
		ClientConnection.setNumClients(numClients);
		if (numClients < ClientConnection.getClientSerialNumber()) {
			ClientConnection.setClientSerialNumber(numClients);
		}
		serverMain.getRequestHandler().handleRequest(new ClientUpdateMessage(numClients, ClientConnection.getClientSerialNumber()));
		
		// The following is not really needed
		String response = "<collabedit>" + 
							 "<message>" + 
					           "<type>" + "update" + "</type>" + 
							   "<success>" + true + "</success>" +
							   "<clientSerialNumber>" + ClientConnection.getClientSerialNumber() + "</clientSerialNumber>" +
					         "</message>" +
							"</collabedit>";
		response = "{\"message\": \"SUCCESS\"}";
		return response;
	}	
}


