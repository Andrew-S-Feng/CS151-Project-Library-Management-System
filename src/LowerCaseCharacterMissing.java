package theliberryproject;

public class LowerCaseCharacterMissing extends PasswordException{
	public LowerCaseCharacterMissing() {
		super("Password needs an lowercase character.");
	}
}
