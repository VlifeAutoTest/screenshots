package com.vlife.springmvc.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vlife.springmvc.dao.MobileDao;
import com.vlife.springmvc.dao.RuninfoDao;
import com.vlife.springmvc.model.Runinfo;
import com.vlife.springmvc.service.VendorService;

@Service("runinfoService")
@Transactional
public class RuninfoServiceImpl implements RuninfoService{
	
	@Autowired
	private RuninfoDao dao;
	
	@Autowired
	private VendorService vendor_service;
	
	@Autowired
	private MobileService mobile_service;
	
	@Autowired
	private ApplicationService app_service;
	
	@Autowired
	private ThemeService theme_service;
	
	@Autowired
	private TestServerService server_service;
	
	public void saveRuninfo(Runinfo runinfo) {
		
		dao.saveRuninfo(runinfo);
		
	}
	
	
	public List<Runinfo> findAllRuninfo(){
		return dao.findAllRuninfo();
	}
	
	
	public List<Object[]> translaterinfo(List<Runinfo> runinfos){
		
		List<Object[]> res = new ArrayList<Object[]>();
		
		for(int i=0; i< runinfos.size(); i++) {
			
			Object[] tmp = new Object[11];
			
			Runinfo rf = runinfos.get(i);
			
			// vendor name
			int vid = rf.getVid();
			tmp[0] = vendor_service.findById(vid).getName();
			
			// mobile name
			int mid = rf.getMid();
			tmp[1] = mobile_service.findById(mid).getName();
			
			// server name
			int sid = rf.getSid();
			tmp[2] = server_service.findById(sid).getSsn();
			
			// resource
			String[] source = rf.getResource().split(",");
			String resources="";
			for(int j=0; j< source.length; j++) {
				int id = Integer.parseInt(source[j]);
				resources = resources + theme_service.findById(id).getName() + ",";
			}
			tmp[3] = resources;
			
			// apps
			String[] application = rf.getApp().split(",");
			String apps="";
			for(int j=0; j< application.length; j++) {
				int id = Integer.parseInt(application[j]);
				apps = apps + app_service.findById(id).getName() + ",";
			}
			tmp[4] = apps;
			
			//other infos
			tmp[5] = rf.getStime();
			tmp[6] = rf.getEtime();
			tmp[7] = rf.getImagepath();
			tmp[8] = rf.getZip();
			tmp[9] = rf.getLogFile();
			tmp[10] = rf.getStatus();
			
			res.add(tmp);
			
		}
			

		return res;	
		
	}

}
