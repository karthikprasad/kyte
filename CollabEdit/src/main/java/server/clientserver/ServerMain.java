package server.clientserver;

import java.io.File;

import communication.MessageSender;
import communication.RequestHandler;
import db.PPSDBConnector;
import db.ScheduledDatabasePoller;
import model.PPSCache;

public class ServerMain {

	PPSDBConnector dbConnector;
	RequestHandler requestHandler;
	MessageProcessor messageProcessor;
	PPSCache cache;
	MessageSender sender;
	ClientCounter clientCounter;
	

	private static ServerMain instance;
	
	private ServerMain(){}
	
	private void init()
	{
		dbConnector = new PPSDBConnector(new File(ServerConfig.ENV_HOME), ServerConfig.GRP_NAME, 
				ServerConfig.NODE_NAME, ServerConfig.NODE_HOST_PORT, ServerConfig.HELPER_HOST_PORT);
		messageProcessor = new MessageProcessor(dbConnector);
		requestHandler = new RequestHandler(messageProcessor);
		cache = new PPSCache();
		sender = new MessageSender(); 
	}

	public static ServerMain getInstance()
	{
		if(instance == null)
			instance = new ServerMain();
		instance.init();
		return instance;
	}
	
	public PPSDBConnector getDbConnector() {
		return dbConnector;
	}


	public RequestHandler getRequestHandler() {
		return requestHandler;
	}


	public MessageProcessor getMessageProcessor() {
		return messageProcessor;
	}


	public PPSCache getCache() {
		return cache;
	}
	
	public MessageSender getMessageSender(){
		return sender;
	}
	public ClientCounter getClientCounter() {
		return clientCounter;
	}
	
	public void setClientCounter(ClientCounter clientCounter) {
		this.clientCounter = clientCounter;
	}

	public void startDatabasePoller() {
		ScheduledDatabasePoller poller = new ScheduledDatabasePoller();
		Thread t = new Thread(poller);
		t.start();
	}
}
