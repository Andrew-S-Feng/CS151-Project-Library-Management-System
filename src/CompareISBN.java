package theliberryproject;

import java.util.Comparator;

public class CompareISBN implements Comparator<Book> {

	boolean m_reverse  = false ;

	CompareISBN () {
		super();
	}
	
	CompareISBN ( boolean reverse ) {
		super();
		m_reverse = reverse ;	
	}
	
    @Override
    public int compare(Book o1, Book o2) {
		int z = o1.getISBN().compareTo(o2.getISBN());
		if ( m_reverse ) {
			z = -z;
		}
        return z;
    }
	@Override
	public String toString () {
		String s = this.getClass().getName();
		if (m_reverse ) s += " (reverse)" ;
		return s ;
	}
}