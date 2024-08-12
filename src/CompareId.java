package theliberryproject;

import java.util.Comparator;

public class CompareId implements Comparator<User> {

boolean reverse = false;
	
	// default super
	public CompareId() {
		super();
	}
	
	public CompareId(boolean reverse) {
		super();
		this.reverse = reverse;
	}
	
	@Override
	public int compare(User o1, User o2) {
		int id = o1.getCard().compareTo(o2.getCard());
		if (reverse == true) { id = -id;}
		return id;
	}
	

	@Override
	public String toString() {
		String s = this.getClass().getName();
		if (reverse == true) { s += " (reverse)";}
		return s;
	}
};
