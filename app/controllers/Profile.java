package controllers;

import play.*;
import play.db.jpa.Blob;
import play.mvc.*;

import java.util.*;

import Driver.RecommenderAPI;
import models.*;

public class Profile extends Controller
{
  public static void index() throws Exception
  {
	  RecommenderAPI rec = Accounts.getRecommender();
	  Map<Integer, Movie> allmovies = rec.movies;
	  String userId = session.get("logged_in_userid");
	     if (userId != null) {
	    	User user = rec.users.get(Integer.parseInt(userId));
	    	List<Rating> ratings = user.ratings;
	    	render(user, ratings, allmovies);	
	     } else {
		   	Accounts.index();
	     }
  }
  
  public static void changeStatus(String statusText) throws Exception
  {
    User user = Accounts.getLoggedInUser();
    user.setStatusText(statusText);
    Logger.info("Status changed to " + statusText);
    index();
  }
  
  public static void deleteRating(Long timestamp) throws Exception {
	  User user = Accounts.getLoggedInUser();
	  Rating todelete = null;
	  
	  
	  for(Rating rating : user.ratings) {
		  Logger.info(rating.getTimestamp() + " ");
		  if(rating.getTimestamp().equals(timestamp)) {
			  Logger.info("timestampmatch: " + timestamp);
			  todelete = rating;
		  }
	  }
	  
	  
	  if(todelete != null) {
		  user.ratings.remove(todelete);
		  user.merge();
	  }
	  
	  index();
  }
  
  public static void deleteUser(Integer id) throws Exception{
	  RecommenderAPI rec = Accounts.getRecommender();
	  rec.users.remove(id);
	  Accounts.index();
  }
  
  public static void deleteMovie(Integer id) throws Exception {
	  RecommenderAPI rec = Accounts.getRecommender();
	  Movie movie = rec.movies.get(id);
	  
	  for(User user : rec.users.values()) {
		  List<Rating> todelete = new ArrayList<Rating>();
		  for(Rating rating : user.ratings) {
			  if(rating.getObject2() == movie.getMovieID()) {
				  todelete.add(rating);
			  }
		  }
		  for(Rating rating : todelete) {
			  user.ratings.remove(rating);
			  user.merge();
		  }
	  }
	  
	  rec.movies.remove(id);
	  index();
  }
  
  
}