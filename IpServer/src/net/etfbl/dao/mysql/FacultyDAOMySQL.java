package net.etfbl.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.etfbl.dao.FacultyDAO;
import net.etfbl.dao.beans.Faculty;
import net.etfbl.util.ConnectionPool;

public class FacultyDAOMySQL implements FacultyDAO {

	@Override
	public ArrayList<Faculty> getAll() {
		String query = "select name from faculty";
		try {
			Connection connection = ConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<Faculty> toReturn = new ArrayList<>();
			while(resultSet.next()) {
				Faculty faculty = new Faculty();
				faculty.setName(resultSet.getString(1));
				toReturn.add(faculty);
			}
			ConnectionPool.releaseConnection(connection);
			return toReturn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean addFaculty(Faculty faculty) {
		String query = "insert into faculty(name)"
				+ " values(?)";
		try {
			Connection connection = ConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, faculty.getName());
			preparedStatement.execute();
			ConnectionPool.releaseConnection(connection);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
