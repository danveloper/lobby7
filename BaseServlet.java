import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1846833903206709985L;

	public BaseServlet() {}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		response.setStatus(HttpServletResponse.SC_OK);

		DatabaseAccess dba = new DatabaseAccess();
		try {
			String action = request.getParameter("action");
			if (action != null && action.equals("add")) {
				dba.addRecord(request.getParameter("text"));
			} else {	
				response.getWriter().println(dba.getLatestRecord());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
