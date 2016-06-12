package server.clientserver;

public class ClientCounter {
	
	private final int clientSerialNumber;
	private int numberOfClients;
	
	public ClientCounter(int clientSerialNumber, int numberOfClients) {
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
}
