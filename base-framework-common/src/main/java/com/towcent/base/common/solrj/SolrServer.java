/*
 * 文 件 名:  SolrServer.java
 * 版   权: Copyright www.face-garden.com Corporation 2013 版权所有
 * 描     述:  <描述>
 * 修 改 人:  shiwei
 * 修改时间:  2013-7-27
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package com.towcent.base.common.solrj;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  shiwei
 * @version  [版本号, 2013-7-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SolrServer implements InitializingBean
{

	public static Logger logger = LoggerFactory.getLogger(SolrServer.class);

    private String                      solrUrl                      = "http://127.0.0.1:8389/solr/";

    private int                         maxTotalConnections          = 100;

    private int                         soTimeout                    = 10000;

    private int                         connectionTimeout            = 5000;

    private int                         defaultMaxConnectionsPerHost = 100;

    private String                      followRedirects              = "false";

    private String                      allowCompression             = "true";

    private int                         maxRetries                   = 1;

    private HttpSolrClient              solrClient                   = null;

    private Map<String, HttpSolrClient> solrClientMap                = new HashMap<String, HttpSolrClient>();

    public String getSolrUrl()
    {
        return solrUrl;
    }

    public void setSolrUrl(String solrUrl)
    {
        this.solrUrl = solrUrl;
    }

    public int getMaxTotalConnections()
    {
        return maxTotalConnections;
    }

    public void setMaxTotalConnections(int maxTotalConnections)
    {
        this.maxTotalConnections = maxTotalConnections;
    }

    public int getSoTimeout()
    {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout)
    {
        this.soTimeout = soTimeout;
    }

    public int getConnectionTimeout()
    {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout)
    {
        this.connectionTimeout = connectionTimeout;
    }

    public int getDefaultMaxConnectionsPerHost()
    {
        return defaultMaxConnectionsPerHost;
    }

    public void setDefaultMaxConnectionsPerHost(int defaultMaxConnectionsPerHost)
    {
        this.defaultMaxConnectionsPerHost = defaultMaxConnectionsPerHost;
    }

    public String getFollowRedirects()
    {
        return followRedirects;
    }

    public void setFollowRedirects(String followRedirects)
    {
        this.followRedirects = followRedirects;
    }

    public String getAllowCompression()
    {
        return allowCompression;
    }

    public void setAllowCompression(String allowCompression)
    {
        this.allowCompression = allowCompression;
    }

    public int getMaxRetries()
    {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries)
    {
        this.maxRetries = maxRetries;
    }

    public HttpSolrClient getSolrServer(String solrAlias)
    {
        if (logger.isDebugEnabled()) {
            logger.debug("solr connection alias is " + solrAlias + "\n");
        }
        return solrClientMap.get(StringUtils.isEmpty(solrAlias) ? "default" : solrAlias);
    }

    public void setSolrServer(HttpSolrClient solrServer)
    {
        this.solrClient = solrServer;
    }

    @SuppressWarnings("deprecation")
	@Override
    public void afterPropertiesSet() throws Exception
    {
        try
        {
            // 判断是否存在多个solr服务需要连接
            if (solrUrl.indexOf(",") != -1)
            {
                // 拆分solr服务连接
                String[] solrServiceList = solrUrl.split(";");
                String[] solrServices = null;
                for (String solrUrls : solrServiceList)
                {
                    if (!StringUtils.isEmpty(solrUrls))
                    {
                        solrServices = solrUrls.split(",");
                        if (solrServices.length == 2 && !StringUtils.isEmpty(solrServices[0])
                                && !StringUtils.isEmpty(solrServices[1]))
                        {
                            logger.info("solr connection url is " + solrServices[1] + "\n");

                            solrClient = new HttpSolrClient(solrServices[1]);
                            solrClient.setMaxTotalConnections(maxTotalConnections);
                            solrClient.setSoTimeout(soTimeout); // socket read timeout
                            solrClient.setConnectionTimeout(connectionTimeout);
                            solrClient.setDefaultMaxConnectionsPerHost(defaultMaxConnectionsPerHost);
                            solrClient.setFollowRedirects(Boolean.getBoolean(followRedirects));
                            solrClient.setAllowCompression(Boolean.getBoolean(allowCompression));
                            solrClient.setMaxRetries(maxRetries);// 最大重试次数

                            solrClientMap.put(solrServices[0], solrClient);
                        }
                    }
                }
            }
            else
            {
                logger.info("solr connection url is " + solrUrl + "\n");
                solrClient = new HttpSolrClient(solrUrl);
                solrClient.setMaxTotalConnections(maxTotalConnections);
                solrClient.setSoTimeout(soTimeout); // socket read timeout
                solrClient.setConnectionTimeout(connectionTimeout);
                solrClient.setDefaultMaxConnectionsPerHost(defaultMaxConnectionsPerHost);
                solrClient.setFollowRedirects(Boolean.getBoolean(followRedirects));
                solrClient.setAllowCompression(Boolean.getBoolean(allowCompression));
                solrClient.setMaxRetries(maxRetries);// 最大重试次数
                solrClientMap.put("default", solrClient);
            }
        }
        catch (Exception e)
        {
            logger.error("solr connection has exception   \n" + e);
        }
    }
}
