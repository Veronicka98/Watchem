package Driver;

import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import models.Rating;
import models.User;

public class HandleXML {
	
	public static void writeData(List<Map> users, String filename) throws Exception {
		XMLEncoder encoder = 
				new XMLEncoder(
						new BufferedOutputStream(
								new FileOutputStream(filename)));
		encoder.writeObject(users);
		encoder.close();
	}
	
	public static List<Map> readData(String filename) throws Exception {
		
		XMLDecoder decoder = 
				new XMLDecoder(new BufferedInputStream(
						new FileInputStream(filename)));
		@SuppressWarnings("unchecked")
		List<Map> data = (List<Map>) decoder.readObject();
		decoder.close();
		return data;
	}
	
	public static void writeRatings(List<Rating> userRatings, String filename) throws Exception {
		XMLEncoder encoder = 
				new XMLEncoder(
						new BufferedOutputStream(
								new FileOutputStream(filename)));
		encoder.writeObject(userRatings);
		encoder.close();
	}
	
	public static List<Rating> readRatings(String filename) throws Exception {
		
		XMLDecoder decoder = 
				new XMLDecoder(new BufferedInputStream(
						new FileInputStream(filename)));
		@SuppressWarnings("unchecked")
		List<Rating> userRatings = (List<Rating>) decoder.readObject();
		decoder.close();
		return userRatings;
	}
}
