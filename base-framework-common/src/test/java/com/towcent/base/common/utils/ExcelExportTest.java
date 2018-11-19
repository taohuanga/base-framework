/*
 * All rights Reserved, Designed By www..com
 * @Project : base-framework-common
 * @Title : ExcelExportTest.java
 * @Package : com.towcent.base.common.utils
 * @date : 2017年12月5日上午11:13:20
 * @Copyright: 2017 www..com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市前海金田科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
package com.towcent.base.common.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.towcent.base.BaseTest;
import com.towcent.base.common.utils.excel.ExportExcel;

/**
 * @ClassName: ExcelExportTest 
 * @Description: 导出excel工具类测试用例 
 *
 * @author huangtao
 * @date 2017年12月5日 上午11:13:20
 * @version 1.0.0
 * @Copyright: 2017 www..com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市前海金田科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class ExcelExportTest extends BaseTest {
	
	private static Logger log = LoggerFactory.getLogger(ExportExcel.class);
	
	/**
	 * @Title: export1
	 * @Description: 普通导出.
	 * @return: void
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Test public void export1() throws FileNotFoundException, IOException { 
		List<String> headerList = Lists.newArrayList();
		for (int i = 1; i <= 10; i++) {
			headerList.add("表头"+i);
		}
		
		List<String> dataRowList = Lists.newArrayList();
		for (int i = 1; i <= headerList.size(); i++) {
			dataRowList.add("数据"+i);
		}
		
		List<List<String>> dataList = Lists.newArrayList();
		for (int i = 1; i <=100; i++) {
			dataList.add(dataRowList);
		}
		
		ExportExcel ee = new ExportExcel("表格标题", headerList);
		
		for (int i = 0; i < dataList.size(); i++) {
			Row row = ee.addRow();
			for (int j = 0; j < dataList.get(i).size(); j++) {
				ee.addCell(row, j, dataList.get(i).get(j));
			}
		}
		
		ee.writeFile("target/export.xlsx");

		ee.dispose();
		
		log.debug("Export success.");
	}
	
	/**
	 * @Title: export2
	 * @Description: 通过对象注解进行导出.
	 * @return: void
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Test public void export2() throws FileNotFoundException, IOException {
		List<Student> list = Lists.newArrayList();
		Student st = null;
		for (int i = 0; i < 10; i++) {
			st = new Student();
			st.setName("name" + i);
			st.setAge(20 + i);
			st.setDept("部门" + i);
			st.setSex("1");
			list.add(st);
		}
		
		ExportExcel ee = new ExportExcel("学生信息表", Student.class);
		ee.setDataList(list);
		
		ee.writeFile("target/Student.xlsx");
		ee.dispose();
		
		log.debug("Export success.");
	}
	
}

	