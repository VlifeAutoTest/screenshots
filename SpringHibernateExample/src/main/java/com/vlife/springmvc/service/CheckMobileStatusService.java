package com.vlife.springmvc.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.vlife.checkserver.mobilestatus.TimerManager;

public class CheckMobileStatusService implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("2222222222222222222222222222222222222222222");
		new TimerManager();
	}

}
