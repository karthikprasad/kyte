package server.masterserver.listeners.performoperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import model.WriteMessage;
import server.masterserver.MasterServerMain;

@Path("/")
public class PerformOperation {
	
	@Path("/insert/{c}/{pos}")
	@GET
	@Produces("application/json")
	public String insertCharacter(@PathParam("c") String c, @PathParam("pos") Double positionStamp) {
		
		MasterServerMain masterServerMain = MasterServerMain.getInstance();
		masterServerMain.getRequestHandler().handleRequest(new WriteMessage(positionStamp, c.charAt(0)));
  
		String response = "<collabedit>" + 
							 "<message>" + 
					           "<type>" + "insert" + "</type>" + 
							   "<success>" + true + "</success>" +
					         "</message>" +
							"</collabedit>";
		response = "{ \"messsage\" : \"SUCCESS\" }";
		return response;
	}
 
	@Path("/delete/{pos}")
	@GET
	@Produces("application/json")
	public String deleteCharacter(@PathParam("pos") Double positionStamp) {
		
		MasterServerMain masterServerMain = MasterServerMain.getInstance();
		masterServerMain.getRequestHandler().handleRequest(new WriteMessage(positionStamp, null));
  
		String response = "<collabedit>" + 
							 "<message>" + 
					           "<type>" + "delete" + "</type>" + 
							   "<success>" + true + "</success>" +
					         "</message>" +
							"</collabedit>";
		response = "{ \"messsage\" : \"SUCCESS\" }";
		return response;
	}	
}
