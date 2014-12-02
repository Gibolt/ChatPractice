package com.example.chatpractice;

public class UserMain extends User {
	private static String name;
	private static String lang = "en";
	UserMain(String name, String lang) {
		super(name, lang);
		UserMain.name = name;
		UserMain.lang = lang;
	}
	
	public static void set(String name, String lang) {		
		UserMain.name = name;
		UserMain.lang = lang;
	}
	
	public static String name() {
		return name;
	}
	
	public static String lang() {
		return lang;
	}
}
