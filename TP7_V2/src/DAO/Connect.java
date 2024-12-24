package DAO;
import java.sql.*;
public class Connect {	
	private static final String url="jdbc:mysql://localhost:3306/tp7";
	private static final String User="root";
	static Connection conn=null;
	
	
	public Connection getConnect() {
		
		try {
			conn=DriverManager.getConnection(url,User,"");
			}catch(SQLException  e) {
			  System.err.println("Erreur : " + e); 
		}
		
		return conn;
	}
} 
