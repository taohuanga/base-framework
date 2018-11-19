package com.towcent.base.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 银行卡信息
 */
@Data
public class BankInfoDo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
     * 银行卡号
     */
    private String bankCardNumber;
    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行卡类型，0:不能识别; 1: 借记卡; 2: 信用卡
     */
    private Integer bankCardType;
}
