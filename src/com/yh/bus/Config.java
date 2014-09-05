package com.yh.bus;

import android.content.Context;
import android.content.SharedPreferences;

public class Config {

	public static final String BUS_LINE_SEARCH_PATH = "http://60.216.101.229/server-ue2/rest/buslines/simple/370100/";

	public static final String BUS_VIEW_GET_SITE_PATH = "http://60.216.101.229/server-ue2/rest/buslines/370100/";

	public static final String SP_BUS_LINE_SEARCH_INFO = "bus_line_ceearch_info";

	public static final String SP_BUS_VIEW_SITE = "bus_view_site";

	/**
	 * 将线路查询的结果保存
	 */
	/**
	 * @param context
	 * @param key
	 *            线路名称
	 * @param info
	 */
	public static void saveBusLineSearchInfo(Context context, String key,
			String info) {
		SharedPreferences.Editor e = context.getSharedPreferences(
				SP_BUS_LINE_SEARCH_INFO, Context.MODE_PRIVATE).edit();
		e.putString(key, info);

		e.commit();
	}

	/**
	 * 将线路站点的结果保存
	 * 
	 * @param context
	 * @param key
	 *            线路id
	 * @param info
	 */
	public static void saveBusViewSite(Context context, String key, String info) {
		SharedPreferences.Editor e = context.getSharedPreferences(
				SP_BUS_VIEW_SITE, Context.MODE_PRIVATE).edit();
		e.putString(key, info);

		e.commit();
	}

	/**
	 * 将线路查询的结果取出
	 * 
	 * @param context
	 * @param key
	 *            车辆id
	 * @return
	 */
	public static String getBusLineSearchInfo(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(
				SP_BUS_LINE_SEARCH_INFO, Context.MODE_PRIVATE);

		return sp.getString(key, null);

	}

	/**
	 * 将线路站点的结果取出
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getBusViewSite(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(SP_BUS_VIEW_SITE,
				Context.MODE_PRIVATE);

		return sp.getString(key, null);

	}

}
