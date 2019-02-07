package net.etfbl.dao;

import java.util.ArrayList;

import net.etfbl.dao.beans.UserConnection;
import net.etfbl.dao.beans.User;

public interface UserConnectionDAO {

	public ArrayList<UserConnection> getAllConnections();
	public ArrayList<UserConnection> getConnectionsForUser(User user);
	public ArrayList<UserConnection> getConnectionsForUser(String idUser);
	public boolean addConnection(UserConnection connection);
	public boolean acceptConnection(String acceptingUser, String acceptedUser);
	public boolean cancelRequest(String cancellingUser, String cancelledUser);
	
}
