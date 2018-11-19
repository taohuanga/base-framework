package com.towcent.base.common.model;

import java.io.Serializable;
import java.util.Date;

import com.towcent.base.common.config.SpringConfig;
import com.towcent.base.common.utils.SpringContextHolder;

/**
 * 请写出类的用途 
 * 
 */
public class CodingRule implements Serializable {
    
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String requestId;

    /**
     * 
     */
    private String requestName;

    /**
     * 
     */
    private String prefix;

    /**
     * 
     */
    private Integer currentSerialNo;

    /**
     * 
     */
    private Integer serialNoLength;

    /**
     * 
     */
    private Integer resetMode;

    /**
     * 该字段去取数据库时间，而不是应用服务器时间
     */
    private Date resetTime;
    
    /**
     * 数据库时间
     */
    private Date dbTime;

    /**
     * 
     */
    private Boolean allowBreakNo;
    
    /**
     * 
     */
    private Boolean isAbstract;

    /**
     * 
     */
    private String remarks;
    
    private Integer merchantId;

    /**
     * 
     * {@linkplain #id}
     *
     * @return the value of coding_rule.id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * {@linkplain #id}
     * @param id the value for coding_rule.id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #requestId}
     *
     * @return the value of coding_rule.request_id
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * 
     * {@linkplain #requestId}
     * @param requestId the value for coding_rule.request_id
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * 
     * {@linkplain #requestName}
     *
     * @return the value of coding_rule.request_name
     */
    public String getRequestName() {
        return requestName;
    }

    /**
     * 
     * {@linkplain #requestName}
     * @param requestName the value for coding_rule.request_name
     */
    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    /**
     * 
     * {@linkplain #prefix}
     *
     * @return the value of coding_rule.prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * 
     * {@linkplain #prefix}
     * @param prefix the value for coding_rule.prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * 
     * {@linkplain #currentSerialNo}
     *
     * @return the value of coding_rule.current_serial_no
     */
    public Integer getCurrentSerialNo() {
        return currentSerialNo;
    }

    /**
     * 
     * {@linkplain #currentSerialNo}
     * @param currentSerialNo the value for coding_rule.current_serial_no
     */
    public void setCurrentSerialNo(Integer currentSerialNo) {
        this.currentSerialNo = currentSerialNo;
    }

    /**
     * 
     * {@linkplain #serialNoLength}
     *
     * @return the value of coding_rule.serial_no_length
     */
    public Integer getSerialNoLength() {
        return serialNoLength;
    }

    /**
     * 
     * {@linkplain #serialNoLength}
     * @param serialNoLength the value for coding_rule.serial_no_length
     */
    public void setSerialNoLength(Integer serialNoLength) {
        this.serialNoLength = serialNoLength;
    }

    /**
     * 
     * {@linkplain #resetMode}
     *
     * @return the value of coding_rule.reset_mode
     */
    public Integer getResetMode() {
        return resetMode;
    }

    /**
     * 
     * {@linkplain #resetMode}
     * @param resetMode the value for coding_rule.reset_mode
     */
    public void setResetMode(Integer resetMode) {
        this.resetMode = resetMode;
    }

    /**
     * 
     * {@linkplain #resetTime}
     *
     * @return the value of coding_rule.reset_time
     */
    public Date getResetTime() {
        return resetTime;
    }

    /**
     * 
     * {@linkplain #resetTime}
     * @param resetTime the value for coding_rule.reset_time
     */
    public void setResetTime(Date resetTime) {
        this.resetTime = resetTime;
    }
    
    

    public Date getDbTime() {
		return dbTime;
	}

	public void setDbTime(Date dbTime) {
		this.dbTime = dbTime;
	}

	/**
     * 
     * {@linkplain #allowBreakNo}
     *
     * @return the value of coding_rule.allow_break_no
     */
    public Boolean getAllowBreakNo() {
        return allowBreakNo;
    }

    /**
     * 
     * {@linkplain #allowBreakNo}
     * @param allowBreakNo the value for coding_rule.allow_break_no
     */
    public void setAllowBreakNo(Boolean allowBreakNo) {
        this.allowBreakNo = allowBreakNo;
    }
    
    /**
     * 
     * {@linkplain #isAbstract}
     *
     * @return the value of coding_rule.isAbstract
     */
    public Boolean getIsAbstract() {
		return isAbstract;
	}

    /**
     * 
     * {@linkplain #isAbstract}
     *
     * @return the value of coding_rule.isAbstract
     */
	public void setIsAbstract(Boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	/**
     * 
     * {@linkplain #remarks}
     *
     * @return the value of coding_rule.remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 
     * {@linkplain #remarks}
     * @param remarks the value for coding_rule.remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
	/**
	 * 获取数据库名称
	 */
	// @JsonIgnore
	public String getDbName(){
		SpringConfig config = SpringContextHolder.getBean(SpringConfig.class);
		return config.getJdbcType();
	}

	/**
	 * merchantId.
	 *
	 * @return the merchantId
	 */
	public Integer getMerchantId() {
		return merchantId;
	}

	/**
	 * merchantId.
	 *
	 * @param merchantId the merchantId to set
	 */
	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}
}