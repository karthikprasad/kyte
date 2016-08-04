package model;

public class RegisterMessage implements Message {
	
	private String clientAddress;
	
	public RegisterMessage(String clientAddress) {
		this.setClientAddress(clientAddress);		
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public Class<? extends Message> getMessageType() {
		return this.getClass();
	}

	public Integer getMessageID() {
		// TODO Auto-generated method stub
		return null;
	}

}
