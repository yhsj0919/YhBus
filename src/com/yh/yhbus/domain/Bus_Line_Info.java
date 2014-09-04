package com.yh.yhbus.domain;

/**
 * 线路查询返回的对象
 * 
 * User: LOVE Date: 14-08-30 Time: 下午1:33 Mail: 1130402124@qq.com
 *
 */
public class Bus_Line_Info {
	/** id */
	private String id;
	/** 本地id */
	private String localLineId;
	/** 终点站 */
	private String endStationName;
	/** 线路名称 */
	private String lineName;
	/** 起始站 */
	private String startStationName;
	/** 更新时间 */
	private String updateTime;

	public Bus_Line_Info(String id, String localLineId, String endStationName,
			String lineName, String startStationName, String updateTime) {
		super();
		this.id = id;
		this.localLineId = localLineId;
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

	public String getLocalLineId() {
		return localLineId;
	}

	public void setLocalLineId(String localLineId) {
		this.localLineId = localLineId;
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
