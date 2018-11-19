/*
 * 文 件 名:  SolrManagerImpl.java
 * 版   权: Copyright www.face-garden.com Corporation 2013 版权所有
 * 描     述:  <描述>
 * 修 改 人:  shiwei
 * 修改时间:  2013-7-27
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package com.towcent.base.common.solrj.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.towcent.base.common.model.BaseSolrjModel;
import com.towcent.base.common.model.BaseSolrjQueryModel;
import com.towcent.base.common.solrj.SolrManager;
import com.towcent.base.common.solrj.SolrServer;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author shiwei
 * @version [版本号, 2013-7-27]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SolrManagerImpl implements SolrManager {

	public static Logger logger = LoggerFactory
			.getLogger(SolrManagerImpl.class);

	@Resource
	private SolrServer SolrServer;

	@Override
	public Map<String, Object> add(BaseSolrjModel baseModel) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("add BaseModel --" + baseModel.toString());
		}

		Map<String, Object> rs = new HashMap<String, Object>();
		HttpSolrClient HttpSolrClient = SolrServer.getSolrServer(baseModel
				.getSolrAlias());
		try {
			UpdateResponse updateResponse = HttpSolrClient.addBean(baseModel);
			rs.put("status", updateResponse.getStatus());
			rs.put("QTime", updateResponse.getQTime());
			HttpSolrClient.commit();
		} catch (Exception e) {
			logger.error("", e);
			try {
				HttpSolrClient.rollback();
			} catch (Exception e1) {
				logger.error("", e1);
			}
			throw e;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Result Map --" + rs);
		}
		return rs;
	}

	@Override
	public Map<String, Object> update(BaseSolrjModel baseModel)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("update BaseModel --" + baseModel.toString());
		}

		Map<String, Object> rs = new HashMap<String, Object>();
		HttpSolrClient HttpSolrClient = SolrServer.getSolrServer(baseModel
				.getSolrAlias());
		try {
			UpdateResponse updateResponse = HttpSolrClient.addBean(baseModel);
			rs.put("status", updateResponse.getStatus());
			rs.put("QTime", updateResponse.getQTime());
			HttpSolrClient.commit();
		} catch (Exception e) {
			logger.error("", e);
			try {
				HttpSolrClient.rollback();
			} catch (Exception e1) {
				logger.error("", e1);
			}
			throw e;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Result Map --" + rs);
		}
		return rs;
	}

	@Override
	public Map<String, Object> deleteById(String solrAlias, String id) throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug("deleteById id --" + id);
		}
		Map<String, Object> rs = new HashMap<String, Object>();
		HttpSolrClient HttpSolrClient = SolrServer.getSolrServer(solrAlias);
		try {
			UpdateResponse updateResponse = HttpSolrClient.deleteById(id);
			rs.put("status", updateResponse.getStatus());
			rs.put("QTime", updateResponse.getQTime());
			HttpSolrClient.commit();
		} catch (Exception e) {
			logger.error("", e);
			try {
				HttpSolrClient.rollback();
			} catch (Exception e1) {
				logger.error("", e1);
			}
			throw e;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Result Map --" + rs);
		}
		return rs;
	}

	@Override
	public Map<String, Object> deleteByIds(String solrAlias, List<String> ids) throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug(" deleteByIds ids --" + ids);
		}

		Map<String, Object> rs = new HashMap<String, Object>();
		HttpSolrClient HttpSolrClient = SolrServer.getSolrServer(solrAlias);
		try {
			UpdateResponse updateResponse = HttpSolrClient.deleteById(ids);
			rs.put("status", updateResponse.getStatus());
			rs.put("QTime", updateResponse.getQTime());
			HttpSolrClient.commit();
		} catch (Exception e) {
			logger.error("", e);
			try {
				HttpSolrClient.rollback();
			} catch (Exception e1) {
				logger.error("", e1);
			}
			throw e;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Result Map --" + rs);
		}
		return rs;
	}

	@Override
	public Map<Object, Object> query(BaseSolrjQueryModel baseQueryModel,
			Class<?> classType) throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug("query --" + baseQueryModel);
		}

		Map<Object, Object> rs = new HashMap<Object, Object>();
		try {
			SolrQuery query = new SolrQuery();
			query.setQuery(baseQueryModel.getQueryStr());
			if (baseQueryModel.isFacet()) {
				query.setFacet(baseQueryModel.isFacet());
				query.setFacetMinCount(1);
				query.setFacetLimit(8);
				for (String facetField : baseQueryModel.getFacetFieldList()) {
					query.addFacetField(facetField);
				}
			} else {
				query.setRows(baseQueryModel.getRows());
				query.setStart(baseQueryModel.getStart());
				if (baseQueryModel.getOrder() >= 0) {
					query.setSort(
							baseQueryModel.getSortField(),
							1 == baseQueryModel.getOrder() ? SolrQuery.ORDER.asc
									: SolrQuery.ORDER.desc);
				}

				if (1 == baseQueryModel.getIsHighlighting()) {
					query.setHighlight(true);
					query.addHighlightField(baseQueryModel
							.getFieldToHighlight());
					query.setHighlightSimplePre("<font color='red'>");// 前缀
					query.setHighlightSimplePost("</font>");// 后缀

				}
				if (null != baseQueryModel.getFilterQuery()) {
					for (Map<String, Object> filterField : baseQueryModel
							.getFilterQuery()) {
						if (!StringUtils.isEmpty(filterField.get("id")
								.toString())) {
							query.addFilterQuery(filterField.get("id")
									.toString()
									+ ":"
									+ filterField.get("value"));
						}
					}
				}

			}
			if (null != baseQueryModel.getQueryF()) {
				query.set("qf", baseQueryModel.getQueryF());
			}

			QueryResponse rsp = SolrServer.getSolrServer(
					baseQueryModel.getSolrAlias()).query(query);

			if (!baseQueryModel.isFacet()) {
				List<?> objectList = rsp.getBeans(classType);
				rs.put("row", baseQueryModel.getRows());
				rs.put("start", baseQueryModel.getStart());
				rs.put("queryResult", objectList);
				rs.put("status", rsp.getStatus());
				rs.put("QTime", rsp.getQTime());
				rs.put("count", rsp.getResults().getNumFound());
				rs.put("hightList", rsp.getHighlighting());
			} else {
				rs.put("flist", rsp.getFacetFields());
			}
		} catch (Exception e) {
			logger.error("", e);
			throw e;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Result Map --" + rs);
		}
		return rs;
	}

	@Override
	public Map<String, Object> deleteByQuery(String solrAlias, String query) throws Exception{
		if (logger.isDebugEnabled()) {
			logger.debug("deleteByQuery query --" + query);
		}

		Map<String, Object> rs = new HashMap<String, Object>();
		HttpSolrClient HttpSolrClient = SolrServer.getSolrServer(solrAlias);
		try {
			UpdateResponse updateResponse = HttpSolrClient.deleteByQuery(query);
			rs.put("status", updateResponse.getStatus());
			rs.put("QTime", updateResponse.getQTime());
			HttpSolrClient.commit();
		} catch (Exception e) {
			logger.error("", e);
			try {
				HttpSolrClient.rollback();
			} catch (Exception e1) {
				logger.error("", e1);
			}
			throw e;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Result Map --" + rs);
		}
		return rs;
	}

}
