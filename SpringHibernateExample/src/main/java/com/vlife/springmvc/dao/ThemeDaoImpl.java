package com.vlife.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
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
		return (List<Theme>) criteria.list();
	}
	
	public void deleteThemeByID(int id) {
		Query query = getSession().createSQLQuery("delete from theme where id = :id");
		query.setInteger("id", id);
		query.executeUpdate();
	}

}