package com.vlife.springmvc.dao;

import java.util.List;

import com.vlife.springmvc.model.TestServer;

public interface TestServerDao {

	TestServer findById(int id);

	TestServer findTestServerBySsn(String ssn);

	void saveTestServer(TestServer testserver);

	List<TestServer> findAllTestServer();

	void deleteTestServerByID(int id);

	void deleteTestServerBySsn(String ssn);

	int[] findAllServerId();

}
