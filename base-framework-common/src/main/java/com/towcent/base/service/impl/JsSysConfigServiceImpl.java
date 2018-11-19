package com.towcent.base.service.impl;

import static com.towcent.base.common.utils.BaseCacheKey.SYS_PROPERTY_KEY;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.model.JsSysConfig;
import com.towcent.base.common.redis.RedisTemplateExt;
import com.towcent.base.common.utils.StringUtils;
import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.dal.db.JsSysConfigMapper;
import com.towcent.base.service.JsSysConfigService;

/**
 * 
 * @author huangtao
 * @date 2018-10-31 11:31:24
 * @version 1.0
 * @copyright facegarden.com
 */
@Service("jsSysConfigServiceImpl")
public class JsSysConfigServiceImpl extends BaseCrudServiceImpl implements JsSysConfigService {

    @Resource
    private JsSysConfigMapper jsSysConfigMapper;
    @Resource
    private RedisTemplateExt<String, Object> redisTemplateExt;
    
    @Override
    public CrudMapper init() {
        return jsSysConfigMapper;
    }

    @Override
    public JsSysConfig getSysPropertyByKey(Integer merchantId, String key) throws ServiceException {
    	// Redis缓存
    	String hashKey = merchantId + "_" + key;
    	JsSysConfig property = (JsSysConfig) redisTemplateExt.hGet(SYS_PROPERTY_KEY, hashKey);
    	if (null == property) {
    		Map<String, Object> params = Maps.newHashMap();
        	params.put("configKey", key);
        	params.put("merchantId", merchantId);
        	List<JsSysConfig> list = this.findByBiz(params);
        	property = CollectionUtils.isEmpty(list) ? null : list.get(0);
        	if (null != property) {
        		redisTemplateExt.hSet(SYS_PROPERTY_KEY, hashKey, property);
        	}
    	}
    	return property;
    }
    
    @Override
    public String getSysPropertyToString(Integer merchantId, String key) throws ServiceException {
    	JsSysConfig property = this.getSysPropertyByKey(merchantId, key);
    	return null == property ? null : property.getConfigValue();
    }
    
    @Override
    public Integer getSysPropertyToInt(Integer merchantId, String key) throws ServiceException {
    	JsSysConfig property = this.getSysPropertyByKey(merchantId, key);
    	if (null == property) {
    		return null;
    	}
    	return Integer.valueOf(property.getConfigValue());
    }
    
    @Override
    public Double getSysPropertyToDouble(Integer merchantId, String key) throws ServiceException {
    	JsSysConfig property = this.getSysPropertyByKey(merchantId, key);
    	if (null == property) {
    		return null;
    	}
    	return Double.valueOf(property.getConfigValue());	
    }
    
    @Override
    public Boolean getSysPropertyToBoolean(Integer merchantId, String key) throws ServiceException {
    	JsSysConfig property = this.getSysPropertyByKey(merchantId, key);
    	if (null == property) {
    		return null;
    	}
    	return StringUtils.endsWithIgnoreCase("true", property.getConfigValue());	
    }
    
    @Override
    public BigDecimal getSysPropertyToBigDecimal(Integer merchantId, String key) throws ServiceException {
    	JsSysConfig property = this.getSysPropertyByKey(merchantId, key);
    	if (null == property) {
    		return null;
    	}
    	return new BigDecimal(property.getConfigValue());
    }
}