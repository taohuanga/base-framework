/*
 * 文 件 名:  QuickMarkServiceImpl.java
 * 版   权: Copyright www.face-garden.com Corporation 2014 版权所有
 * 描     述:  <描述>
 * 修 改 人:  shiwei
 * 修改时间:  2014-4-2
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package com.towcent.base.common.qrcode.impl;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.towcent.base.common.qrcode.QuickMarkService;
import com.towcent.base.common.qrcode.zxing.BufferedImageLuminanceSource;
import com.towcent.base.common.qrcode.zxing.MatrixToImageWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author shiwei
 * @version [版本号, 2014-4-2]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service("quickMarkService")
public class QuickMarkServiceImpl implements QuickMarkService {
	
	private static final Log logger = LogFactory.getLog(QuickMarkServiceImpl.class);

	@Override
	public void encoderGQRCode2File(String content, String imgPath, String imgType, int size) {
		try {
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, size, size);
			MatrixToImageWriter.writeToFile(bitMatrix, imgType, new File(imgPath));
		} catch (Exception e) {
			logger.error("二维码生成失败" + e.getMessage());
		}
	}

	@Override
	public String decoderGQRCode4File(String imgPath) {
		String content = "";
		try {
			MultiFormatReader formatReader = new MultiFormatReader();
			File file = new File(imgPath);
			BufferedImage image = ImageIO.read(file);
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Result result = formatReader.decode(binaryBitmap);
			logger.info("result = " + result.toString() + "\n resultFormat = " + result.getBarcodeFormat());

			content = result.getText();
		} catch (Exception e) {
			logger.error("二维码解析失败" + e.getMessage());
		}

		return content;
	}

	@Override
	public String decoderGQRCode4IO(InputStream input) {
		String content = "";
		try {
			MultiFormatReader formatReader = new MultiFormatReader();
			BufferedImage image = ImageIO.read(input);
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Result result = formatReader.decode(binaryBitmap);
			logger.info("result = " + result.toString() + "\n resultFormat = " + result.getBarcodeFormat());

			content = result.getText();
		} catch (Exception e) {
			logger.error("二维码解析失败" + e.getMessage());
		}

		return content;
	}

}
