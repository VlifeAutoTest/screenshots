package com.vlife.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vlife.springmvc.dao.MobileDao;
import com.vlife.springmvc.dao.RuninfoDao;
import com.vlife.springmvc.model.Runinfo;


@Service("runinfoService")
@Transactional
public class RuninfoServiceImpl implements RuninfoService{
	
	@Autowired
	private RuninfoDao dao;
	
	public void saveRuninfo(Runinfo runinfo) {
		
		dao.saveRuninfo(runinfo);
		
	}

}
