package net.etfbl.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.etfbl.dao.LoginActionDAO;
import net.etfbl.dao.beans.LoginAction;
import net.etfbl.util.ConnectionPool;

public class LoginActionDAOMySQL implements LoginActionDAO {

	@Override
	public ArrayList<LoginAction> getAll() {
		ArrayList<LoginAction> toReturn = new ArrayList<>();
		String query = "select id_login_action, id_user, login_time from login_action";
		Connection connection = null;
		try {
			connection = ConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				LoginAction toAdd = new LoginAction();
				toAdd.setIdLoginAction(resultSet.getInt(1));
				toAdd.setIdUser(resultSet.getString(2));
				toAdd.setLoginTime(resultSet.getLong(3));
				toReturn.add(toAdd);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(connection != null)
				ConnectionPool.releaseConnection(connection);
		}
		return toReturn;
	}

	@Override
	public boolean addLoginAction(LoginAction loginAction) {
		String query = "insert into login_action(id_user, login_time) values(?, ?)";
		Connection connection = null;
		try {
			connection = ConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, loginAction.getIdUser());
			preparedStatement.setLong(2, loginAction.getLoginTime());
			boolean status = preparedStatement.execute();
			ConnectionPool.releaseConnection(connection);
			return status;
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(connection != null)
				ConnectionPool.releaseConnection(connection);
		}
		return false;
	}

}
