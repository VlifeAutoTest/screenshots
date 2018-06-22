package com.vlife.springmvc.service;

import java.util.List;

import com.vlife.springmvc.model.Runinfo;

public interface RuninfoService {
	
	void saveRuninfo(Runinfo runinfo);
	
	List<Runinfo> findAllRuninfo();
	
	List<Object[]> translaterinfo(List<Runinfo> runinfos);

}
