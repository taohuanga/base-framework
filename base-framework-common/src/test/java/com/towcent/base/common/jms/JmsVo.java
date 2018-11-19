package com.towcent.base.common.jms;

import com.towcent.base.common.vo.BaseDto;

/**
 * Jms消息测试
 * 
 * @author huangtao
 * @date 2016年8月23日 下午6:23:46
 * @version 0.1.0 
 */
public class JmsVo extends BaseDto {

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	public JmsVo(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
