package net.etfbl.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import net.etfbl.dao.FileDescriptorDAO;
import net.etfbl.dao.LoginActionDAO;
import net.etfbl.dao.PostDAO;
import net.etfbl.dao.SessionDAO;
import net.etfbl.dao.UserDAO;
import net.etfbl.dao.beans.FileDescriptor;
import net.etfbl.dao.beans.LoginAction;
import net.etfbl.dao.beans.Post;
import net.etfbl.dao.beans.User;
import net.etfbl.dao.mongodb.FileDescriptorDAOMongoDB;
import net.etfbl.dao.mysql.LoginActionDAOMySQL;
import net.etfbl.dao.mysql.PostDAOMySQL;
import net.etfbl.dao.mysql.SessionDAOMySQL;
import net.etfbl.dao.mysql.UserDAOMySQL;
import net.etfbl.util.Utility;

@WebServlet("/landing")
@MultipartConfig
public class LandingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Sessions sessions = new Sessions();

	public static Sessions getSessions() {
		return sessions;
	}
	
    /**
     * Default constructor. 
     */
    public LandingServlet() {
        SessionDAO sessionDao = new SessionDAOMySQL();
        sessionDao.clearSessions();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean doRedirect = true;
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		String nextJSP = null;
		boolean hadAuthToken = false;
		if(action == null || action.equals("") || action.equals("home")) {
			Cookie[] cookies = request.getCookies();
			if(cookies != null) {
				for(Cookie cookie : cookies) {
					if(cookie != null && cookie.getName().equals("AUTHTOKEN")) {
						User user = sessions.verifySession(cookie.getValue());
						if(user != null) {
							nextJSP = "/WEB-INF/pages/main.jsp";
							request.setAttribute("user_bean", user);
							hadAuthToken = true;
						}
					}
				}
			}
			if(!hadAuthToken)
				nextJSP = "/WEB-INF/pages/login.jsp";
		}else if(action.equals("submit_post")){
			Cookie[] cookies = request.getCookies();
			User user = null;
			if(cookies != null) {
				for(Cookie cookie : cookies) {
					if(cookie != null && cookie.getName().equals("AUTHTOKEN")) {
						user = sessions.verifySession(cookie.getValue());
						break;
					}
				}
			}
			if(user != null) {
				String postContent = request.getParameter("post_field");
				PostDAO postDao = new PostDAOMySQL();
				Post toAdd = new Post();
				toAdd.setTimestamp(new Date().getTime()).setUserIdUser(user.getIdUser()).setContent(postContent);
				postDao.addPost(toAdd);
				request.setAttribute("user_bean", user);
				nextJSP = "/WEB-INF/pages/main.jsp";
			}else {
				nextJSP = "/WEB-INF/pages/login.jsp";
			}
		}else if(action.equals("download_file")){
			Cookie[] cookies = request.getCookies();
			hadAuthToken = false;
			if(cookies != null) {
				for(Cookie cookie : cookies) {
					if(cookie != null && cookie.getName().equals("AUTHTOKEN")) {
						User user = sessions.verifySession(cookie.getValue());
						if(user != null) {
							nextJSP = "/WEB-INF/pages/main.jsp";
							request.setAttribute("user_bean", user);
							hadAuthToken = true;
						}
					}
				}
			}
			if(hadAuthToken) {
				String fileName = request.getParameter("filename");
		        response.setContentType("application/octet-stream");
		        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
				String path = getServletContext().getRealPath("WEB-INF/../");
				File file = new File(path);
				File filesFolder = new File(file, "files");
				if(!filesFolder.exists())
					filesFolder.mkdir();
				File fileToRead = new File(filesFolder, fileName);
				System.out.println(fileToRead.getAbsolutePath());
		        try(InputStream fileStream = new FileInputStream(fileToRead)){
		        	OutputStream out = response.getOutputStream();
		        	byte[] buffer = new byte[2048];
		        	int numBytesRead = 0;
		        	while((numBytesRead = fileStream.read(buffer)) > -1) {
		        		out.write(buffer, 0, numBytesRead);
		        	}
		        }
		        doRedirect = false;
			}else {
				nextJSP = "/WEB-INF/pages/login.jsp";
				request.getSession().invalidate();
			}
		}else if(action.equals("share_file")){
			Cookie[] cookies = request.getCookies();
			User user = null;
			hadAuthToken = false;
			if(cookies != null) {
				for(Cookie cookie : cookies) {
					if(cookie != null && cookie.getName().equals("AUTHTOKEN")) {
						user = sessions.verifySession(cookie.getValue());
						if(user != null) {
							nextJSP = "/WEB-INF/pages/main.jsp";
							request.setAttribute("user_bean", user);
							hadAuthToken = true;
						}
					}
				}
			}
			if(hadAuthToken) {
				Part filePart = request.getPart("file");
				String description = request.getParameter("file_description");
				System.out.println(description);
				if(!(filePart.getName() == null || filePart.getName().isEmpty())) {
					String path = getServletContext().getRealPath("WEB-INF/../");
					System.out.println(path);
					File file = new File(path);
					File filesFolder = new File(file, "files");
					if(!filesFolder.exists()) {
						filesFolder.mkdir();
					}
					InputStream fileInputStream = filePart.getInputStream();
					File sharedFile = new File(filesFolder, filePart.getSubmittedFileName());
					boolean isOkay = true;
					boolean wasReplaced = false;
					String timestamp = null;
					do {
						isOkay = true;
						if(!sharedFile.exists()) {
							sharedFile.createNewFile();
						}else {
							isOkay = false;
							timestamp = String.valueOf(new Date().getTime());
							sharedFile = new File(filesFolder, timestamp + filePart.getSubmittedFileName());
							wasReplaced = true;
						}
					}while(!isOkay);
					FileOutputStream fos = new FileOutputStream(sharedFile);
					int nRead;
					byte[] buffer = new byte[4096];
					while((nRead = fileInputStream.read(buffer, 0, buffer.length)) != -1) {
						fos.write(buffer, 0, nRead);
					}
					fos.close();
					FileDescriptor newFile = new FileDescriptor();
					newFile.setUserId(user.getIdUser());
					if(!wasReplaced)
						newFile.setFileName(filePart.getSubmittedFileName());
					else
						newFile.setFileName(timestamp + filePart.getSubmittedFileName());
					newFile.setDescription(description);
					newFile.setTimestamp(new Date().getTime());
					newFile.setAuthor(user.getFirstName() + " " + user.getLastName());
					FileDescriptorDAO fileDescriptorDao = new FileDescriptorDAOMongoDB();
					fileDescriptorDao.addFile(newFile);
				}
			}else {
				nextJSP = "/WEB-INF/pages/login.jsp";
				request.getSession().invalidate();
			}
		}else if(action.equals("view_profile")){
			Cookie[] cookies = request.getCookies();
			if(cookies != null) {
				for(Cookie cookie : cookies) {
					if(cookie != null && cookie.getName().equals("AUTHTOKEN")) {
						User user = sessions.verifySession(cookie.getValue());
						if(user != null) {
							String viewId = request.getParameter("viewId");
							UserDAO userDao = new UserDAOMySQL();
							ArrayList<User> connectedUsers = userDao.getConnectedUsers(user);
							boolean isConnected = false;
							User viewUser = null;
							if(user.getIdUser().equals(viewId)) {
								isConnected = true;
								viewUser = user;
							}else {
								for(User connectedUser : connectedUsers) {
									if(connectedUser.getIdUser().equals(viewId)) {
										isConnected = true;
										viewUser = connectedUser;
										break;
									}
								}
							}
							if(isConnected) {
								request.setAttribute("user_bean", user);
								request.setAttribute("view_bean", viewUser);
								nextJSP = "/WEB-INF/pages/profile.jsp";
							}else {
								nextJSP = "/WEB-INF/pages/main.jsp";
								request.setAttribute("user_bean", user);
							}
						}else {
							nextJSP = "/WEB-INF/pages/login.jsp";
						}
					}
				}
			}
		}else if(action.equals("update_profile")){
			Cookie[] cookies = request.getCookies();
			User user = null;
			if(cookies != null) {
				for(Cookie cookie : cookies) {
					if(cookie != null && cookie.getName().equals("AUTHTOKEN")) {
						user = sessions.verifySession(cookie.getValue());
						break;
					}
				}
			}
			UserDAO userDao = new UserDAOMySQL();
			user.setCourseFacultyName(request.getParameter("faculty_select"));
			user.setCourseName(request.getParameter("course_select"));
			user.setDescription(request.getParameter("description"));
			String currYear = request.getParameter("year_select");
			if(currYear != null)user.setCurrentYear(Integer.parseInt(currYear));
			Part filePart = request.getPart("file");
			if(!(filePart.getName() == null || filePart.getName().isEmpty())) {
				String path = getServletContext().getRealPath("WEB-INF/../");
				File file = new File(path);
				File imagesFolder = new File(file, "images");
				InputStream fileInputStream = filePart.getInputStream();
				File imageFile = new File(imagesFolder, user.getIdUser());
				if(!imageFile.exists()) {
					imageFile.createNewFile();
				}
				FileOutputStream fos = new FileOutputStream(imageFile);
				int nRead;
				byte[] buffer = new byte[4096];
				while((nRead = fileInputStream.read(buffer, 0, buffer.length)) != -1) {
					fos.write(buffer, 0, nRead);
				}
				fos.close();
			}
			userDao.updateUser(user);
			request.setAttribute("user_bean", user);
			nextJSP = "/WEB-INF/pages/editprofile.jsp";
		}else if(action.equals("logout")) {
			Cookie sessionCookie = null;
			for(Cookie cookie : request.getCookies()) {
				if(cookie.getName().equals("AUTHTOKEN"))
					sessionCookie = cookie;
			}
			if(sessionCookie != null) {
				sessions.invalidate(sessionCookie.getValue());
			}
			Cookie authCookie = new Cookie("AUTHTOKEN", "INVALIDATED");
			authCookie.setMaxAge(1);
			response.addCookie(authCookie);
			session.invalidate();
			
			nextJSP = "/WEB-INF/pages/login.jsp";
		}else if(action.equals("goto_connect")){
			Cookie[] cookies = request.getCookies();
			if(cookies != null) {
				for(Cookie cookie : cookies) {
					if(cookie != null && cookie.getName().equals("AUTHTOKEN")) {
						User user = sessions.verifySession(cookie.getValue());
						if(user != null) {
							nextJSP = "/WEB-INF/pages/connect.jsp";
							request.setAttribute("user_bean", user);
							hadAuthToken = true;
						}
					}
				}
			}
			if(!hadAuthToken)
				nextJSP = "/WEB-INF/pages/login.jsp";
		}else if(action.equals("edit_profile")){
			Cookie[] cookies = request.getCookies();
			if(cookies != null) {
				for(Cookie cookie : cookies) {
					if(cookie != null && cookie.getName().equals("AUTHTOKEN")) {
						User user = sessions.verifySession(cookie.getValue());
						if(user != null) {
							nextJSP = "/WEB-INF/pages/editprofile.jsp";
							request.setAttribute("user_bean", user);
						}else {
							nextJSP = "/WEB-INF/pages/login.jsp";
						}
					}
				}
			}
		}else if(action.equals("login")){
			Cookie[] cookies = request.getCookies();
			User authUser = null;
			if(cookies != null) {
				for(Cookie cookie : cookies) {
					if(cookie != null && cookie.getName().equals("AUTHTOKEN")) {
						User user = sessions.verifySession(cookie.getValue());
						if(user != null) {
							nextJSP = "/WEB-INF/pages/connect.jsp";
							request.setAttribute("user_bean", user);
							hadAuthToken = true;
							authUser = user;
						}
					}
				}
			}
			if(hadAuthToken) {
				nextJSP = "/WEB-INF/pages/main.jsp";
				request.setAttribute("user_bean", authUser);
			}else {
				try {
					UserDAOMySQL userDao = new UserDAOMySQL();
					String username = request.getParameter("username").trim();
					String password = request.getParameter("password").trim();
					MessageDigest md = MessageDigest.getInstance("SHA-256");
					byte[] passwordHashBytes = md.digest(password.getBytes());
					String passwordHash = Utility.bytesToHex(passwordHashBytes);
					ArrayList<User> users = userDao.getAllUsers();
					boolean isValidLogin = false;
					User validUser = null;
					for(User user : users) {
						if(user.getUsername().equals(username) && user.getPassword().equals(passwordHash)) {
							isValidLogin = true;
							validUser = user;
							break;
						}
					}
					if(isValidLogin) {
						LoginActionDAO loginActionDao = new LoginActionDAOMySQL();
						LoginAction loginAction = new LoginAction();
						loginAction.setIdUser(validUser.getIdUser());
						loginAction.setLoginTime(new Date().getTime());
						loginActionDao.addLoginAction(loginAction);
						nextJSP = "/WEB-INF/pages/main.jsp";
						request.setAttribute("user_bean", validUser);
						String token = sessions.createSession(validUser, 60 * 60 * 1000);
						Cookie authCookie = new Cookie("AUTHTOKEN", token);
						authCookie.setMaxAge(60 * 60);
						response.addCookie(authCookie);
					}else {
						request.setAttribute("status_text", "Neuspješna prijava<br>Provjerite unesene podatke");
						nextJSP = "/WEB-INF/pages/login.jsp";
					}
				} catch (NoSuchAlgorithmException e) {
					request.setAttribute("status_text", "500<br>Interna greška");
					nextJSP = "/WEB-INF/pages/error.jsp";
					e.printStackTrace();
				}
			}
		}else if(action.equals("registration")){
			String username = request.getParameter("username").trim();
			String password = request.getParameter("password").trim();
			String passwordSecond = request.getParameter("password_second").trim();
			String email = request.getParameter("email").trim();
			String firstName = request.getParameter("first_name").trim();
			String lastName = request.getParameter("last_name").trim();
			boolean canRegister = true;
			StringBuilder errorBuilder = new StringBuilder();
			UserDAOMySQL userDAO = new UserDAOMySQL();
			ArrayList<User> users = userDAO.getAllUsers();
			if(!password.equals(passwordSecond)) {
				errorBuilder.append("Lozinke se ne poklapaju<br>");
				canRegister = false;
			}
			for(User user : users) {
				if(user.getUsername().equals(username)) {
					errorBuilder.append("Korisničko ime se već koristi<br>");
					canRegister = false;
				}
				if(user.getEmail().equals(email)) {
					errorBuilder.append("E-mail adresa se već koristi<br>");
					canRegister = false;
				}
			}
			if(canRegister) {
				try {
					User toAdd = new User();
					MessageDigest md = MessageDigest.getInstance("SHA-256");
					byte[] usernameBytes = md.digest(username.getBytes());
					String idUser = Utility.bytesToHex(usernameBytes);
					byte[] passwordBytes = md.digest(password.getBytes());
					String passwordHex = Utility.bytesToHex(passwordBytes);
					toAdd.setIdUser(idUser).setUsername(username).setPassword(passwordHex).setEmail(email);
					toAdd.setFirstName(firstName).setLastName(lastName);
					boolean status = userDAO.addUser(toAdd);
					if(status) {
						request.setAttribute("user_bean", toAdd);
						String token = sessions.createSession(toAdd, 60 * 60 * 1000);
						Cookie authCookie = new Cookie("AUTHTOKEN", token);
						authCookie.setMaxAge(60 * 60);
						response.addCookie(authCookie);
						nextJSP = "/WEB-INF/pages/editprofile.jsp";
					}else {
						request.setAttribute("status_text", "500<br>Interna greška");
						nextJSP = "/WEB-INF/pages/registration.jsp";
					}
				} catch (NoSuchAlgorithmException e) {
					request.setAttribute("status_text", "500<br>Interna greška");
					e.printStackTrace();
				}
			}else {
				request.setAttribute("status_text", errorBuilder.toString());
				nextJSP = "/WEB-INF/pages/registration.jsp";
			}
		}
		else if(action.equals("goto_registration")) {
			nextJSP = "/WEB-INF/pages/registration.jsp";
		}else {
			System.out.println(action);
			request.setAttribute("status_text", "404<br>Nije moguće obraditi akciju " + action);
			nextJSP = "/WEB-INF/pages/error.jsp";
		}
		if(nextJSP == null) {
			nextJSP = "/WEB-INF/pages/login.jsp";
		}
		if(doRedirect) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(nextJSP);
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
