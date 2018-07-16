package com.vlife.springmvc.dao;

import java.util.List;

import com.vlife.springmvc.model.Role;

public interface RoleDao {
	
	Role findById(int id);

	void saveRole(Role role);

	List<Role> findAllRole();

	public void deleteRoleByID(int id);
}
