package com.vlife.springmvc.service;

import java.util.List;
import com.vlife.springmvc.model.TestServer;

public interface TestServerService {

	TestServer findById(int id);

	void saveTestServer(TestServer tserver);

	void deleteTestServerBySsn(String ssn);

	List<TestServer> findAllTestServer();

	int[] findAllServerID();

	void updateTestServer(TestServer tserver);

	TestServer findTestServerBySsn(String ssn);

	boolean isTestServerSsnUnique(Integer id, String ssn);

	public void deleteTestServerByID(int id);

}
