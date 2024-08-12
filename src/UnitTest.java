package theliberryproject;

import java.util.*;
import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;


// 
public class UnitTest {
	
	MyLibrary testLib = MyLibrary.DB;
	ArrayList<Book> textbooks = Book.loadBooksFromTextFile("Books");

	@Before
	public void initialize() {
		try {
		testLib.setBooks(textbooks);
		ArrayList<User> userFromTxt = User.loadUsersFromTextFile("Users.txt");
		testLib.setUsers(userFromTxt);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	// cleanup files after each test
	@After
	public void cleanup() {
		
	}
	
	/* @Test 
	public void testUserCons() {
		String fName = "Tony";
		String lName = "Feng";
		String email = "tony.feng@sjsu.edu";
		String pw 	 = "Password1!";
		String card	 = "12345";
		User	lRat = new User(fName, lName, email, pw, card, new Reader());
		testLib.addCard(card, lRat);
		System.out.println(testLib.m_libraryCardMap);
		assertTrue(!testLib.m_libraryCardMap.isEmpty());
	} */
	
	@Test
	public void testDB() {
		System.out.println("TestDB: ");
		for (User b: testLib.getUsers()) {
			System.out.println(b.getFullName());
			System.out.println(b.getEmail());
			System.out.println(b.getPassword());
			System.out.println(b.getCard());
			int c = 1;
			for (Book u: b.getBorrowed().keySet()) {
				System.out.println(c + ") " + u.getTitle() + b.getBorrowed().get(u) );
				c++;
			}
			
			System.out.println();
		}
		System.out.println();
		assertTrue(!testLib.getBooks().isEmpty());
	}
	
	@Test
	public void testCopies() {
		System.out.println("TestCopies: ");
		for (Book b: testLib.copies.keySet()) {
			System.out.println(b.getTitle() + testLib.copies.get(b));
		}
		System.out.println();
		assertTrue(!testLib.copies.isEmpty());
	}
	
	@Test
	public void testAdmin() {
		System.out.println("TestAdmin: ");
		User admin = testLib.m_libraryCardMap.get("0");
		System.out.println(admin.getFullName());
		System.out.println(admin.getEmail());
		System.out.println(admin.getPassword());
		System.out.println(admin.getCard());
		System.out.println();
		
		assertTrue(admin!=null);
	}
	
	@Test
	public void testPwInDB() {
		System.out.print("TestPwDB: ");
		System.out.println(testLib.pwInDB("Liberry1!"));
		assertTrue(testLib.pwInDB("Liberry1!"));
	}
}
