package com.vlife.springmvc.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
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
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;

@Service("runinfoService")
@Transactional
public class RuninfoServiceImpl implements RuninfoService {
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

	public List<Runinfo> findAllRuninfo() {
		return dao.findAllRuninfo();
	}

	public List<Runinfo> queryData(Map<String, String> conditions, Date[] mytime) {
		return dao.queryData(conditions, mytime);
	}

	public List<Object[]> translaterinfo(List<Runinfo> runinfos) {

		List<Object[]> res = new ArrayList<Object[]>();

		for (int i = 0; i < runinfos.size(); i++) {

			Object[] tmp = new Object[12];

			Runinfo rf = runinfos.get(i);

			// vendor name
			int vid = rf.getVid();
			tmp[0] = vendor_service.findById(vid).getName();

			// mobile name
			int mid = Integer.parseInt(rf.getMid());
			tmp[1] = mobile_service.findById(mid).getName();

			// server name
			int sid = rf.getSid();
			tmp[2] = server_service.findById(sid).getSsn();

			// resource
			String[] source = rf.getResource().split(",");
			String resources = "";
			for (int j = 0; j < source.length; j++) {
				int id = Integer.parseInt(source[j]);
				String check_number = String.valueOf(theme_service.findById(id).getChecknumber());
				resources = resources + theme_service.findById(id).getName() + "(" + check_number + ")" + ",";
			}
			tmp[3] = resources;

			// apps
			String[] application = rf.getApp().split(",");
			String apps = "";
			for (int j = 0; j < application.length; j++) {
				int id = Integer.parseInt(application[j]);
				apps = apps + app_service.findById(id).getAlias() + ",";
			}
			tmp[4] = apps;

			// other infos
			tmp[5] = rf.getStime();
			tmp[6] = rf.getEtime();
			tmp[7] = rf.getImagepath();
			tmp[8] = rf.getZip();
			tmp[9] = rf.getLogFile();
			tmp[10] = rf.getStatus();
			tmp[11] = rf.getStyle();

			res.add(tmp);

		}

		return res;

	}

	// 获取本次连接
	public Session getSession(String host, int port, String user, String password) {
		try {
			session = jsch.getSession(user, host, port);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(password);
			session.connect();
		} catch (JSchException e) {
			e.printStackTrace();
		}
		return session;
	}

	// 结束本次连接
	public void endSSH() {
		// if(channelExec!=null) {
		// channelExec.disconnect();
		// }
		if (session != null) {
			session.disconnect();
		}
	}

	// 执行python脚本命令
	public String execCommand(Session session, String pythonPath, String parameters) {
		String result = null;
		try {
			channelExec = (ChannelExec) session.openChannel("exec");
			in = channelExec.getInputStream();
			channelExec.setCommand("python  " + pythonPath + "  " + parameters);
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

	public String doCommand(Session session, String command) {
		// BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader reader = null;
		Channel channel = null;
		String value = "";
		try {
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);

			channel.connect();
			InputStream in = channel.getInputStream();
			reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
			String buf = null;

			while ((buf = reader.readLine()) != null) {
				// System.out.println(buf);
				value = value + buf;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSchException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			channel.disconnect();
			// session.disconnect();
		}
		return value;
	}

	@Override
	public TestServer getTestServer(int sid) {

		return tdao.findById(sid);
	}

	@Override
	public void execShellCommand(Session session, String python) {
		// shell方式执行
		try {
			ChannelShell channel = (ChannelShell) session.openChannel("shell");
			channel.setPty(true);
			channel.connect();
			OutputStream os = channel.getOutputStream();
			os.write((python + "\r\n").getBytes());
			os.flush();
			os.close();
			Thread.sleep(2000);
			channel.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Integer countRunningCase() {
		// TODO 自动生成的方法存根
		return dao.countRunningCase();
	}

	@Override
	public List<Runinfo> findRuninfoByUserID(Integer userID) {

		return dao.findRuninfoByUserID(userID);
	}

	@Override
	public List<Runinfo> findRuninfoByUserIDAndPage(Integer userID, int offset, int length) {
		
		return dao.findRuninfoByUserIDAndPage(userID, offset, length);
	}

}
