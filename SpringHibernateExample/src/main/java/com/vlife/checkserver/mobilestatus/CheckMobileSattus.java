package com.vlife.checkserver.mobilestatus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jcraft.jsch.Session;

/**
 * @author: gaoyaxuan
 * @date:2018年8月13日 下午5:28:43
 */
public class CheckMobileSattus extends TimerTask {
	Methods methods = new Methods();
	List<Integer> list = new LinkedList<Integer>();

	@Override
	public void run() {
		List<String[]> serverList = methods.getTestServerValues();
		for (String[] temp : serverList) {
			try {
				// 把连接在机器上的手机状态判断了
				status(temp[0], 22, temp[1], temp[2]);
			} catch (Exception e) {
				System.out.println("连接测试机: " + temp[0] + "出现错误!");
				e.printStackTrace();
			}

		}

		// 把本次没有检测到的手机状态全部改为disconnect
		methods.assertNoDeviceMobile(list);

	}

	@SuppressWarnings("null")
	public void status(String host, int port, String user, String password) {

		Session session = null;
		try {
			try {
				session = methods.getSession(host, port, user, password);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			int serverID = methods.getTestServerID(host);
			if (session != null) {

				String devices[] = methods.getDevices(session);
				if (devices != null) {
					for (int i = 0; i < devices.length; i++) {
						int mobileStatusID = 0;
						String device = devices[i].trim();
						// 本次的server_id
						// 判断是数据里是否有这个手机
						// 如果数据库中没有这个手机device
						Integer  mobileID = methods.getMobileID(device);
						if (mobileID == null) {
							String size = methods.getSize(session, device);
							String os = methods.getOS(session, device);
							String vendor = methods.getMobileVendor(session, device);
							String name = methods.getMobilename(session, device);
							// 不存在这个新厂商
							Integer vendorID;
							Integer mobileVendorID=methods.getMobileVendorID(device);
							if(mobileVendorID!=null) {
								vendorID=mobileVendorID;
							}else {
							if (methods.getVendorID(vendor) == null) {
								methods.insertMobileVendor(vendor);
							}
							vendorID=methods.getVendorID(vendor);
							}
							// 给mobile插入手机的信息
							
							 String match = "((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))):([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-4]\\d{4}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])";
								Pattern pattern = Pattern.compile(match);
						        Matcher matcher = pattern.matcher(device);
						        if(matcher.matches()) {
						        	String udid=methods.getMobileUUID(session, device);
						        	methods.insertMobile(name.trim(), udid, size.trim(), os.trim(), vendorID, 1, device.trim().split(":")[0].trim(), Integer.parseInt(device.trim().split(":")[1].trim()));
						        }else {
						        	
						        	methods.insertMobile(name.trim(), device.trim(), size.trim(), os.trim(), vendorID,0,null,0);
						        }
							
							// 给mobilestatus表插入此手机的信息
							methods.insertMobileStatus(mobileID, serverID, "free");
							mobileStatusID = methods.getMobileStatusID(mobileID, serverID);
						}

						// 如果已含有的手机
						else {

							// 如果没有使用过
							if (methods.getMobileStatus(mobileID, serverID) == null) {
								methods.insertMobileStatus(mobileID, serverID, "free");
							} else { // 使用过的
								String status = methods.getMobileStatus(mobileID, serverID);
								// 空闲状态
								if (status.trim().equalsIgnoreCase("free")) {
									// 更新下日期
									methods.updateMobileStatus(mobileID, serverID, "free");
								}
								// 忙碌状态
								if (status.trim().equalsIgnoreCase("busy")) {

									String str = methods.getMobileLastConnectTime(mobileID, serverID);

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
									int time=Integer.parseInt(Methods.getProperty("change.mobile.busy.time"));
									long oneDay = time * 60 * 1000;
									// 如果忙碌状态的最后连接时间于今天比超过一天那么更改为free
									if (thistime - getTime >= oneDay) {
										methods.updateMobileStatus(mobileID, serverID, "free");
									}
								}
								// 失联状态
								else if (status.trim().equalsIgnoreCase("disconnect")) {
									methods.updateMobileStatus(mobileID, serverID, "free");
								}
							}
							mobileStatusID = methods.getMobileStatusID(mobileID, serverID);
						}
						list.add(mobileStatusID);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				methods.endSSH(session);
			}
		}
	}

}