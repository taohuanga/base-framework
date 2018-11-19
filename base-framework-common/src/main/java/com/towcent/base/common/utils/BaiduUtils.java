package com.towcent.base.common.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 百度地图工具类
 * @author Administrator
 *
 */
public class BaiduUtils {
	 // 初始化连接邮件服务器的会话信息  
    private static Properties props = null; 
    private static String ak = "";
	static {  
    	PropertiesLoader propertiesLoader = new PropertiesLoader("application.properties");
        props = propertiesLoader.getProperties();  
        ak = props.getProperty("baidu.ak");
    } 
	
	public static void main(String[] args) throws IOException {
		String start = "深圳市福田区联合广场";  
        String end = "深圳市上塘商业大厦";  
        String end2 = "深圳市清湖地铁站";  
  
        String startLonLat = getLonLat(start);  
        String endLonLat = getLonLat(end);  
        String endLonLat2 = getLonLat(end2);  
  
        System.out.println(startLonLat);  
        System.out.println(endLonLat);  
        double dis = getDistance(startLonLat,endLonLat+"|"+endLonLat2);  
        System.out.println(dis);  
	}
	
	/**
	 * 地址转换成坐标
	 * @param address
	 * @return
	 * @throws IOException
	 */
	public static String getLonLat(String address) throws IOException{  
        //返回输入地址address的经纬度信息, 格式是 经度,纬度  
		String lonlat = "";
        String queryUrl = "http://api.map.baidu.com/geocoder/v2/?address="+address+"&output=json&ak="+ak;  
        String conent = BaseHttpClient.httpGet(queryUrl);
        JSONObject jo = JSONObject.parseObject(conent);
        String status = jo.getString("status");
        if(StringUtils.isNotEmpty(status)){
        	if("0".equals(status)){
        		JSONObject location = jo.getJSONObject("result").getJSONObject("location");
        		lonlat = location.getString("lat") + "," + location.getString("lng");
        	}
        }
        return lonlat;  
    }  
	
	/**
	 * 计算最小距离
	 * @param startLonLat 出发点经纬度：40.056878,16.30815|40.063597,116.364973 坐标格式为：lat<纬度>,lng<经度>|lat<纬度>,lng<经度>
	 * @param endLonLat 终点经纬度（同上）
	 * @return
	 * @throws IOException
	 */
	public static Double getDistance(String startLonLat, String endLonLat) throws IOException{  
	 	Double minDistance = Double.valueOf("0");
        //返回起始地startAddr与目的地endAddr之间的距离，单位：米  
        String queryUrl = "http://api.map.baidu.com/routematrix/v2/driving?output=json&origins="+startLonLat+"&destinations="+URLEncoder.encode(endLonLat,"UTF-8")+"&ak="+ak;
        String conent = BaseHttpClient.httpGet(queryUrl);
        JSONObject jo = JSONObject.parseObject(conent);
        String status = jo.getString("status");
        List<BigDecimal> dis = new ArrayList<BigDecimal>();
        if(StringUtils.isNotEmpty(status)){
        	if("0".equals(status)){
        		JSONArray distances = jo.getJSONArray("result");
        		for (int i = 0; i < distances.size(); i++) {
        			JSONObject location = distances.getJSONObject(i);
        			dis.add(BigDecimal.valueOf(Double.parseDouble(location.getJSONObject("distance").getString("value"))));
				}
        	}
        }
        minDistance = Collections.min(dis).doubleValue();
        return minDistance;
        
    }  

}
