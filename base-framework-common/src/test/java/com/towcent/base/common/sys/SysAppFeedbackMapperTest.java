package com.towcent.base.common.sys;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.towcent.base.BaseTest;
import com.towcent.base.common.model.SysAppFeedback;
import com.towcent.base.dal.db.SysAppFeedbackMapper;

/**
 * sys_app_feedback 数据库操作接口测试用例
 * 
 * @author huangtao
 * @date 2018-03-24 10:13:24
 * @version 1.0
 * @copyright facegarden.com
 */
public class SysAppFeedbackMapperTest extends BaseTest {
	
	@Resource
	private SysAppFeedbackMapper mapper;
	
	@Test
	public void insertSelective() {
		SysAppFeedback entity = new SysAppFeedback();
		// 主键Id
		entity.setId(1);
		// 反馈账号
		entity.setUserId(null);
		// 反馈内容
		entity.setContent("");
		// 手机型号
		entity.setPhoneModel("");
		// 系统版本
		entity.setSysVersion("");
		// 手机操作系统(1:ios 2:安卓 3:h5)
		entity.setSysType((byte) 1);
		// 应用版本
		entity.setAppVersion("");
		// App类别(1:医生端)
		entity.setAppType((byte) 1);
		// 反馈时间
		entity.setCreateDate(new Date());
		// 反馈类别(1:内容意见 2:产品建议 3:技术问题 4:其它)
		entity.setFeedbackType("");
		// 图片(多张图;分割)
		entity.setPicUrl("");
		// 联系电话
		entity.setContact("");
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
		SysAppFeedback entity = new SysAppFeedback();
		// 主键Id
		entity.setId(1);
		// 反馈账号
		entity.setUserId(null);
		// 反馈内容
		entity.setContent("");
		// 手机型号
		entity.setPhoneModel("");
		// 系统版本
		entity.setSysVersion("");
		// 手机操作系统(1:ios 2:安卓 3:h5)
		entity.setSysType((byte) 1);
		// 应用版本
		entity.setAppVersion("");
		// App类别(1:医生端)
		entity.setAppType((byte) 1);
		// 反馈时间
		entity.setCreateDate(new Date());
		// 反馈类别(1:内容意见 2:产品建议 3:技术问题 4:其它)
		entity.setFeedbackType("");
		// 图片(多张图;分割)
		entity.setPicUrl("");
		// 联系电话
		entity.setContact("");
		int count = mapper.updateByPrimaryKeySelective(entity);
		System.out.println(count);
	}
	
	@Test
	public void selectByPrimaryKey() {
		SysAppFeedback entity = new SysAppFeedback();
		entity.setId(1);
		entity = mapper.selectByPrimaryKey(entity);
		System.out.println(entity);
	}
	
}