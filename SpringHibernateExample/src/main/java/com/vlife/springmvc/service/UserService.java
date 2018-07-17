package com.vlife.springmvc.service;

import java.util.List;

import com.vlife.springmvc.model.User;

public interface UserService {
	
	void saveUser(User user);

	Boolean findByName(String name);
	
	User findById(int id);

	List<User> findUserByName(String name);

	void updateUserLastLogin(User user);

	List<User> findAllUser();
	
	void updateUser(User user);

	void removeRelRoles(User user);
	
	void deleteUserByID(int id);
}
