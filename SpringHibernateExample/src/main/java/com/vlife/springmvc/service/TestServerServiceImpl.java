package com.vlife.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vlife.springmvc.dao.TestServerDao;
import com.vlife.springmvc.model.TestServer;

@Service("testServerService")
@Transactional
public class TestServerServiceImpl implements TestServerService {

	@Autowired
	private TestServerDao dao;

	public TestServer findById(int id) {
		return dao.findById(id);
	}

	public void saveTestServer(TestServer tserver) {
		dao.saveTestServer(tserver);
	}

	public void deleteTestServerByID(int id) {
		dao.deleteTestServerByID(id);
	}

	public List<TestServer> findAllTestServer() {
		return dao.findAllTestServer();
	}

	public void updateTestServer(TestServer tserver) {
		TestServer entity = dao.findById(tserver.getId());
		if (entity != null) {
			entity.setAddress(tserver.getAddress());
			entity.setUname(tserver.getUname());
			entity.setPasswd(tserver.getPasswd());
			entity.setSsn(tserver.getSsn());

		}
	}

	public int[] findAllServerID() {
		return dao.findAllServerId();

	}

	public void deleteTestServerBySsn(String ssn) {
		dao.deleteTestServerBySsn(ssn);
	}

	public TestServer findTestServerBySsn(String ssn) {
		return dao.findTestServerBySsn(ssn);
	}

	public boolean isTestServerSsnUnique(Integer id, String ssn) {
		TestServer server = findTestServerBySsn(ssn);
		return (server == null || ((id != null) && (server.getId() == id)));
	}

}
