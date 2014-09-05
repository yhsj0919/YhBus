package com.yh.bus.tools;

import android.text.format.Time;

public class Tools {

	/**
	 * 得到开始时间
	 * 
	 * @param operationTime
	 *            返回的时间字符串
	 * @return 开始时间
	 */
	public static String getStartTime(String start_name, String operationTime) {
		String Starttime = "暂无运营时间";
		Time time = new Time();
		time.setToNow();

		String[] a = operationTime.replaceAll("：", " ").split(" * :* *");

		if (a.length <= 3) {

			return Starttime;
		}

		if (start_name.equals(a[0])) {
			if (a.length > 4) {
				if (time.month + 1 >= 3 && time.month + 1 <= 10) {
					return a[2];
				} else {
					return a[4];
				}
			} else {
				return a[1];
			}
		} else {
			if (a.length > 4) {
				if (time.month + 1 >= 3 && time.month + 1 <= 10) {
					return a[7];
				} else {
					return a[9];
				}
			} else {
				return a[3];
			}
		}

	}

	/**
	 * 得到结束时间
	 * 
	 * @param operationTime
	 *            返回的时间字符串
	 * @return 结束时间
	 */
	public static String getEndTime(String start_name, String operationTime) {
		String Starttime = "暂无运营时间";
		Time time = new Time();
		time.setToNow();
		String[] a = operationTime.replaceAll("：", " ").split(" * :* *");

		if (a.length <= 3) {

			return Starttime;
		}

		if (start_name.equals(a[0])) {

			if (a.length > 4) {
				if (time.month + 1 >= 3 && time.month + 1 <= 10) {
					return a[7];
				} else {
					return a[9];
				}
			} else {
				return a[3];
			}
		} else {
			if (a.length > 4) {
				if (time.month + 1 >= 3 && time.month + 1 <= 10) {
					return a[2];
				} else {
					return a[4];
				}
			} else {
				return a[1];
			}
		}

	}
}
