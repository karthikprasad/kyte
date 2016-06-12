package communication;

import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import model.Message;
import model.MessageResponse;
import model.RegisterMessage;
import model.RegisterMessageResponse;
import model.WriteMessage;
import server.clientserver.ClientConnection;

public class MessageSender {

	public MessageResponse sendMessageToMasterServer(Message m) {
		if(m.getMessageType().equals(RegisterMessage.class)) {
			Client client = Client.create();
	
			WebResource webResource = client
			   .resource("http://localhost:8081/CollabEditServer/RegisterClient");
	
			ClientResponse response = webResource.accept("application/json")
	                   .get(ClientResponse.class);
	
			if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
	
			String output = response.getEntity(String.class);
			
			JSONObject jsonOutput = new JSONObject(new JSONTokener(output));
			Integer clientSerialNumber = (Integer) jsonOutput.get("clientSerialNumber");
			Integer numberOfClients = (Integer) jsonOutput.get("numberOfClients");
			
			return new RegisterMessageResponse(numberOfClients, clientSerialNumber);
		}
		if(m.getMessageType().equals(WriteMessage.class)) {
			Client client = Client.create();
			WriteMessage wm = (WriteMessage) m;
			WebResource webResource = client
			   .resource("http://localhost:8081/CollabEditServer/PerformOperation/insert/" + wm.getValue() + "/" + wm.getKey());
	
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
