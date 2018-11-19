package com.towcent.base.common.beans;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 类属性包含断言
 * 
 * @author huangtao
 * @date 2014年6月26日 下午2:41:34
 * @version 0.1.0 
 */
public class BeanPropertyContainsPredicate extends CustomPredicate {

	private String propertyName;
	private Object propertyValue;
	
	public BeanPropertyContainsPredicate(String propertyName, Object propertyValue) {
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
			if (propertyValue == null) {
				return null == another;
			}
			if (another == null) {
				return null == propertyValue;
			}
			String str = another.toString();
			return str.contains(propertyValue.toString());
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

}
