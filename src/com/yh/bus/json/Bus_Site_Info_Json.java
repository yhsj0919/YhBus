package com.yh.bus.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yh.bus.domain.Bus_Line_Info;
import com.yh.bus.domain.Bus_Site_Info;

/**
 * User: LOVE Date: 14-08-30 Time: 下午1:33 Mail: 1130402124@qq.com
 *
 */
public class Bus_Site_Info_Json {

	/**
	 * 所查询的线路信息
	 * 
	 * @param array
	 * @return
	 */
	public static ArrayList<Bus_Site_Info> getBusSiteInfo(JSONArray array) {
		ArrayList<Bus_Site_Info> allSite = new ArrayList<Bus_Site_Info>();
		try {

			for (int i = 0; i < array.length(); i++) {

				JSONObject info = array.getJSONObject(i);
				if (info.getJSONArray("busLineList").length() > 0) {
					allSite.add(new Bus_Site_Info(info.getString("id"), info
							.getString("area"), info.getString("stationName"),
							info.getJSONArray("busLineList").getJSONObject(0)
									.getString("id"), info
									.getJSONArray("busLineList")
									.getJSONObject(0)
									.getString("endStationName"), info
									.getJSONArray("busLineList")
									.getJSONObject(0).getString("lineName"),
							info.getJSONArray("busLineList").getJSONObject(0)
									.getString("startStationName"), info
									.getString("updateTime")));
				}

			}

		} catch (JSONException e) {
			e.printStackTrace();
			return allSite;
		}

		return allSite;

	}
}
