package net.etfbl.dao.beans;

public class LoginAction {
	
	private int idLoginAction;
	private String idUser;
	private long loginTime;
	public int getIdLoginAction() {
		return idLoginAction;
	}
	public void setIdLoginAction(int idLoginAction) {
		this.idLoginAction = idLoginAction;
	}
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	public long getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}
	
}
