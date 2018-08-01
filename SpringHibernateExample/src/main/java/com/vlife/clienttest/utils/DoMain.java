package com.vlife.clienttest.utils;

import org.json.JSONObject;

public class DoMain {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根

		String url = "http://stage.3gmimo.com/testing/log/elasticsearch/query.api";
		String t = OtherMethods.getTime();
		String str = "vlife-" + t + "-testing";
		String c = OtherMethods.doMD5(str);
		url = url + "?t=" + t + "&c=" + c;
		JSONObject jsonObject = HttpMethods.doPost(url,
				JSONMethds.setSearchJSON("packet", 1, "10582514920103", "/user/upload_operational_info"));

	}

}
