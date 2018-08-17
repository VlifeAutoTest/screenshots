package com.vlife.clienttest.utils;

import java.math.BigInteger;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 本类写的是一些对JSON操作的一些方法
 * 
 * @author 高亚轩
 *
 */
public class JsonMethds {
	/**
	 * 通过输入参数组成对发送可运营信息需要的json的独享
	 * 
	 * @param type   json中的type的值
	 * @param size   json中的size的值
	 * @param userid json中的userid的值
	 * @param uri    json中的uri的值
	 * @return 返回的是上午json数据的json的对象
	 */
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

	/**
	 * 获取返回的json的request下的time值
	 * 
	 * @param jsonObject 请求返回来的JsonObject
	 * @return String 类型的 time 的值
	 */
	public static String getRequesTtime(JSONObject jsonObject) {
		JSONArray array = jsonObject.getJSONArray("data");
		JSONObject jsonObject2 = array.getJSONObject(0);
		String aa = jsonObject2.getString("request");
		System.out.println(aa);
		JSONObject jsonObject3 = new JSONObject(aa);
		BigInteger bigInteger = jsonObject3.getBigInteger("time");
		System.out.println(bigInteger);
		return bigInteger.toString();
	}

	/**
	 * 获取返回的json的request的adb_enabled的值
	 * 
	 * @param jsonObject 请求返回来的JsonObject
	 * @return String 类型的 adb_enabled 的值
	 */
	public static String getRequestAdbEnabled(JSONObject jsonObject) {
		JSONArray array = jsonObject.getJSONArray("data");
		JSONObject jsonObject2 = array.getJSONObject(0);
		String aa = jsonObject2.getString("request");
		JSONObject jsonObject3 = new JSONObject(aa);
		JSONObject jsonObject4 = jsonObject3.getJSONObject("data");
		JSONObject jsonObject5 = jsonObject4.getJSONObject("settings");
		int num = jsonObject5.getInt("adb_enabled");
		return String.valueOf(num).trim();

	}

	/**
	 * 获取返回的json的request的device_rooted的值
	 * 
	 * @param jsonObject 请求返回来的JsonObject
	 * @return String 类型的 device_rooted 的值
	 */
	public static String getRequestDeviceRootedt(JSONObject jsonObject) {
		JSONArray array = jsonObject.getJSONArray("data");
		JSONObject jsonObject2 = array.getJSONObject(0);
		String aa = jsonObject2.getString("request");
		JSONObject jsonObject3 = new JSONObject(aa);
		JSONObject jsonObject4 = jsonObject3.getJSONObject("data");
		JSONObject jsonObject5 = jsonObject4.getJSONObject("info");
		String num = jsonObject5.getString("device_rooted");
		return num;

	}

	/**
	 * 获取返回的json的request的device_rooted的值
	 * 
	 * @param jsonObject 请求返回来的JsonObject
	 * @return String 类型的 device_rooted 的值
	 */
	public static String getRequestSystemLogger(JSONObject jsonObject) {
		JSONArray array = jsonObject.getJSONArray("data");
		JSONObject jsonObject2 = array.getJSONObject(0);
		String aa = jsonObject2.getString("request");
		JSONObject jsonObject3 = new JSONObject(aa);
		JSONObject jsonObject4 = jsonObject3.getJSONObject("data");
		JSONObject jsonObject5 = jsonObject4.getJSONObject("info");
		String num = jsonObject5.getString("system_logger");
		return num;
	}

}
