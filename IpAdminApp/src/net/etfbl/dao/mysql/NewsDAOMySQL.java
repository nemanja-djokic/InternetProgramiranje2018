package net.etfbl.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.etfbl.dao.NewsDAO;
import net.etfbl.dao.beans.News;
import net.etfbl.util.NewsEventConnectionPool;

public class NewsDAOMySQL implements NewsDAO {

	@Override
	public ArrayList<News> getAll() {
		String postQuery = "select id_news, date, title, content, timestamp from news";
		try {
			Connection connection = NewsEventConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(postQuery);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<News> toReturn = new ArrayList<>();
			while(resultSet.next()) {
				String newsRatingQuery = "select id_news_rating, id_user, is_like from news_rating where news_id_news=?";
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
				News toAdd = new News();
				toAdd.setIdNews(resultSet.getInt(1));
				toAdd.setDate(resultSet.getLong(2));
				toAdd.setTitle(resultSet.getString(3));
				toAdd.setContent(resultSet.getString(4));
				toAdd.setTimestamp(resultSet.getLong(5));
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
	public boolean addNews(News news) {
		String query = "insert into news(id_news, date, title, content, timestamp) values(?,?,?,?,?)";
		try {
			Connection connection = NewsEventConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, news.getIdNews());
			preparedStatement.setLong(2, news.getDate());
			preparedStatement.setString(3, news.getTitle());
			preparedStatement.setString(4, news.getContent());
			preparedStatement.setLong(5, new java.util.Date().getTime());
			boolean status = preparedStatement.execute();
			NewsEventConnectionPool.releaseConnection(connection);
			return status;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean giveRating(int idNews, String idUser, boolean isLike) {
		String query = "insert into news_rating(news_id_news, id_user, is_like) values(?, ?, ?) "
				+ "on duplicate key update is_like=?";
		try {
			Connection connection = NewsEventConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, idNews);
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
