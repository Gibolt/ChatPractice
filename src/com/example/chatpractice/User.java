package com.example.chatpractice;

public class User {
	private String name = "";
	private String lang = "en";
	
	User(String name, String lang) {
		this.name = name;
		this.lang = lang;
	}
	
	public String getName() {
		return name;
	}
	
	public String getLang() {
		return lang;
	}
}
