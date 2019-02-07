package net.etfbl.dto;

public class EventRatingRequestBody {

	public EventRatingRequestBody() {
		idEvent = 0;
		idUser = "";
		rating = "";
	}
	
	public int idEvent;
	public String idUser;
	public String rating;
	
}
