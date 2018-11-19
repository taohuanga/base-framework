/*
 * 文 件 名:  ImageMagickServiceImpl.java
 * 版   权: Copyright www.face-garden.com Corporation 2014 版权所有
 * 描     述:  <描述>
 * 修 改 人:  shiwei
 * 修改时间:  2014-9-27
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package com.towcent.base.service.impl;

import com.towcent.base.common.utils.CommandUtils;
import com.towcent.base.service.ImageMagickService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  shiwei
 * @version  [版本号, 2014-9-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service("imageMagickService")
public class ImageMagickServiceImpl implements ImageMagickService
{
	/**
	 * 日志对象
	 */
	public Logger logger = LoggerFactory.getLogger(getClass());
	
    @Override
    public boolean resizeImageMagick(String pixel, String inputPath, String outputPath)
    {
        logger.debug("resizeImageMagick compress begin...");

        StringBuffer commond = new StringBuffer(100);

        commond.append("convert -resize ");
        commond.append(pixel);
        commond.append("!  ");
        commond.append(inputPath);
        commond.append("  ");
        commond.append(outputPath);

        logger.debug("commond is ***" + commond.toString());

        return CommandUtils.exeCommand(commond.toString());
    }

    @Override
    public boolean waterMarkImage(String point, String inputPath, String waterMarkImage, String outputPath)
    {
        logger.debug("waterMarkImage compress begin...");

        StringBuffer commond = new StringBuffer(100);

        commond.append("convert ");
        commond.append(inputPath);
        commond.append(" ");
        commond.append(waterMarkImage);
        commond.append(" -gravity southeast -geometry ");
        commond.append(point);
        commond.append(" -composite ");
        commond.append(outputPath);

        logger.debug("commond is ***" + commond.toString());
        
        return CommandUtils.exeCommand(commond.toString());
    }

    @Override
    public boolean waterMarkText(String point, String fontpath, String fontSize, String fontColor, String text,
            String inputPath)
    {
        logger.debug("waterMarkImage compress begin...");

        StringBuffer commond = new StringBuffer(100);

        commond.append("mogrify -font ");
        commond.append(fontpath);
        commond.append("  -pointsize ");
        commond.append(fontSize);
        commond.append("  -fill ");
        commond.append(fontColor);
        commond.append(" -weight bolder -gravity southeast -annotate ");
        commond.append(point);
        commond.append(" @\"" + text + "\" ");
        commond.append(inputPath);

        logger.debug("commond is ***" + commond.toString());

        return CommandUtils.exeCommand(commond.toString());
    }

    @SuppressWarnings("static-access")
    @Override
    public boolean compressImage(String srcFilePath, String descFilePath, long imageMaxSize, String imageType)
    {
        logger.debug("image compress begin...");

        File file = null;
        BufferedImage src = null;
        FileOutputStream out = null;
        ImageWriter imgWrier;
        ImageWriteParam imgWriteParams;

        try
        {
            logger.debug("image compress...,srcFilePath=" + srcFilePath + "  descFilePath=" + descFilePath);

            file = new File(srcFilePath);
            // 指定写图片的方式为 jpg
            imgWrier = ImageIO.getImageWritersByFormatName(StringUtils.isEmpty(imageType) ? "jpg" : imageType).next();
            imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(null);
            // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
            imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

            float qality = Float.parseFloat((0 < imageMaxSize ? imageMaxSize : 300 * 1024) + "") / file.length();
            logger.debug("image compress qality = " + qality);

            // 这里指定压缩的程度，参数qality是取值0~1范围内，
            if (qality >= 1)
            {
                imgWriteParams.setCompressionQuality((float) 1);
            }
            else
            {
                imgWriteParams.setCompressionQuality(qality);
            }

            imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
            ColorModel colorModel = ImageIO.read(new File(srcFilePath)).getColorModel();// ColorModel.getRGBdefault();
            // 指定压缩时使用的色彩模式
            //          imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(
            //                colorModel, colorModel.createCompatibleSampleModel(16, 16)));
            imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel, colorModel
                    .createCompatibleSampleModel(16, 16)));

            src = ImageIO.read(file);
            out = new FileOutputStream(descFilePath);

            imgWrier.reset();
            // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
            // OutputStream构造
            imgWrier.setOutput(ImageIO.createImageOutputStream(out));
            // 调用write方法，就可以向输入流写图片
            imgWrier.write(null, new IIOImage(src, null, null), imgWriteParams);
            out.flush();
            out.close();

            return true;
        }
        catch (Exception e)
        {
            logger.error("", e);
            return false;
        }
        finally
        {
            if (null != out)
            {
                try
                {
                    out.close();
                }
                catch (IOException e)
                {
                	logger.error("", e);
                }
            }

            if (logger.isDebugEnabled()) {
                logger.debug("image compress end...");
            }
        }
    }

    @Override
    public boolean mergeImage(String srcFilePath, String appendFilePath, String descFilePath)
    {
        if (logger.isDebugEnabled()) {
            logger.debug("waterMarkImage compress begin...");
        }

        StringBuffer commond = new StringBuffer(100);

        commond.append("convert -append ");
        commond.append(srcFilePath);
        commond.append("  ");
        commond.append(appendFilePath);
        commond.append("  ");
        commond.append(descFilePath);

        if (logger.isDebugEnabled()) {
            logger.debug("commond is ***" + commond.toString());
        }
        return CommandUtils.exeCommand(commond.toString());
    }

	@Override
	public boolean compositeWaterMark(String point, String inputPath,
			String waterMarkImage, String outputPath) {
        logger.debug("waterMarkImage composite begin...");

        StringBuffer commond = new StringBuffer(100);
        
        // composite -gravity northwest -geometry +60+60  watermark.png 123.jpg 1231.jpg
        
        commond.append("composite -gravity northwest -geometry ");
        commond.append(point);
        commond.append(" ");
        commond.append(waterMarkImage);
        commond.append(" ");
        commond.append(inputPath);
        commond.append(" ");
        commond.append(outputPath);
        
        logger.debug("commond is ***" + commond.toString());
        
        return CommandUtils.exeCommand(commond.toString());
	}

}
