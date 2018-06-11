package com.vlife.springmvc.dao;

import java.util.List;

import com.vlife.springmvc.model.Mobile;

public interface MobileDao {

	Mobile findById(int id);

	void saveMobile(Mobile mobile);
	
	List<Mobile> findAllMobile();
	
	List<Mobile> findMobileByVendor(String vendor);

	void deleteMobileByUid(String uid);

}
