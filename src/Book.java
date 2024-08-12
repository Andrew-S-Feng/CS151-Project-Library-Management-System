	package theliberryproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Book implements Serializable, Comparable<Book>{
	
	private String	title;
	private String	author;
	private String 	numISBN;
	
	// default constructor
	public Book() {
		
	}
	
	public Book(String title, String author, String isbn) {
		this.title = title;
		this.author = author;
		this.numISBN = isbn;
	}
	
	// getter/setter methods
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getISBN() {
		return this.numISBN;
	}
	
	public void setISBN(String isbn) {
		this.numISBN = isbn;
	}

	@Override
	public int compareTo(Book o) {
		return this.numISBN.compareTo(o.numISBN);
	}
	
	public static void saveBooks (   ArrayList<Book> books, String filename )
	{ 
		try
		{ 
			//Saving of users in a file
			FileOutputStream file = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file);
			// Method for serialization of users
			out.writeObject(books);
			// System.out.println (users);
			
			out.close();
			file.close();
			
			System.out.println("Book database has been updated!!!");

		}
		catch(IOException ex)
		{
			System.out.println( ex.getClass().getName() + " 1 is caught");
		}
	}
	
    public static ArrayList <Book> loadBooks( String filename ) {
		ArrayList<Book> books = null;
		try { 
			// Reading the users from a file
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);
			
			// Method for deserialization of object
			books = (ArrayList<Book>)in.readObject();

			System.out.println(books);
			
			in.close();
			file.close();
			
			int n = books.size();
			System.out.println("Book dataBase is being loaded with " + n + " books.");
		} catch(Exception ex) {
			System.out.println(ex.getClass().getName() + " 2 is caught");
		}
		return books;
	}

    // load books from backend database file
	static private Book foo (Scanner fileInput, String temp) {
        Book newBook = new Book();  // create a new book object
        newBook.setISBN(temp); // set the first thing read in to ISBN
        newBook.setTitle(fileInput.nextLine());  // files reads line by line, so do .nextLine
        newBook.setAuthor(fileInput.nextLine());
        
            while (fileInput.hasNextLine()) {
                try {
                    String tempLine = fileInput.nextLine(); // first thing we read from txt
                    if (tempLine.trim().length() == 0) {   // break when no more lines to read
                        break;
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            // System.out.println ( newUser );
		return newBook ;
	}

    static public ArrayList<Book> loadBooksFromTextFile ( String filename ) {
		ArrayList <Book> books = new ArrayList<> () ;

		try {
        	File file = new File( filename );
        	Scanner fileInput = new Scanner(file);
        	while (fileInput.hasNextLine()) {   
            	String temp = fileInput.nextLine();
            	if (temp.trim().length() > 0) {  
					Book newBook = foo (fileInput, temp);
					books.add ( newBook );
            	}
        	}
        	fileInput.close();
		} catch (IOException ex ) {
            System.out.println(ex.getMessage());
		}
		
		return books;
    }
}


