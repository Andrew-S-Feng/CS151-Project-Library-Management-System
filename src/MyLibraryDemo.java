package theliberryproject;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;


// 5 components, main, signup, user, login, exception
// user should import java.io.Serialization

public class MyLibraryDemo extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton signupButton;
	private JButton loginButton;
	private JButton exitButton;
	Container pane;
	private JFrame baseFrame;
	private JLabel welcome;

	ArrayList <User> users = User.loadUsersFromTextFile ( "Users.txt");
	ArrayList <Book> books = Book.loadBooksFromTextFile("Books");
	
	// constructor; generates initial GUI
	public MyLibraryDemo() {
		MyLibrary.DB.setUsers(users);
		MyLibrary.DB.setBooks(books);
		baseFrame = new JFrame();
		baseFrame.setSize(new Dimension(550, 550));
		baseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets what operation is performed once we close the
		// window
		baseFrame.setResizable(false); // can we resize the window
		pane = getContentPane();
		pane.setLayout(null);
		
		baseFrame.setTitle("The Liberry"); // sets or changes the title
		baseFrame.setVisible(true); // to show the JFrame
		
		welcome = new JLabel();	// add Welcome label
		welcome.setText("Welcome!");	// text of label
		welcome.setFont(new Font("Arial", Font.BOLD, 35)); // set font and size of text
 //		welcome.setBorder(BorderFactory.createLineBorder(Color.black, 15)); // creates border for label
		welcome.setVerticalAlignment(JLabel.TOP);
		welcome.setHorizontalAlignment(JLabel.CENTER);
		baseFrame.add(welcome);
		
		// add buttons to frame
		signupButton = new JButton();
		signupButton.setText("Sign up");
		signupButton.setFont(new Font("Arial", Font.BOLD, 25));
		signupButton.setBounds(187, 150, 175, 30);
		signupButton.addActionListener(this);
		baseFrame.add(signupButton);
		
		loginButton = new JButton();
		loginButton.setText("Login");
		loginButton.setFont(new Font("Arial", Font.BOLD, 25));
		loginButton.setBounds(187, 200, 175, 30);
		loginButton.addActionListener(this);
		baseFrame.add(loginButton);
		
		exitButton = new JButton();
		exitButton.setText("Exit");
		exitButton.setFont(new Font("Arial", Font.BOLD, 25));
		exitButton.setBounds(187, 250, 175, 30);
		exitButton.addActionListener(this);
		baseFrame.add(exitButton);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == signupButton) { // perform if Sign up is pressed
			new LibrarySignUp();
		}
		if (e.getSource() == loginButton) {	// perform if Login is pressed
			new LibraryLogin();
		}
		if (e.getSource() == exitButton) { // perform if Exit is pressed
			System.exit(0);
		}
	}
	
	// TEST
	public static void main(String[] agrs) {
		new MyLibraryDemo();
	}
}
