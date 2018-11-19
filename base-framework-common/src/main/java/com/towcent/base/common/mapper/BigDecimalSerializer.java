/*
 * All rights Reserved, Designed By www.songywang.com
 * @Project : sdx-logistics-common
 * @Title : TestSerializer.java
 * @Package : com.towcent.sdx.logistics.common
 * @date : 2018年3月26日下午8:52:11
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
package com.towcent.base.common.mapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @ClassName: BigDecimalSerializer 
 * @Description: 序列化工具类  将json格式化统一输出两位小数<br>
 * <code>
 * 0     -->   0.00      <br>
 * 20.1  -->  20.10      <br>
 * 20.12 -->  20.12      <br>
 * 20.125-->  20.13      <br>
 * </code>
 *
 * @author huangtao
 * @date 2018年3月26日 下午8:52:11
 * @version 1.0.0
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {

	@Override
	public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		gen.writeObject(value.setScale(2, RoundingMode.HALF_UP));
	}

}

	