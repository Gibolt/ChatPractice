package com.example.chatpractice;

import com.loopj.android.http.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.Header;
import org.json.*;

public class TranslationService {
	private String from = "en";
	private String to   = "es";
	private static String urlFormat = "http://translate.google.com/translate_a/t?client=t&text=%s&hl=en&sl=%s&tl=%s&ie=UTF-8&oe=UTF-8&multires=1&otf=1&pc=1&trs=1&ssel=3&tsel=6&sc=1";
	private static String urlEncode = "UTF-8";

	private static JsonHttpResponseHandler addToMessages = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
			String translation = TranslationService.parseGoogleResponse(response);
			MainActivity.addItems(translation);
		}
	};

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

	public static void translate(String text, String from, String to) {
		TranslationService service = new TranslationService(from,to);
		service.translate(text);
	}

	public void translate(String text) {
		translate(text, addToMessages);
	}

	private void translate(String text, JsonHttpResponseHandler handler) {
		String url = formatUrl(text);
		GenericRestClient.get(url, null, handler);
	}

	/*
	 * This encodes the message so that Google will translate multiple sentences
	 */
	private String messageEncode(String text) {
		text = text.replaceAll("\\.", "。");
		text = text.replaceAll("\\?", "？");
		text = text.replaceAll("\\!", "！");
		text = text.replaceAll("\\;", "；");
		return encode(text);
	}
	
	private String encode(String str) {
		try {
			return URLEncoder.encode(str, urlEncode);
		} catch (UnsupportedEncodingException e) {
		}
		return "";
	}

	private String formatUrl(String text) {
		String url = String.format(urlFormat, messageEncode(text), encode(from), encode(to));
		return url;
	}

	public static String parseGoogleResponse(JSONArray json) {
		String result = "";
		try {
			result = json.getJSONArray(0).getJSONArray(0).getString(0);
		}
		catch (Exception e) {
		}
		return result;
	}
}
