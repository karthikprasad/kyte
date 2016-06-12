package communication;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import model.RegisterMessage;
import model.RegisterMessageResponse;
import server.clientserver.ServerMain;

@Path("/RegisterClient")
public class RegisterClient {
 
	@GET
	@Produces("application/xml")
	public String registerNewClient() {		
		ServerMain serverMain = ServerMain.getInstance();
		RegisterMessageResponse registerResponse = (RegisterMessageResponse) serverMain.getRequestHandler().handleRequest(new RegisterMessage());
		String response = "<collabedit>" + 
							 "<message>" + 
					           "<type>" + "registration" + "</type>" + 
							   "<success>" + true + "</success>" +
							   "<clientSerialNumber>" + registerResponse.getClientSerialNumber() + "</clientSerialNumber>" +
					           "<numberOfClients>" + registerResponse.getNumberOfClients() + "</numberOfClients>" +
					         "</message>" +
							"</collabedit>";
		return response;
	}	
}

