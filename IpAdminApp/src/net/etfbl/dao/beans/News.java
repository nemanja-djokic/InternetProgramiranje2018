package net.etfbl.dao.beans;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class News implements Serializable {

	private static final long serialVersionUID = 1959797495345197508L;
	@Expose private int idNews;
	@Expose private long date;
	@Expose private String title;
	@Expose private String content;
	@Expose private long timestamp;
	@Expose private ArrayList<String> likes;
	@Expose private ArrayList<String> dislikes;
	
	public int getIdNews() {
		return this.idNews;
	}
	
	public long getDate() {
		return this.date;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public ArrayList<String> getLikes(){
		return this.likes;
	}
	
	public ArrayList<String> getDislikes(){
		return this.dislikes;
	}
	
	public void setIdNews(int idNews) {
		this.idNews = idNews;
	}
	
	public void setDate(long date) {
		this.date = date;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setLikes(ArrayList<String> likes) {
		this.likes = likes;
	}
	
	public void setDislikes(ArrayList<String> dislikes) {
		this.dislikes = dislikes;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
}
