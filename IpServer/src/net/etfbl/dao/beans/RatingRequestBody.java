package net.etfbl.dao.beans;

public class RatingRequestBody {

	public RatingRequestBody() {
		this.postId = 0;
		this.token = "";
		this.rating = "";
	}
	
	public int postId;
	public String token;
	public String rating;
	
}
