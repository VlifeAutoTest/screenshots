package com.vlife.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.vlife.springmvc.dao.MobileDao;
import com.vlife.springmvc.dao.MobileStatusDao;
import com.vlife.springmvc.dao.TestServerDao;
import com.vlife.springmvc.model.Mobile;
import com.vlife.springmvc.model.TestServer;


@Service("mobileStatusService")
@Transactional
public class MobileStatusServiceImpl implements MobileStatusService {
	
	@Autowired
	private MobileStatusDao dao;
	
	@Autowired
	private TestServerDao sdao;
	
	@Autowired
	private MobileDao mdao;
	
	
	
	public List<Mobile> getFreeDevices(){
		
		List mylist = dao.getConnectDevices();
		List<Mobile> res = new ArrayList<Mobile>();
		
		Iterator it = mylist.iterator();
		while(it.hasNext()){
			Object[] results = (Object[]) it.next();
			int mid = Integer.parseInt(results[0].toString());
			Mobile mobile = mdao.findById(mid);
			res.add(mobile);
		}
		
		return res;
			
	}
	
	public List<Object[]> deviceStatusInfo(){
		
		List mylist = dao.deviceStatusByServer();
		Iterator it = mylist.iterator();
		List<Object[]> res = new ArrayList<Object[]>();
		
		while(it.hasNext()) {
			
			Object[] temp = (Object[]) it.next();
			Object[] tmp = new Object[7];
			
			int id = Integer.parseInt(temp[0].toString());
			TestServer ts = sdao.findById(id);
			//server name
			tmp[0] = ts.getSsn().toString();
			// connect status
			tmp[1] = temp[2].toString();
			
			int mid = Integer.parseInt(temp[1].toString());
			Mobile mobile = mdao.findById(mid);
			// vendor name
			tmp[2] = mobile.getVendor().getName();
			
			// mobile name
			tmp[3] = mobile.getName();
			
			// mobile uid
			tmp[4] = mobile.getUid();
			
			// mobile os
			tmp[5] = mobile.getOs();
			
			// mobile size
			tmp[6] = mobile.getSize();
			
			res.add(tmp);
			
		}
		
		return res;
	}
	
	public List<Object[]> findDeviceStatus() {
		
		List mylist =dao.countDeviceByServer();
		Iterator it = mylist.iterator();
		List<Object[]> res = new ArrayList<Object[]>();
		Map<String, String> map = new HashMap<String, String>();
		String status;
		int number;
		String key;
		
		while(it.hasNext()) {
			
			Object[] temp = (Object[]) it.next();
			// server_id
			int id = Integer.parseInt(temp[0].toString());
			TestServer ts = sdao.findById(id);
			key = ts.getSsn().toString();
			// connect status
			status = temp[2].toString();
			// count number
			number = Integer.parseInt(temp[1].toString());
			if (map.containsKey(key)) {
				if (status.equalsIgnoreCase("disconnect") == false) {

					number = number + Integer.parseInt(map.get(key).toString());
					map.put(key, String.valueOf(number));
				}
				
			}
			else {
				if (status.equalsIgnoreCase("disconnect") == false) {
					map.put(key,  String.valueOf(number));
				}
				else {
					map.put(key, "0");
				}
			}
		}
		
		for(String k: map.keySet()) {
			Object[] tmp = new Object[2];
			tmp[0] = k;
			tmp[1] = map.get(k);
			res.add(tmp);	
		}
		
		return res;
		
	}

}
