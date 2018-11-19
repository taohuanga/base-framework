package com.towcent.base.dal.auth.interceptor;

import com.alibaba.dubbo.rpc.RpcContext;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 数据权限逻辑处理实现
 * 
 * @author huangtao
 * @date 2016年9月25日 下午3:10:40
 * @version 0.1.0 
 */
@Component("dataAccessStatementProcessor")
public class DefaultDataAccessStatementProcessor implements
		DataAccessStatementProcessor {
	private Pattern regex = Pattern
			.compile("((--)?\\W+(AND|OR)?\\W?\\(?\\W?)?@(\\w+\\.)?(\\w+)(!\\w+)?",Pattern.CASE_INSENSITIVE);
	// private Pattern regexAutoWired = Pattern
	//		.compile("--\\W?+@AutoWired+\\W?(\\w+)?",Pattern.CASE_INSENSITIVE);
	
	private static final String rn = System.getProperty("line.separator");
	
	// private DataAccessProvider dataAccessProvider;
	
	@Override
	public String process(MetaObject metaObject) {
		
		BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
		String script = boundSql.getSql();
		if(StringUtils.indexOfIgnoreCase(script, "INSERT INTO") >=0) {
			return null;
		}
		
		return scan(script);
	}
	
	public String scan(String script) {
		StringBuffer sb;
		boolean changed;
		Scanner scanner = new Scanner(script);
		try {
			sb = new StringBuffer();
			changed = false;
			while (scanner.hasNextLine()) {
				String line = StringUtils.trimToEmpty(scanner.nextLine());
				if (StringUtils.isBlank(line)) {
					continue;
				}
				String tmp = replace(line);
				if (tmp == null) {
					sb.append(line);
				} else {
					sb.append(tmp);
					changed = true;
				}
				sb.append(rn);
			} 
		} finally {
			scanner.close();
		}
		return changed ? sb.toString() : null;
	}	
	
	public String replace(String script) {
		Matcher m = regex.matcher(script);
		StringBuffer sb = new StringBuffer();
		boolean finded = m.find();
		if (!finded) {
			return null;
		}
		String statement = RpcContext.getContext().getAttachment("merchantId");
		while (finded) {
			// String express = m.group(0);
			String op = m.group(1);
			String alias = m.group(4);
			String name = m.group(5);
			String field = m.group(6);
			if (field == null) {
				field = name;
			} else {
				field = field.substring(1);
			}
			if (null == alias) {
				alias = "";
			}
			if (op == null) {
				op = "";
			}
			else {
				op = op.replaceFirst("--", "");
			}
			if (null == statement) {
				statement = op + " 1=1 ";
			} else {
				statement = op + " " + alias + field + " = " + statement;
			}
			m.appendReplacement(sb, statement);
			finded = m.find();
		}
		m.appendTail(sb);
		sb.append(rn);
		return sb.toString();
	}

}
