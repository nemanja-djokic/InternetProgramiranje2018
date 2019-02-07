package net.etfbl.dao.beans;

import java.io.Serializable;

import org.bson.Document;

import com.google.gson.annotations.Expose;

public class BlogPostComment implements Serializable {

	private static final long serialVersionUID = 6660545854265476398L;
	@Expose private String parentId;
	@Expose private long parentTimestamp;
	@Expose private String author;
	@Expose private String authorId;
	@Expose private long timestamp;
	@Expose private String content;
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
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
	public Document toDocument() {
		return new Document("parentId", this.parentId)
				.append("author", this.author)
				.append("authorId", this.authorId)
				.append("parentTimestamp", this.parentTimestamp)
				.append("timestamp", this.timestamp)
				.append("content", this.content);
	}
	public long getParentTimestamp() {
		return parentTimestamp;
	}
	public void setParentTimestamp(long parentTimestamp) {
		this.parentTimestamp = parentTimestamp;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	
}
