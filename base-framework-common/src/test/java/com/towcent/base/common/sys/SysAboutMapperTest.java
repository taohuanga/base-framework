package com.towcent.base.common.sys;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.towcent.base.BaseTest;
import com.towcent.base.common.model.SysAbout;
import com.towcent.base.dal.db.SysAboutMapper;

/**
 * sys_about 数据库操作接口测试用例
 * 
 * @author licheng
 * @date 2018-02-01 16:48:45
 * @version 1.0
 * @copyright facegarden.com
 */
public class SysAboutMapperTest extends BaseTest {
	
	@Resource
	private SysAboutMapper mapper;
	
	@Test
	public void insertSelective() {
		SysAbout entity = new SysAbout();
		// ID
		entity.setId(1);
		// app类别(1:医生端)
		entity.setAppType((byte) 1);
		// logo
		entity.setLogo("");
		// 名称
		entity.setAppName("");
		// 关于我们内容
		entity.setAbout("");
		// 版权
		entity.setCopyright("");
		// 软件服务协议
		entity.setServiceAgreement("");
		// 隐私政策
		entity.setPrivacyPolicy("");
		// 用户须知
		entity.setUserNotes("");
		// 创建人
		entity.setCreateBy("1");
		// 创建时间
		entity.setCreateDate(new Date());
		// 修改人
		entity.setUpdateBy("1");
		// 修改日期
		entity.setUpdateDate(new Date());
		int count = mapper.insertSelective(entity);
		System.out.println(count);
	}
	
	@Test
	public void deleteByPrimaryKey() {
		int count = mapper.deleteByPrimaryKey(1 + "");
		System.out.println(count);
	}
	
	@Test
	public void updateByPrimaryKeySelective() {
		SysAbout entity = new SysAbout();
		// ID
		entity.setId(1);
		// app类别(1:医生端)
		entity.setAppType((byte) 1);
		// logo
		entity.setLogo("");
		// 名称
		entity.setAppName("");
		// 关于我们内容
		entity.setAbout("");
		// 版权
		entity.setCopyright("");
		// 软件服务协议
		entity.setServiceAgreement("");
		// 隐私政策
		entity.setPrivacyPolicy("");
		// 用户须知
		entity.setUserNotes("");
		// 创建人
		entity.setCreateBy("1");
		// 创建时间
		entity.setCreateDate(new Date());
		// 修改人
		entity.setUpdateBy("1");
		// 修改日期
		entity.setUpdateDate(new Date());
		int count = mapper.updateByPrimaryKeySelective(entity);
		System.out.println(count);
	}
	
	@Test
	public void selectByPrimaryKey() {
		SysAbout entity = new SysAbout();
		entity.setId(1);
		entity = mapper.selectByPrimaryKey(entity);
		System.out.println(entity);
	}
	
}