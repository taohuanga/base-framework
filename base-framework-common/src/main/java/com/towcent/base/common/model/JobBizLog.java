package com.towcent.base.common.model;

import java.io.Serializable;


/**
 * 调度日志记录对象
 */
public class JobBizLog implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public JobBizLog() {
		super();
	}

	/**
	 * 
	 * @param triggerName {@linkplain #triggerName}
	 * @param groupName {@linkplain #groupName}
	 * @param type {@linkplain #type}
	 * @param gmtDate {@linkplain #gmtDate}
	 * @param remark {@linkplain #remark}
	 */
	public JobBizLog(String triggerName, String groupName, String type, Long gmtDate, String remark) {
		super();
		this.triggerName = triggerName;
		this.groupName = groupName;
		this.type = type;
		this.gmtDate = gmtDate;
		this.remark = remark;
	}

	/**
	 * 调度名称
	 */
	private String triggerName;
	/**
	 * 分组名称
	 */
	private String groupName;
	/**
	 * 调度状态类型
	 * 详细请参考{@linkplain com.towcent.base.common.enums.JobBizStatusEnum}
	 */
	private String type;
	/**
	 * 创建时间
	 */
	private Long gmtDate;
	/**
	 * 运行日志信息
	 */
	private String remark;

	/**
	 * {@linkplain #triggerName}
	 * @return
	 */
	public String getTriggerName() {
		return triggerName;
	}

	/**
	 * {@linkplain #triggerName}
	 * @param triggerName
	 */
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	/**
	 * {@linkplain #groupName}
	 * @return
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * {@linkplain #groupName}
	 * @param groupName
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * {@linkplain #type}
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * {@linkplain #type}
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * {@linkplain #gmtDate}
	 * @return
	 */
	public Long getGmtDate() {
		return gmtDate;
	}

	/**
	 * {@linkplain #gmtDate}
	 * @param gmtDate
	 */
	public void setGmtDate(Long gmtDate) {
		this.gmtDate = gmtDate;
	}

	/**
	 * {@linkplain #remark}
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * {@linkplain #remark}
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
