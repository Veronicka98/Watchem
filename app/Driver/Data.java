package Driver;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.Table;

import models.Movie;
import models.Rating;
import models.User;
import play.db.jpa.Model;

@Entity
@Table(name="`Data`")
public class Data {
	
	static RecommenderAPI r = new RecommenderAPI();
	
	public Data() {
		
	}
	
	public static void loadOriginalData() throws Exception {
		loadUsers();
		loadGenres();
		loadMovies();
		addRatings();
		
	}
	
	public static void loadUsers() throws Exception {
	      InputStream usersFile = new FileInputStream("./bigMovieData/users.dat");
	      @SuppressWarnings("resource")
		  BufferedReader inUsers = new BufferedReader(new InputStreamReader(usersFile));
	      
	        //each field is separated(delimited) by a '|'
	        String delims = "[|]";
	        String userDetails;  
	        while ((userDetails = inUsers.readLine()) != null) {

	            // parse user details string
	            String[] userTokens = userDetails.split(delims);

	            // output user data to console.
	            if (userTokens.length == 7) {
//	                System.out.println(userTokens[0]+" "+
//	                        userTokens[1]+" " + userTokens[2]+" "+
//	                        Integer.parseInt(userTokens[3])+" "+userTokens[4]+" "+
//	                        userTokens[5] + " " +  userTokens[6]);
	                
	                User user = new User(userTokens[1], userTokens[2], Integer.parseInt(userTokens[3]), userTokens[4], userTokens[5]);
	                user.setUserID(Integer.parseInt(userTokens[0]));
	                r.users.put(Integer.parseInt(userTokens[0]),user);
	                r.emailIndex.put(user.getEmail(), user);
	                
	            }else
	            {
	                throw new Exception("Invalid member length: "+userTokens.length);
	            }
	        }
	}
	
	public static void loadGenres() throws Exception {
	      InputStream genreFile = new FileInputStream("./bigMovieData/genre.dat");
	      @SuppressWarnings("resource")
		  BufferedReader inGenres = new BufferedReader(new InputStreamReader(genreFile));
	      
	        //each field is separated(delimited) by a '|'
	        String delims = "[|]";
	        String genre;  
	        while ((genre = inGenres.readLine()) != null) {

	            // parse user details string
	            String[] genreTokens = genre.split(delims);

	            // output user data to console.
	            if (genreTokens.length == 2) {
//	                System.out.println(genreTokens[0]+" "+
//	                		genreTokens[1] );
	                
	                r.genres.put(Integer.parseInt(genreTokens[1]), genreTokens[0]);
	                
	            }else
	            {
	                throw new Exception("Invalid genre info length: "+genreTokens.length);
	            }
	        }
	}
	
	public static void loadMovies() throws Exception {
	      InputStream movieFile = new FileInputStream("./bigMovieData/items.dat");
	      @SuppressWarnings("resource")
		  BufferedReader inMovies = new BufferedReader(new InputStreamReader(movieFile));
	      
	        //each field is separated(delimited) by a '|'
	        String delims = "[|]";
	        String movieDetails;  
	        while ((movieDetails = inMovies.readLine()) != null) {

	            // parse user details string
	            String[] movieTokens = movieDetails.split(delims);

	            // output user data to console.
	            if (movieTokens.length == 23) {
//	                System.out.println(movieTokens[0]+" "+
//	                		movieTokens[1]+" " + movieTokens[2]+" "+
//	                		movieTokens[3]);
	            	int genre = 0;
	                for(int i = 5;i < 23;i++) {
	                	if(Integer.parseInt(movieTokens[i]) == 1) {
	                		genre = i-5;
	                	}
	                }
	                //looks for brackets in a string that have only integers from 0-9
	                Pattern pattern = Pattern.compile("\\((\\d+)\\)");
	                Matcher matcher = pattern.matcher(movieTokens[1]);
	                int year = 0;
	                while(matcher.find()){
	                    year = Integer.parseInt(matcher.group(1));
	                }
	                Movie movie = new Movie(movieTokens[1],year, movieTokens[2], movieTokens[3], r.genres.get(genre));
	                movie.setMovieID(Integer.parseInt(movieTokens[0]));
	                r.movies.put(Integer.parseInt(movieTokens[0]), movie);
	                
	            }else
	            {
	                throw new Exception("Invalid movie info length: "+movieTokens.length);
	            }
	        }
	}
	
	
	
	public static void addRatings() throws Exception {
		InputStream ratingsFile = new FileInputStream("./bigMovieData/ratings.dat");
	      @SuppressWarnings("resource")
		  BufferedReader inGenres = new BufferedReader(new InputStreamReader(ratingsFile));
	      
	      //each field is separated(delimited) by a '|'
	        String delims = "[|]";
	        String ratings; 
	        if(!r.users.isEmpty()) {
		        while ((ratings = inGenres.readLine()) != null) {
	
		            // parse user details string
		            String[] ratingTokens = ratings.split(delims);
	
		            // output user data to console.
		            if (ratingTokens.length == 4) {
//		                System.out.println(ratingTokens[0]+" "+
//		                		ratingTokens[1] + " " + ratingTokens[2] + " " + ratingTokens[3]);
//		               
		               int user= Integer.parseInt(ratingTokens[0]);
		               int movie = Integer.parseInt(ratingTokens[1]);
		               int rating = Integer.parseInt(ratingTokens[2]);
		               Long timestamp = Long.parseLong(ratingTokens[3]);
		               
		               Rating newRating = new Rating(user, movie, rating, timestamp);
		               
		               boolean lower = r.removeDuplicates(user, movie, rating, timestamp, r.users.get(user).getRatings());
		               if(!lower) {
		            	   System.out.println("Rating added to " + r.users.get(user).getFirstName() + " " + lower);
		            	   r.users.get(user).addRating(newRating);
		            	   r.movies.get(movie).addRating(newRating);
		               }
		            }else {
		                throw new Exception("Invalid rating info length: "+ratingTokens.length);
		            }
		        }
	        } else {
	        	throw new Exception("User list is empty");
	        }
	        
	        
	}
	
	
	
	
	

}
