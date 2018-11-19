package com.towcent.base.common.code;

import com.towcent.base.BaseTest;
import com.towcent.base.common.mapper.JsonMapper;
import com.towcent.base.common.model.CodingRule;
import com.towcent.base.dal.db.CodingRuleMapper;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO: 增加描述
 * 
 * @author huangtao
 * @date 2016年9月17日 下午6:31:06
 * @version 0.1.0 
 */
public class CodeRuleTest extends BaseTest {
	
	@Resource CodingRuleMapper mapper;
	
	@Test public void query() {
		List<CodingRule> list = mapper.selectByParams(null);
		System.out.println(JsonMapper.toJsonString(list));
	}
	
}
