package net.etfbl.dao;

import java.util.ArrayList;

import net.etfbl.dao.beans.NewsEvent;

public interface NewsEventDAO {

	public ArrayList<NewsEvent> getAll();
	public boolean remove(NewsEvent toRemove); 
	
}
