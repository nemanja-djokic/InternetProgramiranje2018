package net.etfbl.dao.beans;

import java.io.Serializable;
import java.util.ArrayList;

import org.bson.Document;

import com.google.gson.annotations.Expose;

public class BlogPost implements Serializable {

	private static final long serialVersionUID = 6870205342048139781L;
	@Expose private String id;
	@Expose private String author;
	@Expose private long timestamp;
	@Expose private String content;
	@Expose private ArrayList<BlogPostComment> comments = new ArrayList<>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ArrayList<BlogPostComment> getComments() {
		return comments;
	}
	public void setComments(ArrayList<BlogPostComment> comments) {
		this.comments = comments;
	}
	
	public Document toDocument() {
		return new Document("id", this.id)
				.append("author", this.author)
				.append("timestamp", this.timestamp)
				.append("content", this.content);
	}
	
}
