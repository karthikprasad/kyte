package server.masterserver;

import java.io.File;

import communication.MessageSender;
import communication.RequestHandler;
import db.PPSDBConnector;
import model.PPSCache;
import server.clientserver.MessageProcessor;
import server.clientserver.ServerConfig;

public class MasterServerMain {

	PPSDBConnector dbConnector;
	RequestHandler requestHandler;
	MessageProcessor messageProcessor;
	PPSCache cache;
	MessageSender sender;
	
private static MasterServerMain instance;
	
	private MasterServerMain(){}
	
	private void init()
	{
		dbConnector = new PPSDBConnector(new File(ServerConfig.MASTER_ENV_HOME), ServerConfig.MASTER_GRP_NAME, 
				ServerConfig.MASTER_NODE_NAME, ServerConfig.MASTER_NODE_HOST_PORT, ServerConfig.HELPER_HOST_PORT);
		messageProcessor = new MasterMessageProcessor(dbConnector);
		requestHandler = new RequestHandler(messageProcessor);
		cache = new PPSCache();
		sender = new MessageSender(); 
	}

	public static MasterServerMain getInstance()
	{
		if(instance == null)
			instance = new MasterServerMain();
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

	public void startDatabasePoller() {
		// TODO Auto-generated method stub
		
	}
}

