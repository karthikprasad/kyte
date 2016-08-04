package model;

public class ClientUpdateMessage implements Message {
	
	final Integer numClients;
	final Integer clientSerialNubmer;

	public ClientUpdateMessage(Integer numClients, Integer clientSerialNubmer) {
		this.numClients = numClients;
		this.clientSerialNubmer = clientSerialNubmer;
	}

	public Integer getClientSerialNumber() {
		return clientSerialNubmer;
	}

	public Integer getNumClients() {
		return numClients;
	}

	public Class<? extends Message> getMessageType() {
		return this.getClass();
	}

	public Integer getMessageID() {
		// TODO Auto-generated method stub
		return null;
	}

}