package com.vlife.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vlife.springmvc.dao.UserDao;
import com.vlife.springmvc.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	
	
	@Autowired
	private UserDao dao;

	@Override
	public void saveUser(User user) {
		dao.saveUser(user);
	}

	@Override
	public Boolean findByName(String name) {
		return dao.findByName(name);
	}

	@Override
	public List<User> findUserByName(String name) {
		return dao.findUserByName(name);
	}

	@Override
	public void updateUserLastLogin(User user) {
		User use = dao.findByID(user.getId());
		if (use != null) {

			use.setLasted_update(user.getLasted_update());

		}

	}
	
	public List<User> findAllUser() {
		return dao.findAllUser();
	}
	
	public User findById(int id) {
		
		return dao.findByID(id);
	}
	
	public void removeRelRoles(User user) {
		
		User entity = dao.findByID(user.getId());
		if (entity != null) {
			entity.setRole(null);
		}
	}
	
	public void updateUser(User user) {
		
		User entity = dao.findByID(user.getId());
		if (entity != null) {
			entity.setName(user.getName());
			entity.setPasswd(user.getPasswd());
			entity.setEmail(user.getEmail());
			entity.setIs_active(user.getIs_active());
			entity.setRole(user.getRole());

		}
	}
	
	public void deleteUserByID(int id) {
		dao.deleteUserByID(id);
	}

	@Override
	public Boolean findByEmail(String email) {
		
		return dao.findByEmail(email);
	}

	@Override
	public List<User> findUserByEmail(String email) {
		// TODO 自动生成的方法存根
		return dao.findUserByEmail(email);
	}

}
