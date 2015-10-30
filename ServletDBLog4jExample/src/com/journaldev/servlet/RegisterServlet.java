package com.journaldev.servlet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet(name = "Register", urlPatterns = { "/Register" })
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(RegisterServlet.class);

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bookid = request.getParameter("bookid");
		String dept = request.getParameter("dept");
		String year = request.getParameter("year");
		String authour = request.getParameter("authour");
		String bookname = request.getParameter("noofbooks");
		String errorMsg = null;
		if (bookid == null || bookid.equals("")) {
			errorMsg = "Email ID can't be null or empty.";
		}
		if (authour == null || authour.equals("")) {
			errorMsg = "authour can't be null or empty.";
		}
		if (bookname == null || bookname.equals("")) {
			errorMsg = "no_of_books can't be null or empty.";
		}

		if (errorMsg != null) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/register.html");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>" + errorMsg + "</font>");
			rd.include(request, response);
		} else {

			Connection con = (Connection) getServletContext().getAttribute(
					"DBConnection");
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement("insert into librarys(bookid,dept,year,authour,bookname) values (?,?,?,?,?)");
				ps.setString(1, bookid);
				ps.setString(2, dept);
				ps.setString(3, year);
				ps.setString(4, authour);
				ps.setString(5, bookname);
				ps.execute();

				logger.info("User registered with email= ADMIN ");

				// forward to login page to login
				// RequestDispatcher rd =
				// getServletContext().getRequestDispatcher("/login.html");
				RequestDispatcher rd = getServletContext()
						.getRequestDispatcher("/register.html");
				PrintWriter out = response.getWriter();
				out.println("<font color=green>Registration successful, please login below.</font>");
				rd.include(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("Database connection problem");
				throw new ServletException("DB Connection problem.");
			} finally {
				try {
					ps.close();
				} catch (SQLException e) {
					logger.error("SQLException in closing PreparedStatement");
				}
			}
		}

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		String reponsetext = null;
		PrintWriter out = response.getWriter();
		Connection connection = null;
		Statement statment = null;
		ResultSet result;

		String state = request.getParameter("stateValue");
		String blood = request.getParameter("bloodGroupValue");
		String bloodgroup = "O?";

		try {
			// Connect to database retrieve user credentials

			Class.forName("com.mysql.jdbc.Driver");

			connection = DriverManager.getConnection(
					"https://husainlibrary-husainlibrary.rhcloud.com/phpmyadmin/", "adminkxiekL8", "_bH3ZKCbkfka");
			statment = connection.createStatement();
			ResultSet rs = null;
			if (state != null) {
				rs = statment
						.executeQuery("SELECT bookname FROM `librarys` WHERE authour='"
								+ state + "'");
			}
			// ResultSet rs =
			// statment.executeQuery("SELECT * FROM donarlist WHERE bloodgroup = '"+blood+
			// "' AND state='"+state+"'");
			if (rs != null) {
				rs.first();
				reponsetext = rs.getString(1);
				while (rs.next()) {
					reponsetext = reponsetext+"," + rs.getString(1);
					// rs.next();
				}
				// out.print("<td>" + rs.getString(1) + "</td></tr>");
				// rs.getClob(columnLabel)

			}

			response.setContentType("text/html");
			// ObjectOutputStream oos = new ObjectOutputStream(
			// response.getOutputStream());
			// oos.writeObject(reponsetext+"");rs.getString(1)

			out.println("        " + reponsetext
					);
			// OutputStreamWriter writer = new
			// OutputStreamWriter(response.getOutputStream());

			// writer.write(reponsetext);
			// writer.flush();
		} catch (SQLException e) {
			response.setContentType("text/html");
			ObjectOutputStream oos = new ObjectOutputStream(
					response.getOutputStream());
			oos.writeObject("Exception");
			e.printStackTrace();
			oos.flush();
			oos.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
