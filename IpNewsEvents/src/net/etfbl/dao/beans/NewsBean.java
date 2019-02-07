package net.etfbl.dao.beans;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import net.etfbl.dao.NewsDAO;
import net.etfbl.dao.mysql.NewsDAOMySQL;
import net.etfbl.dto.News;

@ManagedBean(name = "newsBean")
@RequestScoped
public class NewsBean implements Serializable {

	public NewsBean() {
		
	}
	
	private static final long serialVersionUID = 4477385339218973507L;
	
	private News news = new News();
	
	public void setNews(News news) {
		this.news = news;
	}
	
	public News getNews() {
		return this.news;
	}
	
	public String saveNews() {
		NewsDAO newsDao = new NewsDAOMySQL();
		this.news.setDate(new Date().getTime());
		newsDao.addNews(this.news);
		this.news.setTitle(null);
		this.news.setContent(null);
		return null;
	}

}
