package com.vlife.checkserver.mobilestatus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.commons.io.IOUtils;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
/**
 * @author: gaoyaxuan
 * @date:2018年8月13日 下午5:28:43
 */
public class Methods {
	private static String jdbcURL = getProperty("jdbc.url").split("\\?")[0];
	private static final String URL = jdbcURL + "?characterEncoding=utf8&useSSL=true&serverTimezone=GMT";
	private static final String USER = getProperty("jdbc.username");
	private static final String PASSWORD = getProperty("jdbc.password");
	private static Connection conn = null;
	JSch jsch = new JSch();
	Session session = null;
	ChannelExec channelExec = null;
	InputStream in = null;

	// 从application.properties里面获取参数的方法
	public static String getProperty(String propertyName) {
		Properties pro = new Properties();
		try {
			FileInputStream in = new FileInputStream(
					Methods.class.getClassLoader().getResource("application.properties").getPath());
			pro.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return pro.getProperty(propertyName).trim();
	}

	public Session getSession(String host, int port, String user, String password) {
		try {
			session = jsch.getSession(user, host, port);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(password);
			session.connect(6000);
		} catch (JSchException e) {
			e.printStackTrace();
		}
		return session;
	}

	public void endSSH(Session session) {
		if (channelExec != null) {
			channelExec.disconnect();
		}
		if (session != null) {
			session.disconnect();
		}

	}

	// 获取所有device的数组
	public String[] getDevices(Session session) {
		String[] result = null;
		try {
			channelExec = (ChannelExec) session.openChannel("exec");
			in = channelExec.getInputStream();
			// 获取devices
			channelExec.setCommand("source /etc/profile  && adb devices");
			channelExec.setErrStream(System.err);
			channelExec.connect();
			// 获取返回的结果
			String devicesResult = IOUtils.toString(in, "UTF-8");
			devicesResult = devicesResult.substring("List of devices attached".length());
			if (devicesResult.contains("device")) {

				result = devicesResult.trim().split("device");

				for (int i = 0; i < result.length; i++) {
					result[i] = result[i].trim();
				}
			} else {
			}
			in.close();
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public String exeShellCommand(Session session, String command) {
		String result = null;
		try {
			channelExec = (ChannelExec) session.openChannel("exec");
			in = channelExec.getInputStream();
			channelExec.setCommand("source /etc/profile  &&  " + command);
			channelExec.setErrStream(System.err);
			channelExec.connect();
			result = IOUtils.toString(in, "UTF-8");
			in.close();
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String execADBCommand(Session session, String device, String command) {
		String result = null;
		try {
			channelExec = (ChannelExec) session.openChannel("exec");
			in = channelExec.getInputStream();
			channelExec.setCommand("source /etc/profile  && adb -s  " + device + "  " + command);
			channelExec.setErrStream(System.err);
			channelExec.connect();
			result = IOUtils.toString(in, "UTF-8");
			in.close();
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 获取屏幕大小

	public String getSize(Session session, String device) {

		String re = execADBCommand(session, device, "shell wm size");
		return re.substring(re.indexOf(":") + 1, re.indexOf(":") + 11).trim();
	}

	// 获取系统版本
	public String getOS(Session session, String device) {

		String re = execADBCommand(session, device, "shell getprop ro.build.version.release");

		return re;
	}

	// 获取型号

	public String getMobilename(Session session, String device) {
		String re = execADBCommand(session, device, "shell getprop ro.product.model");
		if (re.contains(" ")) {
			re = re.replace(" ", "_");
		}

		return re;
	}

	// 获取厂商
	public String getMobileVendor(Session session, String device) {
		String re = execADBCommand(session, device, "shell getprop ro.product.manufacturer");
		return re;
	}

	// 获取uuid
	public String getMobileUUID(Session session, String device) {
		return execADBCommand(session, device, "shell cat /sys/class/android_usb/android0/iSerial");
	}

	// 查询mobile表是否含有指定的device

	public Integer getMobileID(String device) {

		Integer result = null;
		try {
			String sql = "select  * from mobile where uid =? and delete_flag = 0";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, device);
			ps.execute();
			ResultSet res = ps.executeQuery();
			while (res.next()) {

				result = res.getInt("id");
			}
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}

	// 给数据库增加新手机的信息

	public void insertMobile(String name, String uid, String size, String os, int vendor_id,int wififlag,String address,int port) {
		try {
			String sql = "insert into mobile values(null,?,?,?,?,?,?,?,?,0)";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = conn.prepareStatement(sql);
			// 从1开始 不是0
			ps.setString(1, name);
			ps.setString(2, uid);
			ps.setString(3, size);
			ps.setString(4, os);
			ps.setInt(5, vendor_id);
			ps.setInt(6, wififlag);
			ps.setString(7, address);
			ps.setInt(8, port);
			ps.execute();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	//获取删除了的手机的厂商
	public Integer getMobileVendorID(String device) {

		Integer result = null;
		try {
			String sql = "select  distinct(vendor_id) from mobile where uid =? and  delete_flag = 1";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, device);
			ps.execute();
			ResultSet res = ps.executeQuery();
			

				result = res.getInt("vendor_id");
			
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
			return result;
	}
	// 给数据库增加新厂商

	public void insertMobileVendor(String name) {
		try {
			String sql = "insert into vendor values(null,?,null)";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = conn.prepareStatement(sql);
			// 从1开始 不是0
			ps.setString(1, name.toLowerCase());

			ps.execute();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 查询厂商的ID

	public Integer getVendorID(String vendorName) {

		Integer result = null;
		try {
			String sql = "select  * from vendor where name =?";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, vendorName.toLowerCase());
			ps.execute();
			ResultSet res = ps.executeQuery();
			while (res.next()) {

				result = res.getInt("id");
			}
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	// 获取手机现在的状态
	public String getMobileStatus(int mobile_id, int server_id) {

		String result = null;
		try {
			String sql = "select  * from mobile_status where mobile_id =? and server_id=? ";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, mobile_id);
			ps.setInt(2, server_id);
			ps.execute();
			ResultSet res = ps.executeQuery();
			while (res.next()) {

				result = res.getString("status");
			}
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	// 获取手机现在的状态
	public String getMobileLastConnectTime(int mobile_id, int server_id) {

		String result = null;
		try {
			String sql = "select  * from mobile_status where mobile_id =? and server_id=? ";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, mobile_id);
			ps.setInt(2, server_id);
			ps.execute();
			ResultSet res = ps.executeQuery();
			while (res.next()) {

				result = res.getString("last_update");
			}
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	// 根据 host获取testserverid
	public Integer getTestServerID(String host) {

		Integer result = null;
		try {
			String sql = "select * from testserver where address=? and delete_flag =0";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, host);
			ps.execute();
			ResultSet res = ps.executeQuery();
			while (res.next()) {

				result = res.getInt("id");
			}
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	// 查询目前最大的bport

	public Integer getMaxBport() {

		Integer result = null;
		try {
			String sql = "select distinct(max(bport))  as bport from mobile_status ";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.execute();
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				result = res.getInt("bport");
			}
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	// 根据最大的bport获取目前最大的port
	public Integer getMaxPort(int bport) {

		Integer result = null;
		try {
			String sql = "select distinct(port) from mobile_status where bport= ?";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, bport);
			ps.execute();
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				result = res.getInt("port");
			}
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}
	

	// 给mobilestatus 表插入最新的手机信息

	public void insertMobileStatus(int mobile_id, int server_id, String status) {
		try {
			int maxBport = getMaxBport();
			int maxPort = getMaxPort(maxBport);
			String sql = "insert into mobile_status values (null,?,?,?,?,?,?)";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = conn.prepareStatement(sql);
			// 从1开始 不是0
			ps.setInt(1, mobile_id);
			ps.setInt(2, server_id);
			ps.setString(3, status);

			Date date = new Date();
			DateFormat fa = new SimpleDateFormat("yyyyMMddHHmm");
			String da = fa.format(date);
			ps.setString(4, da);
			ps.setInt(5, maxBport + 1);
			ps.setInt(6, maxPort + 1);
			ps.execute();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 更改手机状态

	public void updateMobileStatus(int mobile_id, int server_id, String status) {
		try {
			String sql = "update  mobile_status set status=? , last_update = ? where mobile_id=? and  server_id =?";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = conn.prepareStatement(sql);
			// 从1开始 不是0
			ps.setString(1, status);
			ps.setInt(3, mobile_id);
			ps.setInt(4, server_id);
			Date date = new Date();
			DateFormat fa = new SimpleDateFormat("yyyyMMddHHmm");
			String da = fa.format(date);
			ps.setString(2, da);
			ps.execute();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 判断free的是否失联
	public void assertFreeMobile() {

		try {
			String sql = "select * from mobile_status where status = 'free' ";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.execute();
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				int mobile_id = res.getInt("mobile_id");
				int server_id = res.getInt("server_id");
				String str = res.getString("last_update");
				Date date = new Date();
				DateFormat fa = new SimpleDateFormat("yyyyMMddHHmm");
				long thistime = date.getTime();
				Date date2 = null;
				try {
					date2 = fa.parse(str);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				long getTime = date2.getTime();
				long oneDay = 1 * 24 * 60 * 60 * 1000;

				// 如果忙碌状态的最后连接时间于今天比超过一天那么更改为free
				if (thistime - getTime > oneDay) {

					updateMobileStatus(mobile_id, server_id, "disconnect");

				}

			}
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 查询test_server中的所有服务器的信息

	public List<String[]> getTestServerValues() {

		List<String[]> list = new ArrayList<String[]>();
		try {
			String sql = "select * from testserver where delete_flag =0 ";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.execute();
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				String[] temp = new String[3];

				temp[0] = res.getString("address");
				temp[1] = res.getString("uname");
				temp[2] = res.getString("passwd");
				list.add(temp);
			}
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	// 把本次没有检测到的手机全部改为disconnect
	public void assertNoDeviceMobile(List<Integer> list) {
		String sql = "";
		String id = "";
		if (list != null && list.size() > 0) {
			for (int a : list) {
				id = id + a + ',';
			}
			id = id.substring(0, id.length() - 1);
			sql = "update mobile_status set status = 'disconnect' where id not in (" + id + ")";
		} else {
			sql = "update mobile_status set status = 'disconnect'";
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = conn.prepareStatement(sql);
			// 从1开始 不是0
			ps.execute();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	//
	public Integer getMobileStatusID(int mobile_id, int server_id) {

		Integer result = null;
		try {
			String sql = "select id from mobile_status where mobile_id =? and server_id =?";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, mobile_id);
			ps.setInt(2, server_id);
			ps.execute();
			ResultSet res = ps.executeQuery();
			while (res.next()) {

				result = res.getInt("id");
			}
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	// 正则表达式匹配下输入的是否是正确的ipv4的地址

	public Boolean matcherIP(String ip) {
		String match = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		Pattern pattern = Pattern.compile(match);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();

	}

}
