package com.vlife.springmvc.service;

import java.util.List;

import com.vlife.springmvc.model.Runinfo;
import com.vlife.springmvc.model.TestServer;
import com.jcraft.jsch.Session;
public interface RuninfoService {
	
	void saveRuninfo(Runinfo runinfo);
	
	List<Runinfo> findAllRuninfo();
	
	List<Object[]> translaterinfo(List<Runinfo> runinfos);
	Session getSession(String host, int port, String user, String password);
	void endSSH();
	String execCommand(Session session, String pythonPath,String parameters);
	TestServer getTestServer(int sid);
}
