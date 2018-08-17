package com.vlife.clienttest.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * 本类封装的是以自我http相关的方法
 * 
 * @author gyx
 *
 */
public class HttpMethods {

	/**
	 * 后台api地址
	 */
	static String url = "http://stage.3gmimo.com/testing/log/elasticsearch/query.api";

	/**
	 * 做一次post请求,没有添加头信息
	 * 
	 * @param url  目标URL
	 * @param json 要发送的json的json的对象
	 * @return 返回的是服务器的回应信息
	 */
	public static JSONObject doPost(String url, JSONObject json) {

		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		JSONObject response = null;
		try {
			StringEntity s = new StringEntity(json.toString());
			s.setContentEncoding("UTF-8");
			// 发送json数据需要设置contentType
			s.setContentType("application/json");
			post.setEntity(s);
			HttpResponse res = httpclient.execute(post);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 返回json格式：
				String result = EntityUtils.toString(res.getEntity());
//	                response = JSONObject.fromObject(result);
				response = new JSONObject(result);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;
	}

	/**
	 * 向server发送一次请求来获取request的time
	 * 
	 * @param uid
	 * @return
	 */
	public static JSONObject serverAssert(String uid) {
		String url2 = url;
		String t = OtherMethods.getTime();
		String str = "vlife-" + t + "-testing";
		String c = OtherMethods.doMD5(str);
		System.out.println(uid);
		url2 = url2 + "?t=" + t + "&c=" + c;
		System.out.println(url2);
		JSONObject jsonObject = HttpMethods.doPost(url2,
				JsonMethds.setSearchJSON("packet", 1, uid, "/user/upload_operational_info"));
		return jsonObject;
	}

}
