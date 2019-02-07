package net.etfbl.dto;

public class NewsRatingRequestBody {

	public NewsRatingRequestBody() {
		idNews = 0;
		idUser = "";
		rating = "";
	}
	
	public int idNews;
	public String idUser;
	public String rating;
	
}
