package com.towcent.base.common.beans;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类属性正则表达式断言
 * 
 * @author huangtao
 * @date 2014年6月26日 下午5:16:29
 * @version 0.1.0 
 */
public class BeanPropertyMatchesPredicate extends CustomPredicate {

	private String propertyName;
	private Pattern propertyValuePattern;
	private boolean isGlobalMatches;
	
	public BeanPropertyMatchesPredicate(String propertyName, Pattern propertyValuePattern) {
		this(propertyName, propertyValuePattern, true);
	}

	public BeanPropertyMatchesPredicate(String propertyName, Pattern propertyValuePattern, boolean isGlobalMatches) {
		this.propertyName = propertyName;
		this.propertyValuePattern = propertyValuePattern;
		this.isGlobalMatches = isGlobalMatches;
	}
	
	/* (non-Javadoc)
	 * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean evaluate(Object object) {
		try {
			String propertyValue = ObjectUtils.toString(PropertyUtils.getProperty(object, propertyName));
			Matcher matcher = propertyValuePattern.matcher(propertyValue);
			return isGlobalMatches ? matcher.matches() : matcher.find();
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

}
