/*
 * All rights Reserved, Designed By www.songywang.com
 * @Project : base-framework-common
 * @Title : PortalDocApiImpl.java
 * @Package : com.towcent.base.manager.impl
 * @date : 2018年4月17日下午7:24:40
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
package com.towcent.base.manager.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.towcent.base.common.exception.RpcException;
import com.towcent.base.common.model.ApiDocMain;
import com.towcent.base.manager.PortalDocApi;

/**
 * @ClassName: PortalDocApiImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *
 * @author huangtao
 * @date 2018年4月17日 下午7:24:40
 * @version 1.0.0
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
@Service
public class PortalDocApiImpl implements PortalDocApi {
	
	@Override
	public ApiDocMain getPortalDocByParam(Map<String, Object> params) throws RpcException {

		// TODO Auto-generated method stub
		return null;

	}

}

	