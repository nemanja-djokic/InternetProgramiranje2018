package net.etfbl.dao;

import java.util.ArrayList;

import net.etfbl.dao.beans.LoginAction;

public interface LoginActionDAO {

	public ArrayList<LoginAction> getAll();
	public boolean addLoginAction(LoginAction loginAction);
	
}
