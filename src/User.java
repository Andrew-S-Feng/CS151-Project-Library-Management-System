package theliberryproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class User implements Serializable, Comparable<User> {

	private String	firstName;
	private String	lastName;
	private String	email;
	private String	password;
	private String	libCardNum;
	boolean active = false;
	private HashMap<Book, Integer> borrowed = null; // contains borrowed books and time left until return
	
	private UserBehavior behavior;
	
	// default
	public User() {
		borrowed = new HashMap<>();
	}
	
	// Constructor
	public User(String firstName, String lastName, String email, String pw, String card, UserBehavior behavior) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = pw;
		this.libCardNum = card;
		this.active = true;
		borrowed = new HashMap<>();
		this.behavior = behavior;
	}
	
	// getter/setter methods
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String n) {
		this.firstName = n;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String n) {
		this.lastName = n;
	}
	
	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}
	
	public void setCard(String id) {
		this.libCardNum = id;
	}
	
	public String getCard() {
		return this.libCardNum;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String pw) {
		this.password = pw;
	}
	
	public HashMap<Book, Integer> getBorrowed() {
		return this.borrowed;
	}
	
	public boolean getStatus() {
		return this.active;
	}
	
	public void setBehavior(UserBehavior behavior) {
		this.behavior = behavior;
	}
	
	// checks in Book b and removes from user borrowed list
	public void checkIn(Book b) {
		MyLibrary.DB.checkIn(b);
		borrowed.remove(b);
	}
	
	// check out book, if successful, add to borrowed
	public void checkOut(Book b) {
		try {
			anyOverdue();
			MyLibrary.DB.checkOut(b);
			borrowed.put(b, 30);
		}
		catch (Exception e) {
			// do something
			String msg = e.getMessage();
		}
	}
	
	// tests if user has any overdue books
	public void anyOverdue() throws Exception {
		for (Book b: borrowed.keySet()) {
			if (borrowed.get(b) <= 0) { // if 
				throw new OverdueBook();  // change to OverdueException
			}
		}
	}
	
	// active/inactive method
	// implemented by behavior
	public void setActive(User u) {
		this.behavior.active(u);
	}
	
	// implemented by behavior
	public void setInactive(User u) {
		this.behavior.inactive(u);
	}
	
	@Override
    public int compareTo(User user) {
            return getFullName().compareTo ( user.getFullName() );
    }
	@Override
	public boolean equals (Object u) { // inherited from Object
		return this.compareTo ( (User) u ) == 0 ; 
	}
	
	public static void saveUsers (   ArrayList <User> users, String filename )
	{ 
		try
		{ 
			//Saving of users in a file
			FileOutputStream file = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file);
			// Method for serialization of users
			out.writeObject(users);
			// System.out.println (users);
			
			out.close();
			file.close();
			
			System.out.println("Users database has been updated!!!");

		}
		catch(IOException ex)
		{
			System.out.println( ex.getClass().getName() + " 1 is caught");
		}
	}
    public static ArrayList <User> loadUsers( String filename ) {
		ArrayList <User> users = null;
		try { 
			// Reading the users from a file
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);
			
			// Method for deserialization of object
			users = (ArrayList<User>)in.readObject();

			System.out.println(users);
			
			in.close();
			file.close();
			
			int n = users.size();
			System.out.println("User dataBase is being loaded with " + n + " users.");
		} catch(Exception ex) {
			System.out.println(ex.getClass().getName() + " 2 is caught");
		}
		return users;
	}

    // load users from backend database file
	static private User foo (Scanner fileInput, String temp ) {
        User newUser = new User();  // create a new user object
        Boolean t =   temp.equals ("active")   ;
        newUser.active = t;	 // set the first thing read in to firstName
        newUser.setFirstName( fileInput.nextLine() ); // set the first thing read in to firstName
        newUser.setLastName(fileInput.nextLine());  // files reads line by line, so do .nextLine
        newUser.setEmail(fileInput.nextLine());
        newUser.setPassword(fileInput.nextLine());
        newUser.setCard(fileInput.nextLine());
        newUser.setBehavior(new Reader());
            // if there is next line, it is checkout book isbn
            while (fileInput.hasNextLine()) {
                try {
                    String tempLine = fileInput.nextLine(); // first thing we read from txt
                    tempLine = tempLine.trim();
                    for (Book b: MyLibrary.DB.getBooks()) {
                    	if(Objects.equals(b.getISBN(), tempLine)) {
                    		newUser.checkOut(b);
                    	}
                    }
                    if (tempLine.trim().length() == 0) {   // break when no more lines to read
                        break;
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            // System.out.println ( newUser );
		return newUser ;
	}

    static public ArrayList<User> loadUsersFromTextFile ( String filename ) {
		ArrayList <User> users = new ArrayList<> () ;

		try {
        	File file = new File( filename );
        	Scanner fileInput = new Scanner(file);
        	while (fileInput.hasNextLine()) {   
            	String temp = fileInput.nextLine();
            	if (temp.trim().length() > 0) {  
					User newUser = foo ( fileInput, temp );
					users.add ( newUser );
            	}
        	}
        	fileInput.close();
		} catch (IOException ex ) {
            System.out.println(ex.getMessage());
		}
		
		return users;
    }
}

