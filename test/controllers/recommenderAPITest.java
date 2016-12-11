package controllers;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Driver.RecommenderAPI;
import models.Movie;
import models.Rating;
import models.User;
import play.test.UnitTest;

import static models.Fixtures.users;
import static models.Fixtures.ratings;
import static models.Fixtures.movies;

public class recommenderAPITest extends UnitTest {
	
	private static RecommenderAPI recommender;

	@Before
	public void setUp() throws Exception {
		recommender = new RecommenderAPI();
		int userCounter = 0;
		int movieCounter = 0;
		recommender.setUserCounter(userCounter);
		recommender.setMovieCounter(movieCounter);
		
		for(User user : users) {
			user.setUserID(userCounter);
			userCounter++;
			recommender.addUser(user.getFirstName(), user.getLastName(), user.getAge(), user.getGender(), user.getOccupation());
		}
		for(Movie movie : movies) {
			movie.setMovieID(movieCounter);
			movieCounter++;
			recommender.addMovie(movie.getTitle(), movie.getYear(), movie.getVideoReleaseDate(), movie.getURL(), movie.getGenre());
		}
		for(Rating rating : ratings) {
			recommender.addRating(rating.getObject1(), rating.getObject2(), rating.getRating(), rating.getTimestamp());
		}
		
//		for(User user : users) {
//			System.out.println(user.getUserID());
//		}
//		for(User user : recommender.users.values()) {
//			System.out.println(user.getUserID());
//		}
//		
//		for(Movie movie : movies) {
//			System.out.println(movie.getMovieID());
//		}
//		for(Movie movie : recommender.movies.values()) {
//			System.out.println(movie.getMovieID());
//		}
	}

	@After
	public void tearDown() throws Exception {
		recommender.clearDatabase();
		recommender = null;
	}

	@Test
	public void test() {
		assertTrue(!recommender.users.values().isEmpty());
	}
	
	@Test
	public void testUser() throws Exception{
		assertEquals (users.length, recommender.getUsers().size());
	    recommender.addUser("homer", "simpson",38, "M", "none");
	    assertEquals (users.length+1, recommender.getUsers().size());
	}
	
	@Test
	  public void testUsersAndEmailIndex()
	  {
	    assertEquals (users.length, recommender.getUsers().size());
	    for (User user: recommender.users.values())
	    {
	      User eachUser = recommender.emailIndex.get(user.getEmail());
	      assertEquals (user, eachUser);
	    }
	  }
	
	@Test
	public void testGetUser() throws Exception{
		for(User user : users) {
			assertEquals(user.getFirstName(),recommender.getUser(user.getUserID()).getFirstName());
		}
	}
	
	@Test
	  public void testDeleteUsers() throws Exception {
	    assertEquals (users.length, recommender.getUsers().size());
	    User marge = recommender.emailIndex.get("Marge-artist@mail.ru");
	    recommender.removeUser(marge.getUserID());
	    assertEquals (users.length-1, recommender.getUsers().size());    
	  }  
	
	@Test
	public void testMovie() throws Exception{
		assertEquals(movies.length , recommender.getMovies().size());
		recommender.addMovie("idk", 1995, "01-Jan-1997", "https://idk.com", "idk");
		assertEquals(movies.length+1, recommender.getMovies().size());
	}
	
	@Test
	public void testGetMovie() throws Exception{
		for(Movie movie : movies) {
			assertEquals(movie.getTitle(),recommender.getMovie(movie.getMovieID()).getTitle());
		}
	}

	@Test
	  public void testDeleteMovies() throws Exception {
	    assertEquals (movies.length, recommender.getMovies().size());
	    Movie shrek = recommender.movies.get(0);
	    recommender.removeMovie(shrek.getMovieID());
	    assertEquals (movies.length-1, recommender.getMovies().size());    
	  }
	
	@Test 
	public void testAddRating() throws Exception{
		List<Rating> ratings = recommender.getUser(0).getRatings();
		assertEquals(1, ratings.size());
		assertEquals(ratings.get(0).getRating() , 3);
		assertEquals((long)ratings.get(0).getTimestamp() , (long) 101);
		
		//to replace existing rating of the same movie
		recommender.addRating(0,2,1,(long) 155);
		List<Rating> newRatings = recommender.getUser(0).getRatings();
		assertEquals(1, newRatings.size());
		assertEquals(newRatings.get(0).getRating() , 1);
		assertEquals((long)newRatings.get(0).getTimestamp() , (long) 155);
	}
	
	@Test 
	public void testAddIncorrectRating() throws Exception{
		List<Rating> ratings = recommender.getUser(4).getRatings();
		assertEquals(0, ratings.size());
		//wrong user id
		recommender.addRating(40, 1, 5, (long) 166);
		ratings = recommender.getUser(4).getRatings();
		assertEquals(0, ratings.size());
		//wrong movie id
		recommender.addRating(4, 100, 5, (long) 166);
		ratings = recommender.getUser(4).getRatings();
		assertEquals(0, ratings.size());
		//wrong rating
		recommender.addRating(4, 1, 4, (long) 166);
		ratings = recommender.getUser(4).getRatings();
		assertEquals(0, ratings.size());
		//wrong negative timestamp
		recommender.addRating(4, 1, 5, (long) -166);
		ratings = recommender.getUser(4).getRatings();
		assertEquals(0, ratings.size());
		}
	@Test 
	public void testRemoveRating() throws Exception{
		User user = recommender.getUser(0);
		User user1 = recommender.getUser(1);
		Rating rating = user.getRatings().get(0);
		
		//removing rating from wrong user doesnt work
		try {
			recommender.removeRating(user1, rating);
			fail("should have failed");
		} catch (Exception e){
			assertTrue(true);
		}
		
		//removing rating from correct user works
		assertEquals(user.getRatings().size(), 1);
		recommender.removeRating(user, rating);
		assertEquals(user.getRatings().size(), 0);
	}
	
	@Test
	public void testGetUserRatings() throws Exception{
		List<Rating> ratings = recommender.getUser(0).getRatings();
		List<Rating> ratings1 = recommender.getUserRatings(0);
		
		assertEquals(ratings.size(), ratings1.size());
		assertEquals(ratings.get(0), ratings1.get(0));
	}
	
	@Test
	public void testUserRecommendations() throws Exception{
		//user with 1 rating, has 2 recommendations
		User user = recommender.getUser(0);
		assertEquals(user.getRatings().size(),1);
		List<Movie> recommendations = recommender.getUserRecommendations(0);
		assertEquals(recommendations.size(), 2);
		//user with no ratings, has 3 recommendations
		User user1 = recommender.getUser(4);
		assertEquals(user1.getRatings().size(), 0);
		List<Movie> recs = recommender.getUserRecommendations(4);
		assertEquals(recs.size(), 3);
		//user with 3 ratings, has 0 recommendations
		User user2 = recommender.getUser(2);
		assertEquals(user2.getRatings().size(), 3);
		List<Movie> recoms = recommender.getUserRecommendations(2);
		assertEquals(recoms.size(), 0);
	}
	
	@Test
	public void testGetTopTenMovies() throws Exception {
		List<Movie> topten = recommender.getTopTenMovies();
		assertEquals(topten.size(), movies.length);
		assertTrue(topten.get(0).overallRating > topten.get(1).overallRating && topten.get(1).overallRating > topten.get(2).overallRating);
	}
}

