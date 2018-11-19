package com.towcent.base.common.page;

import java.io.Serializable;

/**
 * 分页接收参数对象
 * @author HuangTao
 * @version 2015年4月25日
 */
public class SimplePageDto implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_COUNT = 20;
	public static final int DEFAULT_PAGE = 1;
	
	protected int pageSize = DEFAULT_COUNT;
	protected int pageNo = DEFAULT_PAGE;

	public SimplePageDto() {
	}

	public int getPageSize() {
		if (pageSize <= 0) {
			this.pageSize = DEFAULT_COUNT;
		}
		return pageSize;
	}

	public int getPageNo() {
		if (pageNo <= 0) {
			this.pageNo = DEFAULT_PAGE;
		}
		return pageNo;
	}

	/**
	 * 返回当前页首行行号
	 * @return
	 */
	public long getStartRowNum() {
		long rowNum = (pageNo - 1) * pageSize;
		return rowNum > 0 ? rowNum : 0;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

}
