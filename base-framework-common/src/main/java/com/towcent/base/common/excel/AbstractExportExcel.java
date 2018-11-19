/**
 * 
 */
package com.towcent.base.common.excel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessageChannel;

import com.towcent.base.common.utils.SpringFTPUtil;
import com.towcent.base.common.utils.excel.ExportExcel;


/**
 * 导出导入模板工具类
 * @author shiwei
 *
 */
public abstract class AbstractExportExcel<T> {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private MessageChannel ftpChannel;

	@Value("${excel.basePath}")
	private String basePath;

	@Value("${image.url.header}")
	private String baseUrl;
	/**
	 * 
	 * @return
	 */
	protected abstract String[] getHeader();
	
	/**
	 * 模板标题
	 * @return
	 */
	protected abstract String getTitle();
	
	/**
	 * 获取模板
	 * map 查询参数
	 * @return
	 */
	public String  getTemplate(Map<String, Object> map){
		try {
		    return exportExcel(getTitle(), getHeader(), getDataList(map));
		} catch (Exception e) {
			logger.error("解析excel文件出错", e.fillInStackTrace());
		}
		return "";
	}
	
	/**
	 * 获取模板中需要填写的数据
	 * @param map
	 */
	protected abstract List<List<String>> getDataList(Map<String, Object> map);
	
	/**
	 * 导出excel
	 * 
	 * @param title
	 *            文件标题
	 * @param headers
	 *            表格头
	 * @param dataList
	 *            数据
	 * @return
	 */
	protected String exportExcel(String title, String[] headers,
			List<List<String>> dataList) {
		String url = "";
		try {
			ExportExcel exportExcel = new ExportExcel(title, headers);

			for (int i = 0; i < dataList.size(); i++) {
				Row row = exportExcel.addRow();
				for (int j = 0; j < dataList.get(i).size(); j++) {
					exportExcel.addCell(row, j, dataList.get(i).get(j));
				}
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String base ="/xlsx/" + format.format(new Date()) + "/";
			String fileName = System.currentTimeMillis() + ".xlsx";
			String path = basePath + base + fileName;
			new File(basePath + base).mkdirs();
			exportExcel.writeFile(path);
			exportExcel.dispose();

			// 上传文件到FTP服务器
			boolean result = SpringFTPUtil.ftpUpload(ftpChannel,
					new File(path), base);
			if (result) {
				url = (baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl) + base + fileName;
				// 删除文件
				File file = new File(path);
				file.deleteOnExit();
			}
		} catch (Exception e) {
			logger.error("导出失败", e.fillInStackTrace());
		}
		return url;
	}
}
