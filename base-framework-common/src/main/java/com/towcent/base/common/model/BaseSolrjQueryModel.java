/*
 * 文 件 名:  BaseSolrjQueryModel.java
 * 版   权: Copyright www.face-garden.com Corporation 2013 版权所有
 * 描     述:  <描述>
 * 修 改 人:  shiwei
 * 修改时间:  2013-7-27
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package com.towcent.base.common.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  shiwei
 * @version  [版本号, 2013-7-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BaseSolrjQueryModel implements Serializable
{
    /**
     * 
     */
    private static final long         serialVersionUID = 1L;

    private String                    queryStr;                // 查询字符串

    private String                    sortField;               // 排序

    private int                       order            = -1;   // 0 desc 1 asc

    private List<Map<String, Object>> filterQuery      = null; // 过滤查询

    private int                       isHighlighting   = 1;    // 1高亮 0不高亮

    private String                    fieldToHighlight = "";   // 高亮的字段

    private boolean                   isFacet          = false; // 是否导航查询

    private List<String>              facetFieldList   = null; // 是否导航查询

    private int                       rows             = 10;

    private int                       start            = 0;

    private int                       facetMinCount    = 0;

    private int                       facetLimit       = 8;

    private String                    queryF;                  // 权重

    // 用来区分获取到的solr服务连接
    private String                    solrAlias;

    public String getSolrAlias()
    {
        return solrAlias;
    }

    public void setSolrAlias(String solrAlias)
    {
        this.solrAlias = solrAlias;
    }

    public String getQueryF()
    {
        return queryF;
    }

    public void setQueryF(String queryF)
    {
        this.queryF = queryF;
    }

    public int getFacetMinCount()
    {
        return facetMinCount;
    }

    public void setFacetMinCount(int facetMinCount)
    {
        this.facetMinCount = facetMinCount;
    }

    public int getFacetLimit()
    {
        return facetLimit;
    }

    public void setFacetLimit(int facetLimit)
    {
        this.facetLimit = facetLimit;
    }

    public String getQueryStr()
    {
        return queryStr;
    }

    public void setQueryStr(String queryStr)
    {
        this.queryStr = queryStr;
    }

    public String getSortField()
    {
        return sortField;
    }

    public void setSortField(String sortField)
    {
        this.sortField = sortField;
    }

    public int getOrder()
    {
        return order;
    }

    public void setOrder(int order)
    {
        this.order = order;
    }

    public List<Map<String, Object>> getFilterQuery()
    {
        return filterQuery;
    }

    public void setFilterQuery(List<Map<String, Object>> filterQuery)
    {
        this.filterQuery = filterQuery;
    }

    public int getIsHighlighting()
    {
        return isHighlighting;
    }

    public void setIsHighlighting(int isHighlighting)
    {
        this.isHighlighting = isHighlighting;
    }

    public String getFieldToHighlight()
    {
        return fieldToHighlight;
    }

    public void setFieldToHighlight(String fieldToHighlight)
    {
        this.fieldToHighlight = fieldToHighlight;
    }

    public boolean isFacet()
    {
        return isFacet;
    }

    public void setFacet(boolean isFacet)
    {
        this.isFacet = isFacet;
    }

    public List<String> getFacetFieldList()
    {
        return facetFieldList;
    }

    public void setFacetFieldList(List<String> facetFieldList)
    {
        this.facetFieldList = facetFieldList;
    }

    public int getRows()
    {
        return rows;
    }

    public void setRows(int rows)
    {
        this.rows = rows;
    }

    public int getStart()
    {
        return start;
    }

    public void setStart(int start)
    {
        this.start = start;
    }

    public String toString()
    {
        try
        {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
        catch (Exception e)
        {
        }
        return super.toString();
    }

    @Override
    public boolean equals(Object arg0)
    {
        StringBuffer methodName = new StringBuffer();
        Field[] srcfields = this.getClass().getDeclaredFields();
        Method method = null;
        Method method1 = null;
        for (Field field : srcfields)
        {
            if (field.getName().equals("serialVersionUID"))
            {
                continue;
            }
            try
            {
                methodName.delete(0, methodName.length());
                methodName.append("get");
                methodName.append(field.getName().substring(0, 1).toUpperCase());
                methodName.append(field.getName().substring(1));
                method = this.getClass().getMethod(methodName.toString());
                method1 = arg0.getClass().getMethod(methodName.toString());

                if (!method.getClass().equals(method1.getClass())) {
                    return false;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return true;
    }
}
