package com.towcent.base.common.apidoc;

import javax.annotation.Resource;

import org.junit.Test;

import com.towcent.base.BaseTest;
import com.towcent.base.common.model.ApiDocMain;
import com.towcent.base.dal.db.ApiDocMainMapper;

/**
 * api_doc_main 数据库操作接口测试用例
 * 
 * @author huangtao
 * @date 2018-04-17 18:57:40
 * @version 1.0
 * @copyright facegarden.com
 */
public class ApiDocMainMapperTest extends BaseTest {
	
	@Resource
	private ApiDocMainMapper mapper;
	
	@Test
	public void insertSelective() {
		ApiDocMain entity = new ApiDocMain();
		// 主键Id
		entity.setId(1);
		// 接口组(数据字典interface_group)
		entity.setInterfaceGroup("");
		// 接口编码（1.1.0）第一位是组，第二位是模块，第三位是序号
		entity.setInterfaceNo("");
		// 接口名称
		entity.setInterfaceName("");
		// 接口描述
		entity.setInterfaceDesc("");
		// 请求类型(GET|POST)
		entity.setRequestType("");
		// 接口URL
		entity.setInterfaceUrl("");
		// 出参类型(void|object|list|page)
		entity.setParamOutType("");
		// 是否需要验证登录(0:否 1:是)
		entity.setIsLogin("");
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
		ApiDocMain entity = new ApiDocMain();
		// 主键Id
		entity.setId(1);
		// 接口组(数据字典interface_group)
		entity.setInterfaceGroup("");
		// 接口编码（1.1.0）第一位是组，第二位是模块，第三位是序号
		entity.setInterfaceNo("");
		// 接口名称
		entity.setInterfaceName("");
		// 接口描述
		entity.setInterfaceDesc("");
		// 请求类型(GET|POST)
		entity.setRequestType("");
		// 接口URL
		entity.setInterfaceUrl("");
		// 出参类型(void|object|list|page)
		entity.setParamOutType("");
		// 是否需要验证登录(0:否 1:是)
		entity.setIsLogin("");
		int count = mapper.updateByPrimaryKeySelective(entity);
		System.out.println(count);
	}
	
	@Test
	public void selectByPrimaryKey() {
		ApiDocMain entity = new ApiDocMain();
		entity.setId(1);
		entity = mapper.selectByPrimaryKey(entity);
		System.out.println(entity);
	}
	
}