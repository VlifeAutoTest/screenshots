package com.vlife.springmvc.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.vlife.springmvc.model.Runinfo;

@Repository("runinfoDao")
public class RuninfoDaoImpl extends AbstractDao<Integer, Runinfo> implements RuninfoDao {

	public Runinfo findById(int id) {
		return getByKey(id);
	}

	public void saveRuninfo(Runinfo runinfo) {
		persist(runinfo);
	}


	@SuppressWarnings("unchecked")
	public List<Runinfo> findAllRuninfo() {
		Criteria criteria = createEntityCriteria();
		return (List<Runinfo>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Runinfo> queryData(Map<String, String> conditions){
		
		Criteria criteria = createEntityCriteria();
		if (conditions.get("vid") != null && (!conditions.get("vid").equals("0"))) {
			criteria.add(Restrictions.eq("vid", Integer.parseInt(conditions.get("vid"))));
		}
		
		if (conditions.get("mid") != null && (!conditions.get("mid").equals("0"))) {
			criteria.add(Restrictions.eq("mid", Integer.parseInt(conditions.get("mid"))));
		}
		
		if (conditions.get("resource") != null && conditions.get("resource").length() >0) {
			criteria.add(Restrictions.like("resource", "%"+conditions.get("resource")+"%"));
		}
		
		if (conditions.get("app") != null && conditions.get("app").length() >0) {
			criteria.add(Restrictions.like("app", "%"+conditions.get("app")+"%"));
		}
		
		return (List<Runinfo>) criteria.list();
	}

}
