package com.towcent.base.common.utils;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.towcent.base.common.vo.Column;
import org.apache.commons.lang3.StringUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 网站联盟核心处理通用util工具类
 * 
 */
public final class CommonUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	
	private static final Pattern EMAIL_PATTERN = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	
	private static final Pattern PHONE_PATTERN = Pattern.compile("^(13|15|18)\\d{9}$");
	
	private static final String CHINESE_REGEX = "[\u4e00-\u9fa5]+";
	
	public static String getRound(double d) {
		if (d == 0) {
			return "0";
		}
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(d);
	}
	
	private static String replace(String str, String replace, String replaceStr) {
		if (str != null) {
			int i = str.indexOf(replace);
			while (i != -1) {
				str = str.substring(0, i) + replaceStr + str.substring(i + replace.length());
				i = str.indexOf(replace, i + replaceStr.length());
			}
		}
		return str;
	}

	public static String htmlType(String s) {
		if (s == null || s.toLowerCase().equals("null")) {
			return "";
		}
		s = replace(s, " ", "&nbsp;");
		s = replace(s, "\r\n", "<br>");
		return s;
	}

	/**
	 * 判断该字符串是否为中文
	 * @param str 输入字符
	 * @return boolean
	 */
	public static boolean IsChinese(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		}
		return str.matches(CHINESE_REGEX);
	}

	public static boolean isGB(String str) {
		if (null == str) {
			return false;
		}
		if (str.trim() == "") {
			return false;
		}
		byte[] bytes = str.getBytes();
		if (bytes.length < 2) {
			return false;
		}
		byte aa = (byte) 0xB0;
		byte bb = (byte) 0xF7;
		byte cc = (byte) 0xA1;
		byte dd = (byte) 0xFE;
		if (bytes[0] >= aa && bytes[0] <= bb) {
			if (bytes[1] < cc || bytes[1] > dd) {
				return false;
			}
			return true;
		}
		return false;
	}

	public static boolean isBig5(String str) {
		if (null == str) {
			return false;
		}
		if (str.trim() == "") {
			return false;
		}
		byte[] bytes = str.getBytes();
		if (bytes.length < 2) {
			return false;
		}
		byte aa = (byte) 0xB0;
		byte bb = (byte) 0xF7;
		byte cc = (byte) 0xA1;
		byte dd = (byte) 0xFE;
		if (bytes[0] >= aa && bytes[0] <= bb) {
			if (bytes[1] < cc || bytes[1] > dd) {
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * 对字符串进行MD5加密
	 * 
	 * @param plainText
	 *            String
	 * @return String
	 */
	public static String md5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			logger.error("", e);
			return "";
		}
	}

	/** 将输入字符串转化为utf8编码,并返回该编码的字符串 */
	public static String changeEncode(String in) {
		String s = null;
		byte temp[];
		if (in == null) {
			System.out.println("Warn:Chinese null founded!");
			return new String("");
		}
		try {
			temp = in.getBytes("utf8");
			s = new String(temp);
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.toString());
		}
		return s;
	}

	/** 根据时间生成唯一编号 */
	public static String buildNumber(int length) {
		long time = System.currentTimeMillis();

		Random random = new Random();
		StringBuffer buffer = new StringBuffer().append(time);

		String result = "";

		if (buffer.length() >= length) {
			buffer = new StringBuffer();
			for (int i = 0; i < length; i++) {
				buffer.append(random.nextInt(9));
			}
		} else {
			for (int i = 0; i < (length - buffer.length()); i++) {
				buffer.append(random.nextInt(9));
			}
		}

		result = buffer.toString();
		return result;
	}

	/**
	 * 获取国际化资源文件中的键所对应的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getLocaleBundleResourceValue(String key) {
		// 获取系统默认的国家/语言环境
		Locale myLocale = Locale.getDefault();
		// 根据指定的国家/语言环境加载资源文件
		ResourceBundle bundle = ResourceBundle.getBundle("yitianplatform", myLocale);
		// 获取资源文件中的key为hello的value值
		return bundle.getString(key);

	}

	/**
	 * 将list的内容写入到指定的txt文档里
	 * 
	 * @Title: writeTxt
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param dir
	 * @param @param list
	 * @param @throws IOException 设定文件
	 * @return void 返回类型
	 * @author zhu.b
	 * @date 2011-6-16 下午01:32:31
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public static void writeTxt(String dir, List list) throws IOException {
		FileWriter fileWriter = new FileWriter(dir);
		for (int i = 0; i < list.size(); i++) {
			fileWriter.write(String.valueOf(list.get(i) + ""));
		}
		fileWriter.flush();
		fileWriter.close();

	}

	/**
	 * 判断list是否有值
	 * */
	@SuppressWarnings("rawtypes")
	public static boolean hasValue(List list) {
		return ((null != list) && (list.size() > 0));
	}

	public static boolean hasValue(String s) {
		return (s != null) && (s.trim().length() > 0);
	}

	/**
	 * 验证是否为Double
	 * @param value
	 * @return
	 */
	public static boolean validateDouble(String value) {
		if (value == null || value.equals("")) {
			return false;
		}
		try {
			new Double(value);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 验证是否为Long
	 * @param value
	 * @return
	 */
	public static boolean validateLong(String value) {
		if (value == null || value.equals("")) {
			return false;
		}
		try {
			new Long(value);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	

	/**
	 * 验证是否为Date类型
	 * @param value
	 * @return
	 */
	public static boolean validateDate(String value) {
		if (value == null || value.equals("")) {
			return false;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setLenient(false);
			sdf.parse(value);
		} catch (Exception e) {
			logger.error("", e);
			return false;
		}
		return true;
	}

	/**
	 * 从字符串转换为Double
	 * 
	 * @param str
	 * @return
	 */
	public static double stringToDouble(String str) {
		NumberFormat formater = NumberFormat.getInstance();
		double value = 0;
		try {
			if (str == null || str.trim().length() == 0 || !validateDouble(str)) {
				str = "0";
			}
			value = formater.parse(str).doubleValue();
		} catch (Exception e) {
			// SuperContext.showExceptionMsg(e);
		}
		return value;
	}

	public static Map<String, String> conditionExpMap() {
		Map<String, String> conditionExpMap = new HashMap<String, String>();
		conditionExpMap.put("等于", "=");
		conditionExpMap.put("不等于", "<>");
		conditionExpMap.put("大于", ">");
		conditionExpMap.put("大于等于", ">=");
		conditionExpMap.put("小于", "<");
		conditionExpMap.put("小于等于", "<=");
		conditionExpMap.put("包含", "like"); // like '%XXX%'
		conditionExpMap.put("不包含", "not like"); // not like '%XXX%'
		conditionExpMap.put("以开头", "like"); // like  '%XXX'
		conditionExpMap.put("以结尾", "like"); // like 'XXX%'
		return conditionExpMap;
	}

	public static String getConditionExpMap(String conditionExp) {
		return (conditionExpMap().get(conditionExp) != null) ? (String.valueOf(conditionExpMap().get(conditionExp)))
				: "=";
	}

	/**
	 * 处理查询条件公用方法，返回拼装好的SQL
	 * 参数：
	 * queryCondition [  {"columnRelation":"并且","columnName":"factoryname#String","columnCondition":"包含","columnValue":"53"},
	 *                   {"columnRelation":"并且","columnName":"createdate#Date","columnCondition":"大于等于","columnValue":"2012-07-01"},
	 *                   {"columnRelation":"并且","columnName":"counts#double","columnCondition":"大于","columnValue":"100"}
	 *                ]
	 * @param queryConditionParam
	 * @return
	 * and factoryname like '%53%' and to_char(createdate,'yyyy-MM-dd') >= '2013-07-01' or counts > 100.0
	 */
	public static HashMap<String, String> getConditionSQL(String queryConditionParam) {
		StringBuffer sql = new StringBuffer(""); //返回拼装好的SQL
		StringBuffer errorMsg = new StringBuffer("");//返回验证错误信息
		HashMap<String, String> returnMap = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			if (CommonUtil.hasValue(queryConditionParam) && !"[]".equals(queryConditionParam)) {
				//1. JSON转对象
				LinkedList<Column> list = mapper.readValue(queryConditionParam,
						new TypeReference<LinkedList<Column>>() {
						});
				if (CommonUtil.hasValue(list)) {
					int i = 1;
					for (Column col : list) {
						if (col != null && CommonUtil.hasValue(col.getColumnName())) {
							String[] columnNameArr = col.getColumnName().split("#");
							String columnName = "";
							String columnType = "";
							if (columnNameArr != null && columnNameArr.length > 1) {
								columnName = columnNameArr[0];
								columnType = columnNameArr[1];
								//2013-09-16 修改为表配置的形式
								if(CommonUtil.hasValue(columnType)){
									if("2".equals(columnType)){ // 数字
										columnType="double";
									}else if("3".equals(columnType)){ //日期
										columnType="Date";
									}else{
										columnType="string";
									}
								}else {
									columnType="string";
								}
								
								//条件关系
								String columnRelation = "and";
								if (hasValue(col.getColumnRelation()) && "或者".equals(col.getColumnRelation())) {
									columnRelation = "or";
								}

								// like \not like \ left like \right like 要单独判断
								if ("String".equals(columnType) || "string".equals(columnType)
										|| "STRING".equals(columnType)) {
									//字符串类型  
									sql.append(" ").append(columnRelation);
									sql.append(" ").append(columnName);
									String tempExpString = col.getColumnCondition().toLowerCase();
									sql.append(" ").append(CommonUtil.getConditionExpMap(tempExpString));
									if ("包含".equals(tempExpString) || "不包含".equals(tempExpString)) {
										sql.append("  '%").append(col.getColumnValue()).append("%' ");
									} else if ("以开头".equals(tempExpString)) {
										sql.append("  '").append(col.getColumnValue()).append("%' ");
									} else if ("以结尾".equals(tempExpString)) {
										sql.append("  '%").append(col.getColumnValue()).append("' ");
									} else {
										sql.append("  '").append(col.getColumnValue()).append("' ");
									}

								} else if ("double".equals(columnType) || "Double".equals(columnType)
										|| "DOUBLE".equals(columnType) || "short".equals(columnType)
										|| "int".equals(columnType) || "long".equals(columnType)) {

									if (validateDouble(col.getColumnValue())) {
										//数字类型 
										sql.append(" ").append(columnRelation);
										sql.append(" ").append(columnName);
										String tempExpString = col.getColumnCondition().toLowerCase();
										sql.append(" ").append(CommonUtil.getConditionExpMap(tempExpString));
										if ("包含".equals(tempExpString) || "不包含".equals(tempExpString)) {
											sql.append("  '%").append(col.getColumnValue()).append("%' ");
										} else if ("以开头".equals(tempExpString)) {
											sql.append("  '").append(col.getColumnValue()).append("%' ");
										} else if ("以结尾".equals(tempExpString)) {
											sql.append("  '%").append(col.getColumnValue()).append("' ");
										} else {
											sql.append(" ").append(Double.valueOf(col.getColumnValue()));
										}
									} else {
										errorMsg.append("第" + i + "行输入值格式有误，只能为数字类型; \r\n");
									}

								} else if ("Date".equals(columnType)) {
									//日期类型 全部转成按 按日期比较 目前只到天

									if (validateDate(col.getColumnValue())) {
										sql.append(" ").append(columnRelation);
										sql.append(" ").append("to_char(" + columnName + ",'yyyy-MM-dd')"); // to_char(k.createdate,'yyyy-MM-dd')
										String tempExpString = col.getColumnCondition().toLowerCase();
										sql.append(" ").append(CommonUtil.getConditionExpMap(tempExpString));
										if ("包含".equals(tempExpString) || "不包含".equals(tempExpString)) {
											sql.append("  '%").append(col.getColumnValue()).append("%' ");
										} else if ("以开头".equals(tempExpString)) {
											sql.append("  '").append(col.getColumnValue()).append("%' ");
										} else if ("以结尾".equals(tempExpString)) {
											sql.append("  '%").append(col.getColumnValue()).append("' ");
										} else {
											sql.append("  '").append(col.getColumnValue()).append("' ");
										}
									} else {
										errorMsg.append("第" + i + "行输入值格式有误，只能为日期类型; \r\n");
									}

								}
							}
						}
						i++;
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		returnMap.put("sql", sql.toString());
		returnMap.put("errorMsg", errorMsg.toString());
		return returnMap;
	}

	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());

	}

	public static String getCurrentDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

    
     /**
      * 通过反射获得某方法信息
      * @param owner
      * @param methodName
      * @param args
      * @return
      * @throws Exception
      */
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object invokeMethod(Object owner, String methodName, Object[] args) throws Exception {
	      Class ownerClass = owner.getClass();
	      Class[] argsClass = new Class[args.length];
	      for (int i = 0, j = args.length; i < j; i++) {
	          argsClass[i] = args[i].getClass();
	      }
	     Method method = ownerClass.getMethod(methodName, argsClass);
	     return method.invoke(owner, args);
      }
	 
	 /**
	  * 验证权限位
	  * @param power_value  存在 角色模块相关表中
	  * @param index  1-增加 2-修改   操作编号
	  * @return
	  */
	public static boolean checkPower(int power_value,int index){
		boolean flag=false;
		int temp=(int)Math.pow(2,index);
		int result = power_value&temp;
		if(result==temp){
			flag=true;
		}
		return flag;
	}
	
	public static boolean checkPower(String power_value,int index){
		boolean flag=false;
		String [] powerArray = power_value.split(",");
		for(int i=0; i<powerArray.length; i++){
			if(Integer.parseInt(powerArray[i]) == index){
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * 
	 * @param powerStr 1,2,3,4
	 * @return
	 */
	public static int getPower(String powerStr) {
		int result = 0;
		if (CommonUtil.hasValue(powerStr)) {
			String[] temp = powerStr.split(",");
			if (temp != null && temp.length > 0) {
				for (String tempV : temp) {
					if (hasValue(tempV)) {
						result += (int) Math.pow(2, Integer.valueOf(tempV));
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @return 生成一个UUID-字符串
	 */
	public static String getUUIDString() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().toUpperCase();
	}
	

	/**
	 * 
	 * @return 返回字符串长度
	 */
	public static int getStringlength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }
	
	/**
	 * 判断邮件email是否正确格式
	 * @param email 邮箱地址
	 * @return
	 */
	public static boolean checkEmail(String email) {
		if (StringUtils.isBlank(email)) {
			return false;
		}
		return EMAIL_PATTERN.matcher(email).matches();
	}
	
	/**
	 * 判断手机号phone是否正确格式
	 * @param phone 手机号码
	 * @return
	 */
	public static boolean checkPhone(String phone) {
		if (StringUtils.isBlank(phone)) {
			return false;
		}
		return PHONE_PATTERN.matcher(phone).matches();
	}
 
	
	public static void main(String[] args) {
		
	}

}
