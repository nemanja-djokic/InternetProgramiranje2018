package net.etfbl.dao.beans;

import com.google.gson.annotations.Expose;

public class User {
	
	@Expose private String idUser;
	@Expose private String username;
	@Expose(serialize = false) private String password;
	@Expose private String email;
	@Expose private String firstName;
	@Expose private String lastName;
	@Expose private String courseName;
	@Expose private String courseFacultyName;
	@Expose private String description;
	@Expose private int currentYear;

	public String getIdUser() {
		return this.idUser;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getCourseName() {
		return this.courseName;
	}
	
	public String getCourseFacultyName() {
		return this.courseFacultyName;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public int getCurrentYear() {
		return this.currentYear;
	}
	
	public User setIdUser(String idUser) {
		this.idUser = idUser;
		return this;
	}
	
	public User setUsername(String username) {
		this.username = username;
		return this;
	}
	
	public User setPassword(String password) {
		this.password = password;
		return this;
	}
	
	public User setEmail(String email) {
		this.email = email;
		return this;
	}
	
	public User setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	
	public User setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	
	public User setCourseName(String courseName) {
		this.courseName = courseName;
		return this;
	}
	
	public User setCourseFacultyName(String courseFacultyName) {
		this.courseFacultyName = courseFacultyName;
		return this;
	}
	
	public User setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public User setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
		return this;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		else if(!(o instanceof User))
			return false;
		else if(this == o)
			return true;
		else {
			User toCompare = (User)o;
			return toCompare.getUsername().equals(this.getUsername()) && toCompare.getEmail().equals(this.getEmail());
		}
	}
	
}
