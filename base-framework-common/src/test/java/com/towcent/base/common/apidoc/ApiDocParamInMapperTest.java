package com.towcent.base.common.apidoc;

import javax.annotation.Resource;

import org.junit.Test;

import com.towcent.base.BaseTest;
import com.towcent.base.common.model.ApiDocParamIn;
import com.towcent.base.dal.db.ApiDocParamInMapper;

/**
 * api_doc_param_in 数据库操作接口测试用例
 * 
 * @author huangtao
 * @date 2018-04-17 18:57:40
 * @version 1.0
 * @copyright facegarden.com
 */
public class ApiDocParamInMapperTest extends BaseTest {
	
	@Resource
	private ApiDocParamInMapper mapper;
	
	@Test
	public void insertSelective() {
		ApiDocParamIn entity = new ApiDocParamIn();
		// 主键Id
		entity.setId(1);
		// 参数名称
		entity.setParamName("");
		// 参数描述
		entity.setParamDesc("");
		// 参数类型(String|Integer|Double|Date)
		entity.setParamType("");
		// 取值说明
		entity.setValDeclare("");
		// 是否必填项(0:否 1:是)
		entity.setRequired("");
		// 正则表达式
		entity.setRegular("");
		// 接口Id
		entity.setInterfaceId(1);
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
		ApiDocParamIn entity = new ApiDocParamIn();
		// 主键Id
		entity.setId(1);
		// 参数名称
		entity.setParamName("");
		// 参数描述
		entity.setParamDesc("");
		// 参数类型(String|Integer|Double|Date)
		entity.setParamType("");
		// 取值说明
		entity.setValDeclare("");
		// 是否必填项(0:否 1:是)
		entity.setRequired("");
		// 正则表达式
		entity.setRegular("");
		// 接口Id
		entity.setInterfaceId(1);
		int count = mapper.updateByPrimaryKeySelective(entity);
		System.out.println(count);
	}
	
	@Test
	public void selectByPrimaryKey() {
		ApiDocParamIn entity = new ApiDocParamIn();
		entity.setId(1);
		entity = mapper.selectByPrimaryKey(entity);
		System.out.println(entity);
	}
	
}