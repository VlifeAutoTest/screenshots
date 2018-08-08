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
		Query query = getSession().createSQLQuery("update mobile set delete_flag=1 where uid = :uid");
		query.setString("uid", uid);
		query.executeUpdate();
	}

	public Mobile findMobileByUid(String uid) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("delflag", 0));
		criteria.add(Restrictions.eq("uid", uid));
		return (Mobile) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Mobile> findAllMobile() {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("delflag", 0));
		criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
		return (List<Mobile>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Mobile> findMobileByVendor(Vendor vendor) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("delflag", 0));
		criteria.add(Restrictions.eq("vendor", vendor));
		criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
		return (List<Mobile>) criteria.list();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Mobile> findMobileByPage(int offset, int length) {
		Query query = getSession().createQuery("from Mobile where delete_flag=0");
		query.setFirstResult(offset);
		query.setMaxResults(length);
		return (List<Mobile>) query.list();
	}
}
