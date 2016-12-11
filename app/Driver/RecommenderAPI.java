package Driver;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;

import edu.princeton.cs.introcs.Stopwatch;
import models.Movie;
import models.Rating;
import models.User;

public class RecommenderAPI {
	
	static File file = new File("data.xml");
	private static XMLSerializer xml = new XMLSerializer(file);

	static Data data = new Data();
	private static int userCounter = 1;//944;
	private static int movieCounter = 1;//1684;
		
	public static Map<Integer, User> users = new HashMap<Integer, User>();
	public static Map<String, User> emailIndex = new HashMap<String, User>();
	public static Map<Integer, Movie> movies = new HashMap<Integer, Movie>();
	public static Map<Integer, String> genres = new HashMap<Integer, String>();
	
	public static void main(String[] args) throws Exception {
				
//		data.loadOriginalData();
//		System.out.println("\n");
//		displayUserRatings(1);
//		System.out.println("\n");
//    	displayUserRatings(2);
//    	System.out.println("\n");
//    	displayUserRatings(3);
//    	System.out.println("\n");
//    	displayUserRatings(4);
//    	System.out.println("\n");
//    	displayUserRatings(5);
//    	System.out.println("\n");
//    	displayMovieRatings(2);
//    	for(User user : emailIndex.values()) {
//    		System.out.println("User: "+ user.getFirstName() + " Email: " + user.getEmail());
//    	}
//		userCounter = users.size()+1;
//		movieCounter = movies.size()+1;
//    	store();
		
		//.takes about 9 seconds for big data 
		//load();
//		List<Movie> topten = getTopTenMovies();
//		System.out.println("Top 10 movies:");
//		for(Movie movie : topten) {
//			System.out.println("Movie: " + movie.getTitle());
//		}
//		
//		displayUserRatings(4);
//		List<Movie> recommendations = getUserRecommendations(4);
//		System.out.println("\n"+users.get(4).getFirstName() + "'s Recommendations:");
//		for(Movie movie : recommendations) {
//			System.out.println("Movie: " + movie.getTitle());
//		}
//		displayUserRatings(5);
//		List<Movie> recommendations1 = getUserRecommendations(5);
//		System.out.println("\n"+users.get(5).getFirstName() + "'s Recommendations:");
//		for(Movie movie : recommendations1) {
//			System.out.println("Movie: " + movie.getTitle());
//		}
//		displayUserRatings(3);
//		List<Movie> recommendations2 = getUserRecommendations(3);
//		System.out.println("\n"+users.get(3).getFirstName() + "'s Recommendations:");
//		for(Movie movie : recommendations2) {
//			System.out.println("Movie: " + movie.getTitle());
//		}
//		displayUserRatings(2);
//		List<Movie> recommendations3 = getUserRecommendations(2);
//		System.out.println("\n"+users.get(2).getFirstName() + "'s Recommendations:");
//		for(Movie movie : recommendations3) {
//			System.out.println("Movie: " + movie.getTitle());
//		}
//		displayUserRatings(1);
//		List<Movie> recommendations5 = getUserRecommendations(1);
//		System.out.println("\n"+users.get(1).getFirstName() + "'s Recommendations:");
//		for(Movie movie : recommendations5) {
//			System.out.println("Movie: " + movie.getTitle());
//		}
		
	}
	
	public RecommenderAPI() {
		
	}	

	public  void clearDatabase() 
	  {
	    users.clear();
	    movies.clear();
	    genres.clear();
	  }
	
	public static Collection<User> getUsers() {
		return users.values();
	}
	public static Collection<Movie> getMovies() {
		return movies.values();
	}
	
	public static void setUserCounter(int setTo) {
		userCounter = setTo;
	}
	public static int getUserCounter() {
		return userCounter;
	}
	public static void setMovieCounter(int setTo) {
		movieCounter = setTo;
	}
	public static int getMovieCounter() {
		return movieCounter;
	}
	
	public static void addUser(String firstName, String lastName,int age,String gender,String occupation) throws Exception{
	    User user = new User(firstName, lastName, age, gender, occupation);
	    if(!users.containsKey(userCounter)) {
	    	user.setUserID(userCounter);
		    users.put(userCounter, user);
		    emailIndex.put(user.getEmail(), user);
		    userCounter++; 
	    } else {
	    	throw new Exception("user counter is wrong");
	    }
	}
	
	public static User getUser(int userID) throws Exception{
		if(users.containsKey(userID)) {
			return users.get(userID);
		} else {
			throw new Exception("user id doesnt exist");
		}
	}
	
	public static void removeUser(int userID) throws Exception {
		if(users.containsKey(userID)) {
			users.remove(userID);
		} else {
			throw new Exception("incorrect user id");
		}
	}
	
	public static void addMovie(String title,int year, String releaseDate, String url, String genre) throws Exception{
		Movie movie = new Movie(title, year, releaseDate, url, genre);
		movie.setMovieID(movieCounter);
		if(!movies.containsKey(movieCounter)) {
			movies.put(movieCounter, movie);
			movieCounter++;
		} else {
			throw new Exception("movie counter is wrong");
		}
	}
	
	public static Movie getMovie(int movieID) throws Exception{
		if(movies.containsKey(movieID)) {
			return movies.get(movieID);
		} else {
			throw new Exception("movie id doesnt exist");
		}
	}
	
	public static void removeMovie(int movieID) throws Exception {
		if(movies.containsKey(movieID)) {
			movies.remove(movieID);
		} else {
			throw new Exception("incorrect movie id");
		}
	}
	
	public static void addRating(int userID,int movieID,int rating, long timestamp) throws Exception{
		int[] ratings = {-5,-3,1,3,5};
		if(users.containsKey(userID) && movies.containsKey(movieID)) {
			if(ArrayUtils.contains(ratings, rating) && timestamp > 0) {
				Rating newRating = new Rating(userID, movieID, rating, timestamp);
				boolean lowerUser = removeDuplicates(userID, movieID, rating, timestamp, users.get(userID).getRatings());
				boolean lowerMovie = removeDuplicates(userID, movieID, rating, timestamp, movies.get(movieID).getRatings());
				if(!lowerUser && !lowerMovie) {
		     	   users.get(userID).addRating(newRating);
		     	   movies.get(movieID).addRating(newRating);
		        }
			}
		}
	}
	
	public static void removeRating(User user, Rating rating) throws Exception{
		if(user.getRatings().contains(rating)) {
			user.getRatings().remove(rating);
		} else {
			throw new Exception("User doesnt have this rating");
		}
	}
	
	
	public static List<Rating> getUserRatings(int userID) throws Exception{
		if(users.containsKey(userID)) {
			return users.get(userID).getRatings();
		} else {
			throw new Exception("movie id doesnt exist");
		}
	}
	
	public static List<Movie> getUserRecommendations(int userID) throws Exception {
		double similarity = 0;
		List<Double> similarities = new ArrayList<Double>();
		
		User thisUser = users.get(userID);
		List<Rating> thisUserRatings = new ArrayList<Rating>();
		for(Rating rating : thisUser.getRatings()) {
			thisUserRatings.add(rating);
		}
		
		for(User thatUser : users.values()) {
			List<Rating> differentRatings = new ArrayList<Rating>();
			similarity = 0;
			if(!thisUser.equals(thatUser)) {
				for(Rating rating : thatUser.getRatings()) {
					differentRatings.add(rating);
				}
				for(Rating thisrating : thisUserRatings) {
					for(Rating thatrating : thatUser.getRatings()) {
						if(thisrating.getObject2() == thatrating.getObject2()) {
							similarity = similarity + (thisrating.getRating()*thatrating.getRating());
							differentRatings.remove(thatrating);
							break;
						} 
					}
				}
			}
			similarities.add(similarity);
			thatUser.setSimilarity(0);
			thatUser.setSimilarity(similarity);
			thatUser.setDifferentRatings((List<Rating>) differentRatings);
				
		}
		Collections.sort(similarities);
		Collections.reverse(similarities);
		
		List<Movie> recommendations = new ArrayList<Movie>();
		
		for(Double thisSimilarity : similarities) {
			for(User user : users.values()) {
				if(recommendations.size() < 10) {
					if(!thisUser.equals(user) && user.getSimilarity() == thisSimilarity && !user.differentRatings.isEmpty()) {
						for(Rating rating : user.getDifferentRatings()) {
							if(recommendations.size() < 10) {
								if(!recommendations.contains(movies.get(rating.getObject2()))) {
									recommendations.add(movies.get(rating.getObject2()));
								}
							} else {
								break;
							}
						}
					}
				} else {
					break;
				}
			}
		}
		
		return recommendations;
	}
	
	

	public static List<Movie> getTopTenMovies() throws Exception{
		List<Rating> allRatings = new ArrayList<Rating>();
		List<Double> allTotals = new ArrayList<Double>();
		List<Movie> tenHighest = new ArrayList<Movie>();
		Set<Double> set = new HashSet<Double>();
		
		for(User user : users.values()) {
			allRatings.addAll(user.getRatings());
		}
		
		for(Movie movie : movies.values()) {
			double total = 0;
			for(Rating rating : movie.getRatings()) {
				total = total + rating.getRating();
			}
			int size = movie.getRatings().size();
			movie.setOverallRating(total/size);
			if(set.add(total/size)) {
				allTotals.add(total/size);
			}
		}

		Collections.sort(allTotals);
		Collections.reverse(allTotals);
		
		for(Double thisTotal : allTotals) {
			for(Movie thismovie : movies.values()) {
				if(thismovie.getOverallRating() == thisTotal) {
					if(tenHighest.size() < 10) {
						tenHighest.add(thismovie);
					} 
				}
			} 
		}

		return tenHighest;
		
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
		System.out.println("\n" + users.get(userID).getFirstName() + " rated the following movies: ");
		for(Rating rating : users.get(userID).getRatings()) {
			System.out.println("Movie: " + movies.get(rating.getObject2()).getTitle() + " " + rating.getTimestamp());
			System.out.println("Rating: " + rating.getRating());
		}
	}
	
	public static void displayMovieRatings(int movieID) {
		System.out.println("\n" + movies.get(movieID).getTitle()+" was rated by the following users:");
		for(Rating rating : movies.get(movieID).getRatings()) {
			System.out.println("User: " + users.get(rating.getObject1()).getFirstName());
			System.out.println("Rating: " + rating.getRating());
		}
	}
	
	public static boolean removeDuplicates(int user, int movie, int rating, Long timestamp, List<Rating> ratings) {
		boolean lower = false;
		List<Rating> dupRatings = new ArrayList<Rating>();
		 if(users.containsKey(user) && movies.containsKey(movie)) {
      	   for(Rating thisRating : ratings) {
      		   if(thisRating.getObject1() == user && thisRating.getObject2() == movie) {
      			   if( thisRating.getTimestamp() < timestamp) {
      				   dupRatings.add(thisRating);
      			   } else {
      				   lower = true;
      				   return lower;
      			   }
      		   }
      	   }
         }
		 for(Rating thisRating : dupRatings) {
	        	ratings.remove(thisRating);
	        }
		 return lower;
	}
	
	
	
	//XML//
	
		public static void load() throws Exception {
			xml.read();
			genres = (Map<Integer, String>) xml.pop();
			movies = (Map<Integer, Movie>) xml.pop();
			emailIndex = (Map<String, User>) xml.pop();
			users = (Map<Integer, User>) xml.pop();
			movieCounter = (Integer) xml.pop();
			userCounter = (Integer) xml.pop();
			
		}
		
		public static void store() throws Exception {
			xml.push(userCounter);
			xml.push(movieCounter);
			xml.push(users);
			xml.push(emailIndex);
			xml.push(movies);
			xml.push(genres);
			xml.write();
		}
}
