package theliberryproject;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LibrarySignUp extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container pane;
	private JLabel firstName;
	private JTextField textFirst;
	private JLabel lastName;
	private JTextField textLast;
	private JLabel email;
	private JTextField textEmail;
	private JLabel password;
	private JTextField textPW;
	private JButton submit;
	private String specialChars = "0123456789/*!@#$%^&*()\"{}_[]|\\?/<>,.";
	
	public LibrarySignUp() {
		
		setTitle("Sign Up");
		setBounds(300, 90, 500, 500);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		pane = getContentPane();
		pane.setLayout(null);
		
		firstName = new JLabel("First Name: ");
		firstName.setFont(new Font("Arial", Font.PLAIN, 15));
		firstName.setSize(100, 30);
		firstName.setLocation(100, 60);
		pane.add(firstName);
		
		textFirst = new JTextField("Enter First Name...");
		textFirst.setFont(new Font("Arial", Font.PLAIN, 18));
		textFirst.setSize(250, 30);
		textFirst.setLocation(100, 90);
		pane.add(textFirst);
		
		lastName = new JLabel("Last Name: ");
		lastName.setFont(new Font("Arial", Font.PLAIN, 15));
		lastName.setSize(100, 30);
		lastName.setLocation(100, 130);
		pane.add(lastName);
		
		textLast = new JTextField("Enter Last Name...");
		textLast.setFont(new Font("Arial", Font.PLAIN, 18));
		textLast.setSize(250, 30);
		textLast.setLocation(100, 160);
		pane.add(textLast);
		
		email = new JLabel("Email: ");
		email.setFont(new Font("Arial", Font.PLAIN, 15));
		email.setSize(100, 30);
		email.setLocation(100, 200);
		pane.add(email);
		
		textEmail = new JTextField("Enter Email...");
		textEmail.setFont(new Font("Arial", Font.PLAIN, 18));
		textEmail.setSize(250, 30);
		textEmail.setLocation(100, 230);
		pane.add(textEmail);
		
		password = new JLabel("Password: ");
		password.setFont(new Font("Arial", Font.PLAIN, 15));
		password.setSize(100, 30);
		password.setLocation(100, 270);
		pane.add(password);
		
		textPW = new JTextField("Enter Password...");
		textPW.setFont(new Font("Arial", Font.PLAIN, 18));
		textPW.setSize(250, 30);
		textPW.setLocation(100, 300);
		pane.add(textPW);
		
		JLabel pwReq = new JLabel("*Password must be at least 8 characters long and "
				+ "contain as least 1");
		JLabel pwReq2 = new JLabel(" uppercase, lowercase, and special character.");
		pwReq.setFont(new Font("Arial", Font.ITALIC, 10));
		pwReq2.setFont(new Font("Arial", Font.ITALIC, 10));
		pwReq.setSize(300, 20);
		pwReq2.setSize(300, 20);
		pwReq.setLocation(100, 330);
		pwReq2.setLocation(100, 340);
		pane.add(pwReq);
		pane.add(pwReq2);
		
		submit = new JButton("Submit");
		submit.setFont(new Font("Arial", Font.BOLD, 15));
		submit.setSize(100, 30);
		submit.setLocation(190, 380);
		submit.addActionListener(this);
		pane.add(submit);
		
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) { // perform if Sign up is pressed
			
			// check if password meets requirements
			String password = textPW.getText().trim();
			try {
				boolean containsUpper = false;
				boolean containsLower = false;
				boolean containsSpecial = false;
				boolean contains8 = false;
				for (char c: password.toCharArray()) {
					if (Character.isUpperCase(c)) {containsUpper = true;}
					if (Character.isLowerCase(c)) {containsLower = true;}
					if (specialChars.contains(Character.toString(c))) {containsSpecial = true;}
				}
				if (password.length() >= 8) {contains8 = true;}
				if (!containsUpper) {throw new UpperCaseCharacterMissing();}
				if (!containsLower) {throw new LowerCaseCharacterMissing();}
				if (!containsSpecial) {throw new SpecialCharacterMissing();}
				if (!contains8) {throw new Minimum8CharactersRequired();}
			}
			catch (Exception error) {	// create error pop-up in case of exception
				JFrame errorFrame = new JFrame();
				errorFrame.setTitle("Password Error");
				errorFrame.setBounds(600, 180, 375, 100);
				errorFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				errorFrame.setResizable(false);
				
				JLabel errorMSG = new JLabel(error.getMessage());
				errorMSG.setFont(new Font("Arial", Font.BOLD, 15));
				errorMSG.setSize(100, 30);
				errorMSG.setVerticalAlignment(JLabel.CENTER);
				errorMSG.setHorizontalAlignment(JLabel.CENTER);
				errorFrame.add(errorMSG);
				
				errorFrame.setVisible(true);
				
				return;	// exit in case of exception
			}
			
			//	generate username and add to User database
			String card;
			Random uID = new Random();
			card = "" + uID.nextInt(10000) + 1;
			
			String t1 = textFirst.getText().trim();
			String t2 = textLast.getText().trim();
			String t3 = textEmail.getText().trim();
			String t4 = textPW.getText().trim();
			
			User finalCheck = new User(t1, t2, t3, t4, card, new Reader());
			
			// if any data entered already exists in database, return error popup
			if (MyLibrary.DB.m_libraryCardMap.containsKey(card)|MyLibrary.DB.pwInDB(card)) {
				JFrame errorFrame = new JFrame();
				errorFrame.setTitle("Database Error");
				errorFrame.setBounds(600, 180, 375, 100);
				errorFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				errorFrame.setResizable(false);
				
				JLabel errorMSG = new JLabel("Data entered is already in database.");
				errorMSG.setFont(new Font("Arial", Font.BOLD, 15));
				errorMSG.setSize(100, 30);
				errorMSG.setVerticalAlignment(JLabel.CENTER);
				errorMSG.setHorizontalAlignment(JLabel.CENTER);
				errorFrame.add(errorMSG);
				
				errorFrame.setVisible(true);
				return;	// exit on database error
			}
			
			// add to database on passing final check
			MyLibrary.DB.addCard(card, finalCheck);
			MyLibrary.DB.addUser(finalCheck);
			
			// generate popup for successful account creation
			JFrame successFrame = new JFrame();
			successFrame.setTitle("Success!");
			successFrame.setBounds(600, 180, 500, 150);
			successFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			successFrame.setResizable(false);
			
			JLabel successMSG = new JLabel("Signup Successful! Your Liberry Card Number is: " + card);
			successMSG.setFont(new Font("Arial", Font.BOLD, 15));
			successMSG.setSize(100, 30);
			successMSG.setVerticalAlignment(JLabel.CENTER);
			successMSG.setHorizontalAlignment(JLabel.CENTER);
			successFrame.add(successMSG);
			
			successFrame.setVisible(true);
			dispose();
		}
	}

	public static void main(String[] agrs) {
		new LibrarySignUp();
	}
}
