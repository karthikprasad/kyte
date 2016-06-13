package model;

public class WriteMessage implements Message {
	
	final Double key;
	final Character value;

	public WriteMessage(Double key, Character value) {
		this.key = key;
		this.value = value;
	}

	public Double getKey() {
		return key;
	}

	public Character getValue() {
		return value;
	}

	public Class<? extends Message> getMessageType() {
		return this.getClass();
	}

	public Integer getMessageID() {
		// TODO Auto-generated method stub
		return null;
	}

}
