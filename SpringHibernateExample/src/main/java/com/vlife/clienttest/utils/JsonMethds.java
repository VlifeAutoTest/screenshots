package com.vlife.clienttest.utils;

import org.json.JSONObject;

public class JsonMethds {

	public static JSONObject setSearchJSON(String type, int size, String userid, String uri) {
		JSONObject jsono = new JSONObject();
		JSONObject js2 = new JSONObject();
		js2.put("session.userid", userid);
		js2.put("uri", uri);
		jsono.put("type", type);
		jsono.put("size", size);
		jsono.put("query", js2);
		System.out.println(jsono.toString());
		return jsono;
	}

}
