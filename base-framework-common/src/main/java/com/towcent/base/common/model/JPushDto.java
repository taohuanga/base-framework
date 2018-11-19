package com.towcent.base.common.model;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 极光推送Vo
 * @author huangtao
 *
 */
@Setter @Getter
public class JPushDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * App类型(0:承运商 1:货主)
	private Byte appType;
	*/
	
	/**
	 * 用户Id
	 */
	private Integer userId;
	
	/**
	 * 消息标题
	 */
	private String title;
	
	/**
	 * 消息内容
	 */
	private String content;
	
	/** 扩展参数 */
	private Map<String, String> extraMap;
	
	public JPushDto() {
		super();
	}
	
	public JPushDto(Integer userId, String title, String content) {
		this(userId, title, content, null);
	}
	
	public JPushDto(Integer userId, String title, String content, Map<String, String> extraMap) {
		super();
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.extraMap = extraMap;
	}
	
}
