package com.vlife.springmvc.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.vlife.springmvc.model.Theme;

@Repository("themeDao")
public class ThemeDaoImpl extends AbstractDao<Integer, Theme> implements ThemeDao {

	public Theme findById(int id) {
		return getByKey(id);
	}

	public void saveTheme(Theme theme) {
		persist(theme);
	}

	@SuppressWarnings("unchecked")
	public List<Theme> findAllTheme() {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("delflag", 0));
		criteria.addOrder(Order.desc("id"));
		return (List<Theme>) criteria.list();

	}

	public void deleteThemeByID(int id) {
		Query query = getSession().createSQLQuery("update theme set delete_flag=1 where id = :id");
		query.setInteger("id", id);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Theme> findByName(String partName) {
		String strSQL = "from Theme WHERE themename like :name and  delete_flag=0 order by id desc";
		Query query = getSession().createQuery(strSQL);
		query.setString("name", "%" + partName + "%");
		List<Theme> result = query.list();
		return result;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Theme> findThemeByPage(int offset, int length) {
		Query query = getSession().createQuery("from Theme where delete_flag=0 order by id desc");
		query.setFirstResult(offset);
		query.setMaxResults(length);
		return (List<Theme>) query.list();
	}

	@Override
	public List<Theme> findThemeByNameAndPage(String partName, int offset, int length) {
		String strSQL = "from Theme WHERE themename like :name and delete_flag=0 order by id desc";
		Query query = getSession().createQuery(strSQL);
		query.setString("name", "%" + partName + "%");
		query.setFirstResult(offset);
		query.setMaxResults(length);
		@SuppressWarnings("unchecked")
		List<Theme> result = query.list();
		return result;
	}

	public Integer getMaxCheckNumberByName(String name) {
		String strSql = "select max(t.checknumber) from Theme t where t.name=:thname and delete_flag=0";
		Query query = getSession().createQuery(strSql);
		query.setString("thname", name);
		Integer count = (Integer) query.list().get(0);
		return count;

	}

}