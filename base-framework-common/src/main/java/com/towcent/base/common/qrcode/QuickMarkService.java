/*
 * 文 件 名:  QuickMarkService.java
 * 版   权: Copyright www.face-garden.com Corporation 2014 版权所有
 * 描     述:  <描述>
 * 修 改 人:  shiwei
 * 修改时间:  2014-4-2
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package com.towcent.base.common.qrcode;

import java.io.InputStream;

/**
 * 二维码生成工具
 * <功能详细描述>
 * 
 * @author  shiwei
 * @version  [版本号, 2014-4-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface QuickMarkService
{  
    
    /**
     * 生成二维码(QRCode)图片（google Zxing）
     * @param content 存储内容
     * @param imgPath 图片路径
     * @param imgType 图片类型(后缀如jpg)
     * @param size 二维码尺寸  宽度和高度一样
     */
    public void encoderGQRCode2File(String content, String imgPath, String imgType, int size);
    
    /**
     * 解析二维码（QRCode）（google Zxing）
     * @param imgPath 图片路径
     * @return
     */
    public String decoderGQRCode4File(String imgPath);
    
    /**
     * 解析二维码（QRCode）（google Zxing）
     * @param input 输入流
     * @return
     */
    public String decoderGQRCode4IO(InputStream input);
}
