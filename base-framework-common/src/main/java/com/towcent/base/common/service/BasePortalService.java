package com.towcent.base.common.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.towcent.base.common.constants.BaseConstant;
import com.towcent.base.common.exception.RpcException;
import com.towcent.base.common.page.SimplePageDto;
import com.towcent.base.common.vo.BaseCommonParam;
import com.towcent.base.common.vo.ResultVo;

/**
 * 公共处理方法
 * 
 * @author huangtao
 * @date 2015年7月8日 下午12:01:01
 * @version 0.1.0 
 */
public abstract class BasePortalService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected static final String DATETIME = "yyyy-MM-dd HH:mm";
	
	protected static final String SUCESS_MSG = "操作成功";
	
	protected static final String ERROR_MSG = "操作失败";
	
	/**
	 * 校验对象
	 * @param dto 校验对象
	 * @param resultVo 返回结果
	 * @throws RpcException
	 */
	protected boolean validationObj(Object dto, ResultVo resultVo) {
		if (null == dto) {
			resultVo.setCode(BaseConstant.E_003);
			resultVo.setErrorMessage("缺少必要参数");
			return false;
		}
		Map<String, String> resultMap = Maps.newHashMap();
		try {
			ValidatorFactory factory = Validation
					.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();

			Set<ConstraintViolation<Object>> constraintViolations = validator
					.validate(dto);
			for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
				resultMap.put(constraintViolation.getPropertyPath() + "",
						constraintViolation.getMessage());
			}
		} catch (Exception e) {
			String message = "Object Validation Exception!";
			logger.error(message, e);
			resultVo.setCode(BaseConstant.E_001);
			resultVo.setErrorMessage(message);
			return false;
		}
		if (!CollectionUtils.isEmpty(resultMap)) {
			String message = "Validation Result : " + resultMap.toString();
			logger.error(message);
			resultVo.setCode(BaseConstant.E_003);
			resultVo.setErrorMessage(message);
			return false;
		}
		return true;
	}
	
	protected boolean validationList(List<?> list, ResultVo resultVo) {
		if (CollectionUtils.isEmpty(list)) {
			resultVo.setCode(BaseConstant.E_003);
			resultVo.setErrorMessage("缺少必要参数");
			return false;
		}
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
			resultVo.setCode(BaseConstant.E_001);
			resultVo.setErrorMessage(message);
			return false;
		}
		if (!CollectionUtils.isEmpty(resultMap)) {
			String message = "Validation Result : " + resultMap.toString();
			logger.error(message);
			resultVo.setCode(BaseConstant.E_003);
			resultVo.setErrorMessage(message);
			return false;
		}
		return true;
	}
	
	/**
	 * 组装分页对象
	 * @param baseParam 
	 * @return
	 */
	protected SimplePageDto buildPage(BaseCommonParam baseParam) {
		SimplePageDto pageDto = new SimplePageDto();
		pageDto.setPageNo(baseParam.getPageNo());
		pageDto.setPageSize(baseParam.getPageSize());
		return pageDto;
	}
	
	/**
	 * 
	 * @param vo
	 * @param code
	 * @param message
	 * @return
	 */
	protected ResultVo assemblyVo(ResultVo vo, String code, String message) {
		vo.setCode(code);
		vo.setErrorMessage(message);
		return vo;
	}
}
