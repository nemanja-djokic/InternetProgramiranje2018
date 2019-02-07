package net.etfbl.dao.beans;

import org.bson.Document;

import com.google.gson.annotations.Expose;

public class FileDescriptor {
	
	@Expose private String userId;
	@Expose private String author;
	@Expose private String fileName;
	@Expose private String description;
	@Expose private long timestamp;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Document toDocument() {
		return new Document("userId", this.userId)
				.append("fileName", this.fileName)
				.append("description", this.description)
				.append("timestamp", this.timestamp)
				.append("author", this.author);
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
}
