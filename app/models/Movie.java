package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.base.Objects;

import Driver.Data;
import models.Rating;
import play.db.jpa.Model;

public class Movie extends Model {

	private String title;
	private String videoReleaseDate;
	private int year;
	private String url;
	private String genre;
	
	public Movie() {
	}
	
	public Movie(String title,int year, String videoReleaseDate, String url,String genre) {
		this.title = title;
		this.year = year;
		this.videoReleaseDate = videoReleaseDate;
		this.url = url;
		this.genre = genre;
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
	public void setLink(String url) {
		this.url = url;
	}
	
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	
}
