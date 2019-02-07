package net.etfbl.services;

import java.util.ArrayList;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.etfbl.dao.NewsDAO;
import net.etfbl.dao.mysql.NewsDAOMySQL;
import net.etfbl.dto.News;
import net.etfbl.dto.NewsRatingRequestBody;

@WebService
@Path("/rest")
public class NewsController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/allnews")
	public String getAllNews() {
		NewsDAO newsDao = new NewsDAOMySQL();
		ArrayList<News> allNews = newsDao.getAll();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		return gson.toJson(allNews);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/givenewsrating")
	public Response giveNewsRating(NewsRatingRequestBody data) {
		if(data.idNews != 0 && data.idUser != null && data.rating != null) {
			NewsDAO newsDao = new NewsDAOMySQL();
			newsDao.giveRating(data.idNews, data.idUser, (data.rating.equals("like")?true:false));
			return Response.status(200).entity(data).build();
		}else {
			return Response.status(500).build();
		}
	}
	
}
