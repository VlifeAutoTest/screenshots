package com.vlife.springmvc.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

import com.vlife.springmvc.model.Mobile;
import com.vlife.springmvc.model.MobileStatus;

@Repository("mobilestatusDao")
public class MobileStatusDaoImpl extends AbstractDao<Integer, MobileStatus> implements MobileStatusDao{
	
	public List countDeviceByServer() {
		
		String hql = "SELECT mobile.sid, COUNT(mobile.mid), mobile.status FROM MobileStatus mobile GROUP BY mobile.sid, mobile.status";
		List list = getSession().createQuery(hql).list();
		return list;
			
		
	}
	
	
	public List deviceStatusByServer() {
		
		String hql = "SELECT mobile.sid, mobile.mid, mobile.status FROM MobileStatus mobile ORDER BY mobile.status  DESC , mobile.sid ";
		List list = getSession().createQuery(hql).list();
		return list;
	}
	
	public List getConnectDevices() {
		
		String hql = "SELECT mobile.mid, mobile.status FROM MobileStatus mobile WHERE mobile.status LIKE 'free%'";
		List list = getSession().createQuery(hql).list();
		
		return list;

		}
		

}
