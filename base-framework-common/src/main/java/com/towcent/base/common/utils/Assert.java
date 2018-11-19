package com.towcent.base.common.utils;

import com.towcent.base.common.exception.RpcException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 
 * @author huangtao
 *
 */
public abstract class Assert {
	
	/**
	 * 布尔值为真断言, 布尔值为false时抛出RpcException
	 * @param expression
	 * @param message
	 * @throws RpcException
	 */
	public static void isTrue(boolean expression, String message) throws RpcException {
		if (!expression) {
			throw new RpcException("", message);
		}
	}
	
	/**
	 * 对象不为空断言, 对象为空时抛出RpcException
	 * @param object
	 * @param message
	 * @throws RpcException
	 */
	public static void notNull(Object object, String message) throws RpcException {
		if (null == object) {
			throw new RpcException("", message);
		}
	}
	
	/**
	 * 字符串不为空断言, 字符串为空时抛出RpcException
	 * @param str
	 * @param message
	 * @throws RpcException
	 */
	public static void isNotEmpty(String str, String message) throws RpcException {
		if (StringUtils.isEmpty(str)) {
			throw new RpcException("", message);
		}
	}
	
	/**
	 * 集合不为空断言, 集合为空时抛出RpcException
	 * @param list
	 * @param message
	 * @throws RpcException
	 */
	public static void isNotEmpty(List<?> list, String message) throws RpcException {
		if (CollectionUtils.isEmpty(list)) {
			throw new RpcException("", message);
		}
	}
	
	/**
	 * 数组不为空断言, 数组为空时抛出RpcException
	 * @param array
	 * @param message
	 * @throws RpcException
	 */
	public static void isNotEmpty(Object[] array, String message) throws RpcException {
		if (ArrayUtils.isEmpty(array)) {
			throw new RpcException("", message);
		}
	}
}
