/*
 * All rights Reserved, Designed By www.songywang.com
 * @Project : base-framework-common
 * @Title : ThumbnailatorUtil.java
 * @Package : com.towcent.base.common.utils
 * @date : 2018年8月1日上午1:04:03
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
package com.towcent.base.common.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.coobird.thumbnailator.Thumbnails;

/**
 * @ClassName: ThumbnailatorUtil 
 * @Description: 图片处理工具类
 *
 * @author huangtao
 * @date 2018年8月1日 上午1:04:03
 * @version 1.0.0
 * @Copyright: 2018 www.songywang.com Inc. All rights reserved. 
 * 注意：本内容仅限于深圳市众旺网络科技有限公司内部传阅，禁止外泄以及用于其他的商业项目
 */
public class ThumbnailatorUtil {
	
	public static Logger logger = LoggerFactory.getLogger(ThumbnailatorUtil.class);
	
	// 1. 加载图片源
	// File file = new File("/Users/qiangzi/data/img","beauty.jpg");
	// Builder<File> builder = Thumbnails.of(file);

	// 2. 缩放
	// uilder = builder.scale(0.9);   //参数是浮点数,大于1表示放大，小于1表示缩小
	
	// 3. 质量压缩
	// builder.outputQuality(0.9); //参数是浮点数，0-1之间
	
	// 4. 剪裁
	// builder.sourceRegion(100, 100, 300, 300);    
	// builder.sourceRegion(Positions.CENTER, 200, 200);
	
	// 5. 根据宽度来缩放
	// builder.width(500);
	
	// 6. 根据高度来缩放
	// builder.height(500);
	
	// 7. 在调整尺寸时保持比例
	// builder.keepAspectRatio(true);  //默认为true，如果要剪裁到特定的比例，设为false即可
	
	// 8. 根据宽度和高度进行缩放
	// builder.size(600, 700);
	// 如果设置了keepAspectRatio(true)，将按比例进行缩放，否则将强制按尺寸输出
	// 缩放策略：如果宽度缩放比>高度缩放比就以宽度来缩放，反之以高度缩放
	
	// 9. 将图片放入内存
	// File file2 = new File("/Users/qiangzi/data/img","logo.png");
	// BufferedImage bufferedImage = Thumbnails.of(file2).scale(1.0).outputQuality(1.0).asBufferedImage();
	// *必须要指定scale
	
	// 10. 加水印
	// builder.watermark(Positions.BOTTOM_RIGHT, bufferedImage, 1.0f);
	// 第一个参数是加水印的位置
	// 第二个参数是要加水印的图片
	// 第三个参数是水印图片的透明度
	// 经过测试：gif图片的彩色会变成黑白，所以尽量使用jpg或png图片吧

	// 11. 输出图片
	// 不管对图片进行什么操作，只有输出才能看到效果
	// builder.toFile(File file);
	
	// 12. *注意：scale、width|height、size三者不能同时共存，但必须要有一个
	
	// 13. 链式调用案例
	// 将原图缩放到宽度为500，压缩质量90%
	// Thumbnails.of(file).width(500).outputQuality(0.9).toFile(file);
	// 将原图按比例缩放，最宽300，最高400，不进行质量压缩
	// Thumbnails.of(file).size(300, 400).outputQuality(1.0).toFile(file);
	
	/**
	 * 按指定像素进行压缩(等比缩放).
	 * @Title thumbnail
	 * @param pixels          压缩尺寸(等比缩放  eg.300X300)
	 * @param srcFilePath     原图文件Path(将要压缩的图片)
	 * @param destFilePath    压缩后的图片Path
	 * @throws IOException 
	 */
	public static void thumbnail(String pixels, String srcFilePath, String destFilePath) {
		File file = new File(srcFilePath);
		thumbnail(pixels, file, destFilePath);
	}
	
	/**
	 * 按指定像素进行压缩(等比缩放).
	 * @Title thumbnail
	 * @param pixels          压缩尺寸(等比缩放  eg.300X300)
	 * @param srcFile     原图文件(将要压缩的图片)
	 * @param destFilePath    压缩后的图片Path
	 * @throws IOException 
	 */
	public static void thumbnail(String pixels, File file, String destFilePath) {
		try {
			int width = Integer.valueOf(StringUtils.substringBefore(pixels, "X"));
			int height = Integer.valueOf(StringUtils.substringAfter(pixels, "X"));
			Thumbnails.of(file).size(width, height).outputQuality(0.8).toFile(destFilePath);
		} catch (IOException e) {
			logger.error("生成缩略图异常.", e);
		}
	}
	
//	public static String generateCode(String codeUrl, Integer userId, String userName) {
//		Font font = new Font("微软雅黑", Font.PLAIN, 30);// 添加字体的属性设置
//		
//		String projectUrl = PathKit.getWebRootPath() + "/before/codeImg/";
//		String imgName = projectUrl + userId + ".png";
//		try {
//			// 加载本地图片
//			String imageLocalUrl = projectUrl + "weixincode2.png";
//			BufferedImage imageLocal = ImageIO.read(new File(imageLocalUrl));
//			// 加载用户的二维码
//			BufferedImage imageCode = ImageIO.read(new URL(codeUrl));
//			// 以本地图片为模板
//			Graphics2D g = imageLocal.createGraphics();
//			// 在模板上添加用户二维码(地址,左边距,上边距,图片宽度,图片高度,未知)
//			g.drawImage(imageCode, 575, imageLocal.getHeight() - 500, 350, 350, null);
//			// 设置文本样式
//			g.setFont(font);
//			g.setColor(Color.BLACK);
//			// 截取用户名称的最后一个字符
//			String lastChar = userName.substring(userName.length() - 1);
//			// 拼接新的用户名称
//			String newUserName = userName.substring(0, 1) + "**" + lastChar + " 的邀请二维码";
//			// 添加用户名称
//			g.drawString(newUserName, 620, imageLocal.getHeight() - 530);
//			// 完成模板修改
//			g.dispose();
//			// 获取新文件的地址
//			File outputfile = new File(imgName);
//			// 生成新的合成过的用户二维码并写入新图片
//			ImageIO.write(imageLocal, "png", outputfile);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// 返回给页面的图片地址(因为绝对路径无法访问)
//		imgName = Constants.PROJECT_URL + "codeImg/" + userId + ".png";
//	 
//		return imgName;
//	}
	
	/**
	 * 合成图片.
	 * @param fodderFile   需要合成的素材(头像/二维码)
	 * @param bgFile       背景图片(海报模板)
	 * @param outFile      输出目标文件
	 * @param x            x轴位置(左边距)
	 * @param y            y轴位置(上边距)
	 * @param width        素材宽度
	 * @param height       素粗高度
	 * @throws IOException
	 */
	public static void compositeImage(File fodderFile, File bgFile, File outFile, int x, int y, int width, int height) throws IOException {
		BufferedImage image = ImageIO.read(fodderFile);    // 需要合成的素材
		BufferedImage bg = ImageIO.read(bgFile);           // 背景图片
		Graphics2D g = bg.createGraphics();
		g.drawImage(image, x, y, width, height, null);
		g.dispose();
		bg.flush();
		image.flush();
		ImageIO.write(bg, "png", outFile);
	}
	
	/**
	 * 合成图片.
	 * @param fodderPath  需要合成的素材(头像/二维码等)
	 * @param bgPath      背景图片(海报)
	 * @param outPath     目标文件路径
	 * @param x           x轴位置(左边距)
	 * @param y           y轴位置(上边距)
	 * @param width       素材宽度
	 * @param height      素粗高度
	 * @throws IOException
	 */
	public static void compositeImage(String fodderPath, String bgPath, String outPath, int x, int y, int width, int height) throws IOException {
		BufferedImage image = ImageIO.read(new File(fodderPath)); // 需要合成的素材
		BufferedImage bg = ImageIO.read(new File(bgPath)); 	      // 背景图片（海报模板）
		Graphics2D g = bg.createGraphics();
		g.drawImage(image, x, y, width, height, null);
		g.dispose();
		bg.flush();
		image.flush();
		ImageIO.write(bg, "png", new File(outPath));
	}
	
//	public static void exportImg2(File phone,File headImg,File imgs){  
//        try {  
//            //1.jpg是你的 主图片的路径  
//            InputStream is = new FileInputStream(imgs);  
//            //通过JPEG图象流创建JPEG数据流解码器 
//            JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(is);  
//            //解码当前JPEG数据流，返回BufferedImage对象  
//            BufferedImage buffImg = jpegDecoder.decodeAsBufferedImage();  
//            //得到画笔对象  
//            Graphics g = buffImg.getGraphics();  
//              
//            //创建你要附加的图象。  
//            //小图片的路径  
//            ImageIcon imgIcon = new ImageIcon(headImg.getPath());   
//              
//            //得到Image对象。  
//            Image img = imgIcon.getImage();  
//              
//            //将小图片绘到大图片上。  
//            //5,300 .表示你的小图片在大图片上的位置。  
//            g.drawImage(img,400,800,null);  
////            g.drawImage(img,400,440,null);
//              
//            //设置颜色。  
//            g.setColor(Color.BLACK);  
//              
//              
//            //最后一个参数用来设置字体的大小，这是用来在海报上面写上字的方法  
//           /* Font f = new Font("宋体",Font.PLAIN,30); 
//            Color mycolor = Color.white;//new Color(0, 0, 255);  
//            g.setColor(mycolor);  
//            g.setFont(f);*/
//              
//            //10,20 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。  
////            g.drawString(username,180,894);  
//            
//          //创建你要附加的图象。  
//            //小图片的路径  
//            ImageIcon imgIconPhone = new ImageIcon(phone.getPath());   
//              
//            //得到Image对象。  
//            Image imgPhone = imgIconPhone.getImage();  
//              
//            //将小图片绘到大图片上。  
//            //5,300 .表示你的小图片在大图片上的位置。  
//            g.drawImage(imgPhone,172,866,null);  
////            g.drawImage(img,400,440,null);
//              
//            //设置颜色。  
//            g.setColor(Color.BLACK);  
//            
//            g.dispose();
//            
//            OutputStream os;  
//          
//            os = new FileOutputStream(imgs);  
////            String shareFileName = "\\upload\\" + System.currentTimeMillis() + ".jpg";  
////            os = new FileOutputStream(shareFileName);  
//             //创键编码器，用于编码内存中的图象数据。            
//            JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);  
//            en.encode(buffImg);           
//            
//            is.close();  
//            os.close();  
//        } catch (FileNotFoundException e) {  
//            // TODO Auto-generated catch block  
//            e.printStackTrace();  
//        } catch (ImageFormatException e) {  
//            // TODO Auto-generated catch block  
//            e.printStackTrace();  
//        } catch (IOException e) {  
//            // TODO Auto-generated catch block  
//            e.printStackTrace();  
//        }
//
//    }
	
	/**
	 * 生成图片文件的后缀（_wXh.jpg）.
	 * @Title getImageSuffix
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String getImageSuffix(InputStream is) throws IOException {
		StringBuffer sb = new StringBuffer("_");
		BufferedImage image = ImageIO.read(is);
		sb.append(image.getWidth());
		sb.append("X");
		sb.append(image.getHeight());
		sb.append(".jpg");
		return sb.toString();
	}
	
	public static void main(String[] args) throws IOException {
//		long t = System.currentTimeMillis();
//		thumbnail("80X80", "d:\\img.jpg", "d:\\123_s.jpg");
//		System.out.println("耗时：" + (System.currentTimeMillis() - t));
//		
//		long t1 = System.currentTimeMillis();
//		PictureUtils.scale("80X80", "d:\\img.jpg", "d:\\123_t_s.jpg");
//		System.out.println("耗时：" + (System.currentTimeMillis() - t1));
		
		compositeImage("d:\\pic\\code_130.jpg", "d:\\pic\\sucai.jpg", "d:\\pic\\t.png", 450, 1100, 130, 130);
	}
}

	