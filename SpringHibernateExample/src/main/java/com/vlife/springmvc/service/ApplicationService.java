package com.vlife.springmvc.service;

import java.util.List;

import com.vlife.springmvc.model.Application;

public interface ApplicationService {
	
	Application findById(int id);

	void saveApplication(Application application);
	
	List<Application> findApplicationByVendorID(int id);	
	
	void deleteApplicationByID(int id);
	
	List<Application> findAllApplication();


}


