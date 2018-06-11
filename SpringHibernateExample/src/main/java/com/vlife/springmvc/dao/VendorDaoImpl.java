package com.vlife.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
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
		return (List<Vendor>) criteria.list();
	}
	
	public void deleteVendorByID(int id) {
		Query query = getSession().createSQLQuery("delete from vendor where id = :id");
		query.setInteger("id", id);
		query.executeUpdate();
	}

}

