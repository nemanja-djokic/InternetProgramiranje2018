package net.etfbl.dao;

import java.util.ArrayList;

import net.etfbl.dao.beans.User;

public interface UserDAO {
	public boolean addUser(User user);
	public ArrayList<User> getAllUsers();
	public ArrayList<User> getConnectedUsers(User user);
	public ArrayList<User> getPendingConnections(User user);
	public boolean updateUser(User user);
	public boolean blockUser(String idUser);
}
