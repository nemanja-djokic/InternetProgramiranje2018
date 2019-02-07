package net.etfbl.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import net.etfbl.dao.EventDAO;
import net.etfbl.dao.NewsDAO;
import net.etfbl.dao.NewsEventDAO;
import net.etfbl.dao.beans.Event;
import net.etfbl.dao.beans.News;
import net.etfbl.dao.beans.NewsEvent;
import net.etfbl.util.NewsEventConnectionPool;

public class NewsEventDAOMySQL implements NewsEventDAO {

	@Override
	public ArrayList<NewsEvent> getAll() {
		ArrayList<NewsEvent> toReturn = new ArrayList<>();
		NewsDAO newsDao = new NewsDAOMySQL();
		EventDAO eventDao = new EventDAOMySQL();
		ArrayList<News> news = newsDao.getAll();
		ArrayList<Event> events = eventDao.getAll();
		for(News newsIter : news) {
			NewsEvent toAdd = new NewsEvent();
			toAdd.setId(newsIter.getIdNews());
			toAdd.setTitle(newsIter.getTitle());
			toAdd.setType("news");
			toReturn.add(toAdd);
		}
		for(Event eventIter : events) {
			NewsEvent toAdd = new NewsEvent();
			toAdd.setId(eventIter.getIdEvent());
			toAdd.setTitle(eventIter.getCategory() + " " + eventIter.getTitle());
			toAdd.setType("event");
			toReturn.add(toAdd);
		}
		return toReturn;
	}

	@Override
	public boolean remove(NewsEvent toRemove) {
		Connection connection = null;
		try {
			connection  = NewsEventConnectionPool.getConnection();
			if(toRemove.getType().equals("news")) {
				String query = "delete from news where id_news=?";
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, toRemove.getId());
				int status = preparedStatement.executeUpdate();
				if(status > 0)
					return true;
				else
					return false;
			}else if(toRemove.getType().equals("event")) {
				String query = "delete from event where id_event=?";
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, toRemove.getId());
				int status = preparedStatement.executeUpdate();
				if(status > 0)
					return true;
				else
					return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(connection != null)
				NewsEventConnectionPool.releaseConnection(connection);
		}
		return false;
	}

}
