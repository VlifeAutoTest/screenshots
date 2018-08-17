package com.vlife.springmvc.service;

import java.util.List;

import com.vlife.springmvc.model.User;

/**
 * 
 * @author Administrator
 *
 */
public interface UserService {
	/**
	 * 保存
	 * 
	 * @param user User的对象
	 */
	void saveUser(User user);

	/**
	 * 查看是否包含指定的账号
	 * 
	 * @param name 账号
	 * @return true:包含 false:不包含
	 */
	Boolean findByName(String name);

	/**
	 * 使用ID寻找User
	 * 
	 * @param id 用户ID
	 * @return 返回User对象
	 */
	User findById(int id);

	/**
	 * 通过账号寻找User对象
	 * 
	 * @param name 账号
	 * @return User的List
	 */
	List<User> findUserByName(String name);

	/**
	 * 是否包含指定的邮件
	 * 
	 * @param email 邮件地址
	 * @return true:包含 flase:不包含
	 */
	List<User> findUserByEmail(String email);

	/**
	 * 更新用户最后登录日期
	 * 
	 * @param user User对象
	 */
	void updateUserLastLogin(User user);

	/**
	 * 查找所有的User
	 * 
	 * @return 返回User的List
	 */
	List<User> findAllUser();

	/**
	 * 更新User信息
	 * 
	 * @param user User对象
	 */
	void updateUser(User user);

	/**
	 * 删除用户所在的组
	 * 
	 * @param user User对象
	 */
	void removeRelRoles(User user);

	/**
	 * 通过ID删除User
	 * 
	 * @param id User ID
	 */
	void deleteUserByID(int id);

	/**
	 * 是否包含指定的邮件
	 * 
	 * @param email 邮件地址
	 * @return true:包含 flase:不包含
	 */
	Boolean findByEmail(String email);

}
