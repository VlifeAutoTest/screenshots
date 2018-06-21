package com.vlife.springmvc.service;

import java.util.List;

import com.vlife.springmvc.model.Mobile;


public interface MobileStatusService {
	
	List<Object[]> findDeviceStatus();
	List<Object[]> deviceStatusInfo();
	List getOriginStatusInfo();
	List<Mobile> getFreeDevices();

}
