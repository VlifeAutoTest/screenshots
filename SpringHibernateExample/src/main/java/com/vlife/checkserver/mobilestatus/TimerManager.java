package com.vlife.checkserver.mobilestatus;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;


public class TimerManager {

	private static final long PERIOD_DAY = Integer.parseInt(Methods.getProperty("check.mobile.status.time.interval")) * 60 * 1000;

	public TimerManager() {
		Calendar calendar = Calendar.getInstance();
		// 定制每日12:30:30执行
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);// 每天
		calendar.set(year, month, day, 9, 0, 0);
		Date date = calendar.getTime(); // 第一次执行定时任务的时间
	
		// 如果当前时间已经过去所定时的时间点，则在第二天时间点开始执行
		while(true) {
			if (date.before(new Date())) {
				date = this.addDay(date, 10);
			}else {
				break;
			}
		}
		
		Timer timer = new Timer();
		CheckMobileSattus cms = new CheckMobileSattus();
		timer.schedule(cms, date, PERIOD_DAY);
	}

	// 增加或减少天数
	private Date addDay(Date date, int num) {
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.MINUTE, num);
		return startDT.getTime();
	}
}
