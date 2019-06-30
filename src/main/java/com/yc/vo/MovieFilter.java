package com.yc.vo;

public class MovieFilter {
	private int selectType = 1;
	//1：正在 2：即将 3：经典
	
	private int catId;
	//0全部 
	
	private String region;
	//0 全部 
	
	private int yearId;
	// 0：全部 1:2018后  2: 2012以前
	
	private int order;
	// 1:热门 2：时间  3:评价
	
	
	public int getSelectType() {
		return selectType;
	}
	public void setSelectType(int selectType) {
		this.selectType = selectType;
	}
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public int getYearId() {
		return yearId;
	}
	public void setYearId(int yearId) {
		this.yearId = yearId;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
}
