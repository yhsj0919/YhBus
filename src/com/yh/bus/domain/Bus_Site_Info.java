package com.yh.bus.domain;

/**
 * 站点查询返回的对象
 * 
 * User: LOVE Date: 14-08-30 Time: 下午1:33 Mail: 1130402124@qq.com
 *
 */
public class Bus_Site_Info {
	/** id */
	private String id;
	/** 城市代码 */
	private String area;
	/** 站点名称 */
	private String stationName;
	/** 线路id */
	private String buslines;
	/** 终点站 */
	private String endStationName;
	/** 线路名称 */
	private String lineName;
	/** 起始站 */
	private String startStationName;
	/** 更新时间 */
	private String updateTime;

	public Bus_Site_Info(String id, String area, String stationName,
			String buslines, String endStationName, String lineName,
			String startStationName, String updateTime) {
		super();
		this.id = id;
		this.area = area;
		this.stationName = stationName;
		this.buslines = buslines;
		this.endStationName = endStationName;
		this.lineName = lineName;
		this.startStationName = startStationName;
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

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getBuslines() {
		return buslines;
	}

	public void setBuslines(String buslines) {
		this.buslines = buslines;
	}

	public String getEndStationName() {
		return endStationName;
	}

	public void setEndStationName(String endStationName) {
		this.endStationName = endStationName;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getStartStationName() {
		return startStationName;
	}

	public void setStartStationName(String startStationName) {
		this.startStationName = startStationName;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
