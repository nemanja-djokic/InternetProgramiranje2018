package net.etfbl.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

import net.etfbl.dao.SessionDAO;
import net.etfbl.dao.beans.Session;
import net.etfbl.dao.beans.User;
import net.etfbl.dao.mysql.SessionDAOMySQL;
import net.etfbl.dao.mysql.UserDAOMySQL;
import net.etfbl.util.Utility;

public class Sessions {

	public Sessions() {
		this.activeSessions = new ArrayList<>();
	}
	
	private ArrayList<Session> activeSessions;
	private SessionDAO sessionDao = new SessionDAOMySQL();
	
	public String createSession(User user, long durationMilis) throws NoSuchAlgorithmException {
		removeExpired();
		for(Session session : this.activeSessions) {
			if(session.getIdUser().equals(user.getIdUser())) {
				session.setValidUntil(new Date().getTime() + durationMilis);
				return session.getSessionToken();
			}
		}
		boolean hasDuplicate;
		do {
			hasDuplicate = false;
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] tokenBytes = md.digest(new Date().toString().getBytes());
			String token = Utility.bytesToHex(tokenBytes);
			for(Session session : this.activeSessions) {
				if(session.getSessionToken().equals(token))
					hasDuplicate = true;
			}
			if(!hasDuplicate) {
				Session toAdd = new Session();
				toAdd.setIdUser(user.getIdUser()).setSessionToken(token).setValidUntil(new Date().getTime() + durationMilis);
				this.activeSessions.add(toAdd);
				sessionDao.setSessions(this.activeSessions);
				return token;
			}
		}while(hasDuplicate);
		sessionDao.setSessions(this.activeSessions);
		return null;
	}
	
	public boolean verifySession(String idUser, String sessionToken) {
		removeExpired();
		for(Session session : this.activeSessions) {
			if(session.getIdUser().equals(idUser) && session.getSessionToken().equals(sessionToken)) {
				sessionDao.setSessions(this.activeSessions);
				return true;
			}
		}
		sessionDao.setSessions(this.activeSessions);
		return false;
	}
	
	public User verifySession(String token) {
		if(token == null) {
			return null;
		}
		removeExpired();
		UserDAOMySQL userDao = new UserDAOMySQL();
		ArrayList<User> users = userDao.getAllUsers();
		for(Session session : this.activeSessions) {
			if(session.getSessionToken().equals(token)) {
				for(User user : users) {
					if(user.getIdUser().equals(session.getIdUser())) {
						sessionDao.setSessions(this.activeSessions);
						return user;
					}
				}
			}
		}
		sessionDao.setSessions(this.activeSessions);
		return null;
	}
	
	public void invalidate(String sessionToken) {
		sessionDao.removeSession(sessionToken);
		for(Session session : this.activeSessions) {
			if(session.getSessionToken().equals(sessionToken))
				session.setValidUntil(0);
		}
		removeExpired();
		sessionDao.setSessions(this.activeSessions);
	}
	
	private void removeExpired() {
		ArrayList<Session> toRemove = new ArrayList<>();
		for(Session session : this.activeSessions) {
			if(session.getValidUntil() < new Date().getTime())
				toRemove.add(session);
		}
		for(Session toRemoveSession : toRemove)
			this.activeSessions.remove(toRemoveSession);
	}
	
}
