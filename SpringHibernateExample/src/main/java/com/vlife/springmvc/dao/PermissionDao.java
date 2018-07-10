package com.vlife.springmvc.dao;

import java.util.List;

import com.vlife.springmvc.model.Permission;

public interface PermissionDao {
	
	Permission findById(int id);

	void savePermission(Permission permission);

	List<Permission> findAllPermission();


}
