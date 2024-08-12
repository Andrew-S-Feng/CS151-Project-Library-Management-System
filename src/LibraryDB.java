package theliberryproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter ;
import java.awt.event.MouseEvent ;

import javax.swing.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LibraryDB extends JFrame implements ActionListener {
	JButton btnExit = new JButton ("Exit" );
    JButton btnSort   = new JButton("Sort"); // 
	JButton btnSearch   = new JButton("Search"); //

    JList<String> jBooks = new JList<>(new DefaultListModel<>());
    DefaultListModel<String> booksModel = (DefaultListModel<String>) jBooks.getModel();
    
    JList<String> jCBooks = new JList<>(new DefaultListModel<>());
    DefaultListModel<String> cbooksModel = (DefaultListModel<String>) jBooks.getModel();
    
    User loginUser;
    
    JComboBox cbxSortCriteria ; 
    JComboBox cbxSearch;	// added for function
    
	ArrayList <Book> cbooks = new ArrayList<Book>();
	ArrayList <Book> books = Book.loadBooksFromTextFile("Books"); // for testing
    // ArrayList<Book> books = MyLibrary.DB.getBooks();
	JRadioButton btnAscending = new JRadioButton ( "Ascending" );
	JRadioButton btnDescending = new JRadioButton ( "Descending" );

	JTextField txtSearch   = new JTextField("a regular expression ...");
	static boolean txtSearchFirstTimeClick = true;

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnExit) {  //
        	System.exit(0); // use System.exit(0); to terminate, JFrame.dipose(); to close frame
        } else if (e.getSource() == btnSort ) {  //
        	
        	// hashmap, with String, new comparator
        	HashMap<String, Comparator<Book>> a = new HashMap<String, Comparator<Book>>();
        	a.put("Title", new CompareTitle());
        	a.put("Author", new CompareAuthor());
        	a.put("ISBN", new CompareISBN());
        	
        	String cri = (String)cbxSortCriteria.getSelectedItem();
        	
        	// search through hashmap for cri, Collections.sort() based on cri
        	if (a.containsKey(cri)) {
        		Collections.sort(books, a.get(cri));
        		if (btnDescending.isSelected()) { Collections.reverse(books);}
        	}
        	
    		booksModel.removeAllElements();
    		for (Book b: books) {
    			booksModel.addElement(b.getTitle());
    		}
    		
    		jBooks.updateUI();
        	
        } else if (e.getSource() == btnSearch ) {  //
		 	String regex = txtSearch.getText () ; 	
        	System.out.println ( regex ); 
        	Pattern pattern ;

    		try {
        		pattern = Pattern.compile(regex );
    		} catch (Exception ex ) {
    			JOptionPane.showMessageDialog(this, "Invalid regular expression: " + regex, ex.getMessage(), JOptionPane.ERROR_MESSAGE);
				return ;
    		}
    		
    		String cri = (String)cbxSearch.getSelectedItem();
    		booksModel.removeAllElements();
    		
    		ArrayList<String> ans = new ArrayList<String>();
    		for (Book b: books) {
    			String name = b.toString();
    			if (cri.equals("Title")) {
    				name = b.getTitle();
    			}
    			else if (cri.equals("Author")) {
    				name = b.getAuthor();
    			}
    			else if (cri.equals("All Fields")) {
    				name = b.getTitle() + b.getAuthor() + b.getISBN();
    			}
    			else if (cri.equals("ID")){
    				name = b.getISBN();
    			}
    			if (name.matches(regex)) {
    				if (pattern.matcher(name).find()) {
    					booksModel.addElement(b.getTitle());
    					ans.add(name);
    				}
    			}
    		}
    		
			jBooks.updateUI();
		}
    }

    public JScrollPane buildUsersScrollPaneRight() {
		for (Book b: books) {
			booksModel.addElement(b.getTitle());
		}

		JScrollPane scroll = new JScrollPane( jBooks );
		return scroll ;
	}
    
    public JScrollPane buildUsersScrollPaneLeft() {
    	for (Book b: cbooks) {
    		cbooksModel.addElement(b.getTitle());
    	}
    	JScrollPane scroll = new JScrollPane(jCBooks);
    	return scroll;
    }

    public JPanel buildPnlTop  ( ) {
		JPanel p = new JPanel ();
		JLabel lblTitle = new JLabel ( "Liberry Books" ) ;
        p.add( lblTitle );
		p.add (btnExit);
		p.setBounds (0,0,500, 30);
        p.setBackground(Color.green);
		
		return p ;
	}

    public JPanel buildUserDisplayPanel  ( ) {
		JPanel 	   p = new JPanel();
        BoxLayout layout = new BoxLayout(p, BoxLayout.Y_AXIS);
        p.setLayout(layout);    // 

		JScrollPane scroll = buildUsersScrollPaneRight();
        scroll.setBackground(Color.green);

        p.add( scroll);
		p.setBounds ( 50,50,400, 300);
        p.setBackground(Color.green);

		return p ;
	}

    public JPanel buildPnlSort  ( ) {
    		ButtonGroup rbgOrder = new ButtonGroup();
      		rbgOrder.add(btnAscending);
      		rbgOrder.add(btnDescending);
			btnAscending.setSelected(true);

        	final JLabel lblSort  = new  JLabel( "Sort By" );
        	String[] criteria = { "Title", "Author", "ISBN" };
        	cbxSortCriteria = new  JComboBox( criteria );
    
        	JButton sortUsersButton   = new JButton("sort"); //

			JPanel p = new JPanel() ;
        	BoxLayout layout = new BoxLayout(p, BoxLayout.X_AXIS);

        	p.setLayout(layout);
        	p.add(lblSort);
        	p.add(cbxSortCriteria);
        	p.add(btnAscending);
        	p.add(btnDescending);
        	p.add(btnSort);

			p.setBounds (0,380,500, 50);
        	p.setBackground(Color.green);

			return p ;
	}
    

	class txtSearchEvet  extends  MouseAdapter{
                @Override
                public void mousePressed(MouseEvent ev){
					if ( txtSearchFirstTimeClick ) {
                    	txtSearch.setText( "" );
						txtSearchFirstTimeClick = false;
					}
                }
	}
    public JPanel buildPnlSearch  ( ) {
        	final JLabel lblSearch  = new  JLabel( "Search By" );
        	String[] criteria = { "All Fields", "Title", "Author", "ISBN" };
        	cbxSearch = new  JComboBox( criteria );
    

			JPanel p = new JPanel() ;
        	BoxLayout layout = new BoxLayout(p, BoxLayout.X_AXIS);
        	p.setLayout(layout);
        	p.add(lblSearch);
        	p.add(cbxSearch);

        	p.add( txtSearch);
        	p.add(btnSearch);

			p.setBounds (0,450,500, 50);
        	p.setBackground(Color.green);

			txtSearch.addMouseListener(new txtSearchEvet() );

			return p ;
	}
    public JPanel buildBigPanel ( 
		JPanel pnlTop, JPanel userDisplayPanel, 
		JPanel pnlSort,
		JPanel pnlSearch
	) {

		JPanel p = new JPanel();
        p.setBackground(Color.red);
        p.setSize( 500,500);

       p.setLayout(new BorderLayout());

        p.add(pnlTop, BorderLayout.NORTH);    //Make the userInfoPanel in the north (the top)
        p.add(userDisplayPanel, BorderLayout.CENTER);   // Set the displayUsersPanel in the center of the frame
        p.add(pnlSort, BorderLayout.SOUTH);    //

		return p ;
	}

	public LibraryDB(User u) {
		this.loginUser = u;
		cbooks.addAll(loginUser.getBorrowed().keySet());
		// set up Frame
		btnExit.addActionListener(this);
		btnSort.addActionListener(this);
		btnSearch.addActionListener(this);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(600, 600);
		this.setBounds(0,0,600, 600);
		this.setLayout(null);
		this.setBackground(Color.green);

		JPanel pnlTop = buildPnlTop ();
		JPanel userDisplayPanel =  buildUserDisplayPanel ( );
		JPanel pnlSort   = buildPnlSort ();
		JPanel pnlSearch   = buildPnlSearch ();

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.insets = new Insets(10, 50, 10, 50);  // Padding around components  

        	this.add(pnlTop, gbc);                                    
        	this.add(userDisplayPanel, gbc);                                    
        	this.add(pnlSort, gbc);
        	this.add(pnlSearch, gbc);
		this.show();
	}
	
	
	public static void main(String[] args) {
		new LibraryDB(MyLibrary.DB.m_libraryCardMap.get("0"));
	}
}