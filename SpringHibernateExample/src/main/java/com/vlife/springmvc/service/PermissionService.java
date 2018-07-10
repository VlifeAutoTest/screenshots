package com.vlife.springmvc.service;

import java.util.List;

import com.vlife.springmvc.model.Permission;

public interface PermissionService {
	
	Permission findById(int id);

	void savePermission(Permission permission);

	List<Permission> findAllPermission();

}
