package theliberryproject;

import java.util.Comparator;

public class CompareLastName implements Comparator<User> {
    
	boolean reverse = false;
	
	// default super
	CompareLastName() {
		super();
	}
	
	public CompareLastName(boolean reverse) {
		super();
		this.reverse = reverse;
	}
	
	@Override
	public int compare(User u1, User u2) {
		int last = u1.getLastName().compareTo(u2.getLastName());
		if (last == 0) {
			last = u1.getCard().compareTo(u2.getCard());
		}
		if (reverse == true) { last = -last;}
		return last;
	}

	@Override
	public String toString() {
		String s = this.getClass().getName();
		if (reverse == true) { s += " (reverse)";}
		return s;
	}
};


