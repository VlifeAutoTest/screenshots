package com.vlife.checkserver.mobilestatus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.jcraft.jsch.Session;

public  class CheckMobileSattus  extends TimerTask{
	Methods methods = new Methods();
	
	
	@Override
	public void run()  {
		// TODO 自动生成的方法存根
		
			System.out.println();
		List<String[]> serverList=methods.getTestServerValues();    
		for (String [] temp:serverList){
		try {
			status(temp[0], 22, temp[1], temp[2]);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			System.out.println("连接测试机: "+ temp[0]+"出现错误!");
			e.printStackTrace();
		}

		}
	}

	
	public void  status(String host, int port, String user, String password) {
		
		Session session=null;
		try {
			try {
				session = methods.getSession(host, port, user, password);
			} catch (Exception e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			String devices[] = methods.getDevices(session);
			for (int i = 0; i < devices.length; i++) {
				String device = devices[i].trim();
				// 判断是数据里是否有这个手机
				//如果数据库中没有这个手机device
				if (methods.getMobileID(device) == null) {
					String size = methods.getSize(session, device);
					String os = methods.getOS(session, device);
					String vendor = methods.getMobileVendor(session, device);
					String name = methods.getMobilename(session, device);
					// 不存在这个新厂商
					if (methods.getVendorID(vendor) == null) {
						methods.insertMobileVendor(vendor);
					}
					// 给mobile插入手机的信息
					methods.insertMobile(name, device, size, os, methods.getVendorID(vendor));
					// 给mobilestatus表插入此手机的信息
					int mobileID = methods.getMobileID(device);
					int serverID = methods.getTestServerID(host);
					methods.insertMobileStatus(mobileID, serverID, "free");
				}

				// 如果已含有的手机
				else {
					int mobileID = methods.getMobileID(device);
					int serverID = methods.getTestServerID(host);
					// 如果没有使用过
					if (methods.getMobileStatus(mobileID, serverID) == null) {
						methods.insertMobileStatus(mobileID, serverID, "free");
					} else {    //使用过的
						String status = methods.getMobileStatus(mobileID, serverID);
						// 空闲状态直接跳过
						if (status.trim().equalsIgnoreCase("free")) {
							//更新下日期
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
								// TODO 自动生成的 catch 块
								e.printStackTrace();
							}
							long getTime = date2.getTime();
							long oneDay = 1 * 24 * 60 * 60 * 1000;
							//如果忙碌状态的最后连接时间于今天比超过一天那么更改为free
							if (thistime - getTime >= oneDay) {
								methods.updateMobileStatus(mobileID, serverID, "free");
							}
						}
						// 失联状态
						else if (status.trim().equalsIgnoreCase("disconnect")) {
							methods.updateMobileStatus(mobileID, serverID, "free");
						}
					}
				}
			}
			
			
			methods.assertFreeMobile();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		finally {
		if(session!=null){
			methods.endSSH();
		}
	}
}

	
	






}