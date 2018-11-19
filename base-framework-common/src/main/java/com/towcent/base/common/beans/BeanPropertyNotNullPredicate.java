package com.towcent.base.common.beans;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 类属性不为空断言
 * 
 * @author huangtao
 * @date 2014年9月11日 下午3:05:55
 * @version 0.1.0 
 */
public class BeanPropertyNotNullPredicate extends CustomPredicate {

	private String propertyName;
	
	public BeanPropertyNotNullPredicate(String propertyName) {
		this.propertyName = propertyName;
	}
	@Override
	public boolean evaluate(Object bean) {
		try {
			Object another = PropertyUtils.getProperty(bean, propertyName);
			/**
			 * 针对默认促销为空串时的处理 
			 */
			if ("defaultProNo".equals(propertyName) || "proNo".equals(propertyName)) {
				if (another != null) {
					return StringUtils.isNotBlank(another.toString());
				}
			}
			return another != null;
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

}
