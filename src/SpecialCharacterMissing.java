package theliberryproject;

public class SpecialCharacterMissing extends PasswordException {
	public SpecialCharacterMissing() {
		super("Password needs an special character.");
	}
}
