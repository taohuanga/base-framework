package com.towcent.base.common.beans;

import org.apache.commons.beanutils.PropertyUtils;

import java.math.BigDecimal;

/**
 * 类属性小于断言
 * 
 * @author huangtao
 * @date 2014年9月11日 下午3:05:55
 * @version 0.1.0 
 */
public class BeanPropertyLessThanPredicate extends CustomPredicate {

	private String propertyName;
	private Object propertyValue;
	/**
	 * 比较类型  INTEGER OR BIGDECIMAL
	 */
	private CompareType compareType;
	
	public BeanPropertyLessThanPredicate(String propertyName, Object propertyValue) {
		this(propertyName, propertyValue, CompareType.INTEGER);
	}
	
	public BeanPropertyLessThanPredicate(String propertyName, Object propertyValue, CompareType compareType) {
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.compareType = compareType;
	}
	
	/* (non-Javadoc)
	 * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
	 */
	@Override
	public boolean evaluate(Object bean) {
		try {
			Object another = PropertyUtils.getProperty(bean, propertyName);
			if (null == another || null == propertyValue) {
				return false;
			}
			if (compareType == CompareType.INTEGER) {
				Integer val = (Integer) another;
				Integer assignVal = (Integer) propertyValue;
				return val < assignVal;
			} else {
				BigDecimal val = (BigDecimal) another;
				BigDecimal assignVal = (BigDecimal) propertyValue;
				return val.compareTo(assignVal) < 0;
			}
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

}
