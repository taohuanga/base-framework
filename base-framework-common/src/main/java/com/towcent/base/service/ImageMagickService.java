/*
 * 文 件 名:  ImageMagickService.java
 * 版   权: Copyright www.face-garden.com Corporation 2014 版权所有
 * 描     述:  <描述>
 * 修 改 人:  shiwei
 * 修改时间:  2014-9-27
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package com.towcent.base.service;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  shiwei
 * @version  [版本号, 2014-9-27]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ImageMagickService
{
    /**
     * 图片裁剪
     * <功能详细描述>
     * @param pixel 处理的像素  例如（10X10）
     * @param inputPath 原始图片路径
     * @param outputPath 处理后的图片输出路径
     * @return 处理成功 true 处理失败 false
     * @see [类、类#方法、类#成员]
     */
    public boolean resizeImageMagick(String pixel, String inputPath, String outputPath);

    /**
     * 图片加图片水印
     * <功能详细描述>
     * @param point  加水印的位置（+5+10）+标示向左 5为高 10为宽
     * @param inputPath  原始图片路径
     * @param waterMarkImage 作为水印的图片路径
     * @param outputPath 处理后的图片输出路径
     * @return 处理成功 true 处理失败 false
     * @see [类、类#方法、类#成员]
     */
    public boolean waterMarkImage(String point, String inputPath, String waterMarkImage, String outputPath);

    /**
     * 图片加文字水印
     * <功能详细描述>
     * @param point   加水印的位置（+5+10）+标示向左 5为高 10为宽
     * @param fontpath  水印文字字体路径
     * @param fontSize  文字大小
     * @param fontColor 文字颜色
     * @param text   文字文件路径
     * @param inputPath 需要加水印的图片路径
     * @return 处理成功 true 处理失败 false
     * @see [类、类#方法、类#成员]
     */
    public boolean waterMarkText(String point, String fontpath, String fontSize, String fontColor, String text,
            String inputPath);

    /**
     * 图片压缩
     * <功能详细描述>
     * @param srcFilePath 原始图片路径
     * @param descFilePath 处理后的图片路径
     * @param imageMaxSize 图片最大字节 5M=5*1024*1024
     * @param imageType 图片类型  例如 jpg
     * @return 处理成功 true 处理失败 false
     * @see [类、类#方法、类#成员]
     */
    public boolean compressImage(String srcFilePath, String descFilePath, long imageMaxSize, String imageType);

    /**
     * 图片合并处理（向下合并）
     * <功能详细描述>
     * @param srcFilePath 原始图片路径
     * @param appendFilePath 被合并的图片路径
     * @param descFilePath 合并输出的图片路径
     * @return 处理成功 true 处理失败 false
     * @see [类、类#方法、类#成员]
     */
    public boolean mergeImage(String srcFilePath, String appendFilePath, String descFilePath);
    
    /**
     * 加图片水印  (使用图片合成命令)
     * @param point 加水印的位置（+5+10）+标示向左 5为高 10为宽
     * @param inputPath 原始图片位置
     * @param waterMarkImage 水印图片位置
     * @param outputPath 处理后的图片输出路径
     * @return
     */
    public boolean compositeWaterMark(String point, String inputPath, String waterMarkImage, String outputPath);
}
