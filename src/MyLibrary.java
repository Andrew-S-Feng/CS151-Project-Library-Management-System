package theliberryproject;
import java.util.*;
import java.io.*;

public enum MyLibrary {
	DB  ; // singleton design
 
	// other useful fields here
	private ArrayList <Book>  m_books = null;
	private ArrayList <User>  m_users = null;
	HashMap<String, User> m_libraryCardMap = null;
	HashMap<Book, Integer> copies = null;

	
	// checks for duplicate password
	public boolean pwInDB(String pw) {
		for (String u: m_libraryCardMap.keySet()) {
			if (Objects.equals(pw, m_libraryCardMap.get(u).getPassword())) { return true;}
		}
		return false;
	}
	
	// getter/setter methods
	public ArrayList<Book> getBooks() {
		return m_books;
	}
	
	// adds any books if they aren't already in book list
	public void setBooks(ArrayList<Book> books) {
		ArrayList<Book> toAdd = new ArrayList<Book>();
		for (Book b: books) {
			if (!m_books.contains(b)&!copies.containsKey(b)) {
				toAdd.add(b);
				copies.put(b, 25);
			}
		}
		m_books.addAll(toAdd);
	}
	
	// for testing
	public ArrayList<User> getUsers(){
		return this.m_users;
	}
	
	// adds any users if they aren't already in user list, used for bulk add
	public void setUsers(ArrayList<User> users) {
		ArrayList<User> toAdd = new ArrayList<User>();
		for (User u: users) {
			if (!m_users.contains(u)) {
				addCard(u.getCard(), u);
				toAdd.add(u);
			}
		}
		m_users.addAll(toAdd);
	}
	
	public void addUser (User u) {
		m_users.add(u);
	}
	
	// adds Book b to list and HashMap, default 15 copies
	public void addBook(Book b) {
		if (!m_books.contains(b)) {
		m_books.add(b);
		copies.put(b, 25);
		}
	}
	
	// prioritize using add/remove card for data organization
	public void addCard(String id, User u) {
		m_libraryCardMap.put(id, u);
	}
	
	public void removeCard(String id, User u) {
		m_libraryCardMap.remove(id, u);
	}
	
	public boolean containsCard(String c) {
		if (m_libraryCardMap.containsKey(c)) { return true;}
		return false;
	}
	
	// check in/out methods
	public void checkIn(Book b) {
		int c = copies.get(b);
		copies.put(b, c++);
	}
	
	public void checkOut(Book b) throws Exception {
		int c = copies.get(b);
		if (c <= 0) {
			throw new NoMoreCopies(); // change to NoMoreCopiesException
		} else {
		copies.put(b, c--);
		}
	}
	
	// decreases number of days until overdue for each user in m_users
	public void overdueIncrement() {
		for (User u: m_users) {
			for (Book b: u.getBorrowed().keySet()) {
				int c = u.getBorrowed().get(b);
				u.getBorrowed().put(b, c--);
			}
		}
	}
	
	private MyLibrary() { // must be private
		m_books = new ArrayList <Book> () ;
    	m_users = new ArrayList <User> () ;
    	m_libraryCardMap = new HashMap<>();
    	copies  = new HashMap<>();
    	
    	// Create Library Admin
    	User librarian = new User(
    			"Liberry",
    			"Administrator",
    			"ladmin@liberry.org",
    			"Liberry1!",
    			"0",
    			new Librarian());
    	addCard("0", librarian);
    	addUser(librarian);
	}
	

}



