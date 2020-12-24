package com.sxh.util;

import sun.net.www.protocol.http.HttpURLConnection;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * 图片处理工具类
 * @author 一池春水倾半城
 * @date 2020年12月24日
 * @Description
 */
public class ImageUtil {
	/**
	 * 生成多张图片的组合图片
	 * 注：生成的图片的清晰度与图片的尺寸成正比
	 * @param images 图片
	 * @param dir 图片存储位置
	 * @param fileName 图片名称
	 * @param type 0:方形，1:圆形
	 * @return
	 */
	public static String getCombinedPic(List<String> images, String dir, String fileName, Integer type) throws IOException {
		int panelWidth = 400; // 画板宽度
		int panelHeight = 400; // 画板高度

		// 生成画布
		BufferedImage outImage = new BufferedImage(panelWidth, panelHeight,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = outImage.createGraphics();

		// 设置背景色为透明
		g2d.setBackground(new Color(231,231,231));
		outImage = g2d.getDeviceConfiguration().createCompatibleImage(panelWidth, panelHeight, Transparency.TRANSLUCENT);
		g2d.dispose();
		g2d = outImage.createGraphics();
		g2d.setColor(new Color(231,231,231));

		// 绘制图形
		if (type == 0) {
			combinePicAsRect(images, panelWidth, panelHeight, g2d);
		} else {
			combinePicAsCircle(images, panelWidth, panelHeight, g2d);
		}

		// 存储图片
		fileName += ".png";
		File file = new File(dir + fileName);
		if(!file.exists()){
			file.mkdirs();
		}
		ImageIO.write(outImage, "PNG", file);

		return fileName;
	}

	/**
	 * 生成多张图片的组合图片(方形)
	 * @param images 图片
	 * @param panelWidth
	 * @param panelHeight
	 * @param g2d
	 * @return
	 */
	private static void combinePicAsRect(List<String> images, int panelWidth, int panelHeight, Graphics2D g2d) throws IOException {
		g2d.fillRect(0, 0, panelWidth, panelHeight);
		BufferedImage resizedImage;
		for (int i = 0; i < images.size(); i++) {
			if (images.size() == 1) {
				resizedImage = ImageUtil.resize2(images.get(i), (int) (panelWidth * 0.7), (int) (panelHeight * 0.7), true, false);
				g2d.drawImage(resizedImage, (int) (panelWidth * 0.15), (int) (panelHeight * 0.15), null);
			} else if (images.size() == 2) {
				resizedImage = ImageUtil.resize2(images.get(i), (int) (panelWidth * 0.45), (int) (panelHeight * 0.45), true, false);
				g2d.drawImage(resizedImage, (int) (panelWidth * 0.48) * i + (int) (panelWidth * 0.04), (int) (panelHeight * 0.26), null);
			} else if (images.size() == 3) {
				resizedImage = ImageUtil.resize2(images.get(i), (int) (panelWidth * 0.45), (int) (panelHeight * 0.45), true, false);
				if (i < 2) {
					g2d.drawImage(resizedImage, (int) (panelWidth * 0.48) * i + (int) (panelWidth * 0.04), (int) (panelHeight * 0.04), null);
				} else {
					g2d.drawImage(resizedImage, (int) (panelWidth * 0.48) * (i % 2) + (int) (panelWidth * 0.275), (int) (panelHeight * 0.51), null);
				}
			} else if (images.size() == 4) {
				resizedImage = ImageUtil.resize2(images.get(i), (int) (panelWidth * 0.45), (int) (panelHeight * 0.45), true, false);
				if (i < 2) {
					g2d.drawImage(resizedImage, (int) (panelWidth * 0.48) * i + (int) (panelWidth * 0.04), (int) (panelHeight * 0.04), null);
				} else {
					g2d.drawImage(resizedImage, (int) (panelWidth * 0.48) * (i % 2) + (int) (panelWidth * 0.04), (int) (panelHeight * 0.51), null);
				}
			} else if (images.size() == 5) {
				resizedImage = ImageUtil.resize2(images.get(i),  (int) (panelWidth * 0.29), (int) (panelHeight * 0.29), true, false);
				if (i < 3) {
					g2d.drawImage(resizedImage, (int) (panelWidth * 0.31) * i + (int) (panelWidth * 0.05), (int) (panelHeight * 0.2), null);
				} else {
					g2d.drawImage(resizedImage, (int) (panelWidth * 0.31) * (i % 3) + (int) (panelWidth * 0.2), (int) (panelHeight * 0.51), null);
				}
			} else if (images.size() == 6) {
				resizedImage = ImageUtil.resize2(images.get(i),  (int) (panelWidth * 0.29), (int) (panelHeight * 0.29), true, false);
				if (i < 3) {
					g2d.drawImage(resizedImage, (int) (panelWidth * 0.31) * i + (int) (panelWidth * 0.05), (int) (panelHeight * 0.2), null);
				} else {
					g2d.drawImage(resizedImage, (int) (panelWidth * 0.31) * (i % 3) + (int) (panelWidth * 0.05), (int) (panelHeight * 0.51), null);
				}
			} else if (images.size() == 7) {
				resizedImage = ImageUtil.resize2(images.get(i),  (int) (panelWidth * 0.29), (int) (panelHeight * 0.29), true, false);
				if (i < 3) {
					g2d.drawImage(resizedImage, (int) (panelWidth * 0.31) * i + (int) (panelWidth * 0.05), (int) (panelHeight * 0.04), null);
				} else if (i < 6){
					g2d.drawImage(resizedImage, (int) (panelWidth * 0.31) * (i % 3) + (int) (panelWidth * 0.05), (int) (panelHeight * 0.35), null);
				} else {
					g2d.drawImage(resizedImage, (int) (panelWidth * 0.31) * (i % 3) + (int) (panelWidth * 0.36), (int) (panelHeight * 0.66), null);
				}
			} else if (images.size() == 8) {
				resizedImage = ImageUtil.resize2(images.get(i),  (int) (panelWidth * 0.29), (int) (panelHeight * 0.29), true, false);
				if (i < 3) {
					g2d.drawImage(resizedImage, (int) (panelWidth * 0.31) * i + (int) (panelWidth * 0.05), (int) (panelHeight * 0.04), null);
				} else if (i < 6){
					g2d.drawImage(resizedImage, (int) (panelWidth * 0.31) * (i % 3) + (int) (panelWidth * 0.05), (int) (panelHeight * 0.35), null);
				} else {
					g2d.drawImage(resizedImage, (int) (panelWidth * 0.31) * (i % 3) + (int) (panelWidth * 0.2), (int) (panelHeight * 0.66), null);
				}
			} else if (images.size() == 9) {
				resizedImage = ImageUtil.resize2(images.get(i),  (int) (panelWidth * 0.29), (int) (panelHeight * 0.29), true, false);
				if (i < 3) {
					g2d.drawImage(resizedImage, (int) (panelWidth * 0.31) * i + (int) (panelWidth * 0.05), (int) (panelHeight * 0.04), null);
				} else if (i < 6){
					g2d.drawImage(resizedImage, (int) (panelWidth * 0.31) * (i % 3) + (int) (panelWidth * 0.05), (int) (panelHeight * 0.35), null);
				} else {
					g2d.drawImage(resizedImage, (int) (panelWidth * 0.31) * (i % 3) + (int) (panelWidth * 0.05), (int) (panelHeight * 0.66), null);
				}
			}
		}
	}

	/**
	 * 生成多张图片的组合图片(圆形)
	 * @param images 图片
	 * @param panelWidth
	 * @param panelHeight
	 * @param g2d
	 * @return
	 */
	private static void combinePicAsCircle(List<String> images, int panelWidth, int panelHeight, Graphics2D g2d) throws IOException {
		g2d.fillOval(0, 0, panelWidth, panelHeight);
		BufferedImage resizedImage;
		for (int i = 0; i < images.size(); i++) {
			if (images.size() == 1) {
				resizedImage = ImageUtil.resize2(images.get(i), (int) (panelWidth * 0.7), (int) (panelHeight * 0.7), true, true);
				g2d.drawImage(resizedImage, (int) (panelWidth * 0.15), (int) (panelHeight * 0.15), null);
			} else if (images.size() == 2) {
				// 大圆里绘制两个小圆，小圆半径：R/2
				resizedImage = ImageUtil.resize2(images.get(i), (int) (panelWidth * 0.49), (int) (panelHeight * 0.49), true, true);
				g2d.drawImage(resizedImage, (int) (panelWidth * 0.5) * i + (int) (panelWidth * 0.005), (panelHeight / 4), null);
			} else if (images.size() == 3) {
				// 大圆里绘制三个小圆，小圆半径：(2√3-3)*R, 2√3-3≈0.46
				resizedImage = ImageUtil.resize2(images.get(i), (int) (0.45 * panelWidth), (int) (0.45 * panelHeight), true, true);
				if (i < 2) {
					g2d.drawImage(resizedImage, (int) (0.46 * panelWidth) * i + (int) (panelWidth * 0.05), (int) (0.14 * panelHeight), null);
				} else {
					g2d.drawImage(resizedImage, (int) (panelWidth * 0.28), (int) (panelHeight * 0.54), null);
				}
			} else if (images.size() == 4) {
				// 大圆里绘制四个小圆
				resizedImage = ImageUtil.resize2(images.get(i), (int) (0.4 * panelWidth), (int) (0.4 * panelHeight), true, true);
				if (i < 2) {
					g2d.drawImage(resizedImage, (int) (0.41 * panelWidth) * i + (int) (0.1 * panelWidth), (int) (0.1 * panelHeight), null);
				} else {
					g2d.drawImage(resizedImage, (int) (0.41 * panelWidth) * (i % 2) + (int) (0.1 * panelWidth), (int) (0.51 * panelHeight), null);
				}
			} else if (images.size() == 5) {
				resizedImage = ImageUtil.resize2(images.get(i), (int) (0.3 * panelWidth), (int) (0.3 * panelHeight), true, true);
				if (i < 3) {
					g2d.drawImage(resizedImage, (int) (0.31 * panelWidth) * i + (int) (0.04 * panelWidth), (int) (0.2 * panelHeight), null);
				} else {
					g2d.drawImage(resizedImage, (int) (0.31 * panelWidth) * (i % 3) + (int) (0.2 * panelWidth), (int) (0.51 * panelHeight), null);
				}
			} else if (images.size() == 6) {
				resizedImage = ImageUtil.resize2(images.get(i), (int) (0.3 * panelWidth), (int) (0.3 * panelHeight), true, true);
				if (i < 3) {
					g2d.drawImage(resizedImage, (int) (0.31 * panelWidth) * i + (int) (0.04 * panelWidth), (int) (0.2 * panelHeight), null);
				} else {
					g2d.drawImage(resizedImage, (int) (0.31 * panelWidth) * (i % 3) + (int) (0.04 * panelWidth), (int) (0.51 * panelHeight), null);
				}
			} else if (images.size() == 7) {
				resizedImage = ImageUtil.resize2(images.get(i), (int) (0.25 * panelWidth), (int) (0.25 * panelHeight), true, true);
				if (i < 3) {
					g2d.drawImage(resizedImage, (int) (0.26 * panelWidth) * i + (int) (0.12 * panelWidth), (int) (0.12 * panelHeight), null);
				} else if (i < 6) {
					g2d.drawImage(resizedImage, (int) (0.26 * panelWidth) * (i % 3) + (int) (0.12 * panelWidth), (int) (0.375 * panelHeight), null);
				} else {
					g2d.drawImage(resizedImage, (int) (0.26 * panelWidth) * (i % 3) + (int) (0.38 * panelWidth), (int) (0.63 * panelHeight), null);
				}
			} else if (images.size() == 8) {
				resizedImage = ImageUtil.resize2(images.get(i), (int) (0.25 * panelWidth), (int) (0.25 * panelHeight), true, true);
				if (i < 3) {
					g2d.drawImage(resizedImage, (int) (0.26 * panelWidth) * i + (int) (0.12 * panelWidth), (int) (0.12 * panelHeight), null);
				} else if (i < 6) {
					g2d.drawImage(resizedImage, (int) (0.26 * panelWidth) * (i % 3) + (int) (0.12 * panelWidth), (int) (0.375 * panelHeight), null);
				} else {
					g2d.drawImage(resizedImage, (int) (0.26 * panelWidth) * (i % 3) + (int) (0.25 * panelWidth), (int) (0.63 * panelHeight), null);
				}
			} else if (images.size() == 9) {
				resizedImage = ImageUtil.resize2(images.get(i), (int) (0.25 * panelWidth), (int) (0.25 * panelHeight), true, true);
				if (i < 3) {
					g2d.drawImage(resizedImage, (int) (0.26 * panelWidth) * i + (int) (0.12 * panelWidth), (int) (0.12 * panelHeight), null);
				} else if (i < 6) {
					g2d.drawImage(resizedImage, (int) (0.26 * panelWidth) * (i % 3) + (int) (0.12 * panelWidth), (int) (0.375 * panelHeight), null);
				} else {
					g2d.drawImage(resizedImage, (int) (0.26 * panelWidth) * (i % 3) + (int) (0.12 * panelWidth), (int) (0.63 * panelHeight), null);
				}
			}
		}
	}

	/**
	 * 图片缩放
	 * @param filePath 图片路径
	 * @param height 高度
	 * @param width 宽度
	 * @param bb 比例不对时是否需要补白
	 * @param isCircle 是否裁剪成圆形
	 */
	private static BufferedImage resize2(String filePath, int width, int height, boolean bb, boolean isCircle) {
		try {
			double ratio; // 缩放比例
			BufferedImage bi = null;
			if (filePath.indexOf("http://") == 0) {
				URL url = new URL(filePath);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(1000);
				if (conn.getResponseCode() == 200) { // 连接可用
					bi = ImageIO.read(url);
				}
			} else {
				bi = ImageIO.read(new File(filePath));
			}
			Image itemp = bi.getScaledInstance(width, height,
					Image.SCALE_SMOOTH);
			// 计算比例
			if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
				if (bi.getHeight() > bi.getWidth()) {
					ratio = (new Integer(height)).doubleValue()
							/ bi.getHeight();
				} else {
					ratio = (new Integer(width)).doubleValue() / bi.getWidth();
				}
				AffineTransformOp op = new AffineTransformOp(
						AffineTransform.getScaleInstance(ratio, ratio), null);
				itemp = op.filter(bi, null);
			}
			if (bb) {
				BufferedImage image = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);
				if (width == itemp.getWidth(null)) {
					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);
				} else {
					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);
				}
				g.dispose();
				itemp = image;
			}
			if (isCircle) { // 裁剪成圆形
				return convertCircular((BufferedImage) itemp);
			} else {
				return (BufferedImage) itemp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将图片裁剪成圆形
	 * @param bi1
	 * @return
	 * @throws IOException
	 */
	private static BufferedImage convertCircular(BufferedImage bi1) throws IOException {
		// 透明底的图片
		BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1.getHeight());
		Graphics2D g2 = bi2.createGraphics();
		g2.setClip(shape);
		// 使用 setRenderingHint 设vWebMvcConfig置抗锯齿
		g2.drawImage(bi1, 0, 0, null);
		// 设置颜色
		g2.setBackground(Color.green);
		g2.dispose();
		return bi2;
	}

	public static void main(String[] argo) throws IOException {
		List<String> images = new LinkedList<>();
		for (int i = 1; i <= 9; i++) {
			images.add("http://localhost:8001/images/"+ i +".png");
			getCombinedPic(images, "D://imgTest/rect/", images.size()+"", 0);
			getCombinedPic(images, "D://imgTest/circle/", images.size()+"", 1);
		}
    }
}
