package model;

public class ReadMessage implements Message{
	final Double key;

	public ReadMessage(Double key) {
		this.key = key;
	}

	public Double getKey() {
		return key;
	}

	public Class<? extends Message> getMessageType() {
		return this.getClass();
	}

	public Integer getMessageID() {
		// TODO Auto-generated method stub
		return null;
	}
}
