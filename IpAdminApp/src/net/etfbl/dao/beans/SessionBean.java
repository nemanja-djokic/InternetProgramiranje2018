package net.etfbl.dao.beans;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import net.etfbl.dao.NewsEventDAO;
import net.etfbl.dao.SessionDAO;
import net.etfbl.dao.UserDAO;
import net.etfbl.dao.mysql.NewsEventDAOMySQL;
import net.etfbl.dao.mysql.SessionDAOMySQL;
import net.etfbl.dao.mysql.UserDAOMySQL;
import net.etfbl.util.Utility;

@ManagedBean(name = "sessionBean")
@ViewScoped
public class SessionBean {

	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;
	
	private SessionDAO sessionDao = new SessionDAOMySQL();
	private UserDAO userDao = new UserDAOMySQL();
	private NewsEventDAO newsEventDao = new NewsEventDAOMySQL();
	
	private String activeUsers = String.valueOf(sessionDao.getAllActive().size());
	private String allUsers = String.valueOf(userDao.getAllUsers().size());
	
	private List<User> userList = userDao.getAllUsers();
	private List<NewsEvent> newsAndEventsList = newsEventDao.getAll();
	
	public LoginBean getLoginBean() {
		return this.loginBean;
	}
	
	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
	
	public String getActiveUsers() {
		return this.activeUsers;
	}
	
	public void setActiveUsers(String activeUsers) {
		this.activeUsers = activeUsers;
	}
	
	public String updateActiveUsers() {
		activeUsers = String.valueOf(sessionDao.getAllActive().size());
		return null;
	}
	
	public String updateAllUsers() {
		allUsers = String.valueOf(userDao.getAllUsers().size());
		return null;
	}
	
	public String updateUserList() {
		this.userList = userDao.getAllUsers();
		return null;
	}
	
	public String updateNewsAndEventsList() {
		this.newsAndEventsList = this.newsEventDao.getAll();
		return null;
	}
	
	public String onLoad() throws IOException {
		if(loginBean.getValidAdministrator() == null) {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	        ec.redirect(ec.getRequestContextPath() + "/login.xhtml");
			return "login.xhtml?faces-redirect=true";
		}else {
			return null;
		}
	}
	
	public String deleteNewsEvent() {
		System.out.println("HERE");
		Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if(map.containsKey("typeToDelete") && map.containsKey("idToDelete")) {
			String type = map.get("typeToDelete");
			int id = Integer.valueOf(map.get("idToDelete"));
			NewsEvent toRemove = new NewsEvent();
			toRemove.setType(type);
			toRemove.setId(id);
			toRemove.setTitle("");
			newsEventDao.remove(toRemove);
			this.newsAndEventsList = newsEventDao.getAll();
			return "main.xhtml?faces-redirect=true";
		}else {
			System.out.println("ERR");
		}
		return null;
	}
	
	public String blockUser() {
		Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if(map.containsKey("idUserToBlock")) {
			String idUser = map.get("idUserToBlock");
			userDao.blockUser(idUser);
			this.userList = userDao.getAllUsers();
			return "main.xhtml?faces-redirect=true";
		}else {
			System.out.println("ERR");
		}
		return null;
	}
	
	public String resetPassword() throws NoSuchAlgorithmException{
		Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if(map.containsKey("idUserToReset")) {
			String idUser = map.get("idUserToReset");
			User userToReset = null;
			for(User user : this.userList)
				if(user.getIdUser().equals(idUser))
					userToReset = user;
			if(userToReset != null) {
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				byte[] hash = md.digest(new java.util.Date().toString().getBytes());
				userToReset.setPassword(Utility.bytesToHex(hash));
				userDao.updateUser(userToReset);
			}
			this.userList = userDao.getAllUsers();
			return "main.xhtml?faces-redirect=true";
		}else {
			System.out.println("ERR");
		}
		return null;
	}

	public String getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(String allUsers) {
		this.allUsers = allUsers;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<NewsEvent> getNewsAndEventsList() {
		return newsAndEventsList;
	}

	public void setNewsAndEventsList(List<NewsEvent> newsAndEventsList) {
		this.newsAndEventsList = newsAndEventsList;
	}
	
}
