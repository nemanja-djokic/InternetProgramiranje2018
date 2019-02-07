package net.etfbl.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.etfbl.dao.UserConnectionDAO;
import net.etfbl.dao.beans.UserConnection;
import net.etfbl.dao.beans.User;
import net.etfbl.util.ConnectionPool;

public class UserConnectionDAOMySQL implements UserConnectionDAO {

	@Override
	public ArrayList<UserConnection> getAllConnections() {
		String query = "select id_user1, id_user2, is_pending from connection";
		try {
			Connection connection = ConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<UserConnection> toReturn = new ArrayList<>();
			while(resultSet.next()) {
				UserConnection userConnection = new UserConnection();
				userConnection.setIdUser1(resultSet.getString(1));
				userConnection.setIdUser2(resultSet.getString(2));
				userConnection.setIsPending(resultSet.getBoolean(3));
				toReturn.add(userConnection);
			}
			ConnectionPool.releaseConnection(connection);
			return toReturn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<UserConnection> getConnectionsForUser(User user) {
		return getConnectionsForUser(user.getIdUser());
	}

	@Override
	public ArrayList<UserConnection> getConnectionsForUser(String idUser) {
		ArrayList<UserConnection> allConnections = getAllConnections();
		ArrayList<UserConnection> toReturn = new ArrayList<>();
		for(UserConnection connection : allConnections) {
			if(connection.getIdUser1().equals(idUser) || connection.getIdUser2().equals(idUser))
				toReturn.add(connection);
		}
		return toReturn;
	}

	@Override
	public boolean addConnection(UserConnection userConnection) {
		String query = "insert into connection(id_user1, id_user2, is_pending)"
				+ " values(?,?,?)";
		try {
			Connection connection = ConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userConnection.getIdUser1());
			preparedStatement.setString(2, userConnection.getIdUser2());
			preparedStatement.setBoolean(3, userConnection.getIsPending());
			preparedStatement.execute();
			ConnectionPool.releaseConnection(connection);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean acceptConnection(String acceptingUser, String acceptedUser) {
		String query = "update connection set is_pending=? where id_user1=? and id_user2=?";
		try {
			Connection connection = ConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setBoolean(1, false);
			preparedStatement.setString(2, acceptedUser);
			preparedStatement.setString(3, acceptingUser);
			preparedStatement.execute();
			ConnectionPool.releaseConnection(connection);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean cancelRequest(String cancellingUser, String cancelledUser) {
		String query = "delete from connection where (id_user1=? and id_user2=?) or (id_user1=? and id_user2=?)";
		try {
			Connection connection = ConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, cancellingUser);
			preparedStatement.setString(2, cancelledUser);
			preparedStatement.setString(3, cancelledUser);
			preparedStatement.setString(4, cancellingUser);
			preparedStatement.execute();
			ConnectionPool.releaseConnection(connection);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
