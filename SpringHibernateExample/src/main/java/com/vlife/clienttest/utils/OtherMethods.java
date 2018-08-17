package com.vlife.clienttest.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * 封装的是一些需要的其他方法
 * 
 * @author 高亚轩
 *
 */
public class OtherMethods {

	/**
	 * MD5加密 32位小写加密
	 * 
	 * @param doMD5String 要加密的字符串
	 * @return 返回加密了的字符串
	 */

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

	/**
	 * 获取当前的毫秒值
	 * 
	 * @return 毫秒值字符串
	 */
	public static String getTime() {
		Date date = new Date();
		return String.valueOf(date.getTime());
	}

	/**
	 * 判断当前系统是linux还是windows
	 * 
	 * @return
	 */
	public static boolean isOSLinux() {
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		String osName = "linux";
		if (os != null && os.toLowerCase().indexOf(osName) > -1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 给一个时间对象增加秒
	 * 
	 * @param date date对象
	 * @param num  需要增加的秒数
	 * @return 增加了秒数的date对象
	 */
	public static Date addSecond(Date date, int num) {
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.SECOND, num);
		return startDT.getTime();
	}

	/**
	 * 获取个 yyyyMMdd-HHmmss格式的时间戳
	 * 
	 * @return String 时间戳
	 */

	public static String getTimestamp() {
		DateFormat dateformat = new SimpleDateFormat("yyyyMMdd-HHmmss");
		Date date = new Date();
		String date1 = dateformat.format(date);
		return date1;
	}

}
