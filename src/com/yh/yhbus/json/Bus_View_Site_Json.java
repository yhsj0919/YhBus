package com.yh.yhbus.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yh.yhbus.domain.Bus_View_Site;

/**
 * User: LOVE Date: 14-08-30 Time: 下午1:33 Mail: 1130402124@qq.com
 *
 */
public class Bus_View_Site_Json {

	/**
	 * 线路的站点信息
	 * 
	 * @param array
	 * @return
	 */
	public static ArrayList<Bus_View_Site> getBusSiteInfo(JSONArray array) {

		ArrayList<Bus_View_Site> allSite = new ArrayList<Bus_View_Site>();
		try {

			for (int i = 0; i < array.length(); i++) {

				JSONObject info = array.getJSONObject(i);
				allSite.add(new Bus_View_Site(info.getString("id"), info
						.getString("area"), info.getString("lat"), info
						.getString("lng"), info.getString("state"), info
						.getString("stationName"), info.getString("updateTime")));
			}

		} catch (JSONException e) {
			e.printStackTrace();
			return allSite;
		}

		return allSite;

	}
}
