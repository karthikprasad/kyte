package server.clientserver.listeners.getupdatedpps;

import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
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

import com.sun.jersey.api.json.JSONWithPadding;

import org.springframework.http.MediaType;

import communication.ClientConnection;
import db.ScheduledDatabasePoller;
import model.RegisterMessage;
import model.RegisterMessageResponse;
import server.clientserver.ServerConfig;
import server.clientserver.ServerMain;

@Path("/")
public class GetUpdatedPPS {
	
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
                json_obj.put("p", key.toString());
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
	@Produces("appliction/json")
	public String getUpdatedPPSFromDB(@Context HttpServletResponse httpResponse/*, @QueryParam("callback") String callback*/) {		
		//ServerMain serverMain = ServerMain.getInstance();
		//SseEmitter newClient = new SseEmitter();
		//newClient.onCompletion(() -> {ClientConnection.setClient(null);});
		//ClientConnection.setClient(newClient);
		//ServerMain.getInstance().startDatabasePoller();
		httpResponse.addHeader("Access-Control-Allow-Origin", "*");
		httpResponse.addHeader("Access-Control-Expose-Headers", "*");
		httpResponse.addHeader("Access-Control-Allow-Credentials", "false");
		//httpResponse.setContentType("text/event-stream");	
		//encoding must be set to UTF-8
		httpResponse.setCharacterEncoding("UTF-8");
		String response = "{ \"messsage\" : \"SUCCESS\"," + 
				 "\"NUM_USERS\" : " + ClientConnection.getNumClients() + "," +
			     "\"USER_ORDER\" : " + ClientConnection.getClientSerialNumber() + "," +
			     "\"PPSList\" : " + mapToString(ScheduledDatabasePoller.poll()) + "}";
		
		//JSONWithPadding resp = new JSONWithPadding("{ \"data\" : " + mapToString(ScheduledDatabasePoller.poll()) + "}", callback);
		return response;
	}	
}

