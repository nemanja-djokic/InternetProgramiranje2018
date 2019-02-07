package net.etfbl.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import net.etfbl.dao.PostDAO;
import net.etfbl.dao.UserConnectionDAO;
import net.etfbl.dao.beans.Post;
import net.etfbl.dao.beans.UserConnection;
import net.etfbl.util.ConnectionPool;

public class PostDAOMySQL implements PostDAO {

	@Override
	public ArrayList<Post> getAllPosts() {
		String postQuery = "select distinct id_post, timestamp, content, user_id_user, is_blocked from post p join user u where p.user_id_user=u.id_user";
		try {
			Connection connection = ConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(postQuery);
			ResultSet resultSet = preparedStatement.executeQuery();
			ArrayList<Post> toReturn = new ArrayList<>();
			while(resultSet.next()) {
				boolean isBlocked = resultSet.getBoolean(5);
				if(!isBlocked) {
					String postRatingQuery = "select id_post_rating, post_user_id_user, is_like from post_rating where post_id_post=?";
					PreparedStatement ratingPreparedStatement = connection.prepareStatement(postRatingQuery);
					ratingPreparedStatement.setInt(1, resultSet.getInt(1));
					ResultSet ratingResultSet = ratingPreparedStatement.executeQuery();
					ArrayList<String> likes = new ArrayList<>();
					ArrayList<String> dislikes = new ArrayList<>();
					while(ratingResultSet.next()) {
						String postUserIdUser = ratingResultSet.getString(2);
						boolean isLike = ratingResultSet.getBoolean(3);
						if(isLike) {
							likes.add(postUserIdUser);
						}else {
							dislikes.add(postUserIdUser);
						}
					}
					Post toAdd = new Post();
					toAdd.setIdPost(resultSet.getInt(1));
					toAdd.setTimestamp(resultSet.getLong(2));
					toAdd.setContent(resultSet.getString(3));
					toAdd.setUserIdUser(resultSet.getString(4));
					toAdd.setLikes(likes);
					toAdd.setDislikes(dislikes);
					String userInfoQuery = "select first_name, last_name from user where id_user=?";
					PreparedStatement userPreparedStatement = connection.prepareStatement(userInfoQuery);
					userPreparedStatement.setString(1, toAdd.getUserIdUser());
					ResultSet userResultSet = userPreparedStatement.executeQuery();
					while(userResultSet.next()) {
						toAdd.setUserFirstName(userResultSet.getString(1));
						toAdd.setUserLastName(userResultSet.getString(2));
					}
					toReturn.add(toAdd);
				}
			}
			ConnectionPool.releaseConnection(connection);
			return toReturn;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addPost(Post post) {
		String query = "insert into post(timestamp, content, user_id_user) values(?,?,?)";
		try {
			Connection connection = ConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setLong(1, post.getTimestamp());
			preparedStatement.setString(2, post.getContent());
			preparedStatement.setString(3, post.getUserIdUser());
			boolean status = preparedStatement.execute();
			ConnectionPool.releaseConnection(connection);
			return status;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ArrayList<Post> getFeedForUser(String idUser) {
		UserConnectionDAO userConnectionDao = new UserConnectionDAOMySQL();
		ArrayList<UserConnection> userConnections = userConnectionDao.getConnectionsForUser(idUser);
		ArrayList<UserConnection> filteredConnections = userConnections.stream().filter(p -> !p.getIsPending()).collect(Collectors.toCollection(ArrayList::new));
		ArrayList<Post> allPosts = getAllPosts();
		ArrayList<Post> toReturn = new ArrayList<>();
		for(Post post : allPosts) {
			boolean isFromConnectedUser = false;
			for(UserConnection connection : filteredConnections) {
				if(post.getUserIdUser().equals(connection.getIdUser1()) || post.getUserIdUser().equals(connection.getIdUser2()))
					isFromConnectedUser = true;
			}
			if(isFromConnectedUser || post.getUserIdUser().equals(idUser)) {
				toReturn.add(post);
			}
		}
		return toReturn;
	}

	@Override
	public boolean giveRating(int idPost, String idUser, boolean isLike) {
		String query = "insert into post_rating(post_id_post, post_user_id_user, is_like) values(?, ?, ?) "
				+ "on duplicate key update is_like=?";
		try {
			Connection connection = ConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, idPost);
			preparedStatement.setString(2, idUser);
			preparedStatement.setBoolean(3, isLike);
			preparedStatement.setBoolean(4, isLike);
			boolean status = preparedStatement.execute();
			ConnectionPool.releaseConnection(connection);
			return status;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
