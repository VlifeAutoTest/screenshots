package com.vlife.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.vlife.springmvc.model.User;

@Repository("UserDao")
/**
 * 
 * @author
 *
 */
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

	@Override
	public void saveUser(User user) {
		persist(user);
	}

	@Override
	public Boolean findByName(String name) {

		String strSQL = "from User WHERE  name = :name and delete_flag=0";
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
		String strSQL = "from User WHERE name = :name and delete_flag=0";
		Query query = getSession().createQuery(strSQL);
		query.setString("name", name);
		List<User> result = query.list();
		return result;
	}

	@Override
	public User findByID(int id) {

		return getByKey(id);

	}

	@Override
	public List<User> findAllUser() {

		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("delflag", 0));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<User>) criteria.list();

	}

	@Override
	public void deleteUserByID(int id) {

		Query query = getSession().createSQLQuery("update auth_user set delete_flag=1 where id = :id");
		query.setInteger("id", id);
		query.executeUpdate();

	}

	@Override
	public Boolean findByEmail(String email) {
		String strSQL = "from User WHERE  email = :email and delete_flag=0";
		Query query = getSession().createQuery(strSQL);
		query.setString("email", email);
		Object obj = query.uniqueResult();
		if (obj == null) {

			return false;
		} else {
			return true;
		}
	}

	@Override
	public List<User> findUserByEmail(String email) {
		String strSQL = "from User WHERE email = :email and delete_flag=0";
		Query query = getSession().createQuery(strSQL);
		query.setString("email", email);
		List<User> result = query.list();
		return result;
	}
}
