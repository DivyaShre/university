package com.university.utility;

public interface Constants {

	public static final String EMPTY_STRING = "";
	public static final String NEWLINE = "\n";
	public static final int IN_ACTIVE = 0;
	public static final int ACTIVE = 1;
	/* ^ - Match the beginning of the string, 
	   $ - Match the end of the string
	   \ is the escape character in reg exp. 
	   \\ the first \ escapes the character that is following - meaning you want to search for whatever is about to follow that \. 
	      It just so happens that what follows is another \ - so that means one of the characters you will accept is a \.
	*/
	public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,6})$";
	public static final String MOBILE_NO_PATTERN ="^[7-9]{1}[0-9]{9}$";
	
	String BASE_PATH = System.getProperty("user.home");
	
}
 
