package Driver;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;


import models.Movie;
import models.Rating;
import models.User;

public class RecommenderAPI {
	
	static File file = new File("data.xml");
	private static XMLSerializer xml = new XMLSerializer(file);

	static Data data = new Data();
	private static int userCounter = 944;
	private static int movieCounter = 1684;
	
	public static List<Map> dataMap = new ArrayList<Map>();
	public static List<Rating> allRatings = new ArrayList<Rating>();
	
	public static Map<Integer, User> users = new Hashtable<>();
	public static Map<String, User>   emailIndex      = new HashMap<>();
	public static Map<Integer, Movie> movies = new Hashtable<>();
	public static Map<Integer, String> genres = new Hashtable<>();
	
	public static void main(String[] args) throws Exception {
				
//		data.loadOriginalData();
//    	displayUserRatings(2);
//    	store();
		
		load();
		displayUserRatings(2);
		
	}
	
	public RecommenderAPI() {
		
	}	

	public  void clearDatabase() 
	  {
	    users.clear();
	    emailIndex.clear();
	    movies.clear();
	    genres.clear();
	  }
	
	public static void addUser(String firstName, String lastName,int age,String gender,String occupation) {
	    User user = new User(firstName, lastName, age, gender, occupation);
	    user.save();
	    user.setUserID(userCounter);
	    users.put(userCounter, user);
	    userCounter++;
	}
	
	public static void removeUser(int userID) {
		users.remove(userID);
	}
	
	public static void addMovie(String title,int year, String releaseDate, String url, String genre) {
		Movie movie = new Movie(title, year, releaseDate, url, genre);
		movie.setMovieID(movieCounter);
		movie.save();
		movies.put(movieCounter, movie);
		movieCounter++;
	}
	
	public static void addRating(int userID,int movieID,int rating) {
		 data.removeDuplicateRatings(userID, movieID, rating);
		 allRatings.add(new Rating(userID, movieID, rating));
	}
	
	public static Movie getMovie(int movieID) {
		return movies.get(movieID);
	}
	
	public static List<Rating> getUserRatings(int userID) {
		List<Rating> ratings = new ArrayList<Rating>();
		for(Rating rating : allRatings) {
			if(rating.getObject1() == userID) {
				ratings.add(rating);
			}
		}
		return ratings;
	}
	
	public static List<Movie> getUserRecommendations(int userID) {
		
		return null;
	}
	
//	public void addUser(String firstName, String lastName,int age,String gender,String occupation);
//	public void removeUser(int userID);
//	public void addMovie(String title,int year, String releaseDate, String url);
//	public void addRating(int userID,int movieID,int rating);
//	public Movie getMovie(int movieID);
//	public Map<Integer, Integer> getUserRatings(int userID);
//	public User getUserRecommendations(int userID);
//	public List<Movie> getTopTenMovies();
//	public Map<Integer,List<Map>>load();
//	public void write();
	
	public static void displayUserRatings(int userID) {
		System.out.println(users.get(userID).getFirstName() + " rated the following movies: ");
		for(Rating rating : allRatings) {
			if(rating.getObject1() == userID) {
				System.out.println("Movie: " + movies.get(rating.getObject2()).getTitle());
				System.out.println("Rating: " + rating.getRating());
			}
		}
	}
	
	public static void displayMovieRatings(int movieID) {
		System.out.println("\n" + movies.get(movieID).getTitle()+" was rated by the following users:");
		for(Rating rating : allRatings) {
			if (rating.getObject2() == movieID) {
				System.out.println("User: " + users.get(rating.getObject1()).getFirstName() + " " + users.get(rating.getObject2()).getLastName());
				System.out.println("Rating: " + rating.getRating());
			}
		}
	}
	
	
	
	//XML//
		@SuppressWarnings("unchecked")
		public static void load() throws Exception {
			xml.read();
			allRatings = (List<Rating>) xml.pop();
			genres = (Map<Integer, String>) xml.pop();
			movies = (Map<Integer, Movie>) xml.pop();
			emailIndex = (Map<String, User>) xml.pop();
			users = (Map<Integer, User>) xml.pop();
			
		}
		
		static void store() throws Exception {
			System.out.println(users.isEmpty());
			xml.push(users);
			xml.push(emailIndex);
			xml.push(movies);
			xml.push(genres);
			xml.push(allRatings);
			xml.write();
		}
}
