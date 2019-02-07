package net.etfbl.dao.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import net.etfbl.dao.EventDAO;
import net.etfbl.dao.mysql.EventDAOMySQL;
import net.etfbl.dto.Event;

@ManagedBean(name = "eventBean")
@RequestScoped
public class EventBean implements Serializable {
	
	private static final long serialVersionUID = 5952542398016844552L;

	public EventBean() {
		categories.add("Ispit");
		categories.add("Predavanje");
		categories.add("Promocija");
		categories.add("Praksa");
		categories.add("Putovanje");
		categories.add("Ostalo");
	}
	
	private Event event = new Event();
	private Date startDate = new Date();
	private Date endDate = new Date();
	private List<String> categories = new ArrayList<>();
	
	public List<String> getCategories(){
		return this.categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	
	public Event getEvent() {
		return this.event;
	}
	
	public void setEvent(Event event) {
		this.event = event;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Date getEndDate() {
		return this.endDate;
	}
	
	public Date getStartDate() {
		return this.startDate;
	}
	
	public String saveEvent() {
		EventDAO eventDao = new EventDAOMySQL();
		event.setStartsOn(startDate.getTime());
		event.setEndsOn(endDate.getTime());
		eventDao.addEvent(event);
		this.event.setCategory(null);
		this.event.setDescription(null);
		this.event.setDislikes(null);
		this.event.setLikes(null);
		this.event.setEndsOn(0);
		this.event.setIdEvent(0);
		this.event.setImageUrl(null);
		this.event.setStartsOn(0);
		this.event.setTitle(null);
		return null;
	}
	
}
