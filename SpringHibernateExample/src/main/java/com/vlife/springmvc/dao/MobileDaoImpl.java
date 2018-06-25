package com.vlife.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.vlife.springmvc.model.Mobile;
import com.vlife.springmvc.model.Vendor;

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
	
	public Mobile findMobileByUid(String uid) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("uid", uid));
		return (Mobile) criteria.uniqueResult();
	}
 

	@SuppressWarnings("unchecked")
	public List<Mobile> findAllMobile() {
		Criteria criteria = createEntityCriteria();
		return (List<Mobile>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Mobile> findMobileByVendor(Vendor vendor) {
		
		Query query = getSession().createSQLQuery("select * from mobile where vendor_id =:id");
		query.setInteger("id", vendor.getId());
		List<Mobile> result = query.list();
/*		String hql = "select new com.vlife.springmc.model.Mobile(name, vendor) from mobile";
		Query query = getSession().createQuery(hql);
		List<Mobile> result = query.list();*/

		return  result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Mobile> findMobileByPage(int offset, int length) {
		// TODO 自动生成的方法存根
		 Query query = getSession().createQuery("from Mobile");
         query.setFirstResult(offset);
         query.setMaxResults(length);
         return (List<Mobile>) query.list();
	}
}
