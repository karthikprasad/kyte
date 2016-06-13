package server.clientserver.listeners.registeruser;

import java.util.TreeMap;
import java.util.Map.Entry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import communication.ClientConnection;
import model.RegisterMessage;
import model.RegisterMessageResponse;
import server.clientserver.ServerConfig;
import server.clientserver.ServerMain;

@Path("/")
public class RegisterClient {
	
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
	@Produces("application/json")
	public String registerNewClient() {		
		System.out.println("Inside client server register client0");
		ServerMain serverMain = ServerMain.getInstance();
		//SseEmitter newClient = new SseEmitter();
		//newClient.onCompletion(() -> {ClientConnection.setClient(null);});
		//ClientConnection.setClient(newClient);
		System.out.println("Inside client server register client1");
		RegisterMessageResponse registerResponse = (RegisterMessageResponse) serverMain.getRequestHandler().handleRequest(new RegisterMessage(ServerConfig.NODE_ADDRESS_FOR_CLIENT_UPDATES));
		System.out.println("Inside client server register client2");
		ClientConnection.setNumClients(registerResponse.getNumberOfClients());
		ClientConnection.setClientSerialNumber(registerResponse.getClientSerialNumber());
		String response = "<collabedit>" + 
							 "<message>" + 
					           "<type>" + "registration" + "</type>" + 
							   "<success>" + true + "</success>" +
							   "<clientSerialNumber>" + registerResponse.getClientSerialNumber() + "</clientSerialNumber>" +
					           "<numberOfClients>" + registerResponse.getNumberOfClients() + "</numberOfClients>" +
					         "</message>" +
							"</collabedit>";
		response = "{ \"messsage\" : \"SUCCESS\"," + 
				 "\"NUM_USERS\" : " + registerResponse.getNumberOfClients() + "," +
			     "\"USER_ORDER\" : " + registerResponse.getClientSerialNumber() + "," +
			     "\"PPSList\" : " + mapToString(serverMain.getDbConnector().getAllEntries()) + "}";
		return response;
	}	
}

