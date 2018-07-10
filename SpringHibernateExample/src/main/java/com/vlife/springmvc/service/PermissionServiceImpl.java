package com.vlife.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vlife.springmvc.dao.PermissionDao;
import com.vlife.springmvc.model.Permission;


@Service("permissionService")
@Transactional
public class PermissionServiceImpl implements PermissionService{
	
	@Autowired
	private PermissionDao dao;

	public Permission findById(int id) {
		return dao.findById(id);
	}

	public void savePermission(Permission permission) {
		dao.savePermission(permission);
	}

	public List<Permission> findAllPermission() {
		return dao.findAllPermission();
	}

}
