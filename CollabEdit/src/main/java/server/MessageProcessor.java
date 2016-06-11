package server;

import db.PPSDBConnector;
import model.Message;
import model.MessageResponse;
import model.RegisterMessage;
import model.ReigsterMessageResponse;

public class MessageProcessor {

	private PPSDBConnector dbConnector;

	public MessageProcessor(PPSDBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}

	public MessageResponse processMessage(Message m)
	{
		if(m.getMessageType().equals(RegisterMessage.class))
		{
			ReigsterMessageResponse response = (ReigsterMessageResponse) ServerMain.getInstance()
					.getMessageSender().sendMessageToMasterServer(m);
			ClientCounter clientCounter = new ClientCounter(response.getClientSerialNumber(), response.getNumberOfClients());
			ServerMain.getInstance().setClientCounter(clientCounter);
			ServerMain.getInstance().startDatabasePoller();
			return response;
		}
		return null;
	}
	
}
