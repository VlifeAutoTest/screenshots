package com.vlife.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.vlife.springmvc.model.User;

@Repository("UserDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

	@Override
	public void saveUser(User user) {
		persist(user);
	}

	@Override
	public Boolean findByName(String name) {
		
		String strSQL = "from User WHERE  name = :name ";
		Query query = getSession().createQuery(strSQL);
		query.setString("name", name);
		Object obj = query.uniqueResult();
		if (obj == null) {

			return false;
		} else {

			return true;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUserByName(String name) {
		String strSQL = "from User WHERE name = :name";
		Query query = getSession().createQuery(strSQL);
		query.setString("name", name);
		List<User> result = query.list();
		return result;
	}

	@Override
	public User findByID(int id) {
		
		return getByKey(id);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findAllUser() {
		
		Criteria criteria = createEntityCriteria();
		criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
		return (List<User>) criteria.list();

	}
	
	public void deleteUserByID(int id) {
		
		Query query = getSession().createSQLQuery("delete from auth_user where id = :id");
		query.setInteger("id", id);
		query.executeUpdate();
		
	}
}
