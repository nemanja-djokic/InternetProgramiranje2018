package net.etfbl.dao.beans;

import java.io.IOException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import net.etfbl.dao.AdministratorDAO;
import net.etfbl.dao.mysql.AdministratorDAOMySQL;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {

	private String username;
	private String password;
	private String statusMessage;
	private Administrator validAdministrator;
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	public String getStatusMessage() {
		return this.statusMessage;
	}
	
	public void setValidAdministrator(Administrator validAdministrator) {
		this.validAdministrator = validAdministrator;
	}
	
	public Administrator getValidAdministrator() {
		return this.validAdministrator;
	}
	
	public String onLoad() throws IOException {
		if(this.validAdministrator != null) {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	        ec.redirect(ec.getRequestContextPath() + "/main.xhtml");
			return "main.xhtml?faces-redirect=true";
		}else
			return null;
	}
	
	public String doLogin() {
		AdministratorDAO administratorDao = new AdministratorDAOMySQL();
		ArrayList<Administrator> administrators = administratorDao.getAll();
		Administrator validAdministrator = null;
		for(Administrator administrator : administrators) {
			if(username != null && password != null) {
				if(username.equals(administrator.getUsername()) && password.equals(administrator.getPassword())){
					validAdministrator = administrator;
					break;
				}
			}
		}
		if(validAdministrator != null) {
			this.validAdministrator = validAdministrator;
			return "main.xhtml?faces-redirect=true";
		}else {
			return null;
		}
	}
	
}
