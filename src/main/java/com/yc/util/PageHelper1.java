package com.yc.util;

import java.util.List;

public class PageHelper1 <T>{
	private int pageNum;
	private int pageSize;
	private int totalRecord;
	
	private int totalPage;
	private int startIndex;
	
	private List<T> list;
	
	private int start;
	private int end;
	public PageHelper1(int pageNum, int pageSize, int totalRecord) {
		super();
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.totalRecord = totalRecord;
		if(totalRecord%pageSize==0){
			this.totalPage=totalRecord/pageSize;
		}else{
			this.totalPage=totalRecord/pageSize+1;
		}
		this.startIndex = (pageNum-1)*pageSize;
		this.start = 1;
		this.end = 5;
		if(totalPage<=5){
			this.end=totalPage;
		}else{
			this.start=pageNum-2;
			this.end=pageNum+2;
			if(this.start<=0){
				this.start=1;
				this.end=5;
			}
			if(this.end>this.totalPage){
				this.end=this.totalPage;
				this.start=this.totalPage-4;
			}
		}
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	
	public List<T> getList(){
		return list;
	}
	
	public void setList(List<T> list){
		this.list=list;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public int getStart() {
		return start;
	}
	public int getEnd() {
		return end;
	}

}
