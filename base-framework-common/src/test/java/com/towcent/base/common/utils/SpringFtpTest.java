package com.towcent.base.common.utils;

import com.towcent.base.BaseTest;
import org.junit.Test;
import org.springframework.messaging.MessageChannel;

import javax.annotation.Resource;
import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * Ftp工具类
 * 
 * @author huangtao
 * @date 2016年8月23日 下午5:45:00
 * @version 0.1.0 
 */
public class SpringFtpTest extends BaseTest {
	
	@Resource
	private MessageChannel ftpChannel;
	
	/*
	#ftp setting
	image.ftp.server=192.168.0.251
	image.ftp.port=21
	image.ftp.username=zjftp
	image.ftp.password=123456
	*/
	/**
	 * 上传Ftp服务器
	 * @throws UnsupportedEncodingException
	 */
	@Test public void upload() throws UnsupportedEncodingException {
		File file = new File(getCurrentPath() + "123.jpg");
		SpringFTPUtil.ftpUpload(ftpChannel, file, "/huangtao/upload/");
		System.out.println("upload success!");
	}
	
	/**
	 * 获取当前目录Path
	 * @return
	 */
	private String getCurrentPath() {
		return getClass().getResource(".").getFile().toString();
	}
	
}
