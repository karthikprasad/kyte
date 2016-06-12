package server.masterserver.listeners;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import model.RegisterMessage;
import model.RegisterMessageResponse;
import server.clientserver.ClientConnection;
import server.clientserver.ServerMain;

@Path("/RegisterClient")
public class RegisterClient {
 
	@GET
	@Produces("application/xml")
	public String registerNewClient() {		
		ServerMain serverMain = ServerMain.getInstance();
		SseEmitter newClient = new SseEmitter();
		newClient.onCompletion(() -> {ClientConnection.setClient(null);});
		ClientConnection.setClient(newClient);
		RegisterMessageResponse registerResponse = (RegisterMessageResponse) serverMain.getRequestHandler().handleRequest(new RegisterMessage());
		ClientConnection.setNumClients(registerResponse.getNumberOfClients());
		ClientConnection.setClientSerialNumber(registerResponse.getClientSerialNumber());
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

