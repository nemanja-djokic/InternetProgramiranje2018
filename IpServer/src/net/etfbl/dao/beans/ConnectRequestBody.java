package net.etfbl.dao.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConnectRequestBody{
	
	public ConnectRequestBody() {
		this.userid = "";
		this.sessiontoken = "";
	}
	
	@XmlElement public String userid;
	@XmlElement public String sessiontoken;
}