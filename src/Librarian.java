package theliberryproject;

import java.util.ArrayList;

public class Librarian implements UserBehavior {
	
	public void active(User u) {
		u.active = true;
	}
	public void inactive(User u) {
		u.active = false;
	}
	
	@Override
	public String toString() {
		return this.getClass().getName();
	}
}
