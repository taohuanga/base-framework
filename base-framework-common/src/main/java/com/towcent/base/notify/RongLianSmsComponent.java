package com.towcent.base.notify;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.google.common.collect.Maps;
import com.towcent.base.common.constants.BaseConstant;
import com.towcent.base.common.exception.RpcException;
import com.towcent.base.common.exception.ServiceException;
import com.towcent.base.common.model.NotifySmsTemplate;
import com.towcent.base.common.model.SmsDto;
import com.towcent.base.common.redis.RedisTemplateExt;
import com.towcent.base.common.utils.BaseCacheKey;
import com.towcent.base.common.utils.StringUtils;
import com.towcent.base.manager.SmsNotifyApi;
import com.towcent.base.service.NotifySmsTemplateService;

/**
 * 容联*云通讯-短信组件  https://doc.yuntongxun.com/p/5a533de33b8496dd00dce07c
 * @author huangtao
 *
 */
@Service(value="rongLianSmsComponent")
public class RongLianSmsComponent implements SmsComponent {
	
	private static final Logger logger = LoggerFactory.getLogger(RongLianSmsComponent.class);
	
	@Value("${rongLian.sms.serverIP}")
	private String serverIP;
	@Value("${rongLian.sms.serverPort}")
	private String serverPort;
	@Value("${rongLian.sms.accountSid}")
	private String accountSid;
	@Value("${rongLian.sms.accountToken}")
	private String accountToken;
	@Value("${rongLian.sms.appId}")
	private String appId;
	
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
		String smsContent = /*template.getSignature() + */MessageFormat.format(template.getContent(), bParams);
		
		//初始化SDK
		CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
		//******************************注释*********************************************
		//*初始化服务器地址和端口                                                       *
		//*沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
		//*生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883");       *
		//*******************************************************************************
		restAPI.init(serverIP, serverPort);
		
		//******************************注释*********************************************
		//*初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN     *
		//*ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
		//*参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。                   *
		//*******************************************************************************
		restAPI.setAccount(accountSid, accountToken);
		
		
		//******************************注释*********************************************
		//*初始化应用ID                                                                 *
		//*测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID     *
		//*应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
		//*******************************************************************************
		restAPI.setAppId(appId);
		
		//******************************注释****************************************************************
		//*调用发送模板短信的接口发送短信                                                                  *
		//*参数顺序说明：                                                                                  *
		//*第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号                          *
		//*第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。    *
		//*系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
		//*第三个参数是要替换的内容数组。																														       *
		//**************************************************************************************************
		
		boolean sendFlag = false;    // 发送成功的标记
		if (!smsNotityApi.getSmsTestFlag()) {
			//**************************************举例说明***********************************************************************
			//*假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为           *
			//*result = restAPI.sendTemplateSMS("13800000000","1" ,new String[]{"6532","5"});																		  *
			//*则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入     *
			//*********************************************************************************************************************
			HashMap<String, Object> result = restAPI.sendTemplateSMS(mobile, template.getSmsTemplateId(), bParams);
			
			logger.info("SDKTestGetSubAccounts result = " + result);
			if ("000000".equals(result.get("statusCode"))) {
				// 正常返回输出data包体信息（map）
				HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
				Set<String> keySet = data.keySet();
				for (String key : keySet) {
					Object object = data.get(key);
					System.out.println(key + " = " + object);
				}
				HashMap<String, Object> data2 = (HashMap<String, Object>) data.get("templateSMS");
				
				sendFlag = true;
				resultVo = new SmsDto(0, "提交成功", data2.get("smsMessageSid").toString());
			} else {
				// 异常返回输出错误码和错误信息
				logger.error("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
				resultVo = new SmsDto(2, MapUtils.getString(statusMap, result.get("statusCode"), ""), "");
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
		
		// 短信模板  -- 国际短信模板id再原有模板加100
		NotifySmsTemplate template = getNotifySmsTemplateByType(smsType + 100);
		String[] bParams = bulidParamVo(template, params);
		String smsContent = template.getSignature() + MessageFormat.format(template.getContent(), bParams);
		
		boolean sendFlag = false;    // 发送成功的标记
		if (!smsNotityApi.getSmsTestFlag()) {
			// TODO
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
			params = new String[]{"", "5"};  // 验证码，时间(分钟)
		}
		
		// 是否需要验证码
		String verifyCode = null;
		if (BaseConstant.YES.equals(template.getIsSecurityCode())) {
			verifyCode = smsNotityApi.generateVerifyCode();
			params[0] = verifyCode;
			params[1] = template.getValidTime() + "";
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
	
	private static final Map<String, String> statusMap = Maps.newHashMap();
	
	static {
		statusMap.put("112300", "【短信】接收短信的手机号码为空");
		statusMap.put("112301", "【短信】短信正文为空");
		statusMap.put("112302", "【短信】群发短信已暂停");
		statusMap.put("112303", "【短信】应用未开通短信功能");
		statusMap.put("112304", "【短信】短信内容的编码转换有误");
		statusMap.put("112305", "【短信】应用未上线，短信接收号码外呼受限");
		statusMap.put("112306", "【短信】接收模板短信的手机号码为空");
		statusMap.put("112307", "【短信】模板短信模板ID为空");
		statusMap.put("112308", "【短信】模板短信模板data参数为空");
		statusMap.put("112309", "【短信】模板短信内容的编码转换有误");
		statusMap.put("112310", "【短信】应用未上线，模板短信接收号码外呼受限");
		statusMap.put("112311", "【短信】短信模板不存在");
		statusMap.put("112312", "【短信】应用未配置此短信扩展码");
		statusMap.put("112313", "【短信】未查到符合条件的短信模板");
		statusMap.put("112314", "【短信】短信下发超过当日发送限制");
		statusMap.put("112315", "【短信】短信模板ID长度超长");
		statusMap.put("112316", "【短信】接收短信的多个号码格式错误");
		statusMap.put("112317", "【短信】号码在黑名单中");
		statusMap.put("112318", "【短信】短信模板ID要求为数字");
		statusMap.put("112319", "【短信】接收短信的手机号码超长");
		statusMap.put("112320", "【短信】用户未配置接收上行短信及回执的回调地址");
		statusMap.put("112321", "【短信】用户未勾选接收上行短信及回执");
		statusMap.put("112323", "【短信】短信模板不存在或审核未通过");
		statusMap.put("112324", "【短信】日期不能为空");
		statusMap.put("112325", "【短信】msgId不能为空");
		statusMap.put("112326", "【短信】发送短信失败");
		statusMap.put("112327", "【短信】日期参数含有非法字符");
		statusMap.put("112328", "【短信】电话号码参数含有非法字符");
		statusMap.put("112329", "【短信】获取短信状态报告count参数不正确");
		statusMap.put("112330", "【短信】获取短信状态报告count参数超过最大限制");
		statusMap.put("112341", "【短信】短信模板产品类型不能为空");
		statusMap.put("112342", "【短信】rest接口生成短信模板时产品类型仅支持已发布应用和网页");
		statusMap.put("112343", "【短信】短信模板标题不能为空");
		statusMap.put("112344", "【短信】短信模板签名不能为空");
		statusMap.put("112345", "【短信】短信模板内容不能为空");
		statusMap.put("112346", "【短信】短信模板签名必须大于3个字小于8个字");
		statusMap.put("112347", "【短信】短信模板签名必须带有中文");
		statusMap.put("112348", "【短信】短信模板产品地址不能为空");
		statusMap.put("112349", "【短信】账户必须企业认证");
		statusMap.put("112350", "【短信】账户必须累计充值2000元以上或购买任一短信包");
		statusMap.put("112351", "【短信】短信状态不存在");
		statusMap.put("112352", "【短信】短信状态已过期");
		statusMap.put("112353", "【短信】应用不属于该主账号");
		statusMap.put("112354", "【短信】查询超过并发次数");
		statusMap.put("113302", "【短信】您正在使用云通讯测试模板且短信接收者不是注册的测试号码");
		statusMap.put("160001", "【短信】短信通道繁忙");
		statusMap.put("160002", "【短信】短信通道繁忙");
		statusMap.put("160003", "【短信】短信通道繁忙");
		statusMap.put("160004", "【短信】短信通道繁忙");
		statusMap.put("160005", "【短信】短信通道繁忙");
		statusMap.put("160006", "【短信】短信通道繁忙");
		statusMap.put("160007", "【短信】短信通道繁忙");
		statusMap.put("160008", "【短信】短信通道繁忙");
		statusMap.put("160009", "【短信】短信通道繁忙");
		statusMap.put("160010", "【短信】短信通道繁忙");
		statusMap.put("160011", "【短信】存在屏蔽词的词汇，目前模板短信都没有校验屏蔽词。");
		statusMap.put("160012", "【短信】短信通道认证出错。");
		statusMap.put("160013", "【短信】号码格式不对，或者该号码不支持");
		statusMap.put("160014", "【短信】短信通道余额不足");
		statusMap.put("160015", "【短信】限制对单号码的发送次数");
		statusMap.put("160016", "【短信】通道限制发送速度");
		statusMap.put("160017", "【短信】单次提交号码过多。天天限制是100个");
		statusMap.put("160018", "【短信】短信通道返回有屏蔽词");
		statusMap.put("160019", "【短信】签名无效");
		statusMap.put("160020", "【短信】短信内容过长。天天限制是250个字符。");
		statusMap.put("160021", "【短信】相同的内容发给同一手机一天中只能发一次");
		statusMap.put("160022", "【短信】对同一个手机一天发送的短信超过限制次数");
		statusMap.put("160031", "【短信】参数解析失败");
		statusMap.put("160032", "【短信】短信模板无效");
		statusMap.put("160033", "【短信】短信存在黑词");
		statusMap.put("160034", "【短信】号码黑名单");
		statusMap.put("160035", "【短信】短信下发内容为空");
		statusMap.put("160036", "【短信】短信模板类型未知");
		statusMap.put("160037", "【短信】短信内容长度限制");
		statusMap.put("160038", "【短信】短信验证码发送过频繁");
		statusMap.put("160039", "【短信】发送数量超出同模板同号天发送次数上限");
		statusMap.put("160040", "【短信】验证码超出同模板同号码天发送上限");
		statusMap.put("160041", "【短信】通知超出同模板同号码天发送上限");
		statusMap.put("160042", "【短信】号码格式有误");
		statusMap.put("160043", "【短信】应用与模板id不匹配");
		statusMap.put("160044", "【短信】发送号码为空");
		statusMap.put("160045", "【短信】群发号码重复");
		statusMap.put("160046", "【短信】营销短信发送内容审核未通过");
		statusMap.put("160047", "【短信】询状态报告包体解析失败");
		statusMap.put("160048", "【短信】号码数超200限制");
		statusMap.put("160049", "【短信】短信内容含敏感词");
		statusMap.put("160051", "【短信】营销退订号码");
		statusMap.put("160052", "【短信】模板变量格式有误");
		statusMap.put("160053", "【短信】IP鉴权失败");
		statusMap.put("160054", "【短信】请求重复");
		statusMap.put("160055", "【短信】请求reqId超长");
		statusMap.put("160056", "【短信】同号码请求内容重复");
		statusMap.put("160057", "【短信】短信模板ID要求为数字");
		statusMap.put("160058", "【短信】账户无国际短信权限");
		statusMap.put("160059", "【短信】国际短信暂不支持群发");
		statusMap.put("160060", "【短信】国际短信账户无营销短信权限");
		statusMap.put("160061", "【短信】暂不支持的国家码号");
		statusMap.put("160062", "【短信】未开通此国家码号");
		statusMap.put("160063", "【短信】短信发送失败");
		statusMap.put("160064", "【短信】短信发送失败");
		statusMap.put("160065", "【短信】子扩展不符合要求");
		statusMap.put("161000", "渠道返回失败");
		statusMap.put("161001", "渠道调用异常");
		statusMap.put("161002", "渠道账户余额不足");
		statusMap.put("161003", "短信计费失败");
		statusMap.put("161004", "短信记录入库失败");
		statusMap.put("161005", "未配置可用通道");
		statusMap.put("161006", "超出通道最大并发数");
		statusMap.put("161007", "短信内容通道敏感");
		statusMap.put("161008", "DB访问异常");
		statusMap.put("161009", "RDS访问异常");
		statusMap.put("161010", "RDS连接失败");
		statusMap.put("161011", "RDS超过最大连接数");
		statusMap.put("161012", "DB超过最大连接数");
		statusMap.put("161020", "直连通道HTTP连接超时");
		statusMap.put("161021", "直连通道HTTP读取超时");
		statusMap.put("161022", "直连通道HTTP请求异常");
		statusMap.put("161023", "直连通道响应空包体");
		statusMap.put("161024", "直连通道响应发送失败");
		statusMap.put("161030", "状态报告推送异常");
		statusMap.put("161031", "上线短信推送异常");
		statusMap.put("161100", "鉴权失败");
		statusMap.put("161101", "请求包头Authorization参数为空");
		statusMap.put("161102", "请求包头Authorization参数Base64解码失败");
		statusMap.put("161103", "请求包头Authorization参数解码后格式有误");
		statusMap.put("161104", "请求包头Authorization参数解码后token为空");
		statusMap.put("161105", "请求包头Authorization参数解码后时间戳为空");
		statusMap.put("161106", "请求包头Authorization参数解码后时间戳过期");
		statusMap.put("161107", "请求包头Authorization参数中时间戳错误");
		statusMap.put("161108", "请求包头Authorization参数解码后时间戳格式有误");
		statusMap.put("112357", "【短信】短信模板签名长度必须在2-16个字之间");
		
	}
}
