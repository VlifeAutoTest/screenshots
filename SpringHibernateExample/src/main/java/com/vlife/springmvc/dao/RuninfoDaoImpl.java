package com.vlife.springmvc.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.vlife.springmvc.model.Runinfo;
/**
 * 
 * @author Administrator
 *
 */
@Repository("runinfoDao")
public class RuninfoDaoImpl extends AbstractDao<Integer, Runinfo> implements RuninfoDao {
	@Override
	public Runinfo findById(int id) {
		return getByKey(id);
	}
	@Override
	public void saveRuninfo(Runinfo runinfo) {
		persist(runinfo);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<Runinfo> findAllRuninfo() {
		Criteria criteria = createEntityCriteria();
		return (List<Runinfo>) criteria.list();
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<Runinfo> queryData(Map<String, String> conditions, Date[] mytime) {

		Criteria criteria = createEntityCriteria();
		if (conditions.get("vid") != null && (!conditions.get("vid").equals("0"))) {
			criteria.add(Restrictions.eq("vid", Integer.parseInt(conditions.get("vid"))));
		}

		if (conditions.get("mid") != null && (conditions.get("mid").length() > 0)) {
			String[] mid_list = conditions.get("mid").split(",");
			Criterion[] cri_list = new Criterion[mid_list.length];
			for (int i = 0; i < mid_list.length; i++) {

				cri_list[i] = Restrictions.eq("mid", mid_list[i]);
			}
			criteria.add(Restrictions.or(cri_list));
		}

		if (conditions.get("resource") != null && conditions.get("resource").length() > 0) {
			String[] res_list = conditions.get("resource").split(",");
			Criterion[] cri_list = new Criterion[res_list.length];
			for (int i = 0; i < res_list.length; i++) {

				// cri_list[i] = Restrictions.like("resource", "%"+res_list[i]+"%");
				cri_list[i] = Restrictions.sqlRestriction("resource REGEXP'(^|,)" + res_list[i] + "($|,)'");
			}
			criteria.add(Restrictions.or(cri_list));
		}

		if (conditions.get("app") != null && conditions.get("app").length() > 0) {
			String[] app_list = conditions.get("app").split(",");
			Criterion[] cri_list = new Criterion[app_list.length];
			for (int i = 0; i < app_list.length; i++) {

				cri_list[i] = Restrictions.sqlRestriction("app REGEXP'(^|,)" + app_list[i] + "($|,)'");
			}
			criteria.add(Restrictions.or(cri_list));
		}

		if (mytime[0] != null && mytime[1] != null) {
			criteria.add(Restrictions.between("stime", mytime[0], mytime[1]));
		}

		criteria.addOrder(Order.desc("stime"));

		return (List<Runinfo>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer countRunningCase() {
		String sql = "select *  from  runinfo WHERE YEAR(stime) = YEAR(NOW()) and  MONTH(stime)= MONTH(NOW()) and  DAY(stime)= DAY(NOW())  and status= 'Running'";
		List<Runinfo> list = getSession().createSQLQuery(sql).list();
		if (list == null) {
			return 0;
		} else {
			return list.size();
		}
	}

	@Override
	public List<Runinfo> findRuninfoByUserID(Integer userID) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("user_id", userID));
		return (List<Runinfo>) criteria.list();
	}
	@Override
	public List<Runinfo> findRuninfoByUserIDAndPage(Integer userID, int offset, int length) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("user_id", userID));
		criteria.addOrder(Order.desc("stime"));
		criteria.setFirstResult(offset);
		criteria.setMaxResults(length);
		return (List<Runinfo>) criteria.list();
	}

}
