package com.vlife.checkserver.mobilestatus;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * @author: gaoyaxuan
 * @date:2018年8月13日 下午5:28:43
 */
public class TimerManager {
	// 每隔多久執行一次
	private static final long PERIOD_DAY = Integer.parseInt(Methods.getProperty("check.mobile.status.time.interval")) * 60 * 1000;

	public TimerManager() {
		Calendar calendar = Calendar.getInstance();
		// 定制每日12:30:30执行
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		// 每天
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.set(year, month, day, 9, 0, 0);
		// 第一次执行定时任务的时间
		Date date = calendar.getTime(); 
		// 如果当前时间已经过去所定时的时间点，则在第二天时间点开始执行
		while(true) {
			if (date.before(new Date())) {
				date = this.addMinute(date, 10);
			}else {
				break;
			}
		}

		@SuppressWarnings("all")
		Timer timer = new Timer();
		CheckMobileSattus cms = new CheckMobileSattus();
		timer.schedule(cms, date, PERIOD_DAY);
	}

	/**
	 * 为date增加分钟
	 * @param	data	Date对象	   
	 * @param	mun		增加的分钟数
	 * @return	返回一个增加了分钟的Date对象
	 */
	private Date addMinute(Date date, int num) {
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.MINUTE, num);
		return startDT.getTime();
	}
}
