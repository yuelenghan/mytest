package com.my;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;

public class TestTask extends TimerTask {

	@Override
	public void run() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println("测试java定时任务---" + sdf.format(Calendar.getInstance().getTime()));
	}

}
