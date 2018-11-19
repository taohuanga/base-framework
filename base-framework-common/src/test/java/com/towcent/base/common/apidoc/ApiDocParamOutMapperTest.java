package com.towcent.base.common.apidoc;

import javax.annotation.Resource;

import org.junit.Test;

import com.towcent.base.BaseTest;
import com.towcent.base.common.model.ApiDocParamOut;
import com.towcent.base.dal.db.ApiDocParamOutMapper;

/**
 * api_doc_param_out 数据库操作接口测试用例
 * 
 * @author huangtao
 * @date 2018-04-17 18:57:40
 * @version 1.0
 * @copyright facegarden.com
 */
public class ApiDocParamOutMapperTest extends BaseTest {
	
	@Resource
	private ApiDocParamOutMapper mapper;
	
	@Test
	public void insertSelective() {
		ApiDocParamOut entity = new ApiDocParamOut();
		// 主键Id
		entity.setId(1);
		// 字段名称
		entity.setFieldName("");
		// 字段描述
		entity.setFieldDesc("");
		// 字段类型(String|Integer|Double|Date|list)
		entity.setFieldType("");
		// 输出格式(例如时间格式化)
		entity.setOutFormat("");
		// 示例值
		entity.setExample("");
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
		ApiDocParamOut entity = new ApiDocParamOut();
		// 主键Id
		entity.setId(1);
		// 字段名称
		entity.setFieldName("");
		// 字段描述
		entity.setFieldDesc("");
		// 字段类型(String|Integer|Double|Date|list)
		entity.setFieldType("");
		// 输出格式(例如时间格式化)
		entity.setOutFormat("");
		// 示例值
		entity.setExample("");
		// 接口Id
		entity.setInterfaceId(1);
		int count = mapper.updateByPrimaryKeySelective(entity);
		System.out.println(count);
	}
	
	@Test
	public void selectByPrimaryKey() {
		ApiDocParamOut entity = new ApiDocParamOut();
		entity.setId(1);
		entity = mapper.selectByPrimaryKey(entity);
		System.out.println(entity);
	}
	
}