package net.etfbl.dao;

import java.util.ArrayList;

import net.etfbl.dao.beans.News;

public interface NewsDAO {

	public ArrayList<News> getAll();
	public boolean addNews(News news);
	public boolean giveRating(int idNews, String idUser, boolean isLike);
	
}
