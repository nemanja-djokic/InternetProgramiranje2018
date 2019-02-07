package net.etfbl.dao.beans;

public class Session {
	private String idUser;
	private String sessionToken;
	private long validUntil;
	
	public Session setIdUser(String idUser) {
		this.idUser = idUser;
		return this;
	}
	
	public Session setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
		return this;
	}
	
	public Session setValidUntil(long validUntil) {
		this.validUntil = validUntil;
		return this;
	}
	
	public String getIdUser() {
		return this.idUser;
	}
	
	public String getSessionToken() {
		return this.sessionToken;
	}
	
	public long getValidUntil() {
		return this.validUntil;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		else if(!(o instanceof Session))
			return false;
		else {
			Session toCompare = (Session)o;
			return this.getSessionToken().equals(toCompare.getSessionToken());
		}
	}
	
	@Override
	public String toString() {
		return this.sessionToken + " " + this.idUser + " " + String.valueOf(this.validUntil);
	}
}