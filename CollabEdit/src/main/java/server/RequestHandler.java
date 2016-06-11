package server;

import model.Message;
import model.MesssageResponse;

public class RequestHandler {

	private MessageProcessor messageProcessor;

	public RequestHandler(MessageProcessor messageProcessor) {
		this.messageProcessor = messageProcessor;
	}

	public MesssageResponse handleRequest(Message m)
	{
		return null;
	}
	
}
