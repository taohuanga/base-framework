package com.towcent.base.service;

import com.google.common.collect.Maps;
import com.towcent.base.common.exception.RpcException;
import com.towcent.base.common.utils.IdGen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class BaseService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected static final String TMPDIR = System.getProperty("java.io.tmpdir");
	
	/**
	 * 校验对象 
	 * @param dto 
	 * @throws RpcException 
	 */
	protected void validationObj(Object dto) throws RpcException {
		Map<String, String> resultMap = Maps.newHashMap();
		try {
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			
			Set<ConstraintViolation<Object>> constraintViolations = validator.validate(dto);
			for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
				resultMap.put(constraintViolation.getPropertyPath() + "", constraintViolation.getMessage());
			}
		} catch (Exception e) {
			String message = "Object Validation Exception!";
			logger.error(message, e);
			throw new RpcException(message, e);
		}
		if (!CollectionUtils.isEmpty(resultMap)) {
			String message = "Validation Result : " + resultMap.toString();
			logger.error(message);
			throw new RpcException("", message);
		}
	}
	
	protected void validationList(List<?> list) throws RpcException {
		Map<String, String> resultMap = Maps.newHashMap();
		
		try {
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			
			int i = 0;
			for (Object dto : list) {
				++i;
				Set<ConstraintViolation<Object>> constraintViolations = validator.validate(dto);
				for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
					resultMap.put(constraintViolation.getPropertyPath() + "_" + i, constraintViolation.getMessage());
				}
			}
		} catch (Exception e) {
			String message = "Object Validation Exception!";
			logger.error(message, e);
			throw new RpcException(message, e);
		}
		if (!CollectionUtils.isEmpty(resultMap)) {
			String message = "Validation Result : " + resultMap.toString();
			logger.error(message);
			throw new RpcException("", message);
		}
	}
	
	/**
	 * 构造一个Map参数
	 * @param key 参数key
	 * @param value 参数值
	 * @return
	 */
	protected Map<String, Object> buildParamMap(String key, Object value) {
		Map<String, Object> map = Maps.newHashMap();
		map.put(key, value);
		return map;
	}
	
	/**
	 * 格式化字符串
	 * @param pattern
	 * @param arguments
	 * @return
	 */
	protected String format(String pattern, Object ... arguments) {
		return MessageFormat.format(pattern, arguments);
	}
	
	/**
	 * 获取服务本地临时目录
	 * @return
	 */
	protected String getTempPath() {
		StringBuilder tempPath = new StringBuilder(TMPDIR);
		tempPath.append(File.separator);
		tempPath.append(IdGen.uuid()).append(File.separator);
		this.mkdir(tempPath.toString());
		return tempPath.toString();
	}
	
	protected void mkdir(String directory) {
		File file = new File(directory);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	
	public static void main(String[] args) throws RpcException {
		
	}
}
