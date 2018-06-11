package com.vlife.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vlife.springmvc.dao.ApplicationDao;
import com.vlife.springmvc.model.Application;

@Service("applicationService")
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationDao dao;
	
	public Application findById(int id) {
		return dao.findById(id);
	}

	public void saveApplication(Application application) {
		dao.saveApplication(application);
	}


	public void deleteApplicationByID(int id) {
		dao.deleteApplicationByID(id);
	}
	
	public List<Application> findApplicationByVendorID(int id){
		return dao.findApplicationByVendorID(id);
	}

	public List<Application> findAllApplication() {
		return dao.findAllApplication();
	}


} 
