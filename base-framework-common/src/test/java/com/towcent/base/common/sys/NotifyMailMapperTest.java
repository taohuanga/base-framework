package com.towcent.base.common.sys;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.towcent.base.BaseTest;
import com.towcent.base.common.model.NotifyMail;
import com.towcent.base.dal.db.NotifyMailMapper;

/**
 * notify_mail 数据库操作接口测试用例
 * 
 * @author huangtao
 * @date 2018-03-24 10:53:20
 * @version 1.0
 * @copyright facegarden.com
 */
public class NotifyMailMapperTest extends BaseTest {
	
	@Resource
	private NotifyMailMapper mapper;
	
	@Test
	public void insertSelective() {
		NotifyMail entity = new NotifyMail();
		// 主键id
		entity.setId(1);
		// 标题
		entity.setTitle("");
		// 内容
		entity.setContent("");
		// 类型（数据字典）
		entity.setNoticeType("");
		// 会员Id
		entity.setUserId(1);
		// App类别(0:承运商 1:货主)
		entity.setAppType((byte) 1);
		// 创建时间
		entity.setCreateDate(new Date());
		// 业务编码(订单号)
		entity.setBizNo("");
		// 是否已读(0:未读 1:已读)
		entity.setIsRead((byte) 1);
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
		NotifyMail entity = new NotifyMail();
		// 主键id
		entity.setId(1);
		// 标题
		entity.setTitle("");
		// 内容
		entity.setContent("");
		// 类型（数据字典）
		entity.setNoticeType("");
		// 会员Id
		entity.setUserId(1);
		// App类别(0:承运商 1:货主)
		entity.setAppType((byte) 1);
		// 创建时间
		entity.setCreateDate(new Date());
		// 业务编码(订单号)
		entity.setBizNo("");
		// 是否已读(0:未读 1:已读)
		entity.setIsRead((byte) 1);
		int count = mapper.updateByPrimaryKeySelective(entity);
		System.out.println(count);
	}
	
	@Test
	public void selectByPrimaryKey() {
		NotifyMail entity = new NotifyMail();
		entity.setId(1);
		entity = mapper.selectByPrimaryKey(entity);
		System.out.println(entity);
	}
	
}