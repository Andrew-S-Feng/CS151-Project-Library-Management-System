package theliberryproject;

public class UpperCaseCharacterMissing extends PasswordException {
	public UpperCaseCharacterMissing() {
		super("Password needs an uppercase character.");
	}
}
