package com.vlife.springmvc.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.io.IOUtils;
import com.jcraft.jsch.Session;
import com.vlife.springmvc.dao.RuninfoDao;
import com.vlife.springmvc.dao.TestServerDao;
import com.vlife.springmvc.model.Runinfo;
import com.vlife.springmvc.model.TestServer;
import com.vlife.springmvc.service.VendorService;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
@Service("runinfoService")
@Transactional
public class RuninfoServiceImpl implements RuninfoService{
	JSch jsch = new JSch();
	Session session = null;
	ChannelExec channelExec = null;
	InputStream in = null;
	@Autowired
	private RuninfoDao dao;
	@Autowired
	private TestServerDao tdao;
	@Autowired
	private VendorService vendor_service;
	
	@Autowired
	private MobileService mobile_service;
	
	@Autowired
	private ApplicationService app_service;
	
	@Autowired
	private ThemeService theme_service;
	
	@Autowired
	private TestServerService server_service;
	
	public void saveRuninfo(Runinfo runinfo) {
		
		dao.saveRuninfo(runinfo);
		
	}
	
	
	public List<Runinfo> findAllRuninfo(){
		return dao.findAllRuninfo();
	}
	
	public List<Runinfo> queryData(Map<String, String> conditions, Date[] mytime){
		return dao.queryData(conditions, mytime);
	}
	
	
	public List<Object[]> translaterinfo(List<Runinfo> runinfos){
		
		List<Object[]> res = new ArrayList<Object[]>();
		
		for(int i=0; i< runinfos.size(); i++) {
			
			Object[] tmp = new Object[11];
			
			Runinfo rf = runinfos.get(i);
			
			// vendor name
			int vid = rf.getVid();
			tmp[0] = vendor_service.findById(vid).getName();
			
			// mobile name
			int mid = rf.getMid();
			tmp[1] = mobile_service.findById(mid).getName();
			
			// server name
			int sid = rf.getSid();
			tmp[2] = server_service.findById(sid).getSsn();
			
			// resource
			String[] source = rf.getResource().split(",");
			String resources="";
			for(int j=0; j< source.length; j++) {
				int id = Integer.parseInt(source[j]);
				resources = resources + theme_service.findById(id).getName() + ",";
			}
			tmp[3] = resources;
			
			// apps
			String[] application = rf.getApp().split(",");
			String apps="";
			for(int j=0; j< application.length; j++) {
				int id = Integer.parseInt(application[j]);
				apps = apps + app_service.findById(id).getName() + ",";
			}
			tmp[4] = apps;
			
			//other infos
			tmp[5] = rf.getStime();
			tmp[6] = rf.getEtime();
			tmp[7] = rf.getImagepath();
			tmp[8] = rf.getZip();
			tmp[9] = rf.getLogFile();
			tmp[10] = rf.getStatus();
			
			res.add(tmp);
			
		}
			

		return res;	
		
	}

	//获取本次连接
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

			//结束本次连接
			public void endSSH() {
//				if(channelExec!=null) {
//					channelExec.disconnect();
//				}
				if(session!=null) {
					session.disconnect();
				}
			}
			//执行命令
			public String execCommand(Session session, String pythonPath,String parameters) {
				String result = null;
				try {
					channelExec = (ChannelExec) session.openChannel("exec");
					in = channelExec.getInputStream();
					channelExec.setCommand( "python  "+ pythonPath+"  "+parameters  );
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


			@Override
			public TestServer getTestServer(int sid) {
				// TODO 自动生成的方法存根
				
			return	tdao.findById(sid);
				
			}


			@Override
			public void execShellCommand(Session session, String python) {
				// TODO 自动生成的方法存根
				//shell方式执行
					 try{
					ChannelShell channel=(ChannelShell) session.openChannel("shell");
			        channel.setPty(true);
			        channel.connect();
			        OutputStream os = channel.getOutputStream();
			        os.write((python +"\r\n").getBytes());
			        os.flush();
			        os.close();
			        Thread.sleep(1000);
			        channel.disconnect();
//			        session.disconnect();
					 }catch(Exception e){
			        e.printStackTrace();
			    }

				
			}

}
