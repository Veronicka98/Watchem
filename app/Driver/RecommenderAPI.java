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
//		userCounter = users.size()+1;
//		movieCounter = movies.size()+1;
//    	store();
//		
		//.takes about 9 seconds for big data 
		//load();
		
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
	
	public static void addUser(String firstName, String lastName,int age,String gender,String occupation) {
	    try {
			User user = new User(firstName, lastName, age, gender, occupation);
		    if(!users.containsKey(userCounter)) {
		    	user.setUserID(userCounter);
			    users.put(userCounter, user);
			    emailIndex.put(user.getEmail(), user);
			    userCounter++; 
		    } 
	    } catch(Exception e) {
	    	System.out.println("user counter is wrong");
	    }
	}
	
	public static User getUser(int userID){
		try {
			if(users.containsKey(userID)) {
				return users.get(userID);
			} 
		} catch(Exception e) {
			System.out.println("user id doesnt exist");
		}
		return null;
	}
	
	public static void removeUser(int userID) {
		try {
			if(users.containsKey(userID)) {
				users.remove(userID);
			} 
		} catch (Exception e) {
			System.out.println("incorrect user id");
		}
	}
	
	public static void addMovie(String title,int year, String releaseDate, String url, String genre) {
		try {
			Movie movie = new Movie(title, year, releaseDate, url, genre);
			movie.setMovieID(movieCounter);
			if(!movies.containsKey(movieCounter)) {
				movies.put(movieCounter, movie);
				movieCounter++;
			} 
		} catch(Exception e) {
			System.out.println("movie counter is wrong");
		}
	}
	
	public static Movie getMovie(int movieID) {
		try {
			if(movies.containsKey(movieID)) {
				return movies.get(movieID);
			} 	
		} catch(Exception e) {
			System.out.println("movie id doesnt exist");
		}
		return null;
	}
	
	public static void removeMovie(int movieID) {
		try {
			if(movies.containsKey(movieID)) {
				movies.remove(movieID);
			}
		} catch(Exception e) {
			System.out.println("incorrect movie id");
		}
	}
	
	public static void addRating(int userID,int movieID,int rating, long timestamp) {
		try {
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
		} catch (Exception e) {
			System.out.println("something went wrong");
		}
	}
	
	public static void removeRating(User user, Rating rating) {
		try {
			if(user.getRatings().contains(rating)) {
				user.getRatings().remove(rating);
			} 
		} catch(Exception e) {
			System.out.println("User doesnt have this rating");
		}
	}
	
	
	public static List<Rating> getUserRatings(int userID){
		try {
			if(users.containsKey(userID)) {
				return users.get(userID).getRatings();
			} 
		} catch (Exception e) {
			System.out.println("movie id doesnt exist");
		}
		return null;
	}
	
	public static List<Movie> getUserRecommendations(int userID)  {
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
	
	

	public static List<Movie> getTopTenMovies() {
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
	
		public static void load() {
			try {
				xml.read();
				genres = (Map<Integer, String>) xml.pop();
				movies = (Map<Integer, Movie>) xml.pop();
				emailIndex = (Map<String, User>) xml.pop();
				users = (Map<Integer, User>) xml.pop();
				movieCounter = (Integer) xml.pop();
				userCounter = (Integer) xml.pop();
			} catch (Exception e) {
				System.out.println("loading unsuccessful");
			}
			
		}
		
		public static void store() throws Exception{
			try {
				xml.push(userCounter);
				xml.push(movieCounter);
				xml.push(users);
				xml.push(emailIndex);
				xml.push(movies);
				xml.push(genres);
				xml.write();
			} catch(Exception e) {
				System.out.println("string unsuccessful");
			}
		}
}
