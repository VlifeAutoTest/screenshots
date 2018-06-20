package com.vlife.springmvc.dao;

import java.util.Iterator;
import java.util.List;

import com.vlife.springmvc.model.Mobile;

public interface MobileStatusDao {
	
	List countDeviceByServer();
	List deviceStatusByServer();
	List getConnectDevices();

}
