package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import Driver.RecommenderAPI;
import models.*;

public class MoviePage extends Controller
{
  public static void index(Integer id) throws Exception
  {
	  RecommenderAPI rec = Accounts.getRecommender();
	  User user = Accounts.getLoggedInUser();
	  
	     if (id != null) {
	    	Movie movie =  rec.movies.get(id);
	    	render(user, movie);	
	     } else {
		    Home.index();
	     }
  }
  
  public static void addRating(Integer id, String statusText) throws Exception{
	  RecommenderAPI rec = Accounts.getRecommender();
	  long unixTime = System.currentTimeMillis() / 1000L;
	  User user = Accounts.getLoggedInUser();
	  int rating = Integer.parseInt(statusText);
	  
	  Logger.info(" " + unixTime);
	  
	  rec.addRating(user.getUserID(), id, rating, unixTime);
	  index(id);
  }
  
  public static void addMovie(String title, int year, String genre, String videoReleaseDate, String url) throws Exception{
	  RecommenderAPI rec = Accounts.getRecommender();
	  rec.addMovie(title, year, videoReleaseDate, url, genre);
	  Profile.index();
  }
}