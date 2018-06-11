package com.vlife.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.vlife.springmvc.model.Application;

@Repository("applicationDao")
public class ApplicationDaoImpl extends AbstractDao<Integer, Application> implements ApplicationDao {

	public Application findById(int id) {
		return getByKey(id);
	}

	public void saveApplication(Application app) {
		persist(app);
	}

	public void deleteApplicationByID(int id) {
		Query query = getSession().createSQLQuery("delete from applications where id = :id");
		query.setInteger("id", id);
		query.executeUpdate();
	};

	@SuppressWarnings("unchecked")
	public List<Application> findAllApplication() {
		Criteria criteria = createEntityCriteria();
		return (List<Application>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Application> findApplicationByVendorID(int id) {
		
		String hql = "select new com.vlife.springmc.model.Application(name) from applications where ";
		Query query = getSession().createQuery(hql);
		List<Application> result = query.list();

		return  result;
	}
}
