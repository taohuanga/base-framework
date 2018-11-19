package com.towcent.base.common.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WechatUtils {
	/** 
     *  
     * 根据文件id下载文件 
     * @param mediaId 
     *            媒体id 
     * @throws Exception 
     */  
  
    public static InputStream getInputStream(String accessToken, String mediaId) {  
    	InputStream is = null;  
        String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="  
                + accessToken + "&media_id=" + mediaId;  
        try {  
        	URL urlGet = new URL(url);  
            HttpURLConnection http = (HttpURLConnection) urlGet  
                    .openConnection();  
            http.setRequestMethod("GET"); // 必须是get方式请求  
            http.setRequestProperty("Content-Type",  
                    "application/x-www-form-urlencoded");  
            http.setDoOutput(true);  
            http.setDoInput(true);  
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒  
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒  
            http.connect();  
            // 获取文件转化为byte流  
            is = http.getInputStream();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return is;  
  
    }  
}
