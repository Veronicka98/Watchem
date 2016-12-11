package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;

public class EditProfile extends Controller
{
  public static void change (String firstName,   String lastName, int    age, 
                             String gender, String occupation, String email, String password) throws Exception
  {
    User user = Accounts.getLoggedInUser();
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(email);
    user.setOccupation(occupation);
    user.setAge(age);
    user.setPassword(password);
    //user.save();
    Profile.index();
  }

  public static void index()
  {
    User user = Accounts.getLoggedInUser();
    render(user);
  }
}