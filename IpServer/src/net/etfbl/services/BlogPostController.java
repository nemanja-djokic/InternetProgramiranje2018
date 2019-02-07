package net.etfbl.services;

import java.util.ArrayList;
import java.util.Date;

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

import net.etfbl.dao.BlogPostDAO;
import net.etfbl.dao.beans.BlogPost;
import net.etfbl.dao.beans.BlogPostComment;
import net.etfbl.dao.beans.BlogPostCommentRequestBody;
import net.etfbl.dao.beans.BlogPostRequestBody;
import net.etfbl.dao.beans.User;
import net.etfbl.dao.mongodb.BlogPostDAOMongoDB;

@WebService
@Path("/rest")
public class BlogPostController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/blogposts/{SESSION_TOKEN}")
	public String getAllBlogPosts(@PathParam("SESSION_TOKEN") String token) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		Sessions sessions = LandingServlet.getSessions();
		User userForSession = sessions.verifySession(token);
		if(token != null && userForSession != null) {
			BlogPostDAO blogPostDao = new BlogPostDAOMongoDB();
			ArrayList<BlogPost> blogPosts = blogPostDao.getAll();
			return gson.toJson(blogPosts);
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
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/addblogpost")
	public Response addBlogPost(BlogPostRequestBody data) {
		if(data.id != null && data.author != null && data.content != null &&
				!data.id.equals("") && !data.author.equals("") && !data.content.equals("")) {
			BlogPost blogPost = new BlogPost();
			blogPost.setId(data.id);
			blogPost.setAuthor(data.author);
			blogPost.setContent(data.content);
			blogPost.setTimestamp(new Date().getTime());
			blogPost.setComments(new ArrayList<BlogPostComment>());
			BlogPostDAO blogPostDao = new BlogPostDAOMongoDB();
			blogPostDao.addPost(blogPost);
			return Response.status(200).build();
		}else {
			System.out.println(data.id + "#" + data.author + "#" + data.content);
			return Response.status(500).build();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/addcomment")
	public Response addComment(BlogPostCommentRequestBody data) {
		if(data.parentId != null && data.parentTimestamp != 0 && data.author != null && data.content != null && data.authorId != null && 
				!data.parentId.equals("") && !data.author.equals("") && !data.authorId.equals("") && !data.content.equals("")) {
			BlogPostComment comment = new BlogPostComment();
			comment.setParentId(data.parentId);
			comment.setAuthor(data.author);
			comment.setAuthorId(data.authorId);
			comment.setContent(data.content);
			comment.setParentTimestamp(data.parentTimestamp);
			comment.setTimestamp(new Date().getTime());
			BlogPostDAO blogPostDao = new BlogPostDAOMongoDB();
			blogPostDao.addComment(comment);
			return Response.status(200).build();
		}else {
			return Response.status(500).build();
		}
	}
	
}
