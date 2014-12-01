package com.example.chatpractice;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ChatApiClient {	
	private final String baseUrl = "104.236.28.245:4730/";
	private static String urlFormatRegister = "register/%s/%s";
	private static String urlFormatGet      = "get/%s";
	private static String urlFormatSend     = "send/%s/%s/%s";
	private static String urlFormatRandom   = "random/%s";
	private static String urlEncode = "UTF-8";

	private static JsonHttpResponseHandler registerUserHandler = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
			String success = ChatApiClient.parseRegisterResponse(response);
		}
	};

	private static JsonHttpResponseHandler getMessagesHandler = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
			ArrayList<Message> messages = ChatApiClient.parseGetResponse(response);
		}
	};

	private static JsonHttpResponseHandler randomUserHandler = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
			User user = ChatApiClient.parseRandomResponse(response);
		}
	};

	public void registerUser() {
		String url = String.format(urlFormatRegister, encode(UserMain.name()), encode(UserMain.lang()));
		GenericRestClient.get(fullUrl(url), null, registerUserHandler);
	}

	public void getMessages() {
		String url = String.format(urlFormatGet, encode(UserMain.name()));
		GenericRestClient.get(fullUrl(url), null, getMessagesHandler);
	}

	public void sendMessage(User to, String msg) {
		String url = String.format(urlFormatSend, encode(UserMain.name()), encode(to.getName()), encode(msg));
		GenericRestClient.get(fullUrl(url), null, null);
	}

	public void randomUser() {
		String url = String.format(urlFormatRandom, encode(UserMain.name()));
		GenericRestClient.get(fullUrl(url), null, randomUserHandler);
	}

	public static String parseRegisterResponse(JSONObject o) {
		String result = "";
		try {
			result = o.getString("success");
		} catch (JSONException e) {
		}
		return result;
	}

	public static ArrayList<Message> parseGetResponse(JSONArray json) {
		ArrayList<Message> list = new ArrayList<Message>();
		try {
			for (int i=0; i<json.length(); i++) {
				JSONObject o = (JSONObject) json.get(i);
				Message r = new Message(o.getString("message"), o.getString("from"), o.getString("lang"), o.getString("time"));
				list.add(r);
			}
		} catch (JSONException e) {
		}
		return list;
	}

	public static User parseRandomResponse(JSONObject json) {
		User user = null;
		try {
			user = new User(json.getString("name"), json.getString("lang"));
		} catch (JSONException e) {
		}
		return user;
	}

	private String encode(String str) {
		try {
			return URLEncoder.encode(str, urlEncode);
		} catch (UnsupportedEncodingException e) {
		}
		return "";
	}
	
	private String fullUrl(String url) {
		return baseUrl + url;
	}
}
