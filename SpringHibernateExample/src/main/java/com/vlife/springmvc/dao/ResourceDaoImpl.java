package com.vlife.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.vlife.springmvc.model.Resources;

@Repository("resourceDao")
public class ResourceDaoImpl extends AbstractDao<Integer, Resources> implements ResourceDao {
	
	public Resources findById(int id) {
		return getByKey(id);
	}

	public void saveResource(Resources resource) {
		persist(resource);
	}
	
	@SuppressWarnings("unchecked")
	public List<Resources> findAllResource() {
		Criteria criteria = createEntityCriteria();
		return (List<Resources>) criteria.list();
	}
}
