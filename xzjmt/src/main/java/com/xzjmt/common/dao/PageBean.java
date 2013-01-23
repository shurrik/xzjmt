package com.xzjmt.common.dao;


public class PageBean {
	private int currentPage;//当前页
	private int totalCount;//总记录数
	private int pageSize = 20;//每页记录数
	private int currTotalCount;//
	private int pageCount;//总页数
	public int getCurrTotalCount() {
		return currTotalCount;
	}

	public void setCurrTotalCount(int currTotalCount) {
		this.currTotalCount += currTotalCount;
	}

	public PageBean(int currentPage, int totalCount) {
		this.currentPage = currentPage;
		this.totalCount = totalCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
}
