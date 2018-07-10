package com.vlife.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.vlife.springmvc.model.Permission;

@Repository("permissondao")
public class PermissionDaoImpl extends AbstractDao<Integer, Permission> implements PermissionDao {
	
	public Permission findById(int id) {
		return getByKey(id);
	}

	public void savePermission(Permission permission) {
		persist(permission);
	}
	
	@SuppressWarnings("unchecked")
	public List<Permission> findAllPermission() {
		Criteria criteria = createEntityCriteria();
		return (List<Permission>) criteria.list();
	}
}
