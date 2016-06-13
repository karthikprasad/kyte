package server.masterserver;

import java.io.UnsupportedEncodingException;

import communication.ClientConnections;
import db.PPSDBConnector;
import model.Message;
import model.MessageResponse;
import model.RegisterMessage;
import model.RegisterMessageResponse;
import model.WriteMessage;

public class MasterMessageProcessor {

	PPSDBConnector dbConnector;
	
	public MasterMessageProcessor(PPSDBConnector dbConnector) {
		this.dbConnector = dbConnector;
	}

	public MessageResponse processMessage(Message m)
	{
		if(m.getMessageType().equals(RegisterMessage.class))
		{
			RegisterMessage rm = (RegisterMessage) m;
			int clientCount = ClientCountTracker.getInstance().incrementAndGetClientCount();
			RegisterMessageResponse response = new RegisterMessageResponse(clientCount, clientCount);
//			response.setClientSerialNumber(clientCount);
//			response.setNumberOfClients(clientCount);
			//TODO set up connection set and then send clientCount to clientServer, and an updated count to all clientservers
			ClientConnections.sendNewClientUpdateMessage(clientCount);
			ClientConnections.addClientServer(rm.getClientAddress());
			return response;
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
