package com.amex.dbtests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBHelper {

	private static ResourceBundle resourceBundle;
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		resourceBundle=ResourceBundle.getBundle("googlesearch");
		String url=resourceBundle.getString("url");
		String userName=resourceBundle.getString("username");
		String password=resourceBundle.getString("password");
		String driver=resourceBundle.getString("driver");
		
		return DriverManager.getConnection(url,userName,password);
		
	}
	
}
