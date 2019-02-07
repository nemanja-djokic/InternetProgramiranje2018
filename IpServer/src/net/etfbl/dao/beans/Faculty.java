package net.etfbl.dao.beans;

import com.google.gson.annotations.Expose;

public class Faculty {

	@Expose private String name;
	
	public Faculty setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getName() {
		return this.name;
	}
	
}
