package controllers;

import play.*;
import play.mvc.*;
import java.util.*;

import Driver.Data;
import Driver.RecommenderAPI;
import models.*;
import play.db.jpa.Model;

import play.db.jpa.JPABase;
import models.User;

public class Accounts extends Controller {
	public static void signup()
	  {
		 
		    if (session.contains("logged_in_userid"))
		    {
		    	User user = Accounts.getLoggedInUser();
		    	user.logged_in = false;
		        user.save();
		        session.clear();
		        render();
		    }
		    else
		    {
		      render();
		    }
	  }

	public static void login()
	  {
		    if (session.contains("logged_in_userid"))
		    {
		    	User user = Accounts.getLoggedInUser();
		    	user.logged_in = false;
		        user.save();
		        session.clear();
		        render();
		    }
		    else
		    {
		      render();
		    }
	  }

	  public static void logout()
	  {
		User user = Accounts.getLoggedInUser();
		user.logged_in = false;
	    user.save();
	    session.clear();
	    index();
	  }

	public static void index()
	  {
		
		    if (session.contains("logged_in_userid"))
		    {
		    	User user = getLoggedInUser();
		    	user.logged_in = false;
		        user.save();
		        session.clear();
		        Collection<User> users = Data.users.values();
		        render(users);
		    }
		    else
		    {
		    	Collection<User> users = Data.users.values();
		        render(users);
		    }
	  }

	  public static User getLoggedInUser()
	  {
	    User user = null;
	    if (session.contains("logged_in_userid"))
	    {
	      String userId = session.get("logged_in_userid");
	      user = Data.users.get(userId);
	    }
	    else
	    {
	      login();
	    }
	    return user;
	  }
	  
	  public static void register(String firstName, String lastName, int age, String gender, String occupation)
	  {
	    Logger.info(firstName + " " + lastName + " " + age + " " + gender + " " + occupation);
	    RecommenderAPI.addUser(firstName, lastName, age, gender, occupation);
	    index();
	  }

	  public static void authenticate(String email, String password)
	  {
	    Logger.info("Attempting to authenticate with " + email + ":" + password);

	    User user = Data.emailIndex.get(email);
	    if ((user != null) && (user.checkPassword(password) == true))
	    {
	      Logger.info("Authentication successful");
	      session.put("logged_in_userid", user.id);
	      user.logged_in = true;
	      user.save();
	      Home.index();
	    }
	    else
	    {
	      Logger.info("Authentication failed");
	      login();
	    }
	  }
}
