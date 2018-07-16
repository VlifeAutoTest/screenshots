package com.vlife.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.vlife.springmvc.model.Role;

@Repository("roledao")
public class RoleDaoImpl extends AbstractDao<Integer, Role> implements RoleDao {

	public Role findById(int id) {
		return getByKey(id);
	}

	public void saveRole(Role role) {
		persist(role);
	}

	@SuppressWarnings("unchecked")
	public List<Role> findAllRole() {
		Criteria criteria = createEntityCriteria();
		criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
		return (List<Role>) criteria.list();
	}

	public void deleteRoleByID(int id) {
		Query query = getSession().createSQLQuery("delete from auth_roles where id = :id");
		query.setInteger("id", id);
		query.executeUpdate();
	};
}
