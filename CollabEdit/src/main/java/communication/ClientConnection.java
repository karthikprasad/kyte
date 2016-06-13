package communication;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class ClientConnection {
	
	private static SseEmitter client;
	private static int numClients;
	private static int clientSerialNumber;

	public static SseEmitter getClient() {
		return client;
	}
	
	public static void setClient(SseEmitter emmitter) {
		ClientConnection.client = emmitter;
	}
	
	public static int getNumClients() {
		return numClients;
	}

	public static void setNumClients(int numClients) {
		ClientConnection.numClients = numClients;
	}

	public static int getClientSerialNumber() {
		return clientSerialNumber;
	}

	public static void setClientSerialNumber(int clientSerialNumber) {
		ClientConnection.clientSerialNumber = clientSerialNumber;
	}


}
