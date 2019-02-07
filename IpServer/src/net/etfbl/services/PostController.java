package net.etfbl.services;

import java.util.ArrayList;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.etfbl.dao.PostDAO;
import net.etfbl.dao.beans.Post;
import net.etfbl.dao.beans.RatingRequestBody;
import net.etfbl.dao.beans.User;
import net.etfbl.dao.mysql.PostDAOMySQL;

@WebService
@Path("/rest")
public class PostController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/posts/{SESSION_TOKEN}")
	public String getPosts(@PathParam("SESSION_TOKEN") String token) {
		PostDAO postDao = new PostDAOMySQL();
		Sessions sessions = LandingServlet.getSessions();
		User userForSession = sessions.verifySession(token);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		if(token != null && userForSession != null) {
			ArrayList<Post> posts = postDao.getFeedForUser(userForSession.getIdUser());
			return gson.toJson(posts);
		}else {
			StringBuilder errorBuilder = new StringBuilder();
			errorBuilder.append("ERROR");
			if(token == null) {
				errorBuilder.append(" Token was null");
			}
			if(userForSession == null) {
				errorBuilder.append(" Invalid session");
			}
			return gson.toJson(errorBuilder.toString());
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/giverating")
	public Response giveRating(RatingRequestBody data) {
		if(data.postId != 0 && data.token != null && data.rating != null) {
			PostDAO postDao = new PostDAOMySQL();
			Sessions sessions = LandingServlet.getSessions();
			User user = sessions.verifySession(data.token);
			postDao.giveRating(data.postId, user.getIdUser(), (data.rating.equals("like")?true:false));
			return Response.status(200).build();
		}else {
			return Response.status(500).build();
		}
	}
	
}
