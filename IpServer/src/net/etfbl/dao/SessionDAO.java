package net.etfbl.dao;

import java.util.ArrayList;

import net.etfbl.dao.beans.Session;

public interface SessionDAO {

	public ArrayList<Session> getAllActive();
	public int setSessions(ArrayList<Session> sessions);
	public boolean removeSession(String sessionToken);
	public boolean clearSessions();
	
}
