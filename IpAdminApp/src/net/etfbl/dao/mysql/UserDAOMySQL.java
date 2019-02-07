package net.etfbl.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.etfbl.dao.UserDAO;
import net.etfbl.dao.beans.User;
import net.etfbl.dao.beans.UserConnection;
import net.etfbl.util.ConnectionPool;

public class UserDAOMySQL implements UserDAO{

	@Override
	public boolean addUser(User user) {
		String query = "insert into user(id_user, username, password_hash, email, first_name, last_name, course_name, course_faculty_name, description, current_year)"
				+ " values(?,?,?,?,?,?,?,?,?,?)";
		try {
			Connection connection = ConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user.getIdUser());
			preparedStatement.setString(2, user.getUsername());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getFirstName());
			preparedStatement.setString(6, user.getLastName());
			preparedStatement.setString(7, user.getCourseName());
			preparedStatement.setString(8, user.getCourseFacultyName());
			preparedStatement.setString(9, user.getDescription());
			preparedStatement.setInt(10, user.getCurrentYear());
			preparedStatement.execute();
			ConnectionPool.releaseConnection(connection);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ArrayList<User> getAllUsers() {
		String query = "select id_user, username, password_hash, email, first_name, last_name, course_name, course_faculty_name, description, current_year from user "
				+ "where is_blocked=false";
		try {
			Connection connection = ConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<User> toReturn = new ArrayList<>();
			while(resultSet.next()) {
				User user = new User();
				user.setIdUser(resultSet.getString(1));
				user.setUsername(resultSet.getString(2));
				user.setPassword(resultSet.getString(3));
				user.setEmail(resultSet.getString(4));
				user.setFirstName(resultSet.getString(5));
				user.setLastName(resultSet.getString(6));
				user.setCourseName(resultSet.getString(7));
				user.setCourseFacultyName(resultSet.getString(8));
				user.setDescription(resultSet.getString(9));
				user.setCurrentYear(resultSet.getInt(10));
				toReturn.add(user);
			}
			ConnectionPool.releaseConnection(connection);
			return toReturn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<User> getConnectedUsers(User user) {
		UserDAOMySQL userDao = new UserDAOMySQL();
		UserConnectionDAOMySQL userConnectionDao = new UserConnectionDAOMySQL();
		ArrayList<User> allUsers = userDao.getAllUsers();
		ArrayList<UserConnection> connectionsForUser = userConnectionDao.getConnectionsForUser(user);
		ArrayList<User> toReturn = new ArrayList<>();
		for(UserConnection userConnection : connectionsForUser) {
			if(!userConnection.getIsPending()) {
				String idConnectedUser = (userConnection.getIdUser1().equals(user.getIdUser()))?userConnection.getIdUser2():userConnection.getIdUser1();
				for(User randomUser : allUsers) {
					if(randomUser.getIdUser().equals(idConnectedUser))
						toReturn.add(randomUser);
				}
			}
		}
		return toReturn;
	}

	@Override
	public ArrayList<User> getPendingConnections(User user) {
		UserDAOMySQL userDao = new UserDAOMySQL();
		UserConnectionDAOMySQL userConnectionDao = new UserConnectionDAOMySQL();
		ArrayList<User> allUsers = userDao.getAllUsers();
		ArrayList<UserConnection> connectionsForUser = userConnectionDao.getConnectionsForUser(user);
		ArrayList<User> toReturn = new ArrayList<>();
		for(UserConnection userConnection : connectionsForUser) {
			if(userConnection.getIsPending()) {
				String idConnectedUser = (userConnection.getIdUser1().equals(user.getIdUser()))?userConnection.getIdUser2():userConnection.getIdUser1();
				for(User randomUser : allUsers) {
					if(randomUser.getIdUser().equals(idConnectedUser))
						toReturn.add(randomUser);
				}
			}
		}
		return toReturn;
	}

	@Override
	public boolean updateUser(User user) {
		String query = "update user set course_name=?, course_faculty_name=?, description=?, email=?, password_hash=?, first_name=?, last_name=?, current_year=? where id_user=?";
		try {
			Connection connection = ConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user.getCourseName());
			preparedStatement.setString(2, user.getCourseFacultyName());
			preparedStatement.setString(3, user.getDescription());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getPassword());
			preparedStatement.setString(6, user.getFirstName());
			preparedStatement.setString(7, user.getLastName());
			preparedStatement.setInt(8, user.getCurrentYear());
			preparedStatement.setString(9, user.getIdUser());
			preparedStatement.execute();
			ConnectionPool.releaseConnection(connection);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean blockUser(String idUser) {
		String query = "update user set is_blocked=? where id_user=?";
		Connection connection = null;
		try {
			connection = ConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setBoolean(1, true);
			preparedStatement.setString(2, idUser);
			int status = preparedStatement.executeUpdate();
			if(status > 0)
				return true;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(connection != null)
				ConnectionPool.releaseConnection(connection);
		}
		return false;
	}

}
