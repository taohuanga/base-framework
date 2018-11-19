package com.towcent.base.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.model.JsSysArea;
import com.towcent.base.common.redis.RedisTemplateExt;
import com.towcent.base.common.utils.BaseCacheKey;
import com.towcent.base.common.utils.StringUtils;
import com.towcent.base.dal.db.CrudMapper;
import com.towcent.base.dal.db.JsSysAreaMapper;
import com.towcent.base.service.JsSysAreaService;

/**
 * 
 * @author huangtao
 * @date 2018-10-31 15:58:33
 * @version 1.0
 * @copyright facegarden.com
 */
@Service("jsSysAreaServiceImpl")
public class JsSysAreaServiceImpl extends BaseCrudServiceImpl implements JsSysAreaService {

    @Resource
    private JsSysAreaMapper jsSysAreaMapper;
    @Resource
    private RedisTemplateExt<String, Object> redisTemplateExt;
    
    @Override
    public CrudMapper init() {
        return jsSysAreaMapper;
    }

    @Override
	public String getAreaDesc(String provinceCode, String cityCode, String areaCode,String spliter) throws ServiceException {
		String desc = "";
		if(provinceCode != null){
			desc += getAreaNameById(provinceCode);
		}
		if(cityCode != null){
			desc = desc + spliter + getAreaNameById(cityCode);
		}
		if(areaCode != null){
			desc = desc + spliter + getAreaNameById(areaCode);
		}
		return desc;
	}

	@Override
	public String getAreaNameById(String code) throws ServiceException {
		JsSysArea area = this.getAreaById(code);
		if (area != null) {
			return area.getAreaName();
		}
		return "";
	}
	
	@Override
	public JsSysArea getAreaById(String code) throws ServiceException {
		if(code == null) {
			return null;
		}
		String key = BaseCacheKey.getAreaDtlKey(code);
		if (redisTemplateExt.exists(key)) {
			return (JsSysArea) redisTemplateExt.get(key);
		} else {
			JsSysArea area = findByKeyValSingle("areaCode", code);
			redisTemplateExt.set(key, area);
			return area;
		}	
	}
	
	@Override
	public List<JsSysArea> getParentAreasById(String code) throws ServiceException {
		List<JsSysArea> list = Lists.newArrayList();
		String[] codes = this.getParentIdsById(code);
		if (ArrayUtils.isNotEmpty(codes)) {
			for (String areaCode : codes) {
				list.add(this.getAreaById(areaCode));
			}
		}
		return list;
	}
	
	@Override
	public String getAreaDescById(String code, String spilter) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		List<JsSysArea> list = getParentAreasById(code);
		if (!CollectionUtils.isEmpty(list)) {
			for (JsSysArea sysArea : list) {
				sb.append(sysArea.getAreaName());
				sb.append(spilter);
			}
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
			
	}
	
	@Override
	public String[] getParentIdsById(String code) throws ServiceException {
		JsSysArea area = this.getAreaById(code);
		String parentIds = StringUtils.substringAfter(area.getParentCodes(), ",");
		String[] as = StringUtils.split(parentIds, ",");
		String[] result = new String[as.length];
		for (int i = 0; i < as.length; i++) {
			result[i] = as[i];
		}
		return result;
	}
}