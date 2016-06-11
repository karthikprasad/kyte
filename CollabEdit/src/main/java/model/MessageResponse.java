package model;

public class MessageResponse {
	public Class<? extends MessageResponse> getResponseType()
	{
		return this.getClass();
	}
}
