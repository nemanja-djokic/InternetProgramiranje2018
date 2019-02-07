package net.etfbl.dao.beans;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class Post {

	@Expose private int idPost;
	@Expose private String userIdUser;
	@Expose private String userFirstName;
	@Expose private String userLastName;
	@Expose private long timestamp;
	@Expose private String content;
	@Expose private ArrayList<String> likes;
	@Expose private ArrayList<String> dislikes;
	
	public String getUserFistName() {
		return this.userFirstName;
	}
	
	public String getUserLastName() {
		return this.userLastName;
	}
	
	public Post setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
		return this;
	}
	
	public Post setUserLastName(String userLastName) {
		this.userLastName = userLastName;
		return this;
	}
	
	public int getIdPost() {
		return idPost;
	}
	public Post setIdPost(int idPost) {
		this.idPost = idPost;
		return this;
	}
	public String getUserIdUser() {
		return userIdUser;
	}
	public Post setUserIdUser(String userIdUser) {
		this.userIdUser = userIdUser;
		return this;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public Post setTimestamp(long timestamp) {
		this.timestamp = timestamp;
		return this;
	}
	public String getContent() {
		return content;
	}
	public Post setContent(String content) {
		this.content = content;
		return this;
	}
	public ArrayList<String> getLikes() {
		return likes;
	}
	public Post setLikes(ArrayList<String> likes) {
		this.likes = likes;
		return this;
	}
	public ArrayList<String> getDislikes() {
		return dislikes;
	}
	public Post setDislikes(ArrayList<String> dislikes) {
		this.dislikes = dislikes;
		return this;
	}
	
	
	
}
