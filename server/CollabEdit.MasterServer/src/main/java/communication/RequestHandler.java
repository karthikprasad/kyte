package communication;

import model.Message;
import model.MessageResponse;
import server.masterserver.MasterMessageProcessor;

public class RequestHandler {

	private MasterMessageProcessor messageProcessor;

	public RequestHandler(MasterMessageProcessor messageProcessor) {
		this.messageProcessor = messageProcessor;
	}

	public MessageResponse handleRequest(Message m)
	{
		return messageProcessor.processMessage(m);
	}
	
}