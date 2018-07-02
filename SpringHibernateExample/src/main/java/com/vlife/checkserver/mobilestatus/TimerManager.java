package com.vlife.checkserver.mobilestatus;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;


public class TimerManager {

	//时间间隔:5分钟
	 private static final long PERIOD_DAY = 5*60 * 1000;
	 public TimerManager () {
	  Calendar calendar = Calendar.getInstance();
	  //定制每日12:30:30执行
//	  calendar.set(Calendar.HOUR_OF_DAY, 12);
//	  calendar.set(Calendar.MINUTE, 10);
	  calendar.set(Calendar.SECOND, 10);
	  Date date = calendar.getTime();  //第一次执行定时任务的时间
	  //如果当前时间已经过去所定时的时间点，则在第二天时间点开始执行
	  if (date.before(new Date())) {
	   date = this.addDay(date, 10);
	  }
	  Timer timer = new Timer();
	  CheckMobileSattus cms =new CheckMobileSattus();
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
