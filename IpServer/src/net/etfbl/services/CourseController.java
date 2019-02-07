package net.etfbl.services;

import java.util.ArrayList;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.etfbl.dao.CourseDAO;
import net.etfbl.dao.FacultyDAO;
import net.etfbl.dao.beans.Course;
import net.etfbl.dao.beans.Faculty;
import net.etfbl.dao.beans.User;
import net.etfbl.dao.mysql.CourseDAOMySQL;
import net.etfbl.dao.mysql.FacultyDAOMySQL;

@WebService
@Path("/rest")
public class CourseController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/allfaculties/{SESSION_TOKEN}")
	public String getAllFaculties(@PathParam("SESSION_TOKEN") String token) {
		Sessions sessions = LandingServlet.getSessions();
		User userForSession = sessions.verifySession(token);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		if(token != null && userForSession != null) {
			FacultyDAO facultyDao = new FacultyDAOMySQL();
			ArrayList<Faculty> allFaculties = facultyDao.getAll();
			return gson.toJson(allFaculties);
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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/coursesforfaculty/{FACULTY}&{SESSION_TOKEN}")
	public String getCoursesForFaculty(@PathParam("SESSION_TOKEN") String token, @PathParam("FACULTY") String faculty) {
		Sessions sessions = LandingServlet.getSessions();
		User userForSession = sessions.verifySession(token);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		if(token != null && userForSession != null) {
			CourseDAO courseDao = new CourseDAOMySQL();
			ArrayList<Course> allCourses = courseDao.getAllCoursesForFaculty(new Faculty().setName(faculty));
			return gson.toJson(allCourses);
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
