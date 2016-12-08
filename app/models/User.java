package models;

import com.google.common.base.Objects;

import Driver.Data;
import models.Rating;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import play.db.jpa.Model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import models.Rating;
import controllers.Accounts;

@Entity
@Table(name="`User`")
public class User extends Model {

	  private int userID;
	  private String firstName;
	  private String lastName;
	  private int    age;
	  private String gender;
	  private String occupation;
	  
	  private String email;
	  private String password;
	  
	  public boolean logged_in;
	  
	  @OneToMany()
	  public List<Rating> ratings = new ArrayList<Rating>();
	  @OneToMany()
	  public List<Rating> differentRatings = new ArrayList<Rating>();
	  
	  private double similarity;
	  
	  public User(String firstName, String lastName,int age,String gender, String occupation)
	  {
	    this.firstName = firstName;
	    this.lastName = lastName;
	    this.age = age;
	    this.gender = gender;
	    this.occupation = occupation;
	    email = firstName+occupation +"@mail.ru";
	    password = "secret";
//	    ratings = new ArrayList<Rating>();
//	    differentRatings = new ArrayList<Rating>();
	    
	  }
	  
	  public User() {
//		  ratings = new ArrayList<Rating>();
//		  differentRatings = new ArrayList<Rating>();
	  }
	  
	  
	  @Override  
	  public int hashCode()  
	  {  
	     return Objects.hashCode(this.lastName, this.firstName, this.email, this.password);  
	  }  
	  
	  @Override
	  public String toString()
	  {
	    return toStringHelper(this).addValue(firstName)
	                               .addValue(lastName)
	                               .addValue(age)
	                               .addValue(gender)  
	                               .addValue(occupation)
	                               .addValue(email)
	                               .addValue(password)
	                               .toString();
	  }
	  
	  public int getUserID() {
		  return userID;
	  }
	  public void setUserID(int userID) {
		  this.userID = userID;
	  }
	  
	  public String getFirstName() {
		  return firstName;
	  }
	  
	  public void setFirstName(String firstName) {
		  this.firstName = firstName;
	  }
	  
	  public String getLastName() {
		  return lastName;
	  }
	  
	  public void setLastName(String lastName) {
		  this.lastName = lastName;
	  }
	  
	  public int getAge() {
		  return age;
	  }
	  
	  public void setAge(int age) {
		  if(age > 0) {
			  this.age = age;
		  }
	  }
	  
	  public String getGender() {
		  return gender;
	  }
	  
	  public void setGender(String gender) {
		  if(gender == "F" || gender == "M") {
			  this.gender = gender;  
		  } else {
			  
		  }
		  
	  }
	  
	  public String getOccupation() {
		  return occupation;
	  }
	  
	  public void setOccupation(String occupation) {
		  this.occupation = occupation;
	  }
	  
	  public String getEmail() {
		  return email;
	  }
	  
	  public void setEmail(String email) {
		  this.email = email;
	  }
	  
	  public String getPassword() {
		  return password;
	  }
	  
	  public void setPassword(String password) {
		  this.password = password;
	  }
	  
	  public boolean checkPassword(String password)
	  {
	    return this.password.equals(password);
	  }
	  
	  public List<Rating> getRatings() {
		  return ratings;
	  }
	  
	  public void setRatings(List<Rating> rating) {
		  ratings = rating;
	  }
	  public void addRating(Rating rating) {
		  ratings.add(rating);
	  }
	  
	  public void setDifferentRatings(List<Rating> rating) {
		  differentRatings = rating;
	  }
	  public List<Rating> getDifferentRatings() {
		  return differentRatings;
	  }
	  public void addDifferentRating(Rating rating) {
		  differentRatings.add(rating);
	  }
	  
	  
	  public void setSimilarity(double similarity) {
		  this.similarity = similarity;
	  }
	  public double getSimilarity() {
		  return similarity;
	  }
	  
	  
	  
	  @OneToMany(mappedBy = "sourceUser")
	  public List<Friendship> friendships = new ArrayList<Friendship>();
	  
	  
}
