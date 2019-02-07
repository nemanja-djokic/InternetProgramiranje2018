package net.etfbl.dao.beans;

public class ChangePasswordRequestBody {

	public ChangePasswordRequestBody() {
		this.token = null;
		this.oldPassword = null;
		this.newPassword = null;
		this.newPasswordRepeat = null;
	}
	
	public String token;
	public String oldPassword;
	public String newPassword;
	public String newPasswordRepeat;
	
}
