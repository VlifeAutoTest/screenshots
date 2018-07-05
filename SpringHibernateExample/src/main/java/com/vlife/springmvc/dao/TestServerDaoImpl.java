package com.vlife.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.vlife.springmvc.model.TestServer;

@Repository("testserverDao")
public class TestServerDaoImpl extends AbstractDao<Integer, TestServer> implements TestServerDao {

	public TestServer findById(int id) {
		return getByKey(id);
	}

	public void saveTestServer(TestServer testserver) {
		persist(testserver);
	}

	@SuppressWarnings("unchecked")
	public List<TestServer> findAllTestServer() {
		Criteria criteria = createEntityCriteria();
		return (List<TestServer>) criteria.list();
	}

	public void deleteTestServerByID(int id) {
		Query query = getSession().createSQLQuery("delete from testserver where id = :id");
		query.setInteger("id", id);
		query.executeUpdate();
	}

	public int[] findAllServerId() {

		// Query query = getSession().createSQLQuery("select id from testserver");
		// List<TestServer> tserver = query.getResultList();
		// int[] temp = new int[tserver.size()];
		// int i = 0;
		// for(TestServer t : tserver) {
		// temp[i] = t.getId();
		// i ++;
		// }
		int[] temp = { 1, 2, 3 };

		return temp;

	}

	public void deleteTestServerBySsn(String ssn) {
		Query query = getSession().createSQLQuery("delete from testserver where ssn = :ssn");
		query.setString("ssn", ssn);
		query.executeUpdate();
	}

	public TestServer findTestServerBySsn(String ssn) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("ssn", ssn));
		return (TestServer) criteria.uniqueResult();
	}

}