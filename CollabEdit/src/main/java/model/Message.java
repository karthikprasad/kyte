package model;

public interface Message {
	public Class<? extends Message> getMessageType();
	public Integer getMessageID();
	public Double getKey();
}
