package com.vlife.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.vlife.springmvc.model.Application;
import com.vlife.springmvc.model.Vendor;

@Repository("applicationDao")
public class ApplicationDaoImpl extends AbstractDao<Integer, Application> implements ApplicationDao {

	public Application findById(int id) {
		return getByKey(id);
	}

	public void saveApplication(Application app) {
		persist(app);
	}

	public void deleteApplicationByID(int id) {
		Query query = getSession().createSQLQuery("delete from application where id = :id");
		query.setInteger("id", id);
		query.executeUpdate();
	};

	@SuppressWarnings("unchecked")
	public List<Application> findAllApplication() {
		Criteria criteria = createEntityCriteria();
		return (List<Application>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Application> findApplicationByVendorID(Vendor id) {
		
//		Query query = getSession().createSQLQuery("select * from application where vendor_id=1");
////		query.setParameter("id", id);
//		List<Application> result = query.list();
		
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("vendor", id));
		return (List<Application>) criteria.list();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Application> findApplicationByPage(int offset, int length) {
		// TODO 自动生成的方法存根
		Criteria criteria = createEntityCriteria();
		criteria.setFirstResult(offset);
		criteria.setMaxResults(length);
		return (List<Application>) criteria.list();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Application> findApplicationByVendorIDaAndPage(Vendor id, int offset, int length) {
		// TODO 自动生成的方法存根
		
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("vendor", id));
		criteria .setFirstResult(offset);
		criteria .setMaxResults(length);
		return (List<Application>) criteria.list();
	}
}
