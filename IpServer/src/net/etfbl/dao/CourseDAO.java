package net.etfbl.dao;

import java.util.ArrayList;

import net.etfbl.dao.beans.Course;
import net.etfbl.dao.beans.Faculty;

public interface CourseDAO {

	public ArrayList<Course> getAll();
	public boolean addCourse(Course course);
	public ArrayList<Course> getAllCoursesForFaculty(Faculty faculty);
	
}
