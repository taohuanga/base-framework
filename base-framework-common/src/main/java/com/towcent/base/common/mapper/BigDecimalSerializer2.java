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

import static java.math.RoundingMode.HALF_UP;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @ClassName: BigDecimalSerializer 
 * @Description: 将json格式化时, 按最小标度输出小数位, 最多两个小数位<br>
 * <code>
 * 0.00  -->  0          <br>
 * 20.10 -->  20.1       <br>
 * 20.12 -->  20.12      <br>
 * 20.125-->  20.13      <br>
 * </code>
 * @author huangtao
 * @date 2018年3月26日 下午8:52:11
 * @version 1.0.0
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class BigDecimalSerializer2 extends JsonSerializer<BigDecimal> {

	@Override
	public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		if (null != value) {
			BigDecimal v = value.setScale(0, HALF_UP);
			if (v.doubleValue() == value.doubleValue()) {
				gen.writeObject(v);
				return;
			}
			v = value.setScale(1, RoundingMode.HALF_UP);
			if (v.doubleValue() == value.doubleValue()) {
				gen.writeObject(v);
				return;
			}
		}
		gen.writeObject(value.setScale(2, HALF_UP));
	}

}

	