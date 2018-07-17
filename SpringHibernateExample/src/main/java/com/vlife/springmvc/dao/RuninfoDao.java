package com.vlife.springmvc.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.vlife.springmvc.model.Runinfo;

public interface RuninfoDao {

	Runinfo findById(int id);

	void saveRuninfo(Runinfo Runinfo);

	List<Runinfo> findAllRuninfo();

	List<Runinfo> queryData(Map<String, String> conditions, Date[] mytime);

	Integer countRunningCase();

}
