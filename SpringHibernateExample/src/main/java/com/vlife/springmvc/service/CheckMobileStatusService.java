package com.vlife.springmvc.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.vlife.checkserver.mobilestatus.TimerManager;

public class CheckMobileStatusService implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO 自动生成的方法存根
		new TimerManager();
	}

}
