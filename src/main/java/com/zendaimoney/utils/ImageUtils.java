package com.zendaimoney.utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.NoSuchElementException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.ImageIcon;

public class ImageUtils {
	private static final String IMAGE_TYPE = ".jpg";
	private static final String SMALL_FIX = "_small";
	private static final float JPEG_Q = 0.50f;

	private static Object[] readTiff(File tifFile) {
		ImageReader reader = null;
		FileImageInputStream fis = null;
		Object[] res = null;
		try {
			reader = ImageIO.getImageReadersByFormatName("tiff").next();
			fis = new FileImageInputStream(tifFile);
			reader.setInput(fis);
			res = new Object[] { reader, fis };
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	// 获取tiff dpi
	private static long[] getTiffDPI(ImageReader reader, int index) {
		long[] dpi = new long[2];
		IIOMetadata meta = null;
		try {
			meta = reader.getImageMetadata(index);
			org.w3c.dom.Node n = meta.getAsTree("javax_imageio_1.0");
			n = n.getFirstChild();
			while (n != null) {
				if (n.getNodeName().equals("Dimension")) {
					org.w3c.dom.Node n2 = n.getFirstChild();
					while (n2 != null) {
						if (n2.getNodeName().equals("HorizontalPixelSize")) {
							org.w3c.dom.NamedNodeMap nnm = n2.getAttributes();
							org.w3c.dom.Node n3 = nnm.item(0);
							float hps = Float.parseFloat(n3.getNodeValue());
							dpi[0] = Math.round(25.4f / hps);
						}
						if (n2.getNodeName().equals("VerticalPixelSize")) {
							org.w3c.dom.NamedNodeMap nnm = n2.getAttributes();
							org.w3c.dom.Node n3 = nnm.item(0);
							float vps = Float.parseFloat(n3.getNodeValue());
							dpi[1] = Math.round(25.4f / vps);
						}
						n2 = n2.getNextSibling();
					}
				}
				n = n.getNextSibling();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dpi;
	}

	private static void handle(BufferedImage source, long[] dpiData, String path, String name, int smallWidth, int smallHeight) throws IOException {
		int width = source.getWidth();
		int height = source.getHeight();
		if (dpiData != null) {
			float fsc = (float) dpiData[0] / (float) dpiData[1];
			if (Math.abs(fsc - 1.0) > 0.2) {
				if (fsc < 1) {
					width = (int) (width / fsc);
				} else {
					height = (int) (height * fsc);
				}
			}
		}
		output(source, width, height, path + name, dpiData == null, false);
		if (smallWidth > 0 && smallHeight > 0) {
			name = name.substring(0,name.lastIndexOf("."));
			output(source, smallWidth, smallHeight, path + name + SMALL_FIX + IMAGE_TYPE, false, true);
		}
		source.flush();
	}

	private static void output(BufferedImage source, int width, int height, String filename, boolean isDirect, boolean isScale) throws IOException {
		BufferedImage target = source;
		if (!isDirect) {
			target = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g2 = (Graphics2D) target.createGraphics();
			if (isScale) {
				Image scaleImage = source.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				g2.drawImage(scaleImage, 0, 0, null);
			} else {
				g2.drawImage(source, 0, 0, null);
			}
			g2.dispose();
		}

		ImageWriteParam imgWriteParams = new JPEGImageWriteParam(null);
		imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		imgWriteParams.setCompressionQuality(JPEG_Q);
		imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);

		File file = new File(filename);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		OutputStream out = new FileOutputStream(file);
		ImageWriter imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
		imgWrier.reset();
		imgWrier.setOutput(ImageIO.createImageOutputStream(out));
		imgWrier.write(null, new IIOImage(target, null, null), imgWriteParams);
		out.flush();
		out.close();
		target.flush();
		imgWrier.dispose();
	}

	/**
	 * 预处理CMYK颜色模式
	 * 
	 * @param image
	 * @return
	 */

	private static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}
		image = new ImageIcon(image).getImage();
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			int transparency = Transparency.OPAQUE;
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
			
		} catch (IllegalArgumentException e) {
			// The system does not have a screen
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (bimage == null) {
			int type = BufferedImage.TYPE_INT_RGB;
			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		}
		Graphics g = bimage.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();			
		return bimage;
	}

	public static boolean transform(String filename, String path, String target, int smallWidth, int smallHeight) {
		try {
			Image img = Toolkit.getDefaultToolkit().createImage(filename);
			BufferedImage source = null;
			try{
			
			 source = toBufferedImage(img);
			
			}catch(Exception e){
				source = ImageIO.read(new File(filename));
			}finally{
				img.flush();	
			}
			handle(source, null, path, target, smallWidth, smallHeight);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean transformBMP(String filename, String path, String target, int smallWidth, int smallHeight) {
		try {
			BufferedImage source = ImageIO.read(new File(filename));
			handle(source, null, path, target, smallWidth, smallHeight);
			source.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean transformTiff(String filename, String path, String target, int smallWidth, int smallHeight) {
		ImageReader reader = null;
		FileImageInputStream fis = null;
		boolean bres = true;
		try {
			Object[] src = readTiff(new File(filename));
			if (src == null) {
				return false;
			}
			reader = (ImageReader) src[0];
			fis = (FileImageInputStream) src[1];

			if (reader != null) {
				int numPages = reader.getNumImages(true);
				if (numPages > 0) {
					long[] dpiData = getTiffDPI(reader, 0);
					BufferedImage img = reader.read(0);
					handle(img, dpiData, path, target, smallWidth, smallHeight);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			bres = false;
		} finally {
			if (reader != null) {
				reader.dispose();
			}
			if (fis != null) {
				try {
					fis.flush();
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bres;
	}
}
