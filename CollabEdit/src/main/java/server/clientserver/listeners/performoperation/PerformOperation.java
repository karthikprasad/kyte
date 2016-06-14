package server.clientserver.listeners.performoperation;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import com.sun.jersey.api.json.JSONWithPadding;

import model.WriteMessage;
import server.clientserver.ServerMain;

@Path("/")
public class PerformOperation {
	
	@Path("/insert/{c}/{pos}")
	@GET
	@Produces("application/json")
	public String insertCharacter(@PathParam("c") String c, @PathParam("pos") Double positionStamp/*, @QueryParam("callback") String callback*/, @Context HttpServletResponse httpResponse) {
		System.out.println("Inside Perform Operation for client 1 " + c);
		ServerMain serverMain = ServerMain.getInstance();
		serverMain.getRequestHandler().handleRequest(new WriteMessage(positionStamp, c.charAt(0)));
		
		httpResponse.addHeader("Access-Control-Allow-Origin", "*");
		httpResponse.addHeader("Access-Control-Expose-Headers", "*");
		httpResponse.addHeader("Access-Control-Allow-Credentials", "false");
  
		String response = "<collabedit>" + 
							 "<message>" + 
					           "<type>" + "insert" + "</type>" + 
							   "<success>" + true + "</success>" +
					         "</message>" +
							"</collabedit>";
		response = "{ \"messsage\" : \"SUCCESS\" }";
		//JSONWithPadding resp = new JSONWithPadding(response, callback);
		return response;
	}
 
	@Path("/delete/{pos}")
	@GET
	@Produces("application/json")
	public String deleteCharacter(@PathParam("pos") Double positionStamp/*, @QueryParam("callback") String callback*/) {
		
		ServerMain serverMain = ServerMain.getInstance();
		serverMain.getRequestHandler().handleRequest(new WriteMessage(positionStamp, null));
  
		String response = "<collabedit>" + 
							 "<message>" + 
					           "<type>" + "delete" + "</type>" + 
							   "<success>" + true + "</success>" +
					         "</message>" +
							"</collabedit>";
		response = "{ \"messsage\" : \"SUCCESS\" }";
		//JSONWithPadding resp = new JSONWithPadding(response, callback);
		return response;
	}	
}
