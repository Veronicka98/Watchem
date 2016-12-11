package models;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.util.Arrays;

import com.google.common.base.Objects;
import Driver.Data;

import play.db.jpa.Model;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.ArrayUtils;

@Entity
@Table(name="`Rating`")
public class Rating extends Model{

	private int object1; 
	private int object2; 
	private int rating;
	private Long timestamp;
	
	private int[] ratings = {-5,-3,1,3,5};
	
	public Rating(int object1, int object2,int  rating, Long timestamp) {
		this.object1 = object1;
		this.object2 = object2;
		setRating(rating);
		this.timestamp = timestamp;
	}
	
	public Rating() {
		
	}
	
	@Override  
	  public int hashCode()  
	  {  
	     return Objects.hashCode(this.object1, this.object2, this.rating);  
	  }  
	  
	  @Override
	  public String toString()
	  {
	    return toStringHelper(this).addValue(object1)
	                               .addValue(object2)
	                               .addValue(rating)
	                               .toString();
	  }
	

	public int getObject1() {
		return object1;
	}
	public void setObject1(int object1) {
		this.object1 = object1;
	}
	public int getObject2() {
		return object2;
	}
	public void setObject2(int object2) {
		this.object2 = object2;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating){
		if(ArrayUtils.contains(ratings, rating)) {
			this.rating = rating;
		} 
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	  
}
