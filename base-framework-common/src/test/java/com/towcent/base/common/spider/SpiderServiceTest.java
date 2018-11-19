package com.towcent.base.common.spider;

import org.junit.Test;

import com.towcent.base.BaseTest;
import com.towcent.base.common.exception.RpcException;

/**
 * 爬虫测试
 * 
 * @author huangtao
 *
 */
public class SpiderServiceTest extends BaseTest {

	@Test
	public void spider() throws RpcException {
		SpiderProcesser spiderProcesser = new SpiderProcesser();
		spiderProcesser.setServiceId("xicdlSpiderHandle");
		spiderProcesser.spider();
	}

}
