package model;

public class ReigsterMessageResponse extends MessageResponse {

	int numberOfClients;
	int clientSerialNumber;

	public int getClientSerialNumber() {
		return clientSerialNumber;
	}

	public int getNumberOfClients() {
		return numberOfClients;
	}

	public void setNumberOfClients(int numberOfClients) {
		this.numberOfClients = numberOfClients;
	}

	public void setClientSerialNumber(int clientSerialNumber){
		this.clientSerialNumber = clientSerialNumber;
	}
	
}
