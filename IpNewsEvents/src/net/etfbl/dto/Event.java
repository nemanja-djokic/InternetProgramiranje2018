package net.etfbl.dto;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class Event implements Serializable{
	private static final long serialVersionUID = -8749945100471500639L;
	@Expose private int idEvent;
	@Expose private String title;
	@Expose private long startsOn;
	@Expose private long endsOn;
	@Expose private long timestamp;
	@Expose private String description;
	@Expose private String imageUrl;
	@Expose private String category;
	@Expose private ArrayList<String> likes;
	@Expose private ArrayList<String> dislikes;
	public int getIdEvent() {
		return idEvent;
	}
	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getStartsOn() {
		return startsOn;
	}
	public void setStartsOn(long startsOn) {
		this.startsOn = startsOn;
	}
	public long getEndsOn() {
		return endsOn;
	}
	public void setEndsOn(long endsOn) {
		this.endsOn = endsOn;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public ArrayList<String> getLikes() {
		return likes;
	}
	public void setLikes(ArrayList<String> likes) {
		this.likes = likes;
	}
	public ArrayList<String> getDislikes() {
		return dislikes;
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
