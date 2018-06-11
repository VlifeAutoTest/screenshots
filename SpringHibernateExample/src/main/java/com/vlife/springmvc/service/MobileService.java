package com.vlife.springmvc.service;


import java.util.List;

import com.vlife.springmvc.model.Mobile;

public interface MobileService {

	Mobile findById(int id);

	void saveMobile(Mobile mobile);
	
	List<Mobile> findAllMobile();
	
	List<Mobile> findMobileByVendor(String vendor);

	void deleteMobileByUid(String uid);
	
	void updateMobile(Mobile mobile);

}