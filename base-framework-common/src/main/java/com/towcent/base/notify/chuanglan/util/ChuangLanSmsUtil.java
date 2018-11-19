package com.towcent.base.notify.chuanglan.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ClassName: ChuangLanSmsUtil 
 * @Description: 创蓝短信工具类
 *
 * @author huangtao
 * @date 2018年8月15日 下午8:04:19
 * @version 1.0.0
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class ChuangLanSmsUtil {
    public static String sendSmsByPost(String path, String postContent) {
        URL url = null;

        try {
            url = new URL(path);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);

            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type",
                "application/json");

            httpURLConnection.connect();

            OutputStream os = httpURLConnection.getOutputStream();
            os.write(postContent.getBytes("UTF-8"));
            os.flush();

            StringBuilder sb = new StringBuilder();
            int httpRspCode = httpURLConnection.getResponseCode();

            if (httpRspCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                            httpURLConnection.getInputStream(), "utf-8"));
                String line = null;

                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                br.close();

                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
