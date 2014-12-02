package com.example.chatpractice;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URL {
	private static String urlEncode = "UTF-8";
	private static final String baseUrl = "http://wildhacks.cloudapp.net:4730/";

	public static String format(String base, String ... str) {
		return formatBase(baseUrl, base, str);
	}
	
	public static String formatSpecial(String baseUrl, String base, String ... str) {
		return formatBase(baseUrl, base, str);
	}

	private static String formatBase(String baseUrl, String base, String ... str) {
		for (int i=0; i<str.length; i++) {
			str[i] = encode(str[i]);
		}
		return baseUrl + String.format(base, (Object[]) str);
	}
	
	public static String encode(String str) {
		try {
			return URLEncoder.encode(str, urlEncode)
					   .replaceAll("\\%28", "(")                          
					   .replaceAll("\\%29", ")")   		
					   .replaceAll("\\+", "%20")                          
					   .replaceAll("\\%27", "'")   			   
					   .replaceAll("\\%21", "!")
					   .replaceAll("\\%7E", "~");
		} catch (UnsupportedEncodingException e) {
		}
		return "";
	}
}
