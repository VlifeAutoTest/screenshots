package com.vlife.springmvc.service;

import java.util.List;

import com.vlife.springmvc.model.Mobile;
import com.vlife.springmvc.model.Vendor;

public interface MobileService {

	Mobile findById(int id);

	void saveMobile(Mobile mobile);

	List<Mobile> findAllMobile();

	List<Mobile> findMobileByVendor(Vendor vendor);

	void deleteMobileByUid(String uid);

	void updateMobile(Mobile mobile);

	Mobile findMobileByUid(String uid);

	boolean isMobileUidUnique(Integer id, String uid);

	List<Mobile> findMobileByPage(int offset, int length);

}