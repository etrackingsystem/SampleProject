package com.journaldev.servlet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MobileRequest", urlPatterns = { "/MobileRequest" })
public class MobileRequest extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");

		//PrintWriter out = response.getWriter();
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
					"jdbc:mysql://localhost:3306/test", "root", "");
			statment = connection.createStatement();

			ResultSet rs = statment
					.executeQuery("SELECT name FROM `donarlist` WHERE bloodgroup= 'O?'");
			// ResultSet rs =
			// statment.executeQuery("SELECT * FROM donarlist WHERE bloodgroup = '"+blood+
			// "' AND state='"+state+"'");
			while (rs.next()) {
			//	out.print("<td>" + rs.getString(1) + "</td></tr>");
				rs.getString(1);

			}
			PrintWriter out = response.getWriter();
			out.println("Hello Android !!!!");
			response.setContentType("text/html");
			ObjectOutputStream oos = new ObjectOutputStream(
					response.getOutputStream());
			oos.writeObject("Success");
			

		} catch (SQLException e) {
			response.setContentType("text/html");
			ObjectOutputStream oos = new ObjectOutputStream(
					response.getOutputStream());
			oos.writeObject("Exception");
			e.printStackTrace();
			oos.flush();
			oos.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		}
	}

}
