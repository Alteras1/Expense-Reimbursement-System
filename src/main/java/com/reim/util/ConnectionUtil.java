package com.reim.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	{
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private String url = System.getProperty("aws_url");
	private String password = System.getProperty("aws_password");
	private String username = System.getProperty("aws_username");
	private static ConnectionUtil instance;
	
	private ConnectionUtil() {
	}
	
	public static ConnectionUtil getInstance() {
		if (instance == null) {
			instance = new ConnectionUtil();
		}
		return instance;
	}

	public Connection getConnection() throws SQLException {
		System.out.println(System.getProperties());
		return DriverManager.getConnection(url, username, password);
	}

}