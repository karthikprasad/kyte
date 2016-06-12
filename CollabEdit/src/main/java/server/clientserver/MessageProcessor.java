package server.clientserver;

import db.PPSDBConnector;
import model.Message;
import model.MessageResponse;
import model.PPSCache;
import model.RegisterMessage;
import model.RegisterMessageResponse;
import model.WriteMessage;

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
		if(m.getMessageType().equals(WriteMessage.class))
		{
			return processWriteMessage((WriteMessage) m);
		}
		return null;
	}

	/**
	 * First put the entry in the server cache. 
	 * Then send a synchronous request to Master Server to insert into the 
	 * replicated DB.
	 * If successful, remove from cache. 
	 * @param m
	 * @return 
	 */
	private MessageResponse processWriteMessage(WriteMessage m) {
		PPSCache cache = ServerMain.getInstance().getCache();
		cache.getPps().put(m.getKey(), m.getValue());
		MessageResponse response = ServerMain.getInstance()
				.getMessageSender().sendMessageToMasterServer(m);
		if(response.isSuccess())
		{
			cache.getPps().remove(m.getKey());
		}
		//TODO: handle failures (need some kind of retry)
		return response;
	}
	
}
