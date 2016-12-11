package models;

public class Fixtures
{
  public static User[] users =
  {
    new User ("Marge", "Simpson", 36 ,"F", "artist"),
    new User ("Veronika",  "Karpenko", 18, "F", "student"),
    new User ("Joe",  "Bloggs", 33, "M",   "programmer"),
    new User ("Tim","Gunn", 55, "M", "entertainment"),
    new User ("Miranda", "Sings" , 22, "F", "entertainment")
    
  };
  
  public static Movie[] movies =
  {
    new Movie ("Shrek", 1997, "01-Jan-2001", "https://imdb1.com" , "Children's"),
    new Movie ("Hackers", 1995, "21-Feb-1995", "https://imdb2.com" , "Sci-fi"),
    new Movie ("Fargo", 1996, "10-Jul-1996", "https://imdb3.com" , "Thiller")
  };
  
  public static Rating[] ratings =
  {
    new Rating (0,2,5, (long) 100),
    new Rating (0,2,3, (long) 101),
    new Rating (1,1,-3, (long) 102),
    new Rating (1,0,1, (long) 103),
    new Rating (1,2,5, (long) 104),
    new Rating (2,0,3, (long) 1011),
    new Rating (2,1,5, (long) 105),
    new Rating (2,2,-3, (long) 106),
    new Rating (3,0,-5, (long) 107),
    new Rating (3,2,1, (long) 108),
    new Rating (3,1,5, (long) 109)
    };
}