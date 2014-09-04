package com.yh.yhbus.domain;

/**
 * 线路站点返回的对象
 * 
 * User: LOVE Date: 14-08-30 Time: 下午1:33 Mail: 1130402124@qq.com
 *
 */
public class Bus_View_Site {
	/** id */
	private String id;
	/** 本地id */
	private String area;
	/** 终点站 */
	private String lat;
	/** 线路名称 */
	private String lng;
	/**  */
	private String state;
	/** 站点名称 */
	private String stationName;
	/** 更新时间 */
	private String updateTime;

	public Bus_View_Site(String id, String area, String lat, String lng,
			String state, String stationName, String updateTime) {
		super();
		this.id = id;
		this.area = area;
		this.lat = lat;
		this.lng = lng;
		this.state = state;
		this.stationName = stationName;
		this.updateTime = updateTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
