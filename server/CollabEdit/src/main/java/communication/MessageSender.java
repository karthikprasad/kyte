package communication;

import java.io.IOException;
import java.net.URLEncoder;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import model.Message;
import model.MessageResponse;
import model.RegisterMessage;
import model.RegisterMessageResponse;
import model.WriteMessage;
import server.clientserver.ServerConfig;

public class MessageSender {

	public MessageResponse sendMessageToMasterServer(Message m) {
		if(m.getMessageType().equals(RegisterMessage.class)) {
			RegisterMessage rm = (RegisterMessage) m;
			Client client = Client.create();
	
			WebResource webResource = client
			   .resource("http://localhost:8080/CollabEdit.MasterServer/RegisterClient/" + rm.getClientAddress());
	
			ClientResponse response = webResource.accept("application/json")
	                   .get(ClientResponse.class);
	
			if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
	
			String output = response.getEntity(String.class);
			
			JSONObject jsonOutput = new JSONObject(new JSONTokener(output));
			Integer clientSerialNumber = (Integer) jsonOutput.get("USER_ORDER");
			Integer numberOfClients = (Integer) jsonOutput.get("NUM_USERS");
			
			return new RegisterMessageResponse(numberOfClients, clientSerialNumber);
		}
		if(m.getMessageType().equals(WriteMessage.class)) {
			Client client = Client.create();
			WriteMessage wm = (WriteMessage) m;
			WebResource webResource = client
			   .resource("http://localhost:8080/CollabEdit.MasterServer/PerformOperation/insert/" + URLEncoder.encode(wm.getValue().toString()) + "/" + wm.getKey());
	
			ClientResponse response = webResource.accept("application/json")
	                   .get(ClientResponse.class);
	
			if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
			return null;
		}
		return null;
	}

	public MessageResponse sendMessageToClientServer(int clientServerID, Message m) {
		return null;
	}

	public void sendMessageToClient(Message m) {
		try {
			ClientConnection.getClient().send(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
