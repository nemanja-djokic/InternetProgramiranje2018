package net.etfbl.dao.beans;

public class UserConnection {

	private String idUser1;
	private String idUser2;
	private boolean isPending;
	
	public UserConnection setIdUser1(String idUser1) {
		this.idUser1 = idUser1;
		return this;
	}
	
	public UserConnection setIdUser2(String idUser2) {
		this.idUser2 = idUser2;
		return this;
	}
	
	public UserConnection setIsPending(boolean isPending) {
		this.isPending = isPending;
		return this;
	}
	
	public String getIdUser1() {
		return this.idUser1;
	}
	
	public String getIdUser2() {
		return this.idUser2;
	}
	
	public boolean getIsPending() {
		return this.isPending;
	}
	
}
