package com.vlife.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vlife.springmvc.dao.RoleDao;
import com.vlife.springmvc.model.Role;


@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleDao dao;

	public Role findById(int id) {
		return dao.findById(id);
	}

	public void saveRole(Role role) {
		dao.saveRole(role);
	}

	public List<Role> findAllRole() {
		return dao.findAllRole();
	}
	
	public void updateRole(Role role) {
		Role entity = dao.findById(role.getId());
		if (entity != null) {

			entity.setName(role.getName());
			entity.setDescription(role.getDescription());
			entity.setAvailable(role.getAvailable());
			entity.setRelresources(role.getRelresources());
			

		}
	}
	
	public void removeResources(Role role) {
		Role entity = dao.findById(role.getId());
		if (entity != null) {
			entity.setRelresources(null);
		}
	}
	
	public void deleteRoleByID(int id) {
		dao.deleteRoleByID(id);
	}
}
