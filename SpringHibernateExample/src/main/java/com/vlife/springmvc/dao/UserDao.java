package com.vlife.springmvc.dao;

import java.util.List;

import com.vlife.springmvc.model.User;

public interface UserDao {
	
	void saveUser(User user);
	Boolean findByName(String name);
	List<User>  findPasswdByName(String name);
	
}
