package com.towcent.base.common.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.towcent.base.common.model.JsSysDictData;
import com.towcent.base.common.utils.Encodes;
import com.towcent.base.common.utils.Reflections;
import com.towcent.base.common.utils.SpringContextHolder;
import com.towcent.base.common.utils.excel.annotation.ExcelField;
import com.towcent.base.manager.BaseCommonApi;

/**
 * 导出Excel文件（导出“XLSX”格式，支持大数据量导出 @see org.apache.poi.ss.SpreadsheetVersion）
 * 
 * 
 * @version 2013-04-21
 */
public class GoodsExportExcel {

	private static Logger log = LoggerFactory.getLogger(GoodsExportExcel.class);

	/** 工作薄对象 */
	private XSSFWorkbook wb;

	/** 工作表对象 */
	private Sheet sheet;

	/** 样式列表 */
	private Map<String, CellStyle> styles;

	/** 当前行号 */
	private int rownum;

	/** 注解列表（Object[]{ ExcelField, Field/Method }） */
	List<Object[]> annotationList = Lists.newArrayList();

	/**
	 * 构造函数
	 * 
	 * @param title
	 *            表格标题，传“空值”，表示无标题
	 * @param cls
	 *            实体对象，通过annotation.ExportField获取标题
	 */
	public GoodsExportExcel(String title, Class<?> cls) {
		this(title, cls, 1);
	}

	/**
	 * 初始化并创建表头
	 * 
	 * @param title
	 *            表格标题，传“空值”，表示无标题
	 * @param headers
	 *            表头数组
	 */
	public GoodsExportExcel(String title, String[] headers) {
		initialize(title, Lists.newArrayList(headers));
	}

	/**
	 * 初始化并创建表头
	 * 
	 * @param title
	 *            表格标题，传“空值”，表示无标题
	 * @param headerList
	 *            表头列表
	 */
	public GoodsExportExcel(String title, List<String> headerList) {
		initialize(title, headerList);
	}

	/**
	 * 初始化并创建模版表头
	 * 
	 * @param sheetName
	 *            sheet名称
	 */
	public GoodsExportExcel(String sheetName) {
		initialize(sheetName);
	}

	/**
	 * 初始化并创建动态模版表头
	 * 
	 * @param sheetName
	 *            sheet名称
	 * @param newCells
	 *            新列列表
	 * @param maxColumn
	 *            模版最大列
	 */
	public GoodsExportExcel(String sheetName, String[] newCells, int maxColumn) {
		initialize(sheetName, newCells, maxColumn);
	}

	/**
	 * 构造函数
	 * 
	 * @param title
	 *            表格标题，传“空值”，表示无标题
	 * @param cls
	 *            实体对象，通过annotation.ExportField获取标题
	 * @param type
	 *            导出类型（1:导出数据；2：导出模板）
	 * @param groups
	 *            导入分组
	 */
	public GoodsExportExcel(String title, Class<?> cls, int type, int... groups) {
		// Get annotation field
		Field[] fs = cls.getDeclaredFields();
		for (Field f : fs) {
			ExcelField ef = f.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type() == 0 || ef.type() == type)) {
				if (groups != null && groups.length > 0) {
					boolean inGroup = false;
					for (int g : groups) {
						if (inGroup) {
							break;
						}
						for (int efg : ef.groups()) {
							if (g == efg) {
								inGroup = true;
								annotationList.add(new Object[] { ef, f });
								break;
							}
						}
					}
				} else {
					annotationList.add(new Object[] { ef, f });
				}
			}
		}
		// Get annotation method
		Method[] ms = cls.getDeclaredMethods();
		for (Method m : ms) {
			ExcelField ef = m.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type() == 0 || ef.type() == type)) {
				if (groups != null && groups.length > 0) {
					boolean inGroup = false;
					for (int g : groups) {
						if (inGroup) {
							break;
						}
						for (int efg : ef.groups()) {
							if (g == efg) {
								inGroup = true;
								annotationList.add(new Object[] { ef, m });
								break;
							}
						}
					}
				} else {
					annotationList.add(new Object[] { ef, m });
				}
			}
		}
		// Field sorting
		Collections.sort(annotationList, new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				return new Integer(((ExcelField) o1[0]).sort()).compareTo(new Integer(((ExcelField) o2[0]).sort()));
			};
		});
		// Initialize
		List<String> headerList = Lists.newArrayList();
		for (Object[] os : annotationList) {
			String t = ((ExcelField) os[0]).title();
			// 如果是导出，则去掉注释
			if (type == 1) {
				String[] ss = StringUtils.split(t, "**", 2);
				if (ss.length == 2) {
					t = ss[0];
				}
			}
			headerList.add(t);
		}
		initialize(title, headerList);
	}

	private void initialize(String sheetName, String[] newCells, int maxColumn) {
		try {
			// 获取已有模版
			String path = this.getClass().getClassLoader().getResource("/").getPath();
			File file = new File(path);
			path = file.getParentFile().getParent() + File.separator + "static" + File.separator + "template"
					+ File.separator + "goods_template.xlsx";
			log.info("template file path : " + path);
			InputStream in = new FileInputStream(new File(path));
			wb = new XSSFWorkbook(in);
			// 重命名
			wb.setSheetName(0, sheetName);
			// 初始化样式
			this.styles = createStyles(wb);
			// 新增标题列
			sheet = wb.getSheetAt(0);
			Row r = sheet.getRow(0);
			for (int i = 0; i < newCells.length; i++) {
				Cell nCell = this.addCell(r, maxColumn++, newCells[i]);
				nCell.setCellStyle(r.getCell(7).getCellStyle());
				XSSFDrawing p = (XSSFDrawing) sheet.createDrawingPatriarch();
				XSSFComment comment = p.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
				comment.setString(new XSSFRichTextString("该列是商品属性"));
				nCell.setCellComment(comment);
			}

			rownum++;
			// 得到第1行的第一个单元格的样式
			Row row = sheet.getRow(1);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				styles.put("data-style-" + i, row.getCell(i).getCellStyle());
			}
			sheet.removeRow(row);
		} catch (Exception e) {
			log.error("初始化失败", e);
		}

	}

	private void initialize(String sheetName) {
		try {
			// 获取已有模版
			String path = this.getClass().getClassLoader().getResource("/").getPath();
			File file = new File(path);
			path = file.getParentFile().getParent() + File.separator + "static" + File.separator + "template"
					+ File.separator + "goods_template.xlsx";
			log.info("template file path : " + path);
			InputStream in = new FileInputStream(new File(path));
			wb = new XSSFWorkbook(in);
			wb.setSheetName(0, sheetName);
			this.styles = createStyles(wb);

			sheet = wb.getSheetAt(0);
			rownum++;
			// 得到第1行的第一个单元格的样式
			Row row = sheet.getRow(1);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				styles.put("data-style-" + i, row.getCell(i).getCellStyle());
			}
			sheet.removeRow(row);
		} catch (Exception e) {
			log.error("初始化失败", e);
		}

	}

	/**
	 * 初始化函数
	 * 
	 * @param title
	 *            表格标题，传“空值”，表示无标题
	 * @param headerList
	 *            表头列表
	 */
	private void initialize(String title, List<String> headerList) {
		this.wb = new XSSFWorkbook();
		this.sheet = wb.createSheet("Sheet1");
		this.styles = createStyles(wb);
		if (headerList == null) {
			throw new RuntimeException("headerList not null!");
		}
		Row headerRow = sheet.createRow(rownum++);
		headerRow.setHeightInPoints(16);
		for (int i = 0; i < headerList.size(); i++) {
			Cell cell = headerRow.createCell(i);
			if (i == 7) {
				cell.setCellStyle(styles.get("title_green"));
			} else if (i == 9) {
				cell.setCellStyle(styles.get("title_msg"));
			} else {
				cell.setCellStyle(styles.get("title_red"));
			}

			String[] ss = StringUtils.split(headerList.get(i), "**", 2);
			if (ss.length == 2) {
				cell.setCellValue(ss[0]);
				Comment comment = this.sheet.createDrawingPatriarch()
						.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
				comment.setString(new XSSFRichTextString(ss[1]));
				cell.setCellComment(comment);
			} else {
				cell.setCellValue(headerList.get(i));
			}
			sheet.autoSizeColumn(1,true);
		}
		for (int i = 0; i < headerList.size(); i++) {
			int colWidth = sheet.getColumnWidth(i) * 2;
			sheet.setColumnWidth(i, colWidth > 3000 ? 3000 : colWidth);
		}
		log.debug("Initialize success.");
	}

	/**
	 * 创建表格样式
	 * 
	 * @param wb
	 *            工作薄对象
	 * @return 样式列表
	 */
	private Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font titleFont = wb.createFont();
		titleFont.setFontName("宋体");
		titleFont.setColor(Font.COLOR_RED);
		titleFont.setFontHeightInPoints((short) 11);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(titleFont);
		styles.put("title_red", style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		titleFont = wb.createFont();
		titleFont.setFontName("宋体");
		titleFont.setColor(HSSFColor.GREEN.index);
		titleFont.setFontHeightInPoints((short) 11);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(titleFont);
		styles.put("title_green", style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.VERTICAL_TOP);
		style.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		titleFont = wb.createFont();
		titleFont.setFontName("宋体");
		style.setWrapText(true);
		titleFont.setColor(HSSFColor.RED.index);
		titleFont.setFontHeightInPoints((short) 11);
		style.setFont(titleFont);
		styles.put("title_msg", style);

		style = wb.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		Font dataFont = wb.createFont();
		dataFont.setFontName("宋体");
		dataFont.setFontHeightInPoints((short) 11);
		style.setFont(dataFont);
		styles.put("data", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_LEFT);
		styles.put("data1", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_CENTER);
		styles.put("data2", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		styles.put("data3", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		// style.setWrapText(true);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font headerFont = wb.createFont();
		headerFont.setFontName("宋体");
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(headerFont);
		styles.put("header", style);

		return styles;
	}

	/**
	 * 添加一行
	 * 
	 * @return 行对象
	 */
	public Row addRow() {
		return sheet.createRow(rownum++);
	}

	/**
	 * 添加一个单元格
	 * 
	 * @param row
	 *            添加的行
	 * @param column
	 *            添加列号
	 * @param val
	 *            添加值
	 * @return 单元格对象
	 */
	public Cell addCell(Row row, int column, Object val) {
		return this.addCell(row, column, val, 0, Class.class);
	}

	/**
	 * 添加一个单元格
	 * 
	 * @param row
	 *            添加的行
	 * @param column
	 *            添加列号
	 * @param val
	 *            添加值
	 * @param align
	 *            对齐方式（1：靠左；2：居中；3：靠右）
	 * @return 单元格对象
	 */
	public Cell addCell(Row row, int column, Object val, int align, Class<?> fieldType) {
		Cell cell = row.createCell(column);
		try {
			if (val == null) {
				cell.setCellValue("");
			} else if (val instanceof String) {
				cell.setCellValue((String) val);
			} else if (val instanceof Integer) {
				cell.setCellValue((Integer) val);
			} else if (val instanceof Long) {
				cell.setCellValue((Long) val);
			} else if (val instanceof Double) {
				cell.setCellValue((Double) val);
			} else if (val instanceof Float) {
				cell.setCellValue((Float) val);
			} else if (val instanceof Date) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//DataFormat format = wb.createDataFormat();
				// style.setDataFormat(format.getFormat("yyyy-MM-dd"));
				cell.setCellValue(sdf.format(val));
			} else {
				if (fieldType != Class.class) {
					cell.setCellValue((String) fieldType.getMethod("setValue", Object.class).invoke(null, val));
				} else {
					cell.setCellValue((String) Class
							.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(),
									"fieldtype." + val.getClass().getSimpleName() + "Type"))
							.getMethod("setValue", Object.class).invoke(null, val));
				}
			}
		} catch (Exception ex) {
			log.info("Set cell value [" + row.getRowNum() + "," + column + "] error: " + ex.toString());
			cell.setCellValue(val.toString());
		}
		CellStyle style = styles.get("data-style-" + column);
		if (style != null) {
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cell.setCellStyle(style);
		}

		return cell;
	}

	/**
	 * 添加数据（通过annotation.ExportField添加数据）
	 * 
	 * @return list 数据列表
	 */
	public <E> GoodsExportExcel setDataList(List<E> list) {
		BaseCommonApi baseCommonApi = SpringContextHolder.getBean(BaseCommonApi.class);
		for (E e : list) {
			int colunm = 0;
			Row row = this.addRow();
			StringBuilder sb = new StringBuilder();
			for (Object[] os : annotationList) {
				ExcelField ef = (ExcelField) os[0];
				Object val = null;
				// Get entity value
				try {
					if (StringUtils.isNotBlank(ef.value())) {
						val = Reflections.invokeGetter(e, ef.value());
					} else {
						if (os[1] instanceof Field) {
							val = Reflections.invokeGetter(e, ((Field) os[1]).getName());
						} else if (os[1] instanceof Method) {
							val = Reflections.invokeMethod(e, ((Method) os[1]).getName(), new Class[] {},
									new Object[] {});
						}
					}
					// If is dict, get dict label
					if (StringUtils.isNotBlank(ef.dictType())) {
						JsSysDictData dictDtl = baseCommonApi.getDictByKeyVal(0, ef.dictType(), val == null ? "" : val.toString());
						val = (dictDtl == null ? "" : dictDtl.getDictLabel());
						// val = DictUtils.getDictLabel(val==null?"":val.toString(), ef.dictType(), "");
					}
				} catch (Exception ex) {
					// Failure to ignore
					log.info(ex.toString());
					val = "";
				}
				this.addCell(row, colunm++, val, ef.align(), ef.fieldType());
				sb.append(val + ", ");
			}
			log.debug("Write success: [" + row.getRowNum() + "] " + sb.toString());
		}
		return this;
	}

	/**
	 * 输出数据流
	 * 
	 * @param os
	 *            输出数据流
	 */
	public GoodsExportExcel write(OutputStream os) throws IOException {
		wb.write(os);
		return this;
	}

	/**
	 * 输出到客户端
	 * 
	 * @param fileName
	 *            输出文件名
	 */
	public GoodsExportExcel write(HttpServletResponse response, String fileName) throws IOException {
		response.reset();
		response.setContentType("application/octet-stream; charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));
		write(response.getOutputStream());
		return this;
	}

	/**
	 * 输出到文件
	 * 
	 * @param fileName
	 *            输出文件名
	 */
	public GoodsExportExcel writeFile(String name) throws FileNotFoundException, IOException {
		FileOutputStream os = new FileOutputStream(name);
		this.write(os);
		return this;
	}

	/**
	 * 清理临时文件
	 */
	public GoodsExportExcel dispose() {
		// wb.dispose();
		return this;
	}

	// /**
	// * 导出测试
	// */
	// public static void main(String[] args) throws Throwable {
	//
	// List<String> headerList = Lists.newArrayList();
	// for (int i = 1; i <= 10; i++) {
	// headerList.add("表头"+i);
	// }
	//
	// List<String> dataRowList = Lists.newArrayList();
	// for (int i = 1; i <= headerList.size(); i++) {
	// dataRowList.add("数据"+i);
	// }
	//
	// List<List<String>> dataList = Lists.newArrayList();
	// for (int i = 1; i <=1000000; i++) {
	// dataList.add(dataRowList);
	// }
	//
	// ExportExcel ee = new ExportExcel("表格标题", headerList);
	//
	// for (int i = 0; i < dataList.size(); i++) {
	// Row row = ee.addRow();
	// for (int j = 0; j < dataList.get(i).size(); j++) {
	// ee.addCell(row, j, dataList.get(i).get(j));
	// }
	// }
	//
	// ee.writeFile("target/export.xlsx");
	//
	// ee.dispose();
	//
	// log.debug("Export success.");
	//
	// }

}
