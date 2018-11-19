package com.towcent.base.common.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页对象
 * 
 * @author huangtao
 * @date 2015年6月25日 下午2:13:29
 * @version 0.1.0 
 */
@Setter @Getter
public class PageVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int DEF_COUNT = 10;
	public static final int DEF_PAGE = 1;
	
	protected int pageSize = DEF_COUNT;
	
	protected int pageNo = DEF_PAGE;
	
	/**
	 * 排序参数
	 */
	private String orderParam;
	
	/**
	 * 排序类型[asc,desc]
	 */
	private String orderBy;
}
