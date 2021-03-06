package com.vlife.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
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
		Query query = getSession().createSQLQuery("update application set delete_flag=1 where id = :id");
		query.setInteger("id", id);
		query.executeUpdate();
	};

	@SuppressWarnings("unchecked")
	public List<Application> findAllApplication() {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("delflag", 0));
		return (List<Application>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Application> findApplicationByVendorID(Vendor id) {

		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("vendor", id));
		criteria.add(Restrictions.eq("delflag", 0));
		criteria.addOrder(Order.desc("style"));
		return (List<Application>) criteria.list();

	}

	@SuppressWarnings("unchecked")
	public List<Application> findApplicationByVendorIDAndStyle(Vendor id, String style) {

		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("vendor", id));
		criteria.add(Restrictions.eq("style", style));
		criteria.add(Restrictions.eq("delflag", 0));
		criteria.addOrder(Order.desc("style"));
		return (List<Application>) criteria.list();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Application> findApplicationByPage(int offset, int length) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("delflag", 0));
		criteria.setFirstResult(offset);
		criteria.setMaxResults(length);
		return (List<Application>) criteria.list();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Application> findApplicationByVendorIDaAndPage(Vendor id, int offset, int length) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("vendor", id));
		criteria.add(Restrictions.eq("delflag", 0));
		criteria.addOrder(Order.desc("style"));
		criteria.setFirstResult(offset);
		criteria.setMaxResults(length);
		return (List<Application>) criteria.list();
	}
}
