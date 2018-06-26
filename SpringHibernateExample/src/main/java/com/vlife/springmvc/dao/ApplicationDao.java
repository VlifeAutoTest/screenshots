package com.vlife.springmvc.dao;

import java.util.List;

import com.vlife.springmvc.model.Application;
import com.vlife.springmvc.model.Theme;
import com.vlife.springmvc.model.Vendor;

public interface ApplicationDao {

	Application findById(int id);

	void saveApplication(Application application);
	
	List<Application> findApplicationByVendorID(Vendor id);	
	
	void deleteApplicationByID(int id);
	
	List<Application> findAllApplication();
	List<Application> findApplicationByPage(int offset, int length);

}