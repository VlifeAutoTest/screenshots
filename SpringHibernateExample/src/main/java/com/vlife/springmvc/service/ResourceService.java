package com.vlife.springmvc.service;

import java.util.List;

import com.vlife.springmvc.model.Resources;

public interface ResourceService {
	
	Resources findById(int id);

	void saveResource(Resources resource);

	List<Resources> findAllResource();

}
