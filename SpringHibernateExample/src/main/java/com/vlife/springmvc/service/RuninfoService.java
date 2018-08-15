package com.vlife.springmvc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.vlife.springmvc.model.Runinfo;
import com.vlife.springmvc.model.TestServer;
import com.jcraft.jsch.Session;

public interface RuninfoService {

	void saveRuninfo(Runinfo runinfo);

	List<Runinfo> findAllRuninfo();

	List<Object[]> translaterinfo(List<Runinfo> runinfos);

	Session getSession(String host, int port, String user, String password);

	void endSSH();

	String execCommand(Session session, String pythonPath, String parameters);

	TestServer getTestServer(int sid);

	List<Runinfo> queryData(Map<String, String> conditions, Date[] mytime);

	void execShellCommand(Session session, String python);

	String doCommand(Session session, String command);

	Integer countRunningCase();

	/**
	 * 通过UserID寻找所有的相关的Runinfo
	 * 
	 * @param userID UserID
	 * @return 与UserID相关的所有的list
	 */
	List<Runinfo> findRuninfoByUserID(Integer userID);

	/**
	 * 分页查询结果
	 * 
	 * @param userID 用户ID
	 * @param offset 开始的数
	 * @param length 从开始截取的长度
	 * @return Runinfo的List的集合
	 */
	List<Runinfo> findRuninfoByUserIDAndPage(Integer userID, int offset, int length);
}
