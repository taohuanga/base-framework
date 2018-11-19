/*
 * 文 件 名:  BaseHttpClient.java
 * 版   权: Copyright www.face-garden.com Corporation 2013 版权所有
 * 描     述:  <描述>
 * 修 改 人:  shiwei
 * 修改时间:  2013-7-27
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package com.towcent.base.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author shiwei
 * @version [版本号, 2013-7-27]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@SuppressWarnings("deprecation")
public class BaseHttpClient {
	
	protected static Logger logger = LoggerFactory.getLogger(BaseHttpClient.class);
	
	public static final String UTF8 = "UTF-8";
	
	/**
	 * post方式请求http <功能详细描述>
	 * 
	 * @param url
	 *            请求地址
	 * @param msg
	 *            请求的消息
	 * @param headerMap
	 *            http头信息
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("finally")
	public static String sendHttpMsg(String url, String msg,
			Map<String, String> headerMap) {
		logger.debug("url={}  msg={} headerMap={}.", url, msg, headerMap);

		String cotent = "";
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient base = httpClientBuilder.build();
		try {
			HttpContext context = new BasicHttpContext();
			context.setAttribute(HttpClientContext.COOKIE_STORE,
					new BasicCookieStore());
			HttpPost post = new HttpPost(url);
			
			StringEntity stringEntity = new StringEntity(msg, "UTF-8");
			stringEntity.setContentEncoding("UTF-8");    
			stringEntity.setContentType("application/json");  
			post.setEntity(stringEntity);

			HttpResponse response = base.execute(post, context);
			HttpEntity entity = response.getEntity();
			try {
				if (null != entity) {
					cotent = EntityUtils.toString(entity, "UTF-8");
				}
			} catch (IOException ex) {
				logger.error("", ex);
			}
		} catch (Exception ex) {
			logger.error("", ex);
		} finally {
			try {
				base.close();
			} catch (IOException e) {
				logger.error("", e);
			}
			return cotent;
		}
	}
	
	public static String sendHttpsMsg(String url, String msg,
			Map<String, String> headerMap) {
		logger.debug("url={}  msg={} headerMap={}.", url, msg, headerMap);

		String cotent = "";
		// HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient base = createSSLClientDefault();
		try {
			HttpContext context = new BasicHttpContext();
			context.setAttribute(HttpClientContext.COOKIE_STORE,
					new BasicCookieStore());
			HttpPost post = new HttpPost(url);
			
			StringEntity stringEntity = new StringEntity(msg, "UTF-8");
			stringEntity.setContentEncoding("UTF-8");    
			stringEntity.setContentType("application/json");  
			post.setEntity(stringEntity);

			HttpResponse response = base.execute(post, context);
			HttpEntity entity = response.getEntity();
			try {
				if (null != entity) {
					cotent = EntityUtils.toString(entity, "UTF-8");
				}
			} catch (IOException ex) {
				logger.error("", ex);
			}
		} catch (Exception ex) {
			logger.error("", ex);
		} finally {
			try {
				base.close();
			} catch (IOException e) {
				logger.error("", e);
			}
			return cotent;
		}
	}
	
	@SuppressWarnings("finally")
	public static String sendHttpMsg(String url) {
		logger.debug("url={} ", url);

		String cotent = "";
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient base = httpClientBuilder.build();
		try {
			HttpContext context = new BasicHttpContext();
			context.setAttribute(HttpClientContext.COOKIE_STORE,
					new BasicCookieStore());
			HttpPost post = new HttpPost(url);

			HttpResponse response = base.execute(post, context);
			HttpEntity entity = response.getEntity();
			try {
				if (null != entity) {
					cotent = EntityUtils.toString(entity, "UTF-8");
				}
			} catch (IOException ex) {
				logger.error("", ex);
			}
		} catch (Exception ex) {
			logger.error("", ex);
		} finally {
			try {
				base.close();
			} catch (IOException e) {
				logger.error("", e);
			}
			return cotent;
		}
	}
	
	public static String sendHttpFile(String url, String filePath) {
		String cotent = "";
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient base = httpClientBuilder.build();
		try {
			HttpContext context = new BasicHttpContext();
			context.setAttribute(HttpClientContext.COOKIE_STORE,
					new BasicCookieStore());
			HttpPost post = new HttpPost(url);
			
			File file = new File(filePath);
			FileBody fileBody = new FileBody(file, ContentType.APPLICATION_OCTET_STREAM, file.getName());
			
			//对请求的表单域进行填充  
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setCharset(Charset.forName("utf8"));
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            entityBuilder.addPart("file", fileBody);
            post.setEntity(entityBuilder.build());
            
			HttpResponse response = base.execute(post, context);
			HttpEntity entity = response.getEntity();
			try {
				if (null != entity) {
					cotent = EntityUtils.toString(entity, "UTF-8");
				}
			} catch (IOException ex) {
				logger.error("", ex);
			}
		} catch (Exception ex) {
			logger.error("", ex);
		}
		return cotent;
	}
	
	 /**
	 * 下载文件
	 * @param  url
	 * @param  destFileName   xxx.jpg/xxx.png/xxx.txt
	 * @throws  ClientProtocolException
	 * @throws IOException
	 */
	public static File getFile(String url, String destFileName)
			throws ClientProtocolException, IOException {
		RequestConfig defaultRequestConfig = RequestConfig.custom()
			    .setSocketTimeout(5000)
			    .setConnectTimeout(5000)
			    .setConnectionRequestTimeout(5000)
			    .setStaleConnectionCheckEnabled(true)
			    .build();
		
		// 生成一个httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		httpget.setConfig(defaultRequestConfig);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		InputStream in = entity.getContent();
		File file = new File(destFileName);
		try {
			FileOutputStream fout = new FileOutputStream(file);
			int l = -1;
			byte[] tmp = new byte[1024];
			while ((l = in.read(tmp)) != -1) {
				fout.write(tmp, 0, l);
				// 注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试
			}
			fout.flush();
			fout.close();
		} finally {
			// 关闭低层流。
			in.close();
		}
		httpclient.close();
		return file;
	}
	
	public static OutputStream doGet(String url) throws IOException {
		RequestConfig defaultRequestConfig = RequestConfig.custom()
			    .setSocketTimeout(5000)
			    .setConnectTimeout(5000)
			    .setConnectionRequestTimeout(5000)
			    .setStaleConnectionCheckEnabled(true)
			    .build();
		
		OutputStream os = null;  
		
		// 生成一个httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(url);
			httpget.setConfig(defaultRequestConfig);
			
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null)  
			{  
			    InputStream instream = entity.getContent();  
			    os = new ByteArrayOutputStream();  
			    int temp = 0;  
			    while ((temp = instream.read()) != -1)  
			    {  
			        os.write(temp);  
			    }  
			    os.flush();  
			    os.close();  
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			httpclient.close();
		}
		return os;  
	}
	
	public static String httpGet(String url) throws IOException {
		RequestConfig defaultRequestConfig = RequestConfig.custom()
			    .setSocketTimeout(5000)
			    .setConnectTimeout(5000)
			    .setConnectionRequestTimeout(5000)
			    .setStaleConnectionCheckEnabled(true)
			    .build();
		
		String content = "";
		// 生成一个httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(url);
			httpget.setConfig(defaultRequestConfig);
			
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				try {
					if (null != entity) {
						content = EntityUtils.toString(entity, "UTF-8");
					}
				} catch (IOException ex) {
					logger.error("", ex);
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			httpclient.close();
		}
		return content;  
	}
	
	public static void main(String[] args) throws IOException {
		// OutputStream os = doGet("http://www.dypho.com/inside_pages/solutions_health.html");
		// System.out.println(os.toString());
	}
	
	/**
	 * 发起post请求
	 * @param url
	 * @param params
	 * @return
	 */
    public static String postMethod(String url, Map<String, String> params){
		String result = null;
    	CloseableHttpClient httpClient = getHttpClient();
		try {
			HttpPost post = new HttpPost(url); 
			List<NameValuePair> list = Lists.newArrayList();
			if (!CollectionUtils.isEmpty(params)) {
				for (String key : params.keySet()) {
					list.add(new BasicNameValuePair(key, MapUtils.getString(params, key)));
				}
			}
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list, UTF8);
			post.setEntity(uefEntity);
			
			// 执行请求
			CloseableHttpResponse httpResponse = httpClient.execute(post);
			try {
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity) {
					result = EntityUtils.toString(entity);
					logger.debug("result");
				}
			} finally {
				httpResponse.close();
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		} finally {
			try {
				closeHttpClient(httpClient);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
		return result;
    }
	
	
    private static CloseableHttpClient getHttpClient(){
        return HttpClients.createDefault();
    }
     
    private static void closeHttpClient(CloseableHttpClient client) throws IOException{
        if (client != null){
            client.close();
        }
    }
    
    public static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(
					null, new TrustStrategy() {
						// 信任所有
						public boolean isTrusted(X509Certificate[] chain,
								String authType) throws CertificateException {
							return true;
						}
					}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}
    
    /**
	 * 
	 * @param url
	 * @param msg body参数
	 * @param contentType
	 * @param contentEncoding
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String sendHttpsMsg(String url, String msg,
			String contentType, String contentEncoding) {
		logger.debug("url={}  msg={} contentType={} contentEncoding={}.", url,
				msg, contentType, contentEncoding);

		String cotent = "";
		// HttpClient
		CloseableHttpClient base = createSSLClientDefault();
		try {
			HttpContext context = new BasicHttpContext();
			context.setAttribute(HttpClientContext.COOKIE_STORE,
					new BasicCookieStore());
			HttpPost post = new HttpPost();
			post.setURI(new URI(url));
			if (StringUtils.isNotEmpty(msg)) {
				StringEntity stringEntity = new StringEntity(
						msg,
						StringUtils.isNotEmpty(contentEncoding) ? contentEncoding
								: "UTF-8");
				stringEntity.setContentEncoding(StringUtils
						.isNotEmpty(contentEncoding) ? contentEncoding
						: "UTF-8");
				stringEntity.setContentType(StringUtils
						.isNotEmpty(contentType) ? contentType
						: "application/json");
				post.setEntity(stringEntity);
			}
			HttpResponse response = base.execute(post, context);
			HttpEntity entity = response.getEntity();
			try {
				if (null != entity) {
					cotent = EntityUtils.toString(entity, StringUtils
							.isNotEmpty(contentEncoding) ? contentEncoding
							: "UTF-8");
				}
			} catch (IOException ex) {
				logger.error("", ex);
			}
		} catch (Exception ex) {
			logger.error("", ex);
		} finally {
			try {
				base.close();
			} catch (IOException e) {
				logger.error("", e);
			}
			return cotent;
		}
	}

	/**
	 * 
	 * @param url
	 * @param msg
	 * @param contentType
	 * @param contentEncoding
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String sendHttpMsg(String url, String msg,
			String contentType, String contentEncoding) {
		logger.debug("url={}  msg={} contentType={} contentEncoding={}.", url,
				msg, contentType, contentEncoding);

		String cotent = "";
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient base = httpClientBuilder.build();
		try {
			HttpContext context = new BasicHttpContext();
			context.setAttribute(HttpClientContext.COOKIE_STORE,
					new BasicCookieStore());
			HttpPost post = new HttpPost(url);

			if (StringUtils.isNoneEmpty(msg)) {
				StringEntity stringEntity = new StringEntity(
						msg,
						StringUtils.isNoneEmpty(contentEncoding) ? contentEncoding
								: "UTF-8");
				stringEntity.setContentEncoding(StringUtils
						.isNoneEmpty(contentEncoding) ? contentEncoding
						: "UTF-8");
				stringEntity.setContentType(StringUtils
						.isNoneEmpty(contentType) ? contentType
						: "application/json");
				post.setEntity(stringEntity);
			}
			HttpResponse response = base.execute(post, context);
			HttpEntity entity = response.getEntity();
			try {
				if (null != entity) {
					cotent = EntityUtils.toString(entity, StringUtils
							.isNoneEmpty(contentEncoding) ? contentEncoding
							: "UTF-8");
				}
			} catch (IOException ex) {
				logger.error("", ex);
			}
		} catch (Exception ex) {
			logger.error("", ex);
		} finally {
			try {
				base.close();
			} catch (IOException e) {
				logger.error("", e);
			}
			return cotent;
		}
	}
	
	/**
	 * 
	 * @param url
	 * @param map
	 * @param contentType
	 * @param contentEncoding
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String sendHttpsFrom(String url, Map<String,String> map,
			String contentType, String contentEncoding) {
		logger.debug("url={}  msg={} contentType={} contentEncoding={}.", url,
				map, contentType, contentEncoding);

		String cotent = "";
		// HttpClient
		CloseableHttpClient base = createSSLClientDefault();
		try {
			HttpContext context = new BasicHttpContext();
			context.setAttribute(HttpClientContext.COOKIE_STORE,
					new BasicCookieStore());
			HttpPost post = new HttpPost();
			post.setURI(new URI(url));
			if (null != map) {
				
				List<NameValuePair> list = Lists.newArrayList();
				if (!CollectionUtils.isEmpty(map)) {
					for (String key : map.keySet()) {
						list.add(new BasicNameValuePair(key, MapUtils.getString(map, key)));
					}
				}
				UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list, UTF8);
				post.setEntity(uefEntity);
			}
			HttpResponse response = base.execute(post, context);
			HttpEntity entity = response.getEntity();
			try {
				if (null != entity) {
					cotent = EntityUtils.toString(entity, StringUtils
							.isNoneEmpty(contentEncoding) ? contentEncoding
							: "UTF-8");
				}
			} catch (IOException ex) {
				logger.error("", ex);
			}
		} catch (Exception ex) {
			logger.error("", ex);
		} finally {
			try {
				base.close();
			} catch (IOException e) {
				logger.error("", e);
			}
			return cotent;
		}
	}
	
	/**
	 * 爬数据使用 map中传递ip 和 port为代理服务的ip和port 这个两个是必填字段
	 * @param url
	 * @param headerMap
	 *            请求头信息
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String sendGet(String url, Map<String, String> headerMap) {
		logger.debug("url={}  headerMap={}.", url, headerMap);

		String cotent = "";
		// HttpClient
		CloseableHttpClient base = null;
		try {
			if (url.startsWith("https://")) {
				base = createSSLClientDefault();
			} else {
				HttpClientBuilder httpClientBuilder = HttpClientBuilder
						.create();
				base = httpClientBuilder.build();
			}

			HttpContext context = new BasicHttpContext();
			context.setAttribute(HttpClientContext.COOKIE_STORE,
					new BasicCookieStore());
			HttpGet httpGet = new HttpGet(new URI(url));

			HttpHost proxy = new HttpHost(headerMap.get("ip"),
					Integer.parseInt(headerMap.get("port")));
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectTimeout(10000).setSocketTimeout(10000)
					.setProxy(proxy).setConnectionRequestTimeout(3000).build();
			httpGet.setConfig(requestConfig);

			Set<String> keySet = headerMap.keySet();
			for (String key : keySet) {
				httpGet.setHeader(key, headerMap.get(key));
			}

			HttpResponse response = base.execute(httpGet, context);
			if (response != null) {
				HttpEntity entity = response.getEntity(); // 获取返回实体
				if (entity != null) {
					cotent = EntityUtils.toString(entity, "utf-8");
				}
			}
		} catch (Exception ex) {
			logger.error("", ex);
		} finally {
			try {
				base.close();
			} catch (IOException e) {
				logger.error("", e);
			}
			return cotent;
		}
	}

}