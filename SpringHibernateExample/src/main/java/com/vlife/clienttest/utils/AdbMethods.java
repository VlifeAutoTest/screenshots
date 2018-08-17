package com.vlife.clienttest.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdbMethods {

	// 重启手机
	public static void ADBReboot(String device) {
		try {
			Process ss = Runtime.getRuntime().exec("adb -s  " + device + "  reboot");
			ss.waitFor();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	// 使用adb命令来实现一个滑屏操作

	public static void ADBswipe(String device, String beforeWide, String beforeHigh, String afterWide, String afterHigh,
			String Time) {

		try {

			Process ss = Runtime.getRuntime().exec("adb -s " + "  " + device + "  " + "shell input swipe" + " "
					+ beforeWide + " " + beforeHigh + " " + afterWide + " " + afterHigh + " " + Time);
			ss.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 检测当前屏幕是否是关屏状态!

	public static boolean isScreenOut(String devcie) {
		boolean flag = false;

		try {
			Runtime rt = Runtime.getRuntime();
			Process p = rt
					.exec("adb -s " + "  " + devcie + "  " + "shell dumpsys power | findstr \"Display Power:state=\"");
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			String content = "";

			while ((line = in.readLine()) != null)
				content = content + line;
			if (content.contains("Display Power: state=OFF"))
				flag = true;
			p.destroy();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return flag;
	}

	// 用来检测当前屏幕是否是锁屏状态
	public static boolean assertIsLock(String device) {
		boolean flag = false;
		try {
			Runtime rt = Runtime.getRuntime();
			// 第一种方法
			Process p = rt.exec(
					"adb -s " + "  " + device + "  " + "shell dumpsys window policy  | findstr \"mShowingLockscreen\"");

			// 第二种方法
			// Process p = rt.exec("adb -s "+" "+Data.PhoneDevice+" "
			// +"shell dumpsys window policy | findstr
			// \"isStatusBarKeyguard\"");

			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			String content = "";

			while ((line = in.readLine()) != null)
				content = content + line;

			// 第一种结果
			if (content.contains("mShowingLockscreen=true"))

				// 第二种结果

				// if (content.contains("isStatusBarKeyguard=true"))
				flag = true;
			p.destroy();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return flag;

	}

	// 点击方法 coordinate是int型的长度为2的坐标数组
	public static void tap(String device, int[] coordinate) {

		try {
			Process ss = Runtime.getRuntime().exec("adb -s " + "  " + device + "  " + "shell input tap  " + " "
					+ String.valueOf(coordinate[0]) + " " + String.valueOf(coordinate[1]));
			ss.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 把手机文件复制到本地PC上
	public static void pull(String device, String phonePath, String pcPath) {

		try {
			Process ss = Runtime.getRuntime()
					.exec("adb -s " + "  " + device + "  " + "pull " + " " + phonePath + "   " + pcPath);
			ss.waitFor();
		} catch (Exception e) {
			e.printStackTrace();

			System.out.println("复制文件pull方法出错");
		}
	}

	// 把本地文件push到手机
	public static void push(String device, String pcPath, String phonePath) {

		try {
			Process ss = Runtime.getRuntime()
					.exec("adb -s " + "  " + device + "  " + "push " + " " + pcPath + "   " + phonePath);
			ss.waitFor();
		} catch (Exception e) {
			e.printStackTrace();

			System.out.println("复制文件pull方法出错");
		}
	}

	// 获取当前界面的xml文件
	public static void getJMXX(String device, String Path) {

		try {
			Process ss = Runtime.getRuntime()
					.exec("adb -s " + "  " + device + "  " + "shell uiautomator dump " + " " + Path);
			ss.waitFor();

		} catch (Exception e) {

			System.out.println("获取界面控件XML出现问题!");
			e.printStackTrace();
		}
	}

	// 点击home键回到主屏幕

	public static void clickHome(String device) {
		try {

			Process ss = Runtime.getRuntime().exec("adb -s " + "  " + device + "  " + "shell input keyevent  3");
			ss.waitFor();

		} catch (Exception e) {

			System.out.println("获取桌面控件XML出现问题!");
			e.printStackTrace();
		}

	}

	// 结束shutdownAPP

	public static void shutdownAPP(String device, String APPName) {

		try {
			Process ss = Runtime.getRuntime()
					.exec("adb -s " + "  " + device + "  " + "shell am force-stop  " + "  " + APPName);
			ss.waitFor();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	// 清空指定应用的缓存信息

	public static void clearAPP(String device, String APPName) {

		try {

			Process ss = Runtime.getRuntime()
					.exec("adb -s " + "  " + device + "  " + "shell  pm clear  " + "  " + APPName);
			ss.waitFor();

		} catch (Exception e) {

			System.out.println("获取桌面控件XML出现问题!");
			e.printStackTrace();
		}

	}

	// 安装应用
	public static void installAPP(String device, String APPPath) {
		try {

			Process ss = Runtime.getRuntime().exec("adb -s " + "  " + device + "  " + "install  -r " + "  " + APPPath);
			ss.waitFor();

		} catch (Exception e) {

			System.out.println("获取桌面控件XML出现问题!");
			e.printStackTrace();
		}

	}

	// 启动一个服务
	public static void startService(String device, String packageNameAndActivity) {
		try {

			Process ss = Runtime.getRuntime()
					.exec("adb -s " + "  " + device + "  shell startservice  -n   " + packageNameAndActivity);
			ss.waitFor();

		} catch (Exception e) {

			System.out.println("获取桌面控件XML出现问题!");
			e.printStackTrace();
		}

	}

	/*
	 * adb connect 尝试连接手机,返回连接是否成功的boolean类型
	 */
	public static Boolean connectMobile(String ip, String port) {
		Boolean statu = false;
		try {
			Runtime rt = Runtime.getRuntime();
			String path = ip + ":" + port;
			Process p = rt.exec("adb  connect    " + path);
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			String content = "";
			while ((line = in.readLine()) != null)
				content = content + line;
			if (content.contains("connected to ")) {
				statu = true;
			} else {
				statu = false;
			}
			p.destroy();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return statu;
	}
	
	//获取wifi连接的手机的uuid
	
	public static String getUUID(String ip_port) {
		String content = "";
		try {
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec("adb -s "+" "+ip_port + "  "+"shell   cat /sys/class/android_usb/android0/iSerial");
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			
			while ((line = in.readLine()) != null)
				content = content + line;
			p.destroy();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return content.trim();
	}

}
