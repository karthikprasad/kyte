package server;

import java.io.File;

import db.PPSDBConnector;
import model.PPSCache;

public class ServerMain {

	PPSDBConnector dbConnector;
	RequestHandler requestHandler;
	MessageProcessor messageProcessor;
	PPSCache cache;
	
	private static ServerMain instance;
	
	private ServerMain(){}
	
	private void init()
	{
		dbConnector = new PPSDBConnector(new File(ServerConfig.ENV_HOME), ServerConfig.GRP_NAME, 
				ServerConfig.NODE_NAME, ServerConfig.NODE_HOST_PORT, ServerConfig.HELPER_HOST_PORT);
		messageProcessor = new MessageProcessor(dbConnector);
		requestHandler = new RequestHandler(messageProcessor);
		cache = new PPSCache();
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
}
