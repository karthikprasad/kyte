package server.masterserver;

import java.io.UnsupportedEncodingException;

import db.PPSDBConnector;
import model.Message;
import model.MessageResponse;
import model.RegisterMessage;
import model.RegisterMessageResponse;
import model.WriteMessage;
import server.clientserver.MessageProcessor;

public class MasterMessageProcessor extends MessageProcessor {

	PPSDBConnector dbConnector;
	
	public MasterMessageProcessor(PPSDBConnector dbConnector) {
		super(dbConnector);
		this.dbConnector = dbConnector;
	}

	public MessageResponse processMessage(Message m)
	{
		if(m.getMessageType().equals(RegisterMessage.class))
		{
			int clientCount = ClientCountTracker.getInstance().incrementAndGetClientCount();
			RegisterMessageResponse response = new RegisterMessageResponse();
			response.setClientSerialNumber(clientCount);
			response.setNumberOfClients(clientCount);
			//TODO set up connection set and then send clientCount to clientServer, and an updated count to all clientservers
		}
		if(m.getMessageType().equals(WriteMessage.class))
		{
			try {
				dbConnector.write(((WriteMessage) m).getKey(), ((WriteMessage) m).getValue());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			MessageResponse response = new MessageResponse();
			response.setSuccess(true);
			return response;
		}
		return null;
	}
	
}
