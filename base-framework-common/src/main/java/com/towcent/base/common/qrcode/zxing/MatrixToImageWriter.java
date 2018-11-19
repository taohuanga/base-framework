/*
 * 文 件 名:  MatrixToImageWriter.java
 * 版   权: Copyright www.face-garden.com Corporation 2014 版权所有
 * 描     述:  <描述>
 * 修 改 人:  shiwei
 * 修改时间:  2014-4-10
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package com.towcent.base.common.qrcode.zxing;

import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 利用zxing写成二维码文件 <功能详细描述>
 * 
 * @author shiwei
 * @version [版本号, 2014-4-10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MatrixToImageWriter {
	private static final int BLACK = 0xFF000000;

	private static final int WHITE = 0xFFFFFFFF;

	private MatrixToImageWriter() {
	}

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	public static void writeToFile(BitMatrix matrix, String format, File file)
			throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format " + format
					+ " to " + file);
		}
	}

	public static void writeToStream(BitMatrix matrix, String format,
			OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException(
					"Could not write an image of format " + format);
		}
	}
}
