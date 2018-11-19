/*
 * All rights Reserved, Designed By www.songywang.com
 * @Project : sdx-logistics-dubbo-client
 * @Title : MessageMailVo.java
 * @Package : com.towcent.sdx.logistics.app.client.sys.dto
 * @date : 2018年3月24日上午11:01:24
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
package com.towcent.base.common.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: MessageMailVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *
 * @author huangtao
 * @date 2018年3月24日 上午11:01:24
 * @version 1.0.0
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
@Setter @Getter
public class MessageMailVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 通知Id
	 */
	private Integer id;
	
	/**
	 * 通知标题
	 */
	private String title;
	
	/**
	 * 通知内容
	 */
	private String content;
	
	/**
	 * 消息类型
	 */
	private String noticeType;
	
	/**
	 * 消息创建时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date date;
	
	/**
	 * 是否是系统公告。<br>
	 * 系统公告：sys_notice<br>
	 * 站内信：notify_mail
	 */
	private boolean isSysNotice;
	
	/**
     * 业务编码(订单号).
     */
	private String bizNo;
	
	/**
     * 是否已读(0:未读 1:已读).
     */
	private Byte isRead;
}

	