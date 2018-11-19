/*
 * 文 件 名:  BeanMapUtils.java
 * 版   权: Copyright www.face-garden.com Corporation 2013 版权所有
 * 描     述:  <描述>
 * 修 改 人:  shiwei
 * 修改时间:  2013-7-27
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package com.towcent.base.common.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  shiwei
 * @version  [版本号, 2013-7-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BeanMapUtils
{
	public static Logger logger = LoggerFactory.getLogger(BeanMapUtils.class);

    private static final String FORMAT_0 = "yyyy-MM-dd HH:mm:ss";

    private static final String FORMAT_1 = "yyyy-MM-dd";

    private static final String FORMAT_2 = "HH:mm:ss";

    @SuppressWarnings("rawtypes")
    public static Object getMap2Bean(Map map, Class<?> beanClass)
    {
        Object inst = null;
        try
        {
            Constructor<?> constructor1 = beanClass.getConstructor();
            inst = constructor1.newInstance();
            
            for(; beanClass != Object.class ; beanClass = beanClass.getSuperclass()) 
            { 

            for (Field field : beanClass.getDeclaredFields())
            {
                if (field.getName().equals("serialVersionUID"))
                {
                    continue;
                }
                try
                {
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), inst.getClass());
                    Method method = pd.getWriteMethod();
                    if (null != map.get(field.getName()))
                    {
                        if ("Long".equals(field.getType().getSimpleName())){
                        	if(map.get(field.getName()).toString().indexOf(".0") != -1){
                        		method.invoke(inst, StringUtils.isEmpty(map.get(field.getName()).toString()) ? null
                                        : (long)Double.parseDouble(map.get(field.getName()).toString()));
                        	}else{
                        		 method.invoke(
                                         inst,
                                         StringUtils.isEmpty(map.get(field.getName()).toString()) ? null : Long
                                                 .parseLong(map.get(field.getName()).toString()));
                        	}
                        }
                        else if ("Date".equals(field.getType().getSimpleName()))
                        {
                            if ("java.util.Date".equals(field.getType())) {
                                method.invoke(inst, StringUtils.isEmpty(map.get(field.getName()).toString()) ? null
                                        : reverse2Date(map.get(field.getName()).toString()));
                            } else {
                                method.invoke(inst, StringUtils.isEmpty(map.get(field.getName()).toString()) ? null
                                        : reverse2SqlDate(map.get(field.getName()).toString()));
                            }
                        }
                        else if ("Integer".equals(field.getType().getSimpleName())
                                || "int".equals(field.getType().getSimpleName())){
                        	if(map.get(field.getName()).toString().indexOf(".0") != -1){
                        		method.invoke(inst, StringUtils.isEmpty(map.get(field.getName()).toString()) ? null
                                        : (int)Double.parseDouble(map.get(field.getName()).toString()));
                        	}else{
                        		 method.invoke(inst, StringUtils.isEmpty(map.get(field.getName()).toString()) ? null
                                         : Integer.parseInt(map.get(field.getName()).toString()));
                        	}
                        }
                        else if ("Double".equals(field.getType().getSimpleName())) {
                            method.invoke(inst, StringUtils.isEmpty(map.get(field.getName()).toString()) ? null
                                    : Double.parseDouble(map.get(field.getName()).toString()));
                        } else if ("BigDecimal".equals(field.getType().getSimpleName())
                                || "int".equals(field.getType().getSimpleName())) {
                            method.invoke(inst, StringUtils.isEmpty(map.get(field.getName()).toString()) ? null
                                    : new BigDecimal(map.get(field.getName()).toString()));
                        } else if ("List".equals(field.getType().getSimpleName())) {
                            method.invoke(inst,
                                    null == map.get(field.getName()) ? null : (List) map.get(field.getName()));
                        }  else if ("Map".equals(field.getType().getSimpleName())) {
                                method.invoke(inst,
                                        null == map.get(field.getName()) ? null : (Map) map.get(field.getName()));
                        }   else {
                            method.invoke(
                                    inst,
                                    StringUtils.isEmpty(map.get(field.getName()).toString()) ? null : map.get(field
                                            .getName()));
                        }
                    }
                }
                catch (Exception e)
                {
                    logger.error("", e);
                }
            }
            }
        }
        catch (Exception e)
        {
            logger.error("", e);
        }
        return inst;
    }

    @SuppressWarnings(
    { "unchecked", "rawtypes" })
    public static Map getBean2Map(Object beanObj)
    {
        Map map = new HashMap();
        if (null == beanObj) {
            return map;
        }
        try
        {
            Class<?> classType = beanObj.getClass();
            
            for(; classType != Object.class ; classType = classType.getSuperclass()) {  
                try {  
                    for (Field field : classType.getDeclaredFields() )
                    {
                        if (field.getName().equals("serialVersionUID"))
                        {
                            continue;
                        }
                        try
                        {
                            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), beanObj.getClass());
                            Method method = pd.getReadMethod();
                            Object value = method.invoke(beanObj);
                            if (null != value)
                            {
                                map.put(field.getName(), value);
                            }
                            else
                            {
                                if ("Long".equals(field.getType().getSimpleName())
                                        || "Integer".equals(field.getType().getSimpleName()))
                                {
                                    map.put(field.getName(), -1);
                                }
                                else
                                {
                                    map.put(field.getName(), "");
                                }
                            }
                        }
                        catch (Exception e)
                        {
                            logger.error("", e);
                        }
                    }
                    
                    
                } catch (Exception e) {  
                    //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。  
                    //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了  
                	logger.error("", e);
                }   
            } 

        }
        catch (Exception e)
        {
            logger.error("", e);
        }
        return map;
    }

    public static Date reverse2Date(String date)
    {

        SimpleDateFormat simple = null;

        switch (date.trim().length())
        {

            case 19:// 日期+时间
                simple = new SimpleDateFormat(FORMAT_0);

                break;

            case 10:// 仅日期

                simple = new SimpleDateFormat(FORMAT_1);

                break;

            case 8:// 仅时间
                simple = new SimpleDateFormat(FORMAT_2);

                break;

            default:

                break;

        }

        try
        {

            return simple.parse(date.trim());

        }
        catch (ParseException e)
        {

            e.printStackTrace();

        }

        return null;

    }

    public static java.sql.Date reverse2SqlDate(String date)
    {

        SimpleDateFormat simple = null;

        switch (date.trim().length())
        {

            case 19:// 日期+时间
                simple = new SimpleDateFormat(FORMAT_0);

                break;

            case 10:// 仅日期

                simple = new SimpleDateFormat(FORMAT_1);

                break;

            case 8:// 仅时间
                simple = new SimpleDateFormat(FORMAT_2);

                break;

            default:

                break;

        }

        try
        {

            java.sql.Date sqldate = new java.sql.Date(simple.parse(date.trim()).getTime());
            return sqldate;

        }
        catch (ParseException e)
        {

            e.printStackTrace();

        }

        return null;

    }
}
