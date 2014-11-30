package com.example.chatpractice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

public class TranslationService {
	private String from = "en";
	private String to   = "es";
    private static String urlFormat = "http://translate.google.com/translate_a/t?client=t&text=%s&hl=en&sl=%s&tl=%s&ie=UTF-8&oe=UTF-8&multires=1&otf=1&pc=1&trs=1&ssel=3&tsel=6&sc=1";
    private static String urlEncode = "UTF-8";
	
	TranslationService(String from, String to) {
		this.from = from;
		this.to   = to;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
	public static String translate(String text, String from, String to) {
		TranslationService service = new TranslationService(from,to);
		return service.translate(text);
	}
	
	public String translate(String text) {
		String url      = formatUrl(text);
		String response = callUrl(url);
		String result   = parseJSON(response);
		return result;
	}
	
	private String encode(String str) {
		try {
			return URLEncoder.encode(str, urlEncode);
		} catch (UnsupportedEncodingException e) {
		}
		return "";
	}
	
	private String formatUrl(String text) {
		String url = String.format(urlFormat, encode(text), encode(from), encode(to));
		return url;
	}
	
	private String callUrl(String url) {
		StringBuilder line = new StringBuilder();
		try {
			HttpClient client = new DefaultHttpClient();
	        HttpGet request   = new HttpGet(url);
	        HttpResponse response = client.execute(request);

	        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
	        String lineAdd = "";
			while ((lineAdd = rd.readLine()) != null) {
	            line.append(lineAdd);
	        }
		} catch (Exception e) {
		}
		System.out.println(line);
		return line.toString();
	}
	
	private String parseJSON(String json) {
		String result = "";
		try {
			JSONArray ar1 = new JSONArray(json);
			JSONArray ar2 = ar1.getJSONArray(0);
			JSONArray ar3 = ar2.getJSONArray(0);
			result = ar3.getString(0);
		}
		catch (Exception e) {
		}
		return result;
	}
}
