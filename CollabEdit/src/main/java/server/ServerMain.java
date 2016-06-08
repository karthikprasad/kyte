package server;

import java.io.File;

import db.PPSDBConnector;

public class ServerMain {

	PPSDBConnector dbConnector;
	RequestHandler requestHandler;
	MessageProcessor messageProcessor;

	
	public void init()
	{
		dbConnector = new PPSDBConnector(new File(ServerConfig.ENV_HOME), ServerConfig.GRP_NAME, 
				ServerConfig.NODE_NAME, ServerConfig.NODE_HOST_PORT, ServerConfig.HELPER_HOST_PORT);
		messageProcessor = new MessageProcessor(dbConnector);
		requestHandler = new RequestHandler(messageProcessor);
	}
}
