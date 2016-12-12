package controllers;

import play.*;
import play.db.jpa.Blob;
import play.mvc.*;

import java.util.*;

import Driver.RecommenderAPI;
import models.*;

public class PublicProfile extends Controller
{
  public static void index(int id) throws Exception
  {
	  RecommenderAPI rec = Accounts.getRecommender();
	  Map<Integer, Movie> allmovies = rec.movies;
	  User user = rec.users.get(id);
	  List<Rating> ratings = user.ratings;
	  render(user, ratings, allmovies);	

  
  }
}