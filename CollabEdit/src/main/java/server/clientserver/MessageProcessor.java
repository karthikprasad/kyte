package server.clientserver;

import db.PPSDBConnector;
import model.Message;
import model.MessageResponse;
import model.RegisterMessage;
import model.RegisterMessageResponse;

public class MessageProcessor {

	private PPSDBConnector dbConnector;

	public MessageProcessor(PPSDBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}

	public MessageResponse processMessage(Message m)
	{
		if(m.getMessageType().equals(RegisterMessage.class))
		{
			RegisterMessageResponse response = (RegisterMessageResponse) ServerMain.getInstance()
					.getMessageSender().sendMessageToMasterServer(m);
			ClientCounter clientCounter = new ClientCounter(response.getClientSerialNumber(), response.getNumberOfClients());
			ServerMain.getInstance().setClientCounter(clientCounter);
			ServerMain.getInstance().startDatabasePoller();
			return response;
		}
		return null;
	}
	
}
