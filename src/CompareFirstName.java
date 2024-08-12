package theliberryproject;

import java.util.Comparator;

public class CompareFirstName implements Comparator<User> {

	boolean m_reverse  = false ;

	CompareFirstName () {
		super();
	}
	CompareFirstName ( boolean reverse ) {
		super();
		m_reverse = reverse ;	
	}
    @Override
    public int compare(User o1, User o2) {
		int z = o1.getFirstName().compareTo(o2.getFirstName());
		if ( z==0 ) {
			z = o1.getCard().compareTo(o2.getCard());
		}
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


};
