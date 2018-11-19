package com.towcent.base.common.sys;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.towcent.base.BaseTest;
import com.towcent.base.common.model.SysCarouselConf;
import com.towcent.base.dal.db.SysCarouselConfMapper;

/**
 * sys_carousel_conf 数据库操作接口测试用例
 * 
 * @author licheng
 * @date 2018-04-18 11:11:48
 * @version 1.0
 * @copyright facegarden.com
 */
public class SysCarouselConfMapperTest extends BaseTest {
	
	@Resource
	private SysCarouselConfMapper mapper;
	
	@Test
	public void insertSelective() {
		SysCarouselConf entity = new SysCarouselConf();
		// id
		entity.setId(1);
		// 轮播图类型(数据字典:carousel_type)
		entity.setCarouselType("");
		// 标题
		entity.setTitle("");
		// 图片地址
		entity.setUrl("");
		// 是否是链接(1:是 0:不是)
		entity.setIsHref("");
		// 目标链接
		entity.setTargetUrl("");
		// 排序
		entity.setSort(1);
		// 备注
		entity.setRemark("");
		// 创建者
		entity.setCreateBy(1);
		// 创建时间
		entity.setCreateDate(new Date());
		// 更新者
		entity.setUpdateBy(1);
		// 更新时间
		entity.setUpdateDate(new Date());
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
		SysCarouselConf entity = new SysCarouselConf();
		// id
		entity.setId(1);
		// 轮播图类型(数据字典:carousel_type)
		entity.setCarouselType("");
		// 标题
		entity.setTitle("");
		// 图片地址
		entity.setUrl("");
		// 是否是链接(1:是 0:不是)
		entity.setIsHref("");
		// 目标链接
		entity.setTargetUrl("");
		// 排序
		entity.setSort(1);
		// 备注
		entity.setRemark("");
		// 创建者
		entity.setCreateBy(1);
		// 创建时间
		entity.setCreateDate(new Date());
		// 更新者
		entity.setUpdateBy(1);
		// 更新时间
		entity.setUpdateDate(new Date());
		// 删除标记(0:正常1:删除)
		entity.setDelFlag("");
		int count = mapper.updateByPrimaryKeySelective(entity);
		System.out.println(count);
	}
	
	@Test
	public void selectByPrimaryKey() {
		SysCarouselConf entity = new SysCarouselConf();
		entity.setId(1);
		entity = mapper.selectByPrimaryKey(entity);
		System.out.println(entity);
	}
	
}