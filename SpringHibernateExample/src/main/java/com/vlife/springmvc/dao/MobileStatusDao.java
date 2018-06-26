package com.vlife.springmvc.dao;

import java.util.List;


public interface MobileStatusDao {
	
	@SuppressWarnings("rawtypes")
	List countDeviceByServer();
	@SuppressWarnings("rawtypes")
	List deviceStatusByServer();
	@SuppressWarnings("rawtypes")
	List getConnectDevices();

}
