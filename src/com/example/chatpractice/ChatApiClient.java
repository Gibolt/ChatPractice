package com.example.chatpractice;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ChatApiClient {
	private static String urlFormatRegister = "register/%s/%s";
	private static String urlFormatGet      = "get/%s";
	private static String urlFormatSend     = "send/%s/%s/%s";
	private static String urlFormatRandom   = "random/%s";

	private static JsonHttpResponseHandler registerUserHandler = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
			String success = ChatApiClient.parseRegisterResponse(response);
			Log.d("Translate", success);
			MainActivity.addItems(success);
		}
	};

	private static JsonHttpResponseHandler getMessagesHandler = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
			ArrayList<Message> messages = ChatApiClient.parseGetResponse(response);
			for (Message m : messages) {
				MainActivity.addItems(m.toString());
			}
		}
	};
	
	private static JsonHttpResponseHandler sendMessagesHandler = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
			MainActivity.addItems("Sent successfully");
		}
	};

	private static JsonHttpResponseHandler randomUserHandler = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
			User user = ChatApiClient.parseRandomResponse(response);
		}
	};

	public void registerUser() {
		String url = URL.format(urlFormatRegister, UserMain.name(), UserMain.lang());
		GenericRestClient.get(url, null, registerUserHandler);
	}

	public void getMessages() {
		String url = URL.format(urlFormatGet, UserMain.name());
		GenericRestClient.get(url, null, getMessagesHandler);
	}

	public void sendMessage(User to, String msg) {
		String url = URL.format(urlFormatSend, UserMain.name(), to.getName(), msg);
		GenericRestClient.get(url, null, sendMessagesHandler);
	}

	public void randomUser() {
		String url = URL.format(urlFormatRandom, UserMain.name());
		GenericRestClient.get(url, null, randomUserHandler);
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
}
