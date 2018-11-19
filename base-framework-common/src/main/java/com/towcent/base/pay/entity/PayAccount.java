package com.towcent.base.pay.entity;

import com.egzosn.pay.common.bean.MsgType;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Generator
 * @date 2017-06-22 15:10:19
 * @version 1.0.0
 * @copyright facegarden.com
 */
@Setter @Getter
public class PayAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 主键Id.
     */
    private Integer id;
    
    /**
     * 支付账号id.
     */
    private Integer payId;

    /**
     * 支付合作id,商户id，差不多是支付平台的账号或id.
     */
    private String partner;

    /**
     * 应用id.
     */
    private String appid;

    /**
     * 支付公钥，sign_type只有单一key时public_key与private_key相等，比如sign_type=MD5的情况.
     */
    private String publicKey;

    /**
     * 支付私钥.
     */
    private String privateKey;

    /**
     * 异步回调地址.
     */
    private String notifyUrl;

    /**
     * 同步回调地址.
     */
    private String returnUrl;

    /**
     * 收款账号, 针对支付宝.
     */
    private String seller;

    /**
     * 签名类型.
     */
    private String signType;

    /**
     * 枚举值，字符编码 utf-8,gbk等等.
     */
    private String inputCharset;

    /**
     * 支付类型,aliPay：支付宝，wxPay：微信, youdianPay: 友店微信,此处开发者自定义对应com.egzosn.pay.demo.entity.PayType枚举值.
     */
    private PayType payType;

    /**
     * 消息类型，text,xml,json.
     */
    private String msgType;

    /**
     * 是否为测试环境.
     */
    private Boolean isTest;

    /**
     * 创建人.
     */
    private String createBy;

    /**
     * 创建时间.
     */
    private Date createTime;
	
    /**
     * 商户Id
     */
    private Integer merchantId;
    
	/**
	 * 公众号备注.
	 */
	private String wxRemark;
	
    /**
	 * id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * id.
	 *
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
     * 
     * {@linkplain #payId}
     *
     * @return the value of pay_account.pay_id
     */
    public Integer getPayId() {
        return payId;
    }

    /**
     * {@linkplain #payId}
     * @param payId the value for pay_account.pay_id
     */
    public void setPayId(Integer payId) {
        this.payId = payId;
    }

    /**
     * 
     * {@linkplain #partner}
     *
     * @return the value of pay_account.partner
     */
    public String getPartner() {
        return partner;
    }

    /**
     * {@linkplain #partner}
     * @param partner the value for pay_account.partner
     */
    public void setPartner(String partner) {
        this.partner = partner == null ? null : partner.trim();
    }

    /**
     * 
     * {@linkplain #appid}
     *
     * @return the value of pay_account.appid
     */
    public String getAppid() {
        return appid;
    }

    /**
     * {@linkplain #appid}
     * @param appid the value for pay_account.appid
     */
    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    /**
     * 
     * {@linkplain #publicKey}
     *
     * @return the value of pay_account.public_key
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * {@linkplain #publicKey}
     * @param publicKey the value for pay_account.public_key
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey == null ? null : publicKey.trim();
    }

    /**
     * 
     * {@linkplain #privateKey}
     *
     * @return the value of pay_account.private_key
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * {@linkplain #privateKey}
     * @param privateKey the value for pay_account.private_key
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey == null ? null : privateKey.trim();
    }

    /**
     * 
     * {@linkplain #notifyUrl}
     *
     * @return the value of pay_account.notify_url
     */
    public String getNotifyUrl() {
        return notifyUrl;
    }

    /**
     * {@linkplain #notifyUrl}
     * @param notifyUrl the value for pay_account.notify_url
     */
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl == null ? null : notifyUrl.trim();
    }

    /**
     * 
     * {@linkplain #returnUrl}
     *
     * @return the value of pay_account.return_url
     */
    public String getReturnUrl() {
        return returnUrl;
    }

    /**
     * {@linkplain #returnUrl}
     * @param returnUrl the value for pay_account.return_url
     */
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl == null ? null : returnUrl.trim();
    }

    /**
     * 
     * {@linkplain #seller}
     *
     * @return the value of pay_account.seller
     */
    public String getSeller() {
        return seller;
    }

    /**
     * {@linkplain #seller}
     * @param seller the value for pay_account.seller
     */
    public void setSeller(String seller) {
        this.seller = seller == null ? null : seller.trim();
    }

    /**
     * 
     * {@linkplain #signType}
     *
     * @return the value of pay_account.sign_type
     */
    public String getSignType() {
        return signType;
    }

    /**
     * {@linkplain #signType}
     * @param signType the value for pay_account.sign_type
     */
    public void setSignType(String signType) {
        this.signType = signType == null ? null : signType.trim();
    }

    /**
     * 
     * {@linkplain #inputCharset}
     *
     * @return the value of pay_account.input_charset
     */
    public String getInputCharset() {
        return inputCharset;
    }

    /**
     * {@linkplain #inputCharset}
     * @param inputCharset the value for pay_account.input_charset
     */
    public void setInputCharset(String inputCharset) {
        this.inputCharset = inputCharset == null ? null : inputCharset.trim();
    }

    /**
     * 
     * {@linkplain #payType}
     *
     * @return the value of pay_account.pay_type
     */
    public PayType getPayType() {
        return payType;
    }

    /**
     * {@linkplain #payType}
     * @param payType the value for pay_account.pay_type
     */
    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    /**
     * 
     * {@linkplain #msgType}
     *
     * @return the value of pay_account.msg_type
     */
    public String getMsgType() {
    	return msgType;
    }

    public MsgType getMsgType2() {
    	if (StringUtils.equalsIgnoreCase("text", msgType)) {
    		return MsgType.text;
    	} else if (StringUtils.equalsIgnoreCase("json", msgType)) {
    		return MsgType.json;
    	}
    	return MsgType.xml;
    }
    
    /**
     * {@linkplain #msgType}
     * @param msgType the value for pay_account.msg_type
     */
    public void setMsgType(String msgType) {
        this.msgType = msgType == null ? null : msgType.trim();
    }

    /**
     * 
     * {@linkplain #isTest}
     *
     * @return the value of pay_account.is_test
     */
    public Boolean getIsTest() {
        return isTest;
    }

    /**
     * {@linkplain #isTest}
     * @param isTest the value for pay_account.is_test
     */
    public void setIsTest(Boolean isTest) {
        this.isTest = isTest;
    }

}