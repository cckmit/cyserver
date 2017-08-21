package com.cy.util.file;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageHelper {
	/**
	 * @param imgWidth
	 *            缩放宽度
	 * @param imgHeight
	 *            缩放高度
	 * @param zoomPlus
	 *            是否强制缩放
	 * @param inputPath
	 *            缩放文件路径
	 * @param outPath
	 *            缩放完成后文件保存路径
	 * @throws IOException
	 */
	public static void createThumb(int imgWidth, int imgHeight, boolean zoomPlus, String inputPath, String outPath) throws IOException {
		File file = new File(inputPath);
		BufferedImage img = ImageIO.read(file);
		double rate = 0.0;
		double rate1 = 0.0;
		double rate2 = 0.0;
		int newWidth = 0;
		int newHeight = 0;
		if (imgWidth == 0) {
			rate = ((double) img.getHeight()) / (double) imgHeight;
		}
		if (imgHeight == 0) {
			rate = ((double) img.getWidth()) / (double) imgWidth;
		}
		if (imgWidth != 0 && imgHeight != 0) {
			rate1 = ((double) img.getWidth()) / (double) imgWidth;
			rate2 = ((double) img.getHeight()) / (double) imgHeight;
			rate = rate1 > rate2 ? rate2 : rate1;
		}
		newWidth = (int) (((double) img.getWidth()) / rate);
		newHeight = (int) (((double) img.getHeight()) / rate);
		// 如果非强制缩放,且图片缩放大小大于本身图片大小，为了图片不失真，图片不缩放，按原图存放
		if (!zoomPlus && img.getWidth() < imgWidth && img.getHeight() < imgHeight) {
			newWidth = img.getWidth();
			newHeight = img.getHeight();
		}
		BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);
		tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
		FileOutputStream out = new FileOutputStream(outPath);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(tag);
		out.close();
	}
}
