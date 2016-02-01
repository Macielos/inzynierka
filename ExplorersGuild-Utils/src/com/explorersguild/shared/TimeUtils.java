package com.explorersguild.shared;

import java.util.Calendar;
import java.util.Date;

public abstract class TimeUtils {

	private TimeUtils() {

	}

	public static long now() {
		return System.currentTimeMillis();
	}

	public static long timeElapsed(long time) {
		return now() - time;
	}

	public static Date startOfDay() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		calendar.set(year, month, day, 0, 0, 0);
		return calendar.getTime();
	}

}
