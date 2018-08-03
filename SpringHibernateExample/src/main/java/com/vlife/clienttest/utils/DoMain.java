package com.vlife.clienttest.utils;

import org.json.JSONObject;

import com.jcraft.jsch.Session;
import com.vlife.checkserver.mobilestatus.Methods;

public class DoMain {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根

//		String url = "http://stage.3gmimo.com/testing/log/elasticsearch/query.api";
//		String t = OtherMethods.getTime();
//		String str = "vlife-" + t + "-testing";
//		String c = OtherMethods.doMD5(str);
//		url = url + "?t=" + t + "&c=" + c;
//		JSONObject jsonObject = HttpMethods.doPost(url,
//				JSONMethds.setSearchJSON("packet", 1, "10582514920103", "/user/upload_operational_info"));

		Methods methods =new Methods();
		Session session =methods.getSession("10.2.10.123", 22, "lang", "963852");
		System.out.println(methods.getSize(session, "10.2.10.135:9999"));
			
	}

}
