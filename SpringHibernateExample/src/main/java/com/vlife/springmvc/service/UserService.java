package com.vlife.springmvc.service;

import java.util.List;

import com.vlife.springmvc.model.User;

public interface UserService {
	void saveUser(User user);
	Boolean findByName(String name);
	List<User>  findUserByName(String name);
	void updateUserLastLogin(User user) ;
	
}
