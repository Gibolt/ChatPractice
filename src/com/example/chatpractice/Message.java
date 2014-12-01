package com.example.chatpractice;

public class Message {
    String message;
    String from;
    String lang;
    String time;

	Message(String message, String from, String lang, String time) {
		this.message = message;
		this.from = from;
		this.lang = lang;
		this.time = time;
	}
}
