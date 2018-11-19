package com.towcent.base.gen.component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.towcent.base.common.mapper.JaxbMapper;
import com.towcent.base.common.utils.FileUtils;
import com.towcent.base.common.utils.FreeMarkers;
import com.towcent.base.common.utils.excel.ImportExcel;
import com.towcent.base.gen.entity.GenInterface;
import com.towcent.base.gen.entity.GenParamEntry;
import com.towcent.base.gen.entity.GenTemplate;

/**
 * 生成代码工具类
 * 
 * @author huangtao
 *
 */
public class GenTools {

	private static Logger logger = LoggerFactory.getLogger(GenTools.class);

	/**生成的对象(.java)集合*/
	private static List<String> loopList = Lists.newArrayList();

	public static List<GenInterface> buildInterfaceList(String path) throws Exception {
		return buildInterfaceList(new File(path));
	}

	public static List<GenInterface> buildInterfaceList(File file) throws Exception {
		ImportExcel ie = new ImportExcel(file, 0, 0);
		List<GenInterface> genInterList = Lists.newArrayList();
		GenInterface genInter = null;
		Boolean isNew = null;
		GenParamEntry enterEntry = null;
		GenParamEntry outEntry = null;
		GenParamEntry secOutEntry = null;
		List<GenParamEntry> enterList = null;
		List<GenParamEntry> outList = null;
		List<GenParamEntry> secOutList = null;
		for (int i = 2; i <= ie.getLastDataRowNum(); i++) {
			Row row = ie.getRow(i);
			if (row == null) {
				continue;
			}
			if (isNew != null && isNew) {
				isNew = false;
			}
			for (int j = 0; j < row.getLastCellNum(); j++) {
				Object obj = ie.getCellValue(row, j);
				String val = trimObject(obj);

				if (StringUtils.isBlank(val)) {
					continue;
				}
				if (j == 0) {
					isNew = true;
					genInter = new GenInterface();
					genInter.setInterNo(val);

					// 初始化
					enterList = Lists.newArrayList();
					outList = Lists.newArrayList();
					secOutList = Lists.newArrayList();
					genInterList.add(genInter);
					genInter.setEnParamList(enterList);
					genInter.setOutParamList(outList);
					genInter.setSecOutParamList(secOutList);
				} else if (j == 1) {
					genInter.setInterName(val);
				} else if (j == 2) {
					genInter.setFunDesc(val);
				} else if (j == 3) {
					genInter.setReqType(val);
				} else if (j == 4) {
					genInter.setInterUrl(val);
					String[] arrays = StringUtils.split(val, "/");
					genInter.setModuleName(arrays[0]);
					genInter.setClassName(arrays[1]);
					genInter.setdClassName(firstUpperCase(genInter.getClassName()));
					genInter.setFunctionName(arrays[2]);
					genInter.setdFunctionName(firstUpperCase(genInter.getFunctionName()));
				} else if (j == 10) {
					genInter.setOutParamType(val);
				} else if (j == 15) {
					genInter.setSecOutParamType(val);
				} else if (j == 20) {
					genInter.setEntryName(val);
				}

				if (j == 5) {
					enterEntry = new GenParamEntry();
					enterEntry.setFieldName(val);
				} else if (j == 6) {
					enterEntry.setFieldType(val);
					enterEntry.setSimpleJavaType(getSimpleJavaType(val));
					enterEntry.setNotBaseField(isNotBaseField(val));
				} else if (j == 7) {
					enterEntry.setIsNotNull(val);
				} else if (j == 8) {
					enterEntry.setMaxLength(val);
				} else if (j == 9) {
					enterEntry.setDescription(val);
					enterList.add(enterEntry);
				} else if (j == 11) {
					outEntry = new GenParamEntry();
					outEntry.setFieldName(val);
				} else if (j == 12) {
					outEntry.setFieldType(val);
					outEntry.setSimpleJavaType(getSimpleJavaType(val));
					outEntry.setNotBaseField(isNotBaseField(val));
				} else if (j == 13) {
					outEntry.setDescription(val);
					outList.add(outEntry);
				} else if (j == 14) {

				} else if (j == 16) {
					secOutEntry = new GenParamEntry();
					secOutEntry.setFieldName(val);
				} else if (j == 17) {
					secOutEntry.setFieldType(val);
					secOutEntry.setSimpleJavaType(getSimpleJavaType(val));
					secOutEntry.setNotBaseField(isNotBaseField(val));
				} else if (j == 18) {
					secOutEntry.setDescription(val);
					secOutList.add(secOutEntry);
				} else if (j == 19) {

				}
			}
		}
		return genInterList;
	}

	private static String trimObject(Object o) {
		if (null == o) {
			return "";
		}
		return StringUtils.trimToEmpty(o.toString());
	}

	/**
	 * 首字母大写
	 * 
	 * @param str
	 * @return
	 */
	private static String firstUpperCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	private static String getSimpleJavaType(String fieldType) {
		if (StringUtils.isBlank(fieldType)) {
			return "String";
		}
		if ("Date".equalsIgnoreCase(fieldType)) {
			return "Date";
		}
		if ("String".equalsIgnoreCase(fieldType)) {
			return "String";
		}
		if ("int".equalsIgnoreCase(fieldType) || "integer".equalsIgnoreCase(fieldType)) {
			return "Integer";
		}
		if ("double".equalsIgnoreCase(fieldType)) {
			return "BigDecimal";
		}
		if ("list".equalsIgnoreCase(fieldType)) {
			return "List";
		}
		if ("boolean".equalsIgnoreCase(fieldType)) {
			return "Boolean";
		}
		if ("long".equalsIgnoreCase(fieldType)) {
			return "Long";
		}
		return "String";
	}

	private static boolean isNotBaseField(String fieldType) {
		if (StringUtils.isBlank(fieldType)) {
			return false;
		}
		if ("list".equalsIgnoreCase(fieldType)) {
			return true;
		}
		return false;
	}

	/**
	 * XML文件转换为对象
	 * 
	 * @param fileName
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fileToObject(String fileName, Class<?> clazz) {
		try {
			String pathName = "/template/" + fileName;
			// logger.debug("File to object: {}", pathName);
			Resource resource = new ClassPathResource(pathName);
			InputStream is = resource.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuilder sb = new StringBuilder();
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				sb.append(line).append("\r\n");
			}
			if (is != null) {
				is.close();
			}
			if (br != null) {
				br.close();
			}
			return (T) JaxbMapper.fromXml(sb.toString(), clazz);
		} catch (IOException e) {
			logger.warn("Error file convert: {}", e.getMessage());
		}
		return null;
	}

	public static List<GenTemplate> getGenTemplateList() {
		List<GenTemplate> list = Lists.newArrayList();
		list.add((GenTemplate) fileToObject("controller.xml", GenTemplate.class));
		list.add((GenTemplate) fileToObject("paramIn.xml", GenTemplate.class));
		list.add((GenTemplate) fileToObject("paramOut.xml", GenTemplate.class));
		list.add((GenTemplate) fileToObject("paramOutSec.xml", GenTemplate.class));
		list.add((GenTemplate) fileToObject("service.xml", GenTemplate.class));
		list.add((GenTemplate) fileToObject("serviceImpl.xml", GenTemplate.class));
		list.add((GenTemplate) fileToObject("test.xml", GenTemplate.class));
		return list;
	}

	private static GenTemplate getGenBaseControllerTemplate() {
		return (GenTemplate) fileToObject("baseController.xml", GenTemplate.class);
	}

	private static GenTemplate getGenBaseServiceTemplate() {
		return (GenTemplate) fileToObject("baseService.xml", GenTemplate.class);
	}

	private static GenTemplate getGenBaseServiceImplTemplate() {
		return (GenTemplate) fileToObject("baseServiceImpl.xml", GenTemplate.class);
	}

	private static GenTemplate getGenBaseTestTemplate() {
		return (GenTemplate) fileToObject("baseTest.xml", GenTemplate.class);
	}

	/**
	 * 获取数据模型
	 * 
	 * @param genScheme
	 * @param genTable
	 * @return
	 */
	public static Map<String, Object> getDataModel(GenInterface gen) {
		Map<String, Object> model = Maps.newHashMap();

		model.put("packageName", "com.health2world.medical.village.portal");
		model.put("moduleName", gen.getModuleName()); // 模块名
		model.put("className", gen.getClassName()); // 小写类名
		model.put("ClassName", gen.getdClassName()); // 类名(首字母大写)

		model.put("functionName", gen.getFunctionName()); // 方法名
		model.put("FunctionName", gen.getdFunctionName()); // 方法名(首字母大写)
		model.put("functionAuthor", ""); // 作者名称
		model.put("functionVersion", "0.0.2"); // 版本号

		model.put("enParamList", gen.getEnParamList()); // 入参列表
		model.put("enImportList", getGenParamImportList(gen.getEnParamList()));

		model.put("outParamType", gen.getOutParamType()); // 出参类型
		model.put("outParamList", gen.getOutParamList()); // 出参列表
		model.put("outImportList", getGenParamImportList(gen.getOutParamList()));

		model.put("secOutParamType", gen.getSecOutParamType()); // 二级出参类型
		model.put("secOutParamList", gen.getSecOutParamList()); // 二级出参列表
		model.put("secOutImportList", getGenParamImportList(gen.getSecOutParamList()));

		model.put("entryName", gen.getEntryName()); // 接口对应到数据库的实体类
		model.put("interNo", gen.getInterNo()); // 接口编号
		model.put("interName", gen.getInterName()); // 接口名称
		model.put("funDesc", gen.getFunDesc()); // 接口功能简述
		model.put("reqType", gen.getReqType()); // 接口请求类型(POST|GET)
		model.put("interUrl", gen.getInterUrl()); // 接口URL

		return model;
	}

	/**
	 * 生成到文件
	 * 
	 * @param tpl
	 * @param model
	 * @param replaceFile
	 * @return
	 */
	public static String generateToFile(GenTemplate tpl, Map<String, Object> model, boolean isReplaceFile) {

		// 获取生成文件
		String fileName = getProjectPath() + File.separator
				+ StringUtils.replaceEach(FreeMarkers.renderString(tpl.getFilePath() + "/", model),
						new String[] { "//", "/", "." },
						new String[] { File.separator, File.separator, File.separator })
				+ FreeMarkers.renderString(tpl.getFileName(), model);
		logger.debug(" fileName === " + fileName);
		try {
			// 如果选择替换文件，则删除原文件
			if (isReplaceFile && !loopList.contains(fileName)) {
				FileUtils.deleteFile(fileName);
			}
			loopList.add(fileName);

			File file = new File(fileName);

			// 获取生成文件内容
			String content = "";
			String content2 = "";
			//自定义导入模块
			String tempIn = "";
			String tempOut = "";
			//自定义方法体模块
			String temp2 = "";
			//自定义测试模块
			String temp3 = "";

			content2 = FreeMarkers.renderString(StringUtils.trimToEmpty(tpl.getContent()), model);

			Boolean isHasBase = true;
			if (tpl.getFileName().endsWith("Controller.java")) {
				content = getGenBaseControllerTemplate().getContent();
			} else if (tpl.getFileName().endsWith("Service.java")) {
				content = getGenBaseServiceTemplate().getContent();
			} else if (tpl.getFileName().endsWith("ServiceImpl.java")) {
				content = getGenBaseServiceImplTemplate().getContent();
			} else if (tpl.getFileName().endsWith("ControllerTest.java")) {
				content = getGenBaseTestTemplate().getContent();
			} else {
				content = tpl.getContent();
				isHasBase = false;
			}

			if (!isHasBase) {
				content2 = null;
			}

			if (!file.exists()) {
				content = FreeMarkers.renderString(StringUtils.trimToEmpty(content), model);
			} else {
				content = FileUtils.readFileToString(file, "utf-8");
			}
			logger.debug(" content === \r\n" + content);

			//获取相应模块的字符串
			if (StringUtils.isNotBlank(content2)) {
				tempIn = content2.substring(0, content2.indexOf(";") + 1) + "\n";
				if(model.get("outParamList") != null && tpl.getFileName().endsWith("ServiceImpl.java")){
					tempOut = content2.substring(content2.indexOf("In;")+3, content2.indexOf("Out;")+5);
				}
				
				if (tpl.getFileName().endsWith("ControllerTest.java")) {
					temp2 = content2.substring(content2.indexOf("@Test") - 2);
					temp3 = content2.substring(content2.indexOf("descMap.") - 2, content2.indexOf(");") + 3);
				} else {
					temp2 = StringUtils.substringAfter(content2, "||");
				}
			}

			// 创建并写入文件
			if (!file.exists()) {
				FileUtils.createFile(fileName);
				FileUtils.writeToFile(fileName, content, true);
			}
			
			//导入模块
			if (StringUtils.isNotBlank(tempIn)) {
				FileUtils.writeToFile(file, tempIn, content.indexOf("import"));
				// FileUtils.writeToFile(file, tempOut, content.indexOf("import"));
			}
			
			if (StringUtils.isNotBlank(tempOut)) {
				FileUtils.writeToFile(file, tempOut, content.indexOf("import"));
			}

			//方法体模块
			if (StringUtils.isNotBlank(temp2)) {
				File nFile = new File(fileName);
				FileUtils.writeToFile(nFile, temp2 + "\n", nFile.length() - 1);
			}

			//测试用例模块
			if (StringUtils.isNotBlank(temp3)) {
				File nFile = new File(fileName);
				content = FileUtils.readFileToString(nFile, "utf-8");
				int pos = content.indexOf("descMap.")-2;
				if(pos < 0){
					pos = content.indexOf("static {")+9;
				}
				FileUtils.writeToFile(nFile, temp3, pos);
			}
			
			//import出参类型
			if(tpl.getFileName().endsWith("ServiceImpl.java") && model.get("outParamType") != null){
				File nFile = new File(fileName);
				String importStr = "";
				String outParamType = model.get("outParamType").toString();
				if(outParamType.equals("page")){
					importStr = "import com.towcent.base.common.page.PaginationDto;\nimport com.towcent.base.common.page.SimplePageDto;\n";
				}else if(outParamType.equals("List")){
					importStr = "import java.util.List;\nimport com.google.common.collect.Lists;\n";
				}
				
				if(StringUtils.isNotBlank(importStr)){
					FileUtils.writeToFile(nFile, importStr, content.indexOf("import"));
				}
			}
			
			logger.debug(" file create === " + fileName);
			return "生成成功：" + fileName + "<br/>";
		} catch (IOException e) {
			logger.debug("===生成失败");
		}
		return "生成失败：" + fileName + "<br/>";
	}

	/**
	 * 获取工程路径
	 * 
	 * @return
	 */
	public static String getProjectPath() {
		String projectPath = "";
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null) {
				while (true) {
					File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
					if (f == null || f.exists()) {
						break;
					}
					if (file.getParentFile() != null) {
						file = file.getParentFile();
					} else {
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectPath;
	}

	private static Set<String> getGenParamImportList(List<GenParamEntry> list) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		Set<String> importList = Sets.newHashSet();
		for (GenParamEntry genParamEntry : list) {
			if ("BigDecimal".equals(genParamEntry.getSimpleJavaType())) {
				importList.add("java.math.BigDecimal");
			}
			if ("Date".equals(genParamEntry.getSimpleJavaType())) {
				importList.add("java.util.Date");
				importList.add("com.fasterxml.jackson.annotation.JsonFormat");
			}
			if ("List".equals(genParamEntry.getSimpleJavaType())) {
				importList.add("java.util.List");
			}
		}

		return importList;
	}
}
