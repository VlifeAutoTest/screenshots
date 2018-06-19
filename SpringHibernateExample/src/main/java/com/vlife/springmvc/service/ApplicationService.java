package com.vlife.springmvc.service;

import java.util.List;

import com.vlife.springmvc.model.Application;
import com.vlife.springmvc.model.Vendor;

public interface ApplicationService {
	
	Application findById(int id);

	void saveApplication(Application application);
	
	void updateApplication(Application application);
	
	List<Application> findApplicationByVendorID(Vendor id);	
	
	void deleteApplicationByID(int id);
	
	List<Application> findAllApplication();


}


