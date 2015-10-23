package com.journaldev.servlet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sun.org.apache.xerces.internal.util.Status;

@WebServlet(name = "BookOrder", urlPatterns = { "/BookOrder" })
public class BookOrder extends HttpServlet {
	static Logger logger = Logger.getLogger(BookOrder.class);

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String bookname = request.getParameter("bookname");
		String Stud_enroll_no = request.getParameter("Stud_enroll_no");
		String current_date = request.getParameter("current_date");
		String return_date = request.getParameter("return_date");
		String name = request.getParameter("name");
		String status = request.getParameter("status");;
		String errorMsg = null;
		if (bookname == null || bookname.equals("")) {
			errorMsg = "Email ID can't be null or empty.";
		}
		if (Stud_enroll_no == null || Stud_enroll_no.equals("")) {
			errorMsg = "authour can't be null or empty.";
		}
		if (current_date == null || current_date.equals("")) {
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
			try {// update bookstatus SET Stud_enroll_no='123', name ='sahasma'
					// ,`current_date` ='24-5-2015',`return_date`='123' WHERE
					// ''Stud_enroll_no' = '123'
		/*		ps = con.prepareStatement("update bookstatus 	SET `name`='"
						+ name + "' ,`current_date`='" + current_date
						+ "',`return_date`='" + return_date + "',`status`='"
						+ status + "',`bookname`='" + bookname
						+ "' WHERE `Stud_enroll_no` = '" + Stud_enroll_no + "'");*/

				ps = con.prepareStatement("INSERT INTO `bookstatus`(`Stud_enroll_no`, `name`, `current_date`, `return_date`, `status`, `bookname`) VALUES (?,?,?,?,?,?)");
			//	INSERT INTO `bookstatus`(`Stud_enroll_no`, `name`, `current_date`, `return_date`, `status`, `bookname`) VALUES ([value-1],[value-2],[value-3],[value-4],[value-5],[value-6])
				// "insert into librarys where(Stud_enroll_no,name,current_date,authour,bookname) values (?,?,?,?,?)");
				// UPDATE Customers SET ContactName='Alfred Schmidt',
				// City='Hamburg' WHERE CustomerName='Alfreds Futterkiste';
				ps.setString(1, Stud_enroll_no);
				ps.setString(2, name);
				ps.setString(3, current_date);
				ps.setString(4, return_date);
				ps.setString(5, status);
				ps.setString(6, bookname);
				
				ps.execute();

				// logger.info("User registered with email= ADMIN ");

				// forward to login page to login
				// RequestDispatcher rd =
				// getServletContext().getRequestDispatcher("/login.html");

				ObjectOutputStream oos = new ObjectOutputStream(
						response.getOutputStream());
				oos.writeObject("Success");
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
}
