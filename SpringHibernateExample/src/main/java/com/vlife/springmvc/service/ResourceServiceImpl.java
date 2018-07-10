package com.vlife.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vlife.springmvc.dao.ResourceDao;
import com.vlife.springmvc.model.Resources;


@Service("resourceService")
@Transactional
public class ResourceServiceImpl implements ResourceService{
	
	@Autowired
	private ResourceDao dao;

	public Resources findById(int id) {
		return dao.findById(id);
	}

	public void saveResource(Resources res) {
		dao.saveResource(res);
	}


	public List<Resources> findAllResource() {
		return dao.findAllResource();
	}

}
