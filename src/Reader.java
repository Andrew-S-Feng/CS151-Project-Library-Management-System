package theliberryproject;

import java.util.ArrayList;

public class Reader implements UserBehavior{

	@Override
	public void active(User u) {}

	@Override
	public void inactive(User u) {}

	@Override
	public String toString() {
		return this.getClass().getName();
	}
}
