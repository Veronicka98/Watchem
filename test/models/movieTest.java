package models;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

public class movieTest extends UnitTest {
	
	Movie movie;

	@Before
	public void setUp() throws Exception {
		movie = new Movie("Scorpion", 1998, "13-Feb-1998", "https://imdbScorpion.com", "Drama");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructor() {
		assertEquals(movie.getTitle(), "Scorpion");
		assertEquals(movie.getVideoReleaseDate(), "13-Feb-1998");
		assertEquals(movie.getYear(), 1998);
		assertEquals(movie.getURL(), "https://imdbScorpion.com");
		assertEquals(movie.getGenre(), "Drama");
	}
	
	@Test
	public void testSetters() {
		movie.setMovieID(10);
		assertEquals(movie.getMovieID(), 10);
		movie.setTitle("Sco");
		assertEquals(movie.getTitle(), "Sco");
		movie.setVideoReleaseDate("14-Feb-1998");
		assertEquals(movie.getVideoReleaseDate(), "14-Feb-1998");
		movie.setYear(1999);
		assertEquals(movie.getYear(), 1999);
		movie.setURL("yeah");
		assertEquals(movie.getURL(), "yeah");
		movie.setGenre("Comedy");
		assertEquals(movie.getGenre(), "Comedy");
	}
	
	@Test
	public void testRatings() {
		movie.addRating(new Rating(10,1,2,(long)55));
		assertEquals(movie.getRatings().size(), 1);
		assertEquals(movie.getRatings().get(0).getObject1(),10);
	}

}
