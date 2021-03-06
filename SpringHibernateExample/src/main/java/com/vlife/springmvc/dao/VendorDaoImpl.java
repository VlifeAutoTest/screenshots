package com.vlife.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.vlife.springmvc.model.Vendor;

@Repository("vendorDao")
public class VendorDaoImpl extends AbstractDao<Integer, Vendor> implements VendorDao {

	public Vendor findById(int id) {
		return getByKey(id);
	}

	public void saveVendor(Vendor vendor) {
		persist(vendor);
	}

	@SuppressWarnings("unchecked")
	public List<Vendor> findAllVendor() {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("delflag", 0));
		criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
		
		return (List<Vendor>) criteria.list();
	}

	public void deleteVendorByID(int id) {
		Query query = getSession().createSQLQuery("update vendor set delete_flag=1 where id = :id");
		query.setInteger("id", id);
		query.executeUpdate();
	}

}
