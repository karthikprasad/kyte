package server.clientserver.listeners;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import model.WriteMessage;
import server.clientserver.ServerMain;

@Path("/PerformOperation")
public class PerformOperation {
	
	@Path("/insert/{c}/{pos}")
	@GET
	@Produces("application/json")
	public String insertCharacter(@PathParam("c") Character c, @PathParam("pos") Double positionStamp) {
		
		ServerMain serverMain = ServerMain.getInstance();
		serverMain.getRequestHandler().handleRequest(new WriteMessage(positionStamp, c));
  
		String response = "<collabedit>" + 
							 "<message>" + 
					           "<type>" + "insert" + "</type>" + 
							   "<success>" + true + "</success>" +
					         "</message>" +
							"</collabedit>";
		response = "{ 'messsage' : 'SUCCESS' }";
		return response;
	}
 
	@Path("/delete/{pos}")
	@GET
	@Produces("application/json")
	public String deleteCharacter(@PathParam("pos") Double positionStamp) {
		
		ServerMain serverMain = ServerMain.getInstance();
		serverMain.getRequestHandler().handleRequest(new WriteMessage(positionStamp, null));
  
		String response = "<collabedit>" + 
							 "<message>" + 
					           "<type>" + "delete" + "</type>" + 
							   "<success>" + true + "</success>" +
					         "</message>" +
							"</collabedit>";
		response = "{ 'messsage' : 'SUCCESS' }";
		return response;
	}	
}
