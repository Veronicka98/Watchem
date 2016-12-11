package models;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ratingTest {
	
	Rating rating;

	@Before
	public void setUp() throws Exception {
		rating = new Rating(1,5,3,(long)66);
	}

	@After
	public void tearDown() throws Exception {
		rating = null;
	}

	@Test
	public void testConstructor() {
		assertEquals(rating.getObject1(), 1);
		assertEquals(rating.getObject2(), 5);
		assertEquals(rating.getRating(), 3);
		assertEquals((long)rating.getTimestamp(), (long) 66);
	}
	
	@Test
	public void testSetters() {
		rating.setObject1(2);
		assertEquals(rating.getObject1(), 2);
		rating.setObject2(2);
		assertEquals(rating.getObject2(), 2);
		rating.setRating(-5);
		assertEquals(rating.getRating(), -5);
		rating.setTimestamp((long)123);
		assertEquals((long)rating.getTimestamp(), (long) 123);
		
	}

}
