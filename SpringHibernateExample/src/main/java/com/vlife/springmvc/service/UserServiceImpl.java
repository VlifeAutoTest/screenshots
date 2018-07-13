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

	
	
}
