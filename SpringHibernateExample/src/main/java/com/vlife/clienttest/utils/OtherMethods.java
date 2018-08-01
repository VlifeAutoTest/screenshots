package com.vlife.clienttest.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Date;

public class OtherMethods {

	// MD5加密

	public static String doMD5(String doMD5String) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}

		byte[] md5Bytes = null;
		StringBuffer hexValue = null;
		try {
			byte[] byteArray = doMD5String.getBytes("UTF-8");
			md5Bytes = md5.digest(byteArray);
			hexValue = new StringBuffer();
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	// 获取当前的毫秒值
	public static String getTime() {
		Date date = new Date();
		return String.valueOf(date.getTime());
	}

}
