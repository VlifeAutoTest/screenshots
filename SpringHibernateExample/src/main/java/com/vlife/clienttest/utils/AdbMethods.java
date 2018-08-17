package com.vlife.clienttest.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * 关于ADB的一些方法
 * 
 * @author 高亚轩
 */
public class AdbMethods {
	/**
	 * 调用 cmd或者shell执行命令
	 * 
	 * @param device
	 * @param ommand
	 */
	public static void execCommand(String device, String command) {
		try {
			Process ss = Runtime.getRuntime().exec("adb  -s  " + device + "  " + command);
			ss.waitFor();
			ss.destroy();
		} catch (IOException e) {
			System.out.println("execCommand方法出现异常");
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("execCommand方法出现异常");
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	/**
	 * 判断 device的手机是否连接
	 * 
	 * @param devcie 需要判断的手机device
	 * @return true 连接 false 没有连接
	 */
	public static Boolean isConnect(String device) {
		String content = "";
		try {
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec("adb  devices");
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;

			while ((line = in.readLine()) != null) {

				content = content + line;
			}
			p.destroy();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if (content.contains(device)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 重启手机
	 * 
	 * @param device device
	 */
	public static void adbReboot(String device) {
		execCommand(device, "reboot");
	}

	/**
	 * 使用adb命令来实现一个滑屏操作
	 * 
	 * @param device  device
	 * @param beforeX 滑动前的x坐标
	 * @param beforeY 滑动前的y坐标
	 * @param afterX  滑动后的x坐标
	 * @param afterY  滑动后的y坐标
	 * @param time    多少毫秒内完成
	 */

	public static void adbSwipe(String device, int beforeX, int beforeY, int afterX, int afterY, int time) {

		String command = "shell input swipe" + " " + beforeX + " " + beforeY + " " + afterX + " " + afterY + " " + time;
		execCommand(device, command);

	}

	/**
	 * 检测当前屏幕是否是关屏状态!
	 * 
	 * @param devcie device
	 * @return true 关屏状态 false 非关屏状态
	 */
	public static boolean isScreenOut(String device) {
		boolean flag = false;

		try {
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec("adb -s " + "  " + device + "  " + "shell dumpsys power ");
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			String content = "";

			while ((line = in.readLine()) != null) {

				content = content + line;
			}
			String keyWord = "Display Power: state=OFF";
			if (content.contains(keyWord)) {

				flag = true;
			}
			p.destroy();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 用来检测当前屏幕是否是锁屏状态
	 * 
	 * @param device device
	 * @return true 锁屏状态 flase 非锁屏状态
	 */
	public static boolean assertIsLock(String device) {
		boolean flag = false;
		try {
			Runtime rt = Runtime.getRuntime();
			// 第一种方法
			Process p = rt.exec("adb -s " + device + "  shell dumpsys window policy  ");

			// 第二种方法
			// Process p = rt.exec("adb -s "+" "+Data.PhoneDevice+" "
			// +"shell dumpsys window policy | findstr
			// \"isStatusBarKeyguard\"");

			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			String content = "";

			while ((line = in.readLine()) != null) {
				content = content + line;
			}
			// 第一种结果
			String keyWord = "mShowingLockscreen=true";
			if (content.contains(keyWord)) {
				// 第二种结果
				// if (content.contains("isStatusBarKeyguard=true"))
				flag = true;
			}

			p.destroy();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return flag;

	}

	/**
	 * 点击方法 coordinate是int型的长度为2的坐标数组
	 * 
	 * @param device     device
	 * @param coordinate 点击位置的xy坐标组成的数组 (长度为2)
	 */
	public static void tap(String device, int[] coordinate) {
		String command = "shell input tap  " + " " + String.valueOf(coordinate[0]) + " "
				+ String.valueOf(coordinate[1]);
		execCommand(device, command);
	}

	/**
	 * 把手机文件复制到本地PC上
	 * 
	 * @param device    device
	 * @param phonePath 手机上的文件路径
	 * @param pcPath    PC上的路径
	 */
	public static void pull(String device, String phonePath, String pcPath) {
		String command = "pull " + " " + phonePath + "   " + pcPath;
		execCommand(device, command);
	}

	/**
	 * 把本地文件push到手机
	 * 
	 * @param device    deice
	 * @param pcPath    电脑上的文件路径
	 * @param phonePath 手机上的路径
	 */
	public static void push(String device, String pcPath, String phonePath) {
		String command = "push  " + pcPath + "   " + phonePath;
		execCommand(device, command);
	}

	/**
	 * 获取当前界面的xml文件
	 * 
	 * @param device device
	 * @param Path   把xml文件保存到手机上的路径
	 */
	public static void getUiautomator(String device, String path) {
		String command = "shell uiautomator dump  " + path;
		execCommand(device, command);
	}

	/**
	 * 点击home键回到主屏幕
	 * 
	 * @param device device
	 */

	public static void clickHome(String device) {
		String command = "shell input keyevent  3";
		execCommand(device, command);

	}

	public static void clickPower(String device) {
		String command = "shell input keyevent  26";
		execCommand(device, command);
	}

	/**
	 * 结束shutdownAPP
	 * 
	 * @param device      device
	 * @param packageName 由包名和activity组成的 adb shell dumpsys activity activities
	 *                    ,cmp=后面的
	 */

	public static void shutdownAPP(String device, String packageName) {

		String command = "shell am force-stop  " + packageName;
		execCommand(device, command);
	}

	/**
	 * 清空指定应用的缓存信息
	 * 
	 * @param device      device
	 * @param packageName 包名
	 */

	public static void clearAPP(String device, String packageName) {
		String command = "shell  pm clear  " + packageName;
		execCommand(device, command);

	}

	/**
	 * 安装应用
	 * 
	 * @param device  device
	 * @param appPath 应用的路径
	 * @return
	 * @throws InterruptedException
	 */
	public static String installAPP(String device, String appPath) throws InterruptedException {

//		try {
//			Process ss = Runtime.getRuntime().exec("adb  -s  " + device + "  install -r  " + appPath);
//			Thread.sleep(5000);
//			//ss.destroy();
//		} catch (IOException e) {
//			System.out.println("execCommand方法出现异常");
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		} 
		try {
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec("adb  -s  " + device + "  install -r  " + appPath);
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {

				if (line.contains("pkg: /data/local/tmp/")) {
					Thread.sleep(2000);
					return "";
				}
			}
			p.destroy();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 卸载应用
	 * 
	 * @param device      device
	 * @param packageName 包名
	 */
	public static void uninstallAPP(String device, String packageName) {
		String command = "uninstall  " + packageName;
		execCommand(device, command);
	}

	/**
	 * 启动一个服务
	 * 
	 * @param device                 deice
	 * @param packageNameAndActivity 包名和activity组成
	 */
	public static void startService(String device, String packageNameAndActivity) {
		String command = "shell am startservice  -n   " + packageNameAndActivity;
		execCommand(device, command);

	}

	/**
	 * adb connect 尝试连接手机
	 * 
	 * @param ip
	 * @param port
	 * @return 返回Boolean类型 true 连着成功 false 失败
	 */
	public static Boolean connectMobile(String ip, String port) {
		Boolean statu = false;
		try {
			Runtime rt = Runtime.getRuntime();
			String path = ip + ":" + port;
			Process p = rt.exec("adb  connect  " + path);
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			String content = "";
			while ((line = in.readLine()) != null) {

				content = content + line;
			}
			String keyWord = "connected to ";
			if (content.contains(keyWord)) {
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

	/**
	 * 获取wifi连接的手机的uuid
	 * 
	 * @param ip_Port 通过adb connect 连接的手机 ,adb device 显示的IP:Port
	 * @return 此手机的udid
	 */

	public static String getUUID(String ipPort) {
		String content = "";
		try {
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec("adb -s  " + ipPort + "  shell   cat /sys/class/android_usb/android0/iSerial");
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;

			while ((line = in.readLine()) != null) {

				content = content + line;
			}
			p.destroy();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return content.trim();
	}

	/**
	 * 获取second 时间的log信息
	 * 
	 * @param device 需要获取log的device
	 * @param second 获取多少秒
	 * @return 每条记录占用一个位置的数组
	 */
	public static String[] getLog(String device, int second) {
		StringBuffer content = new StringBuffer();
		try {
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec("adb -s  " + device + "   logcat");
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;

			Date date2 = OtherMethods.addSecond(new Date(), second);
			while ((line = in.readLine()) != null) {
				content.append(line);
				content.append("%#%");
				Date date = new Date();
				if (date2.before(date)) {
					break;
				}
			}
			p.destroy();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return content.toString().split("%#%");
	}

	/**
	 * 解锁手机的方法
	 * 
	 * @param device  以下参数是adb shell input swipe 需要的参数
	 * @param beforeX
	 * @param beforeY
	 * @param afterX
	 * @param afterY
	 * @param time
	 */
	public static void unLockPhone(String device, int beforeX, int beforeY, int afterX, int afterY, int time) {

		if (AdbMethods.isScreenOut(device)) {
			AdbMethods.clickPower(device);
		}
		if (AdbMethods.assertIsLock(device)) {
			AdbMethods.adbSwipe(device, beforeX, beforeY, afterX, afterY, time);
		}
		AdbMethods.clickHome(device);
	}

	/**
	 * 判断设备是否root
	 * 
	 * @param device
	 * @return true root flase 没有root
	 */
	public static Boolean isRooted(String device) {
		String content = "";
		try {
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec("adb -s  " + device + "shell");
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;

			while ((line = in.readLine()) != null) {

				content = content + line;
			}
			p.destroy();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		String rootKeyWord = "#";
		if (content.contains(rootKeyWord)) {
			return true;
		} else {
			return false;
		}

	}
}
