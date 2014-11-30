package com.example.chatpractice;

public class Response {
    String message;
    String from;
    String original;
    String fromLang;
    String toLang;
    String time;

	Response(String message, String from, String original, String fromLang, String toLang, String time) {
		this.message = message;
		this.from = from;
		this.original = original;
		this.fromLang = fromLang;
		this.toLang = toLang;
		this.time = time;
	}
}
