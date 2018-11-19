/*
 * All rights Reserved, Designed By www.songywang.com
 * @Project : base-framework-common
 * @Title : SysAreaTest.java
 * @Package : com.towcent.base.common.sys
 * @date : 2018年4月18日下午2:44:08
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
package com.towcent.base.common.sys;

import javax.annotation.Resource;

import org.junit.Test;

import com.towcent.base.BaseTest;
import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.model.JsSysArea;
import com.towcent.base.service.JsSysAreaService;

/**
 * @ClassName: SysAreaTest 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *
 * @author huangtao
 * @date 2018年4月18日 下午2:44:08
 * @version 1.0.0
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class SysAreaTest extends BaseTest {

	@Resource
	private JsSysAreaService areaService;

	@Test
	public void sdf() throws ServiceException {
		JsSysArea area = areaService.getAreaById("110000");
		System.out.println(area.getAreaName());
	}
	
}

	