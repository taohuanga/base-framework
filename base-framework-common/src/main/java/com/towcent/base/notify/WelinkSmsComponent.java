package com.towcent.base.notify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.towcent.base.common.constants.BaseConstant;
import com.towcent.base.common.exception.RpcException;
import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.mapper.JaxbMapper;
import com.towcent.base.common.model.NotifySmsTemplate;
import com.towcent.base.common.model.SmsDto;
import com.towcent.base.common.redis.RedisTemplateExt;
import com.towcent.base.common.utils.BaseCacheKey;
import com.towcent.base.common.utils.StringUtils;
import com.towcent.base.common.utils.WelinkRepsVo;
import com.towcent.base.manager.SmsNotifyApi;
import com.towcent.base.service.NotifySmsTemplateService;

/**
 * 微网-短信组件  http://www.51welink.com/
 * @author huangtao
 *
 */
@Service(value="welinkSmsComponent")
public class WelinkSmsComponent implements SmsComponent {
	
	private static final Logger logger = LoggerFactory.getLogger(WelinkSmsComponent.class);
	
	// welink.sms.url=http://cf.51welink.com/submitdata/Service.asmx/g_Submit
	// welink.sms.sname=dlxjlcs
	// welink.sms.spwd=dlxjlcs123
	
	private static final String postMsg = "sname={0}&spwd={1}&scorpid=&sprdid={2}&sdst={3}&smsg={4}";
	
	// 国内短信产品编码
	private static final String sprdid = "1012818"; 
	// 国际短信产品编码
	private static final String intsprdid = "1012809";
	
	// 国内/国际短信
	@Value("${welink.sms.url}")
	private String url;
	@Value("${welink.sms.sname}")
	private String sname;
	@Value("${welink.sms.spwd}")
	private String spwd;
	
	@Resource
	SmsNotifyApi smsNotityApi;
	@Resource 
	RedisTemplateExt<String, Object> redisTemplateExt;
	@Resource
	private NotifySmsTemplateService templateService;
	
	@Override
	public SmsDto post(String mobile, int smsType, Object... params) throws RpcException {
		SmsDto resultVo = null;
		
		// 短信模板
		NotifySmsTemplate template = getNotifySmsTemplateByType(smsType);
		String[] bParams = bulidParamVo(template, params);
		String smsContent = template.getSignature() + MessageFormat.format(template.getContent(), bParams);
        
		boolean sendFlag = false;    // 发送成功的标记
		if (!smsNotityApi.getSmsTestFlag()) {
			String enCodeContent = smsContent;
			try {
				enCodeContent = URLEncoder.encode(smsContent, "utf-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("", e);
			}
			String reps = SMS(MessageFormat.format(postMsg, sname, spwd, sprdid, mobile, enCodeContent), url);
			
			WelinkRepsVo vo = JaxbMapper.fromXml(reps, WelinkRepsVo.class);
			
	        resultVo = new SmsDto(Integer.valueOf(vo.getState()), vo.getMsgState(), vo.getMsgID());
			if ("0".equals(vo.getState())) {
				sendFlag = true;
			}
		} else {
			resultVo = new SmsDto(0, "测试发送成功", "1220000001");
			sendFlag = true;
		}
		
		// 发送成功
		if (sendFlag && BaseConstant.YES.equals(template.getIsSecurityCode())) {
			String key = BaseCacheKey.getSmsKey((byte) smsType, mobile);
			redisTemplateExt.set(key, bParams[0], template.getValidTime(), TimeUnit.MINUTES);
		}
		resultVo.setSmsContent(smsContent);
		return resultVo;
	}
	
	@Override
	public SmsDto post(String areaCode, String mobile, int smsType, Object... params) throws RpcException {
		// 如果是国内手机号码，则使用普通短信接口
		if (StringUtils.isBlank(areaCode) || "86".equals(areaCode)) {
			return post(mobile, smsType, params);
		}
		
		SmsDto resultVo = null;
		
		// 短信模板
		NotifySmsTemplate template = getNotifySmsTemplateByType(smsType + 100);
		String[] bParams = bulidParamVo(template, params);
		String smsContent = template.getSignature() + MessageFormat.format(template.getContent(), bParams);
        
		boolean sendFlag = false;    // 发送成功的标记
		if (!smsNotityApi.getSmsTestFlag()) {
			String enCodeContent = smsContent;
			try {
				enCodeContent = URLEncoder.encode(smsContent, "utf-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("", e);
			}
			String reps = SMS(MessageFormat.format(postMsg, sname, spwd, intsprdid, "00" + areaCode + mobile, enCodeContent), url);
			
			WelinkRepsVo vo = JaxbMapper.fromXml(reps, WelinkRepsVo.class);
			
	        resultVo = new SmsDto(Integer.valueOf(vo.getState()), vo.getMsgState(), vo.getMsgID());
			if ("0".equals(vo.getState())) {
				sendFlag = true;
			}
		} else {
			resultVo = new SmsDto(0, "测试发送成功", "1220000001");
			sendFlag = true;
		}
		
		// 发送成功
		if (sendFlag && BaseConstant.YES.equals(template.getIsSecurityCode())) {
			String key = BaseCacheKey.getSmsKey((byte) smsType, mobile);
			redisTemplateExt.set(key, bParams[0], template.getValidTime(), TimeUnit.MINUTES);
		}
		resultVo.setSmsContent(smsContent);
		return resultVo;
	}
	
	/**
	 * 构建参数
	 * @param template
	 * @param params
	 * @return
	 * @throws RpcException 
	 */
	private String[] bulidParamVo(NotifySmsTemplate template, Object... params) throws RpcException {
		if (null == params || "code".equals(template.getParamStr())) {
			params = new String[]{""};
		}
		
		// 是否需要验证码
		String verifyCode = null;
		if (BaseConstant.YES.equals(template.getIsSecurityCode())) {
			verifyCode = smsNotityApi.generateVerifyCode();
			params[0] = verifyCode;
			return (String[]) params;
		}
		
		return (String[]) params;
	}
	
	private NotifySmsTemplate getNotifySmsTemplateByType(int smsType) {
		NotifySmsTemplate template = null;
		try {
			Map<String, Object> params = Maps.newHashMap();
			params.put("smsType", smsType);
			List<NotifySmsTemplate> list = templateService.findByBiz(params);
			template = CollectionUtils.isEmpty(list) ? null : list.get(0);
		} catch (ServiceException e) {
			logger.error("", e);
		}
		return template;
	}
	
    public static String SMS(String postData, String postUrl) {
        try {
            //发送POST请求
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();

            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("connect failed!");
                return "";
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return "";
    }
}
