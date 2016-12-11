package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.common.base.Objects;

import Driver.Data;
import models.Rating;
import play.db.jpa.Model;

@Entity
@Table(name="`Movie`")
public class Movie extends Model {
	
	private int movieID;

	private String title;
	private String videoReleaseDate;
	private int year;
	private String url;
	private String genre;
	
	public List<Rating> ratings;
	public double overallRating = 0;
	
	public Movie() {
		ratings = new ArrayList<Rating>();
	}
	
	public Movie(String title,int year, String videoReleaseDate, String url,String genre) {
		this.title = title;
		this.year = year;
		this.videoReleaseDate = videoReleaseDate;
		this.url = url;
		this.genre = genre;
		ratings = new ArrayList<Rating>();
	}
	
	@Override  
	  public int hashCode()  
	  {  
	     return Objects.hashCode(this.title, this.videoReleaseDate, this.url, this,genre);  
	  }  
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	public String getVideoReleaseDate() {
		return videoReleaseDate;
	}
	public void setVideoReleaseDate(String releaseDate) {
		this.videoReleaseDate = releaseDate;
	}
	
	public String getURL() {
		return url;
	}
	public void setURL(String url) {
		this.url = url;
	}
	
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public void setMovieID(int movieID) {
		this.movieID = movieID;
	}
	public int getMovieID() {
		return movieID;
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
	public void setOverallRating(double total) {
		this.overallRating = total;
	}
	public double getOverallRating() {
		return overallRating;
	}
}
