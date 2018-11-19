/*
 * 文 件 名:  BufferedImageLuminanceSource.java
 * 版   权: Copyright www.face-garden.com Corporation 2014 版权所有
 * 描     述:  <描述>
 * 修 改 人:  shiwei
 * 修改时间:  2014-4-10
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容: <修改内容>
 */
package com.towcent.base.common.qrcode.zxing;

import com.google.zxing.LuminanceSource;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * 二维码解析（黑白） <功能详细描述>
 * 
 * @author shiwei
 * @version [版本号, 2014-4-10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BufferedImageLuminanceSource extends LuminanceSource {
	private final BufferedImage image;

	private final int left;

	private final int top;

	public BufferedImageLuminanceSource(BufferedImage image) {
		this(image, 0, 0, image.getWidth(), image.getHeight());
	}

	public BufferedImageLuminanceSource(BufferedImage image, int left, int top,
			int width, int height) {
		super(width, height);

		int sourceWidth = image.getWidth();
		int sourceHeight = image.getHeight();
		if (left + width > sourceWidth || top + height > sourceHeight) {
			throw new IllegalArgumentException(
					"Crop rectangle does not fit within image data.");
		}

		for (int y = top; y < top + height; y++) {
			for (int x = left; x < left + width; x++) {
				if ((image.getRGB(x, y) & 0xFF000000) == 0) {
					image.setRGB(x, y, 0xFFFFFFFF); // = white
				}
			}
		}

		this.image = new BufferedImage(sourceWidth, sourceHeight,
				BufferedImage.TYPE_BYTE_GRAY);
		this.image.getGraphics().drawImage(image, 0, 0, null);
		this.left = left;
		this.top = top;
	}

	@Override
	public byte[] getRow(int y, byte[] row) {
		if (y < 0 || y >= getHeight()) {
			throw new IllegalArgumentException(
					"Requested row is outside the image: " + y);
		}
		int width = getWidth();
		if (row == null || row.length < width) {
			row = new byte[width];
		}
		image.getRaster().getDataElements(left, top + y, width, 1, row);
		return row;
	}

	@Override
	public byte[] getMatrix() {
		int width = getWidth();
		int height = getHeight();
		int area = width * height;
		byte[] matrix = new byte[area];
		image.getRaster().getDataElements(left, top, width, height, matrix);
		return matrix;
	}

	@Override
	public boolean isCropSupported() {
		return true;
	}

	@Override
	public LuminanceSource crop(int left, int top, int width, int height) {
		return new BufferedImageLuminanceSource(image, this.left + left,
				this.top + top, width, height);
	}

	@Override
	public boolean isRotateSupported() {
		return true;
	}

	@Override
	public LuminanceSource rotateCounterClockwise() {
		int sourceWidth = image.getWidth();
		int sourceHeight = image.getHeight();

		AffineTransform transform = new AffineTransform(0.0, -1.0, 1.0, 0.0,
				0.0, sourceWidth);

		BufferedImage rotatedImage = new BufferedImage(sourceHeight,
				sourceWidth, BufferedImage.TYPE_BYTE_GRAY);

		Graphics2D g = rotatedImage.createGraphics();
		g.drawImage(image, transform, null);
		g.dispose();

		int width = getWidth();
		return new BufferedImageLuminanceSource(rotatedImage, top,
				sourceWidth - (left + width), getHeight(), width);
	}
}
