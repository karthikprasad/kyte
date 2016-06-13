package communication;

import java.util.HashSet;
import java.util.Set;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ClientConnections {
	
	private static Set<String> clientServers;

	public static Set<String> getClientServers() {
		if (clientServers == null) {
			clientServers = new HashSet<String>();
		}
		return clientServers;
	}

	public static void addClientServer(String clientServerAdress) {
		if (clientServers == null) {
			clientServers = new HashSet<String>();
		}
		clientServers.add(clientServerAdress);
	}
	
	public static void removeClientServer(String clientServerAdress) {
		if (clientServers != null) {
			clientServers.remove(clientServerAdress);
		}
	}
	
	public static void sendMessageToClient(String clientAddress, int numberOfClients) {
		Client client = Client.create();
		WebResource webResource = client.resource("http://" + clientAddress + "/CollabEdit.ClientServer/NewClientUpdate/" + numberOfClients);
		webResource.accept("application/json").get(ClientResponse.class);
	}
	
	public static void sendNewClientUpdateMessage(int numberOfClients) {
		if (clientServers != null) {
			for (String clientAddress : clientServers) {
				sendMessageToClient(clientAddress, numberOfClients);
			}
		}
	}

}
