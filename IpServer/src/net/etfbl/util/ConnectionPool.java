package net.etfbl.util;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class ConnectionPool {
	private static Connection[] connections;
	private static boolean[] connectionTaken;
	private static int availableConnections;
	private static int currentConnections;
	private static final int INITIAL_CONNECTIONS = 10;
	private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/ip_projektni?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		connections = new Connection[INITIAL_CONNECTIONS];
		connectionTaken = new boolean[INITIAL_CONNECTIONS];
		availableConnections = INITIAL_CONNECTIONS;
		currentConnections = INITIAL_CONNECTIONS;
		for(int i = 0; i < INITIAL_CONNECTIONS; i++)
			try {
				connections[i] = DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public static Connection getConnection() throws SQLException {
		Connection toReturn = null;
		if(availableConnections > 0) {
			for(int i = 0; i < currentConnections; i++) {
				if(!connectionTaken[i]) {
					connectionTaken[i] = true;
					availableConnections--;
					return connections[i];
				}
			}
		}else {
			Connection[] tempConnections = new Connection[currentConnections * 2];
			boolean[] tempConnectionsTaken = new boolean[currentConnections * 2];
			for(int i = 0; i < currentConnections * 2; i++) {
				tempConnections[i] = (i < currentConnections)?connections[i]:DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
				tempConnectionsTaken[i] = (i < currentConnections)?connectionTaken[i]:false;
			}
			availableConnections = currentConnections;
			connections = tempConnections;
			connectionTaken = tempConnectionsTaken;
			connectionTaken[currentConnections] = true;
			toReturn =  connections[currentConnections];
			currentConnections *= 2;
		}
		return toReturn;
	}
	
	public static void releaseConnection(Connection connection) {
		int indexOfConnection = java.util.Arrays.asList(connections).indexOf(connection);
		connectionTaken[indexOfConnection] = false;
		availableConnections++;
	}
}
