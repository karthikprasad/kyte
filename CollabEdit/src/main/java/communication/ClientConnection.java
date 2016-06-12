package communication;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class ClientConnection {
	
	private static SseEmitter client;

	public static SseEmitter getClient() {
		return client;
	}

	public static void setClient(SseEmitter emmitter) {
		ClientConnection.client = emmitter;
	}

}
