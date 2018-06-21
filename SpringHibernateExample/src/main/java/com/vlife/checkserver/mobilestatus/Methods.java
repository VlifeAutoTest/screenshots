package com.vlife.checkserver.mobilestatus;

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

import org.apache.commons.io.IOUtils;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class Methods {
	public static final String URL = "jdbc:mysql://192.168.1.230:3306/mobile_screenshot?characterEncoding=utf8&useSSL=true&serverTimezone=GMT";
	public static final String USER = "auto_test";
	public static final String PASSWORD = "vlife";
	private static Connection conn = null;
	JSch jsch = new JSch();
	Session session = null;
	ChannelExec channelExec = null;
	InputStream in = null;

	public Session getSession(String host, int port, String user, String password) {
		try {
			session = jsch.getSession(user, host, port);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(password);
			session.connect();
		} catch (JSchException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return session;
	}

	public void endSSH() {
		channelExec.disconnect();
		session.disconnect();

	}

	// 获取所有device的数组
	public String[] getDevices(Session session) {
		String[] result = null;
		try {
			channelExec = (ChannelExec) session.openChannel("exec");
			in = channelExec.getInputStream();
			// 获取devices
			channelExec.setCommand("adb devices");
			channelExec.setErrStream(System.err);
			channelExec.connect();
			// 获取返回的结果
			String devicesResult = IOUtils.toString(in, "UTF-8");
			devicesResult = devicesResult.substring("List of devices attached".length());
			result = devicesResult.trim().split("device");

			for (int i = 0; i < result.length; i++) {
				result[i] = result[i].trim();

			}
			in.close();
		} catch (JSchException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		return result;
	}

	public String execCommand(Session session, String device, String command) {
		String result = null;
		try {
			channelExec = (ChannelExec) session.openChannel("exec");
			in = channelExec.getInputStream();
			channelExec.setCommand("adb -s  " + device + "  " + command);
			channelExec.setErrStream(System.err);
			channelExec.connect();
			result = IOUtils.toString(in, "UTF-8");
			in.close();
		} catch (JSchException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}

	// 获取屏幕大小

	public String getSize(Session session, String device) {

		String re = execCommand(session, device, "shell wm size");
		return re.substring(re.indexOf(":") + 1).trim();
	}

	// 获取系统版本
	public String getOS(Session session, String device) {

		String re = execCommand(session, device, "shell getprop ro.build.version.release");

		return re;
	}

	// 获取型号

	public String getMobilename(Session session, String device) {
		String re = execCommand(session, device, "shell getprop ro.product.model");
		return re;
	}

	// 获取厂商
	public String getMobileVendor(Session session, String device) {
		String re = execCommand(session, device, "shell getprop ro.product.manufacturer");
		return re;
	}

	// 查询mobile表是否含有指定的device

	public Integer getMobileID(String device) {

		Integer result = null;
		try {
			String sql = "select  * from mobile where uid =?";
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;

	}

	// 给数据库增加新手机的信息

	public void insertMobile(String name, String uid, String size, String os, int vendor_id) {
		try {
			String sql = "insert into mobile values(null,?,?,?,?,?)";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = conn.prepareStatement(sql);
			// 从1开始 不是0
			ps.setString(1, name);
			ps.setString(2, uid);
			ps.setString(3, size);
			ps.setString(4, os);
			ps.setInt(5, vendor_id);
			ps.execute();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	// 给数据库增加新厂商

	public void insertMobileVendor(String name) {
		try {
			String sql = "insert into vendor values(null,?)";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = conn.prepareStatement(sql);
			// 从1开始 不是0
			ps.setString(1, name);

			ps.execute();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
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
			ps.setString(1, vendorName);
			ps.execute();
			ResultSet res = ps.executeQuery();
			while (res.next()) {

				result = res.getInt("id");
			}
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;

	}

	// 根据 host获取testserverid
	public Integer getTestServerID(String host) {

		Integer result = null;
		try {
			String sql = "select * from testserver where address=? ";
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;

	}

	// 给mobilestatus 表插入最新的手机信息

	public void insertMobileStatus(int mobile_id, int server_id, String status) {
		try {
			String sql = "insert into mobile_status values (null,?,?,?,?)";
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
			ps.execute();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}
	
	
	//更改手机状态
	
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}
	
	//判断free的是否失联
	public void  assertFreeMobile() {

		try {
			String sql = "select * from mobile_status where status = 'free' ";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.execute();
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				int mobile_id =res.getInt("mobile_id");
				int server_id =res.getInt("server_id");
				String str = res.getString("last_update");
//				if(str==null){
//					updateMobileStatus(mobile_id, server_id, "disconnect");
//					continue;
//				}
				Date date = new Date();
				DateFormat fa = new SimpleDateFormat("yyyyMMddHHmm");
				long thistime = date.getTime();
				Date date2 = null;
				try {
					date2 = fa.parse(str);
				} catch (ParseException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				long getTime = date2.getTime();
				long oneDay = 1 * 24 * 60 * 60 * 1000;
				
				//如果忙碌状态的最后连接时间于今天比超过一天那么更改为free
				 if (thistime - getTime > oneDay) {
					
					updateMobileStatus(mobile_id, server_id, "disconnect");
					
				}
				
			}
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}
	
	
	//查询test_server中的所有服务器的信息
	
	public List<String []>  getTestServerValues() {

		List <String []>  list =new ArrayList<String []>();
		try {
			String sql = "select * from testserver ";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.execute();
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				String [] temp= new String [3];
				
				temp[0] = res.getString("address");
				temp[1] =res.getString("uname");
				temp[2] =res.getString("passwd");
				list.add(temp);
			}
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		return list;
	}
	
	
	
	

}
