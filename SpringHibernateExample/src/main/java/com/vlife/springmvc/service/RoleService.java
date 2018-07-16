package com.vlife.springmvc.service;

import java.util.List;

import com.vlife.springmvc.model.Role;

public interface RoleService {

	Role findById(int id);

	void saveRole(Role role);

	List<Role> findAllRole();

	void updateRole(Role role);

	void removeResources(Role role);

	void deleteRoleByID(int id);

}