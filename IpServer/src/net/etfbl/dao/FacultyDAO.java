package net.etfbl.dao;

import java.util.ArrayList;

import net.etfbl.dao.beans.Faculty;

public interface FacultyDAO {

	public ArrayList<Faculty> getAll();
	public boolean addFaculty(Faculty faculty);
	
}
