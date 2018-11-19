/*
 * All rights Reserved, Designed By www.songywang.com
 * @Project : base-framework-common
 * @Title : PortalDocApi.java
 * @Package : com.towcent.base.manager
 * @date : 2018年4月17日下午7:14:42
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
package com.towcent.base.manager;

import java.util.Map;

import com.towcent.base.common.exception.RpcException;
import com.towcent.base.common.model.ApiDocMain;

/**
 * @ClassName: PortalDocApi 
 * @Description: Portal接口文档相关接口
 *
 * @author huangtao
 * @date 2018年4月17日 下午7:14:42
 * @version 1.0.0
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public interface PortalDocApi {
	
	ApiDocMain getPortalDocByParam(Map<String, Object> params) throws RpcException;
	
}

	