package com.vlife.springmvc.dao;

import java.util.List;

import com.vlife.springmvc.model.User;

/**
 * 
 * @author 
 *
 */
public interface UserDao {
	/**
	 * 使用ID寻找User
	 * 
	 * @param id 用户ID
	 * @return 返回User对象
	 */
	User findByID(int id);

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
	 * 通过账号寻找User对象
	 * 
	 * @param name 账号
	 * @return User的List
	 */

	List<User> findUserByName(String name);

	/**
	 * 查找所有的User
	 * 
	 * @return 返回User的List
	 */
	List<User> findAllUser();

	/**
	 * 通过ID删除User对象
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
	
	/**
	 * 通过邮箱寻找User对象
	 * 
	 * @param email 账号
	 * @return User的List
	 */

	List<User> findUserByEmail(String email);

}
