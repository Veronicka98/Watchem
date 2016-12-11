package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import Driver.RecommenderAPI;
import models.*;

public class Home extends Controller
{
  public static void index() throws Exception
  {
	  RecommenderAPI rec = Accounts.getRecommender();
	  Map<Integer, Movie> allmovies = rec.movies;
	  
	  String userId = session.get("logged_in_userid");
	  Logger.info(userId);
	  
	     if (userId != null) {
	    	User user = rec.users.get(Integer.parseInt(userId));
	    	Collection<Movie> movies = rec.getTopTenMovies();
	    	Collection<Movie> recs = rec.getUserRecommendations(user.getUserID());
	    	List<Rating> ratings = user.getRatings();
	    	render(user, movies, recs, allmovies, ratings);	
	     } else {
		   	Accounts.index();
	     }
  }
  
}