package communication;

import java.io.IOException;

import model.Message;
import model.MessageResponse;
import server.clientserver.ClientConnection;

public class MessageSender {

	public MessageResponse sendMessageToMasterServer(Message m) {
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
