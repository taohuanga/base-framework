package com.towcent.base.common.beans;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 类属性相等断言
 * 
 * @author huangtao
 * @date 2014年6月26日 下午2:41:34
 * @version 0.1.0 
 */
public class BeanPropertyEqualsPredicate extends CustomPredicate {

	private String propertyName;
	private Object propertyValue;
	
	public BeanPropertyEqualsPredicate(String propertyName, Object propertyValue) {
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
	}
	
	/* (non-Javadoc)
	 * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
	 */
	@Override
	public boolean evaluate(Object bean) {
		try {
			Object another = PropertyUtils.getProperty(bean, propertyName);
			return propertyValue == null ? another == null : propertyValue.equals(another);
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

}
