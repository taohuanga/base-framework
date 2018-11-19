package com.towcent.base.common.qrcode;

import com.towcent.base.BaseTest;
import com.towcent.base.common.exception.RpcException;
import com.towcent.base.manager.QrcodeApi;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * 生成二维码测试用例
 * @author huangtao
 *
 */
public class QuickMarkServiceTest extends BaseTest {
	
	@Resource QuickMarkService service;

	@Resource
	QrcodeApi qrcodeApi;

	
	@Test public void qrCode() throws RpcException {
		//service.encoderGQRCode2File("https://www.baidu.com", "d:\\456.png", "png", 60);
		// qrcodeApi.createQrcode("https://www.baidu.com", "png", 60);
	}
	
}
