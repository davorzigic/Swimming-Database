package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
	
	public static Connection DbConnector() {
		try {
			Connection conn = null;
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:Viktorija1906.sqlite");
			return conn;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO: handle exception
			System.out.println(e);
			e.printStackTrace();
		}
		return null;
	}

}
