package com.towcent.base.common.beans;

import org.apache.commons.collections.Predicate;

/**
 * 自定制断言
 * 
 * @author huangtao
 * @date 2014年6月26日 下午2:35:06
 * @version 0.1.0 
 */
public abstract class CustomPredicate implements Predicate {
	
	private boolean trim;
	private boolean ignoreCase;
	
	public boolean isTrim() {
		return trim;
	}

	public void setTrim(boolean trim) {
		this.trim = trim;
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}
	
	public boolean equals(Object value, Object another) {
		if (value == null || another == null) {
			return false;
		} else if (value == another) {
			return true;
		} else if (String.class.isInstance(value) && String.class.isInstance(another)) {
			String s1 = (String) value;
			String s2 = (String) another;
			s1 = trim ? s1.trim() : s1;
			s2 = trim ? s2.trim() : s2;
			return ignoreCase ? s1.equalsIgnoreCase(s2) : s1.equals(s2);
		} else {
			return value.equals(another);
		}
	}
	
	public enum CompareType {
		INTEGER, BIGDECIMAL;
	}
}
