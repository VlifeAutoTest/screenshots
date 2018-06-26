package com.vlife.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vlife.springmvc.dao.ApplicationDao;
import com.vlife.springmvc.model.Application;
import com.vlife.springmvc.model.Vendor;

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
	
	public List<Application> findApplicationByVendorID(Vendor id){
		return dao.findApplicationByVendorID(id);
	}

	public List<Application> findAllApplication() {
		return dao.findAllApplication();
	}

	public void updateApplication(Application app) {
		Application entity = dao.findById(app.getId());
		if(entity!=null){
			entity.setName(app.getName());
			entity.setVendor(app.getVendor());
		}
	}

	@Override
	public List<Application> findApplicationByPage(int offset, int length) {
		// TODO 自动生成的方法存根
		return dao.findApplicationByPage(offset, length);
	}

} 
