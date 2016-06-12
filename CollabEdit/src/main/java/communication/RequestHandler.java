package communication;

import model.Message;
import model.MessageResponse;
import server.clientserver.MessageProcessor;

public class RequestHandler {

	private MessageProcessor messageProcessor;

	public RequestHandler(MessageProcessor messageProcessor) {
		this.messageProcessor = messageProcessor;
	}

	public MessageResponse handleRequest(Message m)
	{
		return messageProcessor.processMessage(m);
	}
	
}
