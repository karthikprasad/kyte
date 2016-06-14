package server.clientserver.listeners.registeruser;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

//import com.sun.jersey.api.json.JSONWithPadding;

import communication.ClientConnection;
import model.RegisterMessage;
import model.RegisterMessageResponse;
import server.clientserver.ServerConfig;
import server.clientserver.ServerMain;

@Path("/")
public class RegisterClient {
	
	private String mapToString(TreeMap<Double, Character> pps) {
		JSONObject json_obj;
		String response = "[";
		List<String> jsonObjList = new ArrayList<String>();
        boolean firstTime = true;
		for (Entry<Double, Character> entry : pps.entrySet()) {
        	json_obj=new JSONObject();
            Double key = entry.getKey();
            Character value = entry.getValue();
            try {
                json_obj.put("p", key);
                json_obj.put("c", value.toString());
                jsonObjList.add(json_obj.toString());
                if (!firstTime) {
                	response += ",";
                }
                firstTime = false;
                response += json_obj.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        response += "]";
        return response;
	}
 
	@GET
	@Produces("application/json")
	public String registerNewClient(/*@QueryParam("callback") String callback*/@Context HttpServletResponse httpResponse) {		
		ServerMain serverMain = ServerMain.getInstance();
		//SseEmitter newClient = new SseEmitter();
		//newClient.onCompletion(() -> {ClientConnection.setClient(null);});
		//ClientConnection.setClient(newClient);
		RegisterMessageResponse registerResponse = (RegisterMessageResponse) serverMain.getRequestHandler().handleRequest(new RegisterMessage(ServerConfig.NODE_ADDRESS_FOR_CLIENT_UPDATES));
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
		//JSONWithPadding resp = new JSONWithPadding(response, callback);
		httpResponse.addHeader("Access-Control-Allow-Origin", "*");
		httpResponse.addHeader("Access-Control-Expose-Headers", "*");
		httpResponse.addHeader("Access-Control-Allow-Credentials", "false");
		return response;
//		return response;
	}	
}

