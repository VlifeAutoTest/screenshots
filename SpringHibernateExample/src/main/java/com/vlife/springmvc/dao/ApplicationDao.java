package com.vlife.springmvc.dao;

import java.util.List;

import com.vlife.springmvc.model.Application;
import com.vlife.springmvc.model.Vendor;

public interface ApplicationDao {

	Application findById(int id);

	void saveApplication(Application application);
	
	List<Application> findApplicationByVendorID(Vendor id);	
	
	List<Application> findApplicationByVendorIDAndStyle(Vendor id, String style);	
	
	void deleteApplicationByID(int id);
	
	List<Application> findAllApplication();
	List<Application> findApplicationByPage(int offset, int length);
	List<Application> findApplicationByVendorIDaAndPage(Vendor id,int offset, int length);	
}