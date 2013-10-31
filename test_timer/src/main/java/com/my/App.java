package com.my;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class App {

	public static void main(String[] args) {
		Timer timer = new Timer();
		// 初次执行时间
		Date firstDate = Calendar.getInstance().getTime();
		// 延迟时间ms
		long delay = 10 * 1000;
		timer.schedule(new TestTask(), firstDate, delay);
	}
}
