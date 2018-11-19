/**
 * 
 */
package com.towcent.base.common.ikanalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * ik分词
 * 
 * @author shiwei
 *
 */
public class IKAnalyzerUtils {
	private static final Log logger = LogFactory.getLog(IKAnalyzerUtils.class);

	public static void initIKDic(List<String> extDicList) {
		if (logger.isDebugEnabled()) {
			logger.debug("初始化ik扩展字典，字典内容" + extDicList);
		}
		long begin = System.currentTimeMillis();
		Dictionary.initial(DefaultConfig.getInstance());
		Dictionary.getSingleton().addWords(extDicList);
		if (logger.isDebugEnabled()) {
			logger.debug("初始化ik扩展字典完毕...,总共耗时："
					+ (System.currentTimeMillis() - begin) + "ms");
		}
	}

	public static List<String> ikAnalyzer(String text) {
		if (logger.isDebugEnabled()) {
			logger.debug("分词内容：" + text);
		}
		List<String> list = new ArrayList<String>();
		StringReader reader = null;
		TokenStream ts = null;
		try {
			// 创建分词对象
			@SuppressWarnings("resource")
			Analyzer anal = new IKAnalyzer(true);
			reader = new StringReader(text);
			// 分词
			ts = anal.tokenStream("", reader);
			CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
			ts.reset();
			// 遍历分词数据
			while (ts.incrementToken()) {
				list.add(term.toString());
			}
			reader.close();
			ts.close();
		} catch (Exception e) {
			if (null != reader) {
				reader.close();
			}
			if (null != ts) {
				try {
					ts.close();
				} catch (IOException e1) {
					logger.error("关闭分词失败", e);
				}
			}
			logger.error("分词失败", e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("分词内容结果：" + list);
		}
		return list;
	}
}
