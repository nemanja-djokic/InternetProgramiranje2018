package net.etfbl.services;

import java.util.ArrayList;

import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.etfbl.dao.FileDescriptorDAO;
import net.etfbl.dao.beans.FileDescriptor;
import net.etfbl.dao.beans.User;
import net.etfbl.dao.mongodb.FileDescriptorDAOMongoDB;

@WebService
@Path("/rest")
public class FilesController {

	 @Context private ServletContext context;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/files/{SESSION_TOKEN}")
	public String sharedFiles(@PathParam("SESSION_TOKEN") String token) {
		Sessions sessions = LandingServlet.getSessions();
		User userForSession = sessions.verifySession(token);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		if(token != null && userForSession != null) {
			FileDescriptorDAO fileDescriptor = new FileDescriptorDAOMongoDB();
			ArrayList<FileDescriptor> descriptors = fileDescriptor.getAll();
			return gson.toJson(descriptors);
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
	
}
