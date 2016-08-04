package temp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import server.clientserver.ServerMain;

/**
 * Servlet implementation class ServerStartup
 */
@WebServlet("/ServerStartup")
public class ServerStartup extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServerStartup() {}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		ServerMain serverMain = ServerMain.getInstance();
	}

}
