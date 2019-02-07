package net.etfbl.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.etfbl.dao.AdministratorDAO;
import net.etfbl.dao.beans.Administrator;
import net.etfbl.util.ConnectionPool;

public class AdministratorDAOMySQL implements AdministratorDAO {

	@Override
	public ArrayList<Administrator> getAll() {
		ArrayList<Administrator> toReturn = new ArrayList<>();
		String query = "select username, password from administrator";
		Connection connection = null;
		try {
			connection = ConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Administrator toAdd = new Administrator();
				toAdd.setUsername(resultSet.getString(1));
				toAdd.setPassword(resultSet.getString(2));
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

}
