package com.vlife.springmvc.dao;

import java.util.List;

import com.vlife.springmvc.model.Resources;

public interface ResourceDao {

	Resources findById(int id);

	void saveResource(Resources resource);

	List<Resources> findAllResource();


}
