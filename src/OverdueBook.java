package theliberryproject;

public class OverdueBook extends Exception{

	public OverdueBook() {
		super("Please return any overdue book(s) first.");
	}
}
