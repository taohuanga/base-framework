package com.towcent.base.common.page;

import java.io.Serializable;
import java.util.List;

/**
 * 分页返回对象
 * @author HuangTao
 * @version 2015年4月25日
 */
public class PaginationDto<E> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 当前页的数据
	 */
	private List<E> list;

	private Integer totalCount = 0;
	
	private Integer totalPage;

	public PaginationDto() {
	}

	public PaginationDto(int totalCount, List<E> list) {
		super();
		this.totalCount = totalCount;
		this.list = list;
	}

	public List<E> getList() {
		return list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer pageSize) {
		if (totalCount == null) {
			totalCount = 0;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 10;
		}
		this.totalPage =  (totalCount  +  pageSize  - 1) / pageSize;
	}
}
