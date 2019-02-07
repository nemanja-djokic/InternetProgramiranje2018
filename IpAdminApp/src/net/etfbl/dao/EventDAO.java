package net.etfbl.dao;

import java.util.ArrayList;

import net.etfbl.dao.beans.Event;

public interface EventDAO {
	public ArrayList<Event> getAll();
	public boolean addEvent(Event event);
	public boolean giveRating(int idEvent, String idUser, boolean isLike);
}
