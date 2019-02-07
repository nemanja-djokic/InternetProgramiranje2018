package net.etfbl.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import net.etfbl.dao.SessionDAO;
import net.etfbl.dao.beans.Session;
import net.etfbl.util.ConnectionPool;

public class SessionDAOMySQL implements SessionDAO {

	@Override
	public ArrayList<Session> getAllActive() {
		ArrayList<Session> toReturn = new ArrayList<>();
		String query = "select session_token, id_user, valid_until from session";
		Connection connection = null;
		try {
			connection = ConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				long timestamp = resultSet.getLong(3);
				if(timestamp > new Date().getTime()) {
					Session toAdd = new Session();
					toAdd.setSessionToken(resultSet.getString(1));
					toAdd.setIdUser(resultSet.getString(2));
					toAdd.setValidUntil(resultSet.getLong(3));
					toReturn.add(toAdd);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(connection != null)
				ConnectionPool.releaseConnection(connection);
		}
		return toReturn;
	}

	@Override
	public boolean setSessions(ArrayList<Session> sessions) {
		String insertQuery = "insert into session(session_token, id_user, valid_until) values(?, ?, ?) "
				+ "on duplicate key update valid_until=?";
		Connection connection = null;
		try {
			connection = ConnectionPool.getConnection();
			boolean insertStatus = true;
			for(Session session : sessions) {
				PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
				insertStatement.setString(1, session.getSessionToken());
				insertStatement.setString(2, session.getIdUser());
				insertStatement.setLong(3, session.getValidUntil());
				insertStatement.setLong(4, session.getValidUntil());
				insertStatus = insertStatus && insertStatement.execute();
			}
			return insertStatus;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(connection != null)
				ConnectionPool.releaseConnection(connection);
		}
		return false;
	}

	@Override
	public boolean removeSession(String sessionToken) {
		String query = "delete from session where session_token=?";
		Connection connection = null;
		try {
			connection = ConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, sessionToken);
			boolean status = preparedStatement.execute();
			ConnectionPool.releaseConnection(connection);
			return status;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(connection != null)
				ConnectionPool.releaseConnection(connection);
		}
		return false;
	}

}
