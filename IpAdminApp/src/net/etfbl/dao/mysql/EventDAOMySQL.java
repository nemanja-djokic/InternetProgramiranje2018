package net.etfbl.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.etfbl.dao.EventDAO;
import net.etfbl.dao.beans.Event;
import net.etfbl.util.NewsEventConnectionPool;

public class EventDAOMySQL implements EventDAO {

	@Override
	public ArrayList<Event> getAll() {
		String eventQuery = "select id_event, title, starts_on, ends_on, description, image_url, category, timestamp from event";
		try {
			Connection connection = NewsEventConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(eventQuery);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<Event> toReturn = new ArrayList<>();
			while(resultSet.next()) {
				String newsRatingQuery = "select id_event_rating, id_user, is_like from event_rating where event_id_event=?";
				PreparedStatement ratingPreparedStatement = connection.prepareStatement(newsRatingQuery);
				ratingPreparedStatement.setInt(1, resultSet.getInt(1));
				ResultSet ratingResultSet = ratingPreparedStatement.executeQuery();
				ArrayList<String> likes = new ArrayList<>();
				ArrayList<String> dislikes = new ArrayList<>();
				while(ratingResultSet.next()) {
					String idUser = ratingResultSet.getString(2);
					boolean isLike = ratingResultSet.getBoolean(3);
					if(isLike) {
						likes.add(idUser);
					}else {
						dislikes.add(idUser);
					}
				}
				Event toAdd = new Event();
				toAdd.setIdEvent(resultSet.getInt(1));
				toAdd.setTitle(resultSet.getString(2));
				toAdd.setStartsOn(resultSet.getLong(3));
				toAdd.setEndsOn(resultSet.getLong(4));
				toAdd.setDescription(resultSet.getString(5));
				toAdd.setImageUrl(resultSet.getString(6));
				toAdd.setCategory(resultSet.getString(7));
				toAdd.setTimestamp(resultSet.getLong(8));
				toAdd.setLikes(likes);
				toAdd.setDislikes(dislikes);
				toReturn.add(toAdd);
			}
			NewsEventConnectionPool.releaseConnection(connection);
			return toReturn;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addEvent(Event event) {
		String query = "insert into event(title, starts_on, ends_on, description, image_url, category, timestamp) "
				+ "values(?, ?, ?, ?, ?, ?, ?)";
		try {
			Connection connection = NewsEventConnectionPool.getConnection();
			if(connection == null) {
				System.out.println("CONNECTION NULL");
			}
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, event.getTitle());
			preparedStatement.setLong(2, event.getStartsOn());
			preparedStatement.setLong(3, event.getEndsOn());
			preparedStatement.setString(4, event.getDescription());
			preparedStatement.setString(5, event.getImageUrl());
			preparedStatement.setString(6, event.getCategory());
			preparedStatement.setLong(7, new java.util.Date().getTime());
			boolean status = preparedStatement.execute();
			NewsEventConnectionPool.releaseConnection(connection);
			return status;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean giveRating(int idEvent, String idUser, boolean isLike) {
		String query = "insert into event_rating(event_id_event, id_user, is_like) values(?, ?, ?) "
				+ "on duplicate key update is_like=?";
		try {
			Connection connection = NewsEventConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, idEvent);
			preparedStatement.setString(2, idUser);
			preparedStatement.setBoolean(3, isLike);
			preparedStatement.setBoolean(4, isLike);
			boolean status = preparedStatement.execute();
			NewsEventConnectionPool.releaseConnection(connection);
			return status;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
