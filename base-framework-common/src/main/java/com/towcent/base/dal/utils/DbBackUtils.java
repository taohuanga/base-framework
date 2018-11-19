/*
 * All rights Reserved, Designed By www.songywang.com
 * @Project : base-framework-common
 * @Title : DbBackUtils.java
 * @Package : com.towcent.base.dal.utils
 * @date : 2018年9月8日上午11:27:52
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
package com.towcent.base.dal.utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.towcent.base.common.utils.DateUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: DbBackUtils 
 * @Description: 数据库备份
 *
 * @author huangtao
 * @date 2018年9月8日 上午11:27:52
 * @version 1.0.0
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
@Component
@Setter @Getter
public class DbBackUtils {
	
	protected static Logger logger = LoggerFactory.getLogger(DbBackUtils.class);
	
	// jdbc:mysql://120.79.159.50:3306/zhongwang_test?useUnicode=true&characterEncoding=utf-8
	@Value("${jdbc.url}")
	private String jdbcUrl;
	
	@Value("${jdbc.username}")
	private String jdbcUsername;
	@Value("${jdbc.password}")
	private String jdbcPassword;
	
	/** 数据库备份目录 */
	@Value("${jdbc.backup.path}")
	private String backupPath = "/data/db/backup/";
	
	/**
	 * 备份当前数据库.
	 * @return  返回是否成功
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public boolean backAll() throws InterruptedException, IOException { 
		File saveFile  = new File(backupPath);
		if(!saveFile.exists()){
			saveFile.mkdirs();
		}
		
		String ip = StringUtils.substringBetween(jdbcUrl, "//", ":");
		String port = StringUtils.substring(jdbcUrl, jdbcUrl.lastIndexOf(":") + 1, jdbcUrl.lastIndexOf("/"));
		String dbName = StringUtils.substring(jdbcUrl, jdbcUrl.lastIndexOf("/") + 1, jdbcUrl.lastIndexOf("?"));
			
		String filename = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
		StringBuffer sb = new StringBuffer();
		sb.append("mysqldump ");
		sb.append(" -u").append(jdbcUsername);
		sb.append(" -p").append(jdbcPassword);
		sb.append(" -h").append(ip);
		sb.append(" -P").append(port);
		sb.append(" ").append(dbName);
		sb.append(" -r ");
		sb.append(backupPath).append(filename).append(".sql");
		
		Process process = Runtime.getRuntime().exec(sb.toString());
		if(process.waitFor() == 0){
			return true;
		}else{
			logger.error(IOUtils.toString(process.getErrorStream(), "utf-8"));
			return false;
		}
	}
	
	public static void main(String[] args) {
		String sf = "jdbc:mysql://120.79.159.50:3306/zhongwang_test?useUnicode=true&characterEncoding=utf-8";
		
		String ip = StringUtils.substringBetween(sf, "//", ":");
		System.out.println(ip);
		
		String port = StringUtils.substring(sf, sf.lastIndexOf(":") + 1, sf.lastIndexOf("/"));
		System.out.println(port);
		
		String dbName = StringUtils.substring(sf, sf.lastIndexOf("/") + 1, sf.lastIndexOf("?"));
		System.out.println(dbName);
	}
}

	