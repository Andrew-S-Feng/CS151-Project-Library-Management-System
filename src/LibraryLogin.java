package theliberryproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class LibraryLogin extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Container pane;
	private JLabel username;
	private JTextField textUser;
	private JLabel password;
	private JTextField textPW;
	private JButton login;
	private JButton loginOK;
	private User loginUser;
	private JFrame successFrame;
	
	MyLibrary lib = MyLibrary.DB;

	
	public LibraryLogin() {
		
		// create login popup
		setTitle("Welcome to the The Liberry!");
		setBounds(300, 90, 400, 400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		pane = getContentPane();
		pane.setLayout(null);
		
		username = new JLabel("Username:");
		username.setFont(new Font("Arial", Font.PLAIN, 15));
		username.setSize(100, 30);
		username.setLocation(80, 60);
		pane.add(username);
		
		textUser = new JTextField("Enter Username...");
		textUser.setFont(new Font("Arial", Font.PLAIN, 17));
		textUser.setSize(230, 28);
		textUser.setLocation(80, 90);
		pane.add(textUser);
		
		password = new JLabel("Password:");
		password.setFont(new Font("Arial", Font.PLAIN, 15));
		password.setSize(100, 30);
		password.setLocation(80, 140);
		pane.add(password);
		
		textPW = new JTextField("Enter Password...");
		textPW.setFont(new Font("Arial", Font.PLAIN, 17));
		textPW.setSize(230, 28);
		textPW.setLocation(80, 170);
		pane.add(textPW);
		
		login = new JButton("Login");
		login.setFont(new Font("Arial", Font.BOLD, 19));
		login.setSize(180, 32);
		login.setLocation(100, 250);
		login.addActionListener(this);
		pane.add(login);
		
		setVisible(true);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == login) {
			String usernameTest = textUser.getText().trim();
			String passwordTest = textPW.getText().trim();
			String name = null;
			boolean userInDatabase = false; // change to false after testing
			boolean pwMatch = false;
			for (String u: lib.m_libraryCardMap.keySet()) {
				if (Objects.equals(u, usernameTest)) {
					userInDatabase = true;
					if (Objects.equals(lib.m_libraryCardMap.get(u).getPassword(), passwordTest)) {
						pwMatch = true;
						name = lib.m_libraryCardMap.get(u).getFullName();
						loginUser = lib.m_libraryCardMap.get(u);
					}
					break;
				}
			}
			
			// if username if not in database, generate error popup
			if (!userInDatabase) {
				JFrame errorFrame = new JFrame();
				errorFrame.setTitle("Login Error");
				errorFrame.setBounds(600, 180, 375, 100);
				errorFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				errorFrame.setResizable(false);
				
				JLabel errorMSG = new JLabel("Username is not in database.");
				errorMSG.setFont(new Font("Arial", Font.BOLD, 15));
				errorMSG.setSize(100, 30);
				errorMSG.setVerticalAlignment(JLabel.CENTER);
				errorMSG.setHorizontalAlignment(JLabel.CENTER);
				errorFrame.add(errorMSG);
				
				errorFrame.setVisible(true);
				return;	// exit on database error
			}
			
			// if username is in database, but the password doesn't matach, generate error popup
			if (!pwMatch) {
				JFrame errorFrame = new JFrame();
				errorFrame.setTitle("Login Error");
				errorFrame.setBounds(600, 180, 375, 100);
				errorFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				errorFrame.setResizable(false);
				
				JLabel errorMSG = new JLabel("Password does not match the Username.");
				errorMSG.setFont(new Font("Arial", Font.BOLD, 15));
				errorMSG.setSize(100, 30);
				errorMSG.setVerticalAlignment(JLabel.CENTER);
				errorMSG.setHorizontalAlignment(JLabel.CENTER);
				errorFrame.add(errorMSG);
				
				errorFrame.setVisible(true);
				return;	// exit on database error
			}
			dispose();
			successFrame = new JFrame();
			successFrame.setTitle("Login Success!");
			successFrame.getContentPane().setLayout(null);
			
			successFrame.setBounds(600, 180, 500, 150);
			successFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			successFrame.setResizable(false);
			
			JLabel successMSG = new JLabel("Login successful!: Welcome " + name);
			successMSG.setFont(new Font("Arial", Font.BOLD, 15));
			successMSG.setSize(300, 30);
			successMSG.setLocation(80, 30);
			successFrame.add(successMSG);
						
			loginOK = new JButton("Continue");
			loginOK.setFont(new Font("Arial", Font.PLAIN, 13));
			loginOK.setSize(100, 20);
			loginOK.setLocation(320, 70);
			loginOK.addActionListener(this);
			successFrame.add(loginOK);
						
			successFrame.setVisible(true);
		}
		
		// generates library popup when "Continue" button is clicked
		if (e.getSource() == loginOK) {
			successFrame.dispose();
			new LibraryDB(loginUser);
		}
		
	}
	public static void main(String[] agrs) {
		new LibraryLogin();
	}
}
