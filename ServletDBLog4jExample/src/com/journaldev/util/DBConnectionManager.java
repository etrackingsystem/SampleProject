package com.journaldev.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {

	private Connection connection;
	
	public DBConnectionManager(String dbURL, String user, String pwd) throws ClassNotFoundException, SQLException{
	
		Class.forName("com.mysql.jdbc.Driver");
		this.	connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/test", "root", "");
	}
	
	public Connection getConnection(){
		return this.connection;
	}
}
