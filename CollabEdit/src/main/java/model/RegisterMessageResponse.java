package model;

public class RegisterMessageResponse extends MessageResponse {

	int numberOfClients;
	int clientSerialNumber;
	
	public RegisterMessageResponse(int numberOfClients, int clientSerialNumber) {
		this.clientSerialNumber = clientSerialNumber;
		this.numberOfClients = numberOfClients;
	}

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
