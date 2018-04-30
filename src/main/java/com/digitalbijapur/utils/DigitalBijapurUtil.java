package com.digitalbijapur.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DigitalBijapurUtil {

	public static enum USER_STATUS {
		PENDING, VERIFIED, BLOCKED;
	};

	public static enum USER_ROLE {
		ADMIN, REGISTERED, GUEST
	};

	private static final Pattern PASSWORD_PATTERN = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	/**
	 * @param emailAsString
	 * @return
	 */
	public static boolean isValidEmail(String emailAsString) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailAsString);
		return matcher.find();
	}
	
	/**
	 * @param emailAsString
	 * @return
	 */
	public static boolean isValidPassword(String passowordAsString) {
		Matcher matcher = PASSWORD_PATTERN.matcher(passowordAsString);
		return matcher.find();
	}

}
