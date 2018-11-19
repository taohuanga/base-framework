/**
 * 
 */
package com.towcent.base.common.excel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.towcent.base.common.utils.BeanMapUtils;
import com.towcent.base.common.utils.FileUtils;
import com.towcent.base.common.utils.Img2Base64Util;
import com.towcent.base.common.utils.excel.ImportExcel;


/**
 * 导入工具类
 * @author shiwei
 *
 */
public abstract class AbstractImportExcel<T> {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${excel.basePath}")
	private String basePath;
	
	/**
	 * 设置excel头和实体对象的映射，头为key，实体对象的属性为value
	 * @return
	 */
	protected abstract Map<String, String> getHeader();
	
	
	/**
	 * 解析数据
	 * @param fileName
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<T>  getData(String fileName, Class<T> clazz){
		List<T> list = Lists.newArrayList();
		try {
			Map<String, String> header = this.getHeader();
			List<String> head = Lists.newArrayList();
			ImportExcel ei = new ImportExcel(fileName, 1);
			Row row1 = ei.getRow(1);
			for (int j = 0; j < ei.getLastCellNum(); j++) {
				Object val = ei.getCellValue(row1, j);
				head.add(header.get(val.toString()));
			}
			T entity = null;
			for (int i = 2; i < ei.getLastDataRowNum(); i++) {
				Row row = ei.getRow(i);
				Map<String, Object> objMap = Maps.newHashMap();
				for (int j = 0; j < ei.getLastCellNum(); j++) {
					Object val = ei.getCellValue(row, j);
					
					// 将每一行数据存入map中
					objMap.put(head.get(j), val);
				}
				// 将map转换成为对象
				entity = (T) BeanMapUtils.getMap2Bean(objMap, clazz);
				
				list.add(entity);
			}
			FileUtils.deleteFile(fileName);
		} catch (Exception e) {
			logger.error("解析excel文件出错", e.fillInStackTrace());
		}
		return list;
	}
	
	/**
	 * 将base64的数据转换成文件
	 * @param base64Data
	 * @return
	 */
	protected String getFileName(String base64Data){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String base = File.separator + "temp" + File.separator
				+ format.format(new Date()) + File.separator;
		String fileName = System.currentTimeMillis() + ".xlsx";
		String path = basePath + base + fileName;
		new File(basePath + base).mkdirs();
		
		if (base64Data.indexOf("base64,") >= 0) {
			base64Data = base64Data.split("base64,")[1];
		}
		
		if(Img2Base64Util.generateImage(base64Data, path)){
			return path;
		}else{
			return "";
		}
	}
	
	public abstract void intoDb(String base64Data, Map<String, Object> map);
}
