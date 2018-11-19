package com.towcent.base.common.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 图片处理工具类
 * 
 * @author huangtao
 * @date 2015年7月29日 下午5:32:10
 * @version 0.1.0 
 */
public class PictureUtils {
	
	public static Logger logger = LoggerFactory.getLogger(PictureUtils.class);
	
	/** 水印图片名称 */
	public static final String WATER_MARK = "watermark.png";
	/** 水印图片偏移位置 */
	public static final String WATER_MARK_OFFSET = "+60+60";
	
	public static final String SJPG = "_s.jpg";
	public static final String JPG = ".jpg";
	/** 缩略图像素 */
	public static final String THUMBNAIL_PEXLS = "230X230";
	
	/**
	 * 按尺寸压缩图片(等比缩放).
	 * @Title scale     
	 * @param pixels        压缩尺寸(等比缩放  eg.300X300)
	 * @param srcImageFile  原图文件(将要压缩的图片)
	 * @param resultPath    压缩后的图片Path
	 */
	@Deprecated
	public final static void scale(String pixels, File srcImageFile, String resultPath) {
		try {
			// 读入文件
			BufferedImage src = ImageIO.read(srcImageFile); 
			double percent = ((double)src.getWidth()/(double)src.getHeight());
			pixels = calculatePixels(pixels, percent);
			int width = Integer.valueOf(StringUtils.substringBefore(pixels, "X"));
			int height = Integer.valueOf(StringUtils.substringAfter(pixels, "X")); 
			
			// 判断压缩图片与原图的大小(如若原图比较小则不进行像素压缩)
			if (width > src.getWidth()) {
				width = src.getWidth();
			}
			if (height > src.getHeight()) {
				height = src.getHeight();
			}
			
			Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			//绘制缩小后的图
			g.drawImage(image, 0, 0, null); 
			g.dispose();
			//输出到文件流
			ImageIO.write(tag, "JPEG", new File(resultPath));
		} catch (IOException e) {
			logger.error("生成缩略图异常.", e);
		}
	}
	
	/**
	 * 按尺寸压缩图片(等比缩放).
	 * @Title scale         
	 * @param pixels        压缩尺寸(等比缩放  eg.300X300)
	 * @param srcImagePath  原图文件(将要压缩的图片)
	 * @param resultPath    压缩后的图片Path
	 */
	@Deprecated
	public final static void scale(String pixels, String srcImagePath, String resultPath) {
		scale(pixels, new File(srcImagePath), resultPath);
	}
		
	/**
	 * 获取文件大小(KB)
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static long getFileSizes(File file) {
		long s = 0;
		try {
			if (file.exists()) {
				FileInputStream fis = null;
				fis = new FileInputStream(file);
				s = fis.available();
				fis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return s/1024;
	}
	
	/**
	 * 计算图片文件的长宽比率
	 * @param file
	 * @return
	 */
	public static double getPercent(File file) {
		double result = 1;
		try {
			BufferedImage bi = ImageIO.read(file);
			result = ((double)bi.getWidth()/(double)bi.getHeight());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 通过文件的长宽比例计算需要压缩的像素大小
	 * @param pixels 300X300
	 * @param percent width/heigh
	 * @return
	 */
	public static String calculatePixels(String pixels, double percent) {
		if (StringUtils.isBlank(pixels)) {
			return pixels;
		}
		String[] ss = StringUtils.split(pixels, "X");
		if (ss.length <= 1) {
			int width = 0, heigh = 0;
			//判断是定高还是定宽
			if (pixels.endsWith("X")) { //顶宽
				width = Integer.valueOf(ss[0]);
				heigh = (int) (width/percent);
			} else {
				heigh = Integer.valueOf(ss[0]);
				width = (int) (heigh*percent);
			}
			return width + "X" + heigh;
		}
		int width = Integer.valueOf(ss[0]), heigh = Integer.valueOf(ss[1]);
		if (percent >= 1) {
			heigh = (int) (width/percent);
		} else if (percent < 1) {
			width = (int) (heigh * percent);
		}
		return width + "X" + heigh;
	}
	
	/**
	 * 获取图片像素 <br>
	 * 1. 宽度 <br>
	 * 2. 长度 <br>
	 * 3. 像素比例  宽度/长度 (cm)
	 * 
	 * @param file
	 * @return
	 */
	public static Map<Integer, Double> getPercentMap(File file) {
		Map<Integer, Double> map = new HashMap<Integer, Double>();
		try {
			double result = 1;
			BufferedImage bi = ImageIO.read(file);
			result = ((double)bi.getWidth()/(double)bi.getHeight());
			map.put(1, (double) bi.getWidth());
			map.put(2, (double) bi.getHeight());
			map.put(3, result);
		} catch (IOException e) {
			logger.error("获取图片像素异常.", e);
		}
		return map;
	}
	
	public static void main(String[] args) {
		
	}

}
