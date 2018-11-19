/*
 * 文 件 名:  SolrManager.java
 * 版   权: Copyright www.face-garden.com Corporation 2013 版权所有
 * 描     述:  <描述>
 * 修 改 人:  shiwei
 * 修改时间:  2013-7-27
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package com.towcent.base.common.solrj;

import java.util.List;
import java.util.Map;

import com.towcent.base.common.model.BaseSolrjModel;
import com.towcent.base.common.model.BaseSolrjQueryModel;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author shiwei
 * @version [版本号, 2013-7-27]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface SolrManager {
	public Map<String, Object> add(BaseSolrjModel baseModel) throws Exception;

	public Map<String, Object> update(BaseSolrjModel baseModel)
			throws Exception;

	public Map<String, Object> deleteById(String solrAlias, String id)
			throws Exception;

	public Map<String, Object> deleteByIds(String solrAlias, List<String> ids)
			throws Exception;

	public Map<String, Object> deleteByQuery(String solrAlias, String query)
			throws Exception;

	public Map<Object, Object> query(BaseSolrjQueryModel baseQueryModel,
			Class<?> classType) throws Exception;
}
