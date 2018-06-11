package com.vlife.springmvc.dao;

import java.util.List;

import com.vlife.springmvc.model.Runinfo;

public interface RuninfoDao {

	Runinfo findById(int id);

	void saveRuninfo(Runinfo Runinfo);
	
	List<Runinfo> findAllRuninfo();

}
