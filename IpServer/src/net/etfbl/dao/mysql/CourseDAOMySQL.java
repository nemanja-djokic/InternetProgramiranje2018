package net.etfbl.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.etfbl.dao.CourseDAO;
import net.etfbl.dao.beans.Course;
import net.etfbl.dao.beans.Faculty;
import net.etfbl.util.ConnectionPool;

public class CourseDAOMySQL implements CourseDAO {

	@Override
	public ArrayList<Course> getAll() {
		String query = "select name, faculty_name, duration_years from course";
		try {
			Connection connection = ConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<Course> toReturn = new ArrayList<>();
			while(resultSet.next()) {
				Course course = new Course();
				course.setName(resultSet.getString(1));
				course.setFacultyName(resultSet.getString(2));
				course.setDurationYears(resultSet.getInt(3));
				toReturn.add(course);
			}
			ConnectionPool.releaseConnection(connection);
			return toReturn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean addCourse(Course course) {
		String query = "insert into course(name, faculty_name, duration_years)"
				+ " values(?, ?, ?)";
		try {
			Connection connection = ConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, course.getName());
			preparedStatement.setString(2, course.getFacultyName());
			preparedStatement.setInt(3, course.getDurationYears());
			preparedStatement.execute();
			ConnectionPool.releaseConnection(connection);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ArrayList<Course> getAllCoursesForFaculty(Faculty faculty) {
		String query = "select name, faculty_name, duration_years from course where faculty_name=?";
		try {
			Connection connection = ConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, faculty.getName());
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<Course> toReturn = new ArrayList<>();
			while(resultSet.next()) {
				Course course = new Course();
				course.setName(resultSet.getString(1));
				course.setFacultyName(resultSet.getString(2));
				course.setDurationYears(resultSet.getInt(3));
				toReturn.add(course);
			}
			ConnectionPool.releaseConnection(connection);
			return toReturn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
