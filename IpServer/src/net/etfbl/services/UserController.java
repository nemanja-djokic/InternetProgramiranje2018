package net.etfbl.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

import net.etfbl.dao.UserConnectionDAO;
import net.etfbl.dao.UserDAO;
import net.etfbl.dao.beans.ChangePasswordRequestBody;
import net.etfbl.dao.beans.ConnectRequestBody;
import net.etfbl.dao.beans.User;
import net.etfbl.dao.beans.UserConnection;
import net.etfbl.dao.mysql.UserConnectionDAOMySQL;
import net.etfbl.dao.mysql.UserDAOMySQL;
import net.etfbl.util.Utility;

@WebService
@Path("/rest")
public class UserController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/connected/{SESSION_TOKEN}")
	public String getConnectedStudents(@PathParam("SESSION_TOKEN") String token) {
		UserDAOMySQL userDao = new UserDAOMySQL();
		Sessions sessions = LandingServlet.getSessions();
		User userForSession = sessions.verifySession(token);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		if(token != null && userForSession != null) {
			ArrayList<User> connectedUsers = userDao.getConnectedUsers(userForSession);
			return gson.toJson(connectedUsers);
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
	@Path("/pending/{SESSION_TOKEN}")
	public String getPendingConnectionsStudents(@PathParam("SESSION_TOKEN") String token) {
		UserDAOMySQL userDao = new UserDAOMySQL();
		Sessions sessions = LandingServlet.getSessions();
		User userForSession = sessions.verifySession(token);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		if(token != null && userForSession != null) {
			ArrayList<User> allUsers = userDao.getAllUsers();
			UserConnectionDAO userConnectionDao = new UserConnectionDAOMySQL();
			ArrayList<UserConnection> connections = userConnectionDao.getConnectionsForUser(userForSession);
			ArrayList<User> toReturn = new ArrayList<>();
			for(UserConnection uc : connections) {
				for(User user : allUsers) {
					if(uc.getIsPending() && user.getIdUser().equals(uc.getIdUser1()) && !user.getIdUser().equals(userForSession.getIdUser()))
						toReturn.add(user);
				}
			}
			return gson.toJson(toReturn);
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
	@Path("/userinfo/{USER_ID}&{SESSION_TOKEN}")
	public String getUserInfo(@PathParam("USER_ID") String userId, @PathParam("SESSION_TOKEN") String token) {
		UserDAOMySQL userDao = new UserDAOMySQL();
		Sessions sessions = LandingServlet.getSessions();
		User userForSession = sessions.verifySession(token);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		if(token != null && userForSession != null && userId != null) {
			ArrayList<User> allUsers = userDao.getAllUsers();
			for(User user : allUsers) {
				if(user.getIdUser().equals(userId))
					return gson.toJson(user);
			}
			return gson.toJson("Unknown user");
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
	@Path("/myinfo/{SESSION_TOKEN}")
	public String getMyInfo(@PathParam("SESSION_TOKEN") String token) {
		Sessions sessions = LandingServlet.getSessions();
		User userForSession = sessions.verifySession(token);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		if(token != null && userForSession != null) {
			return gson.toJson(userForSession);
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
	@Path("/allusers/{SESSION_TOKEN}")
	public String getAllUsers(@PathParam("SESSION_TOKEN") String token) {
		Sessions sessions = LandingServlet.getSessions();
		User userForSession = sessions.verifySession(token);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		if(token != null && userForSession != null) {
			UserDAO userDao = new UserDAOMySQL();
			ArrayList<User> allUsers = userDao.getAllUsers();
			return gson.toJson(allUsers);
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
	@Path("/allusersforfaculty/{FACULTY}&{SESSION_TOKEN}")
	public String getAllUsersForFaculty(@PathParam("FACULTY") String faculty, @PathParam("SESSION_TOKEN") String token) {
		Sessions sessions = LandingServlet.getSessions();
		User userForSession = sessions.verifySession(token);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		if(token != null && userForSession != null) {
			UserDAO userDao = new UserDAOMySQL();
			ArrayList<User> allUsers = userDao.getAllUsers();
			ArrayList<User> usersWithConnections = userDao.getConnectedUsers(userForSession);
			usersWithConnections.addAll(userDao.getPendingConnections(userForSession));
			ArrayList<User> toReturn = new ArrayList<>();
			for(User user : allUsers) {
				if(faculty == null || faculty.equals("") || faculty.equals("Svi")) {
					if(!user.getIdUser().equals(userForSession.getIdUser())) {
						boolean hasConnectionWith = false;
						for(User connectionUser : usersWithConnections) {
							if(user.getIdUser().equals(connectionUser.getIdUser())) {
								hasConnectionWith = true;
							}
						}
						if(!hasConnectionWith)
							toReturn.add(user);
					}
				} else if(user.getCourseFacultyName() != null && user.getCourseFacultyName().equals(faculty)){
					if(!user.getIdUser().equals(userForSession.getIdUser())) {
						boolean hasConnectionWith = false;
						for(User connectionUser : usersWithConnections) {
							if(user.getIdUser().equals(connectionUser.getIdUser())) {
								hasConnectionWith = true;
							}
						}
						if(!hasConnectionWith)
							toReturn.add(user);
					}
				}
			}
			return gson.toJson(toReturn);
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
	@Path("/sendrequest")
	public Response sendRequest(ConnectRequestBody requestBody) {
		String token = requestBody.sessiontoken;
		String userId = requestBody.userid;
		Sessions sessions = LandingServlet.getSessions();
		User userForSession = sessions.verifySession(token);
		if(token != null && userForSession != null) {
			UserConnectionDAO userConnectionDao = new UserConnectionDAOMySQL();
			UserConnection userConnection = new UserConnection();
			userConnection.setIdUser1(userForSession.getIdUser()).setIdUser2(userId).setIsPending(true);
			userConnectionDao.addConnection(userConnection);
			return Response.status(200).build();
		}else {
			return Response.status(500).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/acceptrequest")
	public Response acceptRequest(ConnectRequestBody requestBody) {
		String token = requestBody.sessiontoken;
		String userId = requestBody.userid;
		Sessions sessions = LandingServlet.getSessions();
		User userForSession = sessions.verifySession(token);
		if(token != null && userForSession != null) {
			UserConnectionDAO userConnectionDao = new UserConnectionDAOMySQL();
			userConnectionDao.acceptConnection(userForSession.getIdUser(), userId);
			return Response.status(200).build();
		}else {
			return Response.status(500).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/cancelrequest")
	public Response cancelRequest(ConnectRequestBody requestBody) {
		String token = requestBody.sessiontoken;
		String userId = requestBody.userid;
		Sessions sessions = LandingServlet.getSessions();
		User userForSession = sessions.verifySession(token);
		if(token != null && userForSession != null) {
			UserConnectionDAO userConnectionDao = new UserConnectionDAOMySQL();
			userConnectionDao.cancelRequest(userForSession.getIdUser(), userId);
			return Response.status(200).entity(userForSession).build();
		}else {
			return Response.status(500).entity(userForSession).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/cancelactiveconnection")
	public Response cancelActiveConnection(ConnectRequestBody requestBody) {
		String token = requestBody.sessiontoken;
		String userId = requestBody.userid;
		Sessions sessions = LandingServlet.getSessions();
		User userForSession = sessions.verifySession(token);
		if(token != null && userForSession != null) {
			UserConnectionDAO userConnectionDao = new UserConnectionDAOMySQL();
			userConnectionDao.cancelRequest(userForSession.getIdUser(), userId);
			return Response.status(200).entity(userForSession).build();
		}else {
			return Response.status(500).entity(userForSession).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/changepassword")
	public Response changePassword(ChangePasswordRequestBody requestBody) {
		String token = requestBody.token;
		String oldPassword = requestBody.oldPassword;
		String newPassword = requestBody.newPassword;
		String newPasswordRepeat = requestBody.newPasswordRepeat;
		Sessions sessions = LandingServlet.getSessions();
		User userForSession = sessions.verifySession(token);
		if(token != null && userForSession != null) {
			if(!newPassword.equals(newPasswordRepeat))
				return Response.status(500).entity("Lozinke se ne poklapaju").build();
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				String passwordHash = Utility.bytesToHex(md.digest(oldPassword.getBytes()));
				if(!passwordHash.equals(userForSession.getPassword()))
					return Response.status(500).entity("Pogrešna lozinka").build();
				String newPasswordHash = Utility.bytesToHex(md.digest(newPassword.getBytes()));
				UserDAO userDao = new UserDAOMySQL();
				userForSession.setPassword(newPasswordHash);
				userDao.updateUser(userForSession);
				return Response.status(200).entity("Lozinka uspješno promijenjena").build();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				return Response.status(500).entity("Interna greška").build();
			}
		}else {
			return Response.status(500).entity("Invalid session").build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/sent/{SESSION_TOKEN}")
	public String getSentRequests(@PathParam("SESSION_TOKEN") String token) {
		Sessions sessions = LandingServlet.getSessions();
		User userForSession = sessions.verifySession(token);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		if(token != null && userForSession != null) {
			UserConnectionDAO userConnectionDao = new UserConnectionDAOMySQL();
			UserDAO userDao = new UserDAOMySQL();
			ArrayList<UserConnection> sentConnections = userConnectionDao.getConnectionsForUser(userForSession);
			ArrayList<User> toReturn = new ArrayList<>();
			ArrayList<User> allUsers = userDao.getAllUsers();
			for(UserConnection uc : sentConnections) {
				if(uc.getIsPending() && uc.getIdUser1().equals(userForSession.getIdUser())){
					for(User user : allUsers) {
						if(user.getIdUser().equals(uc.getIdUser2()))
							toReturn.add(user);
					}
				}
			}
			return gson.toJson(toReturn);
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