package com.yh.bus.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yh.bus.domain.Bus_Line_Info;

/**
 * User: LOVE Date: 14-08-30 Time: 下午1:33 Mail: 1130402124@qq.com
 *
 */
public class Bus_Line_Info_Json {

	/**
	 * 所查询的线路信息
	 * 
	 * @param array
	 * @return
	 */
	public static ArrayList<Bus_Line_Info> getBusLineInfo(JSONArray array) {
		ArrayList<Bus_Line_Info> allLine = new ArrayList<Bus_Line_Info>();
		try {

			for (int i = 0; i < array.length(); i++) {

				JSONObject info = array.getJSONObject(i);

				allLine.add(new Bus_Line_Info(info.getString("id"), info
						.getString("localLineId"), info
						.getString("endStationName"), info
						.getString("lineName"), info
						.getString("startStationName"), info
						.getString("updateTime")));

			}

		} catch (JSONException e) {
			e.printStackTrace();
			return allLine;
		}

		return allLine;

	}
}
