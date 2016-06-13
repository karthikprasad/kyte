package server.masterserver.listeners.registerclient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import model.RegisterMessage;
import model.RegisterMessageResponse;
import server.masterserver.MasterServerMain;

@Path("/")
public class RegisterClient {
 
	@Path("/{s}")
	@GET
	@Produces("application/json")
	public String registerNewClient(@PathParam("s") String clientAdress) {		
		System.out.println("Inside Master server register client");
		MasterServerMain serverMain = MasterServerMain.getInstance();
		RegisterMessageResponse registerResponse = (RegisterMessageResponse) serverMain.getRequestHandler().handleRequest(new RegisterMessage(clientAdress));
//		ClientConnection.setNumClients(registerResponse.getNumberOfClients());
//		ClientConnection.setClientSerialNumber(registerResponse.getClientSerialNumber());
		String response = "<collabedit>" + 
							 "<message>" + 
					           "<type>" + "registration" + "</type>" + 
							   "<success>" + true + "</success>" +
							   "<clientSerialNumber>" + registerResponse.getClientSerialNumber() + "</clientSerialNumber>" +
					           "<numberOfClients>" + registerResponse.getNumberOfClients() + "</numberOfClients>" +
					         "</message>" +
							"</collabedit>";
		response = "{ \"messsage\" : \"SUCCESS\"," + 
					 "\"NUM_USERS\" : " + registerResponse.getNumberOfClients() + "," +
				     "\"USER_ORDER\" : " + registerResponse.getClientSerialNumber() + "}";
		return response;
	}	
}

