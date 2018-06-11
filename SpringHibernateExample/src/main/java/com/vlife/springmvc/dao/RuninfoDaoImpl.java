package com.vlife.springmvc.dao;

import java.util.List;

import org.hibernate.Criteria;
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
	

}
