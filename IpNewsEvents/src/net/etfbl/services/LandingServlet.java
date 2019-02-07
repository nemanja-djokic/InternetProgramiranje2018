package net.etfbl.services;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LandingServlet
 */
@WebServlet("/landing")
public class LandingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LandingServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		String nextRedirect = "main.xhtml";
		if(action == null || action.equals("")) {
			nextRedirect = "main.xhtml";
		}else if(action.equals("goto_create_news")) {
			nextRedirect = "createNews.xhtml";
		}else if(action.equals("goto_create_event")) {
			nextRedirect = "createEvent.xhtml";
		}else {
			nextRedirect = "main.xhtml";
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextRedirect);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
