package com.vlife.springmvc.dao;

import java.util.List;

import com.vlife.springmvc.model.Mobile;
import com.vlife.springmvc.model.Vendor;

public interface MobileDao {

	Mobile findById(int id);

	void saveMobile(Mobile mobile);

	List<Mobile> findAllMobile();

	List<Mobile> findMobileByVendor(Vendor vendor);

	void deleteMobileByUid(String uid);
	
	void deleteMobileByID(int id);

	Mobile findMobileByUid(String uid);

	List<Mobile> findMobileByPage(int offset, int length);

}
