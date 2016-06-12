package model;

public class MessageResponse {
	
	private boolean success;
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Class<? extends MessageResponse> getResponseType()
	{
		return this.getClass();
	}
	
	
}
