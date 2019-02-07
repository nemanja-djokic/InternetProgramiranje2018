package net.etfbl.dao.beans;

import com.google.gson.annotations.Expose;

public class Course {

	@Expose private String name;
	@Expose private String facultyName;
	@Expose private int durationYears;
	
	public Course setName(String name) {
		this.name = name;
		return this;
	}
	
	public Course setFacultyName(String facultyName) {
		this.facultyName = facultyName;
		return this;
	}
	
	public Course setDurationYears(int durationYears) {
		this.durationYears = durationYears;
		return this;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getFacultyName() {
		return this.facultyName;
	}
	
	public int getDurationYears() {
		return this.durationYears;
	}
	
}
