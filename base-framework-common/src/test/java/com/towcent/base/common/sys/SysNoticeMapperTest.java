package com.towcent.base.common.sys;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.towcent.base.BaseTest;
import com.towcent.base.common.model.SysNotice;
import com.towcent.base.dal.db.SysNoticeMapper;

/**
 * sys_notice 数据库操作接口测试用例
 * 
 * @author huangtao
 * @date 2018-03-24 10:51:53
 * @version 1.0
 * @copyright facegarden.com
 */
public class SysNoticeMapperTest extends BaseTest {
	
	@Resource
	private SysNoticeMapper mapper;
	
	@Test
	public void insertSelective() {
		SysNotice entity = new SysNotice();
		// 主键id
		entity.setId(1);
		// 标题
		entity.setTitle("");
		// 内容
		entity.setContent("");
		// 类型（数据字典）
		entity.setNoticeType("");
		// App类别(0:承运商 1:货主)
		entity.setAppType((byte) 1);
		// 创建时间
		entity.setCreateDate(new Date());
		// 创建人
		entity.setCreateBy("1");
		// 删除标记(0:正常1:删除)
		entity.setDelFlag("");
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
		SysNotice entity = new SysNotice();
		// 主键id
		entity.setId(1);
		// 标题
		entity.setTitle("");
		// 内容
		entity.setContent("");
		// 类型（数据字典）
		entity.setNoticeType("");
		// App类别(0:承运商 1:货主)
		entity.setAppType((byte) 1);
		// 创建时间
		entity.setCreateDate(new Date());
		// 创建人
		entity.setCreateBy("1");
		// 删除标记(0:正常1:删除)
		entity.setDelFlag("");
		int count = mapper.updateByPrimaryKeySelective(entity);
		System.out.println(count);
	}
	
	@Test
	public void selectByPrimaryKey() {
		SysNotice entity = new SysNotice();
		entity.setId(1);
		entity = mapper.selectByPrimaryKey(entity);
		System.out.println(entity);
	}
	
}