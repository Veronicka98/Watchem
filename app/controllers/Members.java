package controllers;

import play.*;
import play.db.jpa.GenericModel;
import play.mvc.*;

import java.util.*;

import Driver.RecommenderAPI;
import models.*;

public class Members extends Controller
{
  public static void index() throws Exception
  {
	    RecommenderAPI rec = Accounts.getRecommender();
	    
	    List<User> users = new ArrayList<User>();
	    
	    Collection<User> myUsers = rec.users.values();
	    for(User user : myUsers) {
	    	if(!users.contains(user)) {
	    		users.add(user);
	    	}
	    }
	    
	    for(User user : users) {
	    	Logger.info(user.getFirstName() + " ");
	    }
	    
	    String userId = session.get("logged_in_userid");
	    if (userId != null) {
	    	User user = rec.users.get(Integer.parseInt(userId));
	    	Logger.info(user.getFirstName());
	    	users.remove(user);
	        render(user, users);
	    } else {
	    	Accounts.index();
	    }
  }
  
}