package com.journaldev.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.journaldev.util.User;

@WebServlet(name = "Login", urlPatterns = { "/Login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(LoginServlet.class);

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bookid = request.getParameter("email");
		String authour = request.getParameter("password");
		String errorMsg = null;
		if (bookid == null || bookid.equals("")) {
			errorMsg = "User Email can't be null or empty";
		}
		if (authour == null || authour.equals("")) {
			errorMsg = "Password can't be null or empty";
		}
		

		if (errorMsg != null) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/login.html");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>" + errorMsg + "</font>");
			rd.include(request, response);
		} else {

//			Connection con = (Connection) getServletContext().getAttribute(
//					"DBConnection");
//			PreparedStatement ps = null;
	//	ResultSet rs = null;
			try {
				// ps =
				// con.prepareStatement("select bookid, dept, year,authour ,noofbooks from Users where bookid=? and authour=? limit 1");
				// ps.setString(1, bookid);
				// ps.setString(2, authour);
				// rs = ps.executeQuery();

			//	if (rs != null) {
					if (bookid.equalsIgnoreCase("admin")
							&& authour.equalsIgnoreCase("admin")) {
					//	rs.next();
						String user = null;
						// User user = new User(rs.getString("bookid"),
						// rs.getString("dept"), rs.getString("year"),
						// rs.getString("noofbooks"));
						// logger.info("User found with details=" + user);
				//		HttpSession session ;= request.getSession();
				//		session.setAttribute("User", user);
						response.sendRedirect("register.html");
				//	}

				} else {
					RequestDispatcher rd = getServletContext()
							.getRequestDispatcher("/login.html");
					PrintWriter out = response.getWriter();
					logger.error("User not found with email=" + authour);

					out.println("<font color=red>No user found with given email id, please register first.</font>");
					rd.include(request, response);
				}
			} finally {

			}

		}
	}

}
