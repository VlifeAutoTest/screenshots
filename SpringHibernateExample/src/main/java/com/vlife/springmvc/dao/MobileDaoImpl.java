package com.vlife.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.vlife.springmvc.model.Mobile;

@Repository("mobileDao")
public class MobileDaoImpl extends AbstractDao<Integer, Mobile> implements MobileDao {

	public Mobile findById(int id) {
		return getByKey(id);
	}

	public void saveMobile(Mobile mobile) {
		persist(mobile);
	}

	public void deleteMobileByUid(String uid) {
		Query query = getSession().createSQLQuery("delete from mobile where uid = :uid");
		query.setString("uid", uid);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Mobile> findAllMobile() {
		Criteria criteria = createEntityCriteria();
		return (List<Mobile>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Mobile> findMobileByVendor(String vendor) {
		
//		Query query = getSession().createSQLQuery("select name, vendor from  mobile where vendor = :vendor");
//		query.setString("vendor", vendor);
//		List<Object[]> result = query.list();
		String hql = "select new com.vlife.springmc.model.Mobile(name, vendor) from mobile";
		Query query = getSession().createQuery(hql);
		List<Mobile> result = query.list();

		return  result;
	}
}
