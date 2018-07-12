package com.vlife.springmvc.dao;

import java.util.List;

import com.vlife.springmvc.model.User;

public interface UserDao {
	User findByID(int id);
	void saveUser(User user);
	Boolean findByName(String name);
	List<User>  findUserByName(String name);
	
}
