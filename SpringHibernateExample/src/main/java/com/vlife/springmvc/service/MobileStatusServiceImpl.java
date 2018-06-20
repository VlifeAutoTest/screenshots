package com.vlife.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vlife.springmvc.dao.MobileStatusDao;
import com.vlife.springmvc.dao.TestServerDao;
import com.vlife.springmvc.model.TestServer;


@Service("mobileStatusService")
@Transactional
public class MobileStatusServiceImpl implements MobileStatusService {
	
	@Autowired
	private MobileStatusDao dao;
	
	@Autowired
	private TestServerDao sdao;
	
	
	
	public List<Object[]> findDeviceStatus() {
		
		List mylist =dao.countDeviceByServer();
		Iterator it = mylist.iterator();
		List<Object[]> res = new ArrayList<Object[]>();
		
		while(it.hasNext()) {
			
			Object[] temp = (Object[]) it.next();
			int id = Integer.parseInt(temp[0].toString());
			TestServer ts = sdao.findById(id);
			temp[0]=ts.getSsn();
			res.add(temp);
		}
		return res;
		
	}

}
