package models;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.test.UnitTest;

public class userTest extends UnitTest{

	User user;
	
	@Before
	public void setUp() throws Exception {
		user = new User("Veronika", "Karpenko", 18, "F", "student");
	}

	@After
	public void tearDown() throws Exception {
		user = null;
	}

	@Test
	public void testConstructor() {
		assertEquals(user.getFirstName(), "Veronika");
		assertEquals(user.getLastName(), "Karpenko");
		assertEquals(user.getAge(), 18);
		assertEquals(user.getOccupation(), "student");
		assertEquals(user.getEmail(), "Veronika-student@mail.ru");
		assertEquals(user.getPassword(), "secret");
	}
	
	@Test
	public void testSetters() {
		user.setUserID(2);
		assertEquals(user.getUserID(), 2);
		user.setFirstName("Ver");
		assertEquals(user.getFirstName(), "Ver");
		user.setLastName("Kar");
		assertEquals(user.getLastName(), "Kar");
		user.setAge(19);
		assertEquals(user.getAge(), 19);
		user.setOccupation("lawyer");
		assertEquals(user.getOccupation(), "lawyer");
		user.setEmail("Veronicka98@mail.ru");
		assertEquals(user.getEmail(), "Veronicka98@mail.ru");
		user.setPassword("hello");
		assertEquals(user.getPassword(), "hello");
	
	}
	
	@Test
	public void testRatings() {
		user.addRating(new Rating(1,2,3,(long)152));
		assertEquals(user.getRatings().size(), 1);
		assertEquals(user.getRatings().get(0).getObject1() , 1);	
	}
	
	

}
