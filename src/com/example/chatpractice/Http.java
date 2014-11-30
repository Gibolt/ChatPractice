package com.example.chatpractice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Http {
	private final String USER_AGENT = "Mozilla/5.0";
	private final String URL = "104.236.28.245:4730/";
 
	public static void main(String[] args) throws Exception {
 
		Http http = new Http();

		System.out.println("\nTesting 2 - Send Http POST request");
		http.sendPost();
 
	}
 
	// HTTP POST request
	private String sendPost() {
		return sendPost("");
	}
	
	private String sendPost(String params) {
		try {
		String url = URL + params;
//		URL obj = new URL(url);
//		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		HttpClient client = new DefaultHttpClient();
        HttpGet request   = new HttpGet(url);
        HttpResponse response;

			response = client.execute(request);

        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
        String line = "", lineAdd = "";
		while ((lineAdd = rd.readLine()) != null) {
            line += lineAdd;
        }
		return line;
		} catch (Exception e) {
		}
		return "";
	}

	public void registerUser(String user, String lang) {
		try {
			String params = user + "/" + lang;
			String response = sendPost(params);
			JSONObject obj;

			obj = new JSONObject(response);
			String s = obj.getString("success");

		} catch (Exception e) {
		}
		// TODO: Finish Here
	}

	public void send(String from, String to, String msg) {
		try {
			String params = to + "/" + from + "/" + msg;
			String response = sendPost(params);
		}
		catch (Exception e) {
		}
		// TODO: Finish Here
	}

	public void translate(String from, String to, String msg) {
		try{
			String params = to + "/" + from + "/" + msg;
			String response = sendPost(params);
			JSONObject obj = new JSONObject(response);
			String text = obj.getString("text");
		}
		catch (Exception e) {
		}
		// TODO: Finish Here
	}
	
	public void get(String user) {
		try {
			String params = user;
			String response = sendPost(params);
			
			JSONArray arr = new JSONArray(response);
			ArrayList<Response> list = new ArrayList<Response>();
			for (int i=0; i<arr.length(); i++) {
				JSONObject o = (JSONObject)arr.get(i);
				Response r = new Response(o.getString("translated"), o.getString("from"), o.getString("original"), o.getString("fromLang"), o.getString("toLang"), o.getString("time"));
				list.add(r);
			}
		}
		catch (Exception e) {
		}
		// TODO: Finish Here
		
	}
	
	public void tts(String text, String lang) {
		
	}
}
