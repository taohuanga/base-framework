package com.towcent.base.common.page;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * 简单分页对象
 *
 * @author huangtao
 * @date 2016年9月6日 上午9:56:31
 * @version 0.1.0
 * @copyright towcent.com
 */
public class SimplePage implements Pageable, Serializable {

	private static final long serialVersionUID = 1L;

	public static final int DEF_COUNT = 20;

	protected String orderByField;
	protected String orderBy;
	protected Map<String, Object> params;
	protected int totalCount = 0;
	protected int pageSize = 20;
	protected int pageNo = 1;

	@SuppressWarnings("unused")
	private int startRowNum;
	@SuppressWarnings("unused")
	private int endRowNum;

	public SimplePage() {
	}

	public SimplePage(int pageNo, int pageSize, int totalCount) {
		this.totalCount = (totalCount <= 0 ? 0 : totalCount);

		this.pageSize = (pageSize <= 0 ? DEF_COUNT : pageSize);

		this.pageNo = (pageNo <= 0 ? 1 : pageNo);

		// if ((this.pageNo - 1) * this.pageSize >= totalCount) {
		// this.pageNo = (int) (totalCount / this.pageSize);
		// }
	}

	public SimplePage(int pageNo, int pageSize, int totalCount, String orderByField, String orderBy,Map<String, Object> params) {
		super();
		this.totalCount = (totalCount <= 0 ? 0 : totalCount);
		this.pageSize = (pageSize <= 0 ? DEF_COUNT : pageSize);
		this.pageNo = (pageNo <= 0 ? 1 : pageNo);
		this.orderByField = orderByField;
		this.orderBy = orderBy;
		this.params = params;
	}

	/**
	 * 调整分页参数，使合理化
	 */
	public void adjustPage() {
		if (totalCount <= 0) {
			totalCount = 0;
		}
		if (pageSize <= 0) {
			pageSize = DEF_COUNT;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		}
		// if ((pageNo - 1) * pageSize >= totalCount) {
		// pageNo = (int) (totalCount / pageSize);
		// }
	}

	@Override
	public int getTotalCount() {
		return totalCount;
	}

	@Override
	public int getTotalPage() {
		int totalPage = (int) (totalCount / pageSize);
		if (totalCount % pageSize != 0 || totalPage == 0) {
			totalPage++;
		}
		return totalPage;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public int getPageNo() {
		return pageNo;
	}

	@Override
	public boolean isFirstPage() {
		return pageNo <= 1;
	}

	@Override
	public boolean isLastPage() {
		return pageNo >= getTotalPage();
	}

	@Override
	public int getNextPage() {
		if (isLastPage()) {
			return pageNo;
		} else {
			return pageNo + 1;
		}
	}

	@Override
	public int getPrePage() {
		if (isFirstPage()) {
			return pageNo;
		} else {
			return pageNo - 1;
		}
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * 返回当前页首行行号
	 *
	 * @return
	 */
	public int getStartRowNum() {
		if (isFirstPage()) {
			return 0;
		}
		return (pageNo - 1) * pageSize;
	}

	public void setStartRowNum(int startRowNum) {
		this.startRowNum = startRowNum;
	}

	/**
	 * 返回当前页结束行号
	 *
	 * @return
	 */
	public int getEndRowNum() {
		return getStartRowNum() + pageSize + 1;
	}

	public void setEndRowNum(int endRowNum) {
		this.endRowNum = endRowNum;
	}

	public String getOrderByField() {
		return orderByField;
	}

	public void setOrderByField(String orderByField) {
		this.orderByField = orderByField;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
