package com.towcent.base.common.sys;

import javax.annotation.Resource;

import org.junit.Test;

import com.towcent.base.BaseTest;
import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.service.JsSysConfigService;

/**
 * sys_property 数据库操作接口测试用例
 * 
 * @author licheng
 * @date 2018-03-12 09:20:53
 * @version 1.0
 * @copyright facegarden.com
 */
public class SysPropertyMapperTest extends BaseTest {
	
	@Resource
	private JsSysConfigService configService;
	
	@Test
	public void testMet() throws ServiceException {
		String spiderService = configService.getSysPropertyToString(0, "gen.defaultPackageName");
//		JsSysConfig s = configService.getSysPropertyByKey(0, "gen.defaultPackageName");
		System.out.println(spiderService);
		
	}
	
}