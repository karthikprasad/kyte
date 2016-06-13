package server.clientserver.listeners.pollingsubscription;

import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import communication.ClientConnection;
import model.RegisterMessage;
import model.RegisterMessageResponse;
import server.clientserver.ServerConfig;
import server.clientserver.ServerMain;

@Path("/")
public class PollingSubscription {
	
	private String mapToString(TreeMap<Double, Character> pps) {
		JSONObject json_obj=new JSONObject();
        for (Entry<Double, Character> entry : pps.entrySet()) {
            Double key = entry.getKey();
            Character value = entry.getValue();
            try {
                json_obj.put(key.toString(), value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json_obj.toString();
	}
 
	@GET
	@Produces("text/event-stream")
	public SseEmitter registerNewClient(@Context HttpServletResponse httpResponse) {		
		System.out.println("Inside client server pps subscription");
		ServerMain serverMain = ServerMain.getInstance();
		SseEmitter newClient = new SseEmitter();
		//newClient.onCompletion(() -> {ClientConnection.setClient(null);});
		ClientConnection.setClient(newClient);
		ServerMain.getInstance().startDatabasePoller();
		httpResponse.addHeader("Access-Control-Allow-Origin", "*");
		httpResponse.addHeader("Access-Control-Expose-Headers", "*");
		httpResponse.addHeader("Access-Control-Allow-Credentials", "false");
		//httpResponse.setContentType("text/event-stream");	
		//encoding must be set to UTF-8
		httpResponse.setCharacterEncoding("UTF-8");
		String response = "{ \"data\" : " + mapToString(serverMain.getDbConnector().getAllEntries()) + "}";
		return newClient;
	}	
}

