/*
 * All rights Reserved, Designed By www.songywang.com
 * @Project : base-framework-common
 * @Title : WelinkRepsVo.java
 * @Package : com.towcent.base.common.utils
 * @date : 2018年8月16日下午1:27:41
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
package com.towcent.base.common.utils;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: WelinkRepsVo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *
 * @author huangtao
 * @date 2018年8月16日 下午1:27:41
 * @version 1.0.0
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
@Setter @Getter
@XmlRootElement(name="CSubmitState", namespace="http://tempuri.org/")
@XmlAccessorType(XmlAccessType.FIELD)
public class WelinkRepsVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final String NAMESPACE = "http://tempuri.org/";
	
	@XmlElement(name = "State", namespace = NAMESPACE)
	private String State;
	@XmlElement(name = "MsgID", namespace = NAMESPACE)
	private String MsgID;
	@XmlElement(name = "MsgState", namespace = NAMESPACE)
	private String MsgState;
	@XmlElement(name = "Reserve", namespace = NAMESPACE)
	private String Reserve;
	
}

	