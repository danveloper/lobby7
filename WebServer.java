import java.io.File;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.jetty.webapp.WebAppContext;


public class WebServer {
	private final Server server = new Server(8080);
	
	public static void main(String[] args) throws Exception {
		WebServer ss = new WebServer();
		Server server = ss.server;
		
		server.setHandler(new WebAppContext("web-inf", "/static"));
		
		Context context = new Context(server, "/", Context.SESSIONS);
		context.addServlet(new ServletHolder(new BaseServlet()), "/s");
		
		if (!new File("mydb.db").exists()) {
			DatabaseAccess dba = new DatabaseAccess();
			dba.createTable();
		}
		
		server.start();
		server.join();
	}
}
