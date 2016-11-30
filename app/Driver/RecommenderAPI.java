package Driver;

import java.util.Collection;
import java.util.Map;

import models.Movie;
import models.User;

public class RecommenderAPI {

	static Data data = new Data();
	private static int userCounter = 944;
	private static int movieCounter = 1684;
	
	public static void main(String[] args) throws Exception {
				
//		data.loadOriginalData();
//		data.displayUserRatings(2);
//		data.saveData();
		
		data.retrieveData();
		data.displayUserRatings(3);
		
	}
	
	public RecommenderAPI() {
		
	}
	
	public static void addUser(String firstName, String lastName,int age,String gender,String occupation) {
	    User user = new User(firstName, lastName, age, gender, occupation);
	    user.save();
	    user.setUserID(userCounter);
	    Data.users.put(userCounter, user);
	    
	    userCounter++;
	}
	
	public static void removeUser(int userID) {
		Data.users.remove(userID);
	}
	
	public static void addMovie(String title,int year, String releaseDate, String url, String genre) {
		Movie movie = new Movie(title, year, releaseDate, url, genre);
		movie.save();
		Data.movies.put(movieCounter, movie);
		movieCounter++;
	}
	
//	public static void addRating(int userID,int movieID,int rating) {
//		Data.users.get(userID).ratings.put(movieID, rating);
//		Data.movies.get(movieID).ratings.put(userID,rating);
//	}
	
	public static Movie getMovie(int movieID) {
		return Data.movies.get(movieID);
	}
	
//	public static Map<Integer, Integer> getUserRatings(int userID) {
//		return Data.users.get(userID).getMovieRatings();
//	}
//	
//	public static User getUserRecommendations(int userID) {
//		Map<Integer, Integer> ratings = Data.users.get(userID).getMovieRatings();
//		//instead of a map try list for Ratings = create class Rating
//		//maybe the map saved to xml will save the list?
//		//sort list by values?
//		//entriesSortedByValues(ratings);
//		return null;
//	}
	
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
	
}
