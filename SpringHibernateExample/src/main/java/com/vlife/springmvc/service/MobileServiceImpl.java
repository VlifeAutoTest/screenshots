package com.vlife.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vlife.springmvc.dao.MobileDao;
import com.vlife.springmvc.model.Mobile;
import com.vlife.springmvc.model.Vendor;

@Service("mobile1Service")
@Transactional
public class MobileServiceImpl implements MobileService {

	@Autowired
	private MobileDao dao;
	
	public Mobile findById(int id) {
		return dao.findById(id);
	}

	public void saveMobile(Mobile mobile) {
		dao.saveMobile(mobile);
	}


	public void deleteMobileByUid(String uid) {
		dao.deleteMobileByUid(uid);
	}
	
	
	public Mobile findMobileByUid(String uid) {
		return dao.findMobileByUid(uid);
	}
	

	public List<Mobile> findAllMobile() {
		return dao.findAllMobile();
	}
	
	public List<Mobile> findMobileByVendor(Vendor vendor){
		return dao.findMobileByVendor(vendor);
	}
	
	
	public void updateMobile(Mobile mobile) {
		Mobile entity = dao.findById(mobile.getId());
		if(entity!=null){
			entity.setName(mobile.getName());
			entity.setVendor(mobile.getVendor());
			entity.setUid(mobile.getUid());
			entity.setSize(mobile.getSize());
			entity.setOs(mobile.getOs());

		}
	}
	
	public boolean isMobileUidUnique(Integer id, String uid) {
		Mobile mobile = findMobileByUid(uid);
		return ( mobile == null || ((id != null) && (mobile.getId() == id)));
	}

	@Override
	public List<Mobile> findMobileByPage(int offset, int length) {
		// TODO 自动生成的方法存根
		return dao.findMobileByPage(offset, length);
		
		
	}


} 
