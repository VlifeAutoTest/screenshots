package com.vlife.springmvc.dao;

import java.util.List;

import com.vlife.springmvc.model.Application;

public interface ApplicationDao {

	Application findById(int id);

	void saveApplication(Application application);
	
	List<Application> findApplicationByVendorID(int id);	
	
	void deleteApplicationByID(int id);
	
	List<Application> findAllApplication();

}