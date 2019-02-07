package net.etfbl.services;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.etfbl.dao.EventDAO;
import net.etfbl.dao.mysql.EventDAOMySQL;
import net.etfbl.dto.EventRatingRequestBody;

@WebService
@Path("/rest")
public class EventController {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/giveeventrating")
	public Response giveNewsRating(EventRatingRequestBody data) {
		if(data.idEvent != 0 && data.idUser != null && data.rating != null) {
			EventDAO eventDao = new EventDAOMySQL();
			eventDao.giveRating(data.idEvent, data.idUser, (data.rating.equals("like")?true:false));
			return Response.status(200).entity(data).build();
		}else {
			return Response.status(500).build();
		}
	}
}
