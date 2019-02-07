package net.etfbl.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.etfbl.dao.LoginActionDAO;
import net.etfbl.dao.beans.LoginAction;
import net.etfbl.dao.mysql.LoginActionDAOMySQL;

@WebService
@Path("/rest")
public class LoginStatistics {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/loginstatistics")
	public String getStatistics() {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		LoginActionDAO loginActionDao = new LoginActionDAOMySQL();
		ArrayList<LoginAction> logins = loginActionDao.getAll();
		ArrayList<ArrayList<LoginAction>> perHour = new ArrayList<>();
		for(int i = 0; i < 24 ; i++)
			perHour.add(new ArrayList<LoginAction>());
		long current = new java.util.Date().getTime();
		for(LoginAction login : logins) {
			long difference = current - login.getLoginTime();
			int indexOfList = (int)Math.floor(difference / (60 * 60 * 1000));
			boolean olderThan24h = false;
			if((new java.util.Date().getTime() - login.getLoginTime()) > 24 * 60 * 60 * 1000) {
				olderThan24h = true;
			}
			if(!olderThan24h) {
				ArrayList<LoginAction> listToUse = perHour.get(indexOfList);
				boolean alreadyContained = false;
				for(LoginAction addedLogin : listToUse) {
					if(login.getIdUser().equals(addedLogin.getIdUser()))
						alreadyContained = true;
					if(alreadyContained) {
						long latest = (login.getLoginTime() > addedLogin.getLoginTime())? login.getLoginTime() : addedLogin.getLoginTime();
						addedLogin.setLoginTime(latest);
					}
				}
				if(!alreadyContained)
					listToUse.add(login);
			}
		}
		int[] numberOfUsers = new int[24];
		String[] labels = new String[24];
		Object[] toReturn = new Object[2];
		toReturn[0] = numberOfUsers;
		toReturn[1] = labels;
		for(int i = 0 ; i < 24 ; i++) {
			numberOfUsers[23 - i] = perHour.get(i).size();
			labels[23 - i] = new SimpleDateFormat("dd.MM HH:mm").format(new java.util.Date(current - ((i + 1) * 60 * 60 * 1000)))
					+ " - " +new SimpleDateFormat("dd.MM HH:mm").format(new java.util.Date(current - (i * 60 * 60 * 1000)));
		}
		return gson.toJson(toReturn);
	}
	
}
