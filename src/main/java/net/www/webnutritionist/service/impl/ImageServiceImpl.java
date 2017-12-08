package net.www.webnutritionist.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;

import org.apache.commons.lang3.StringUtils;

import net.www.webnutritionist.service.ImageService;



public class ImageServiceImpl implements ImageService {

	public ImageServiceImpl() {
	}

	public BufferedImage getImageReader(String type, String path) throws IOException {
		ImageReader reader = ImageIO.getImageReadersByMIMEType(type).next();
		reader.setInput(new FileImageInputStream(new File(path)));
		BufferedImage image = null;
			image = ImageIO.read(new File(path));
		return image;
	}

	@Override
	public void writeImage(BufferedImage image, String format, File file) throws IOException {
		ImageIO.write(image, format, file);
	}

	@Override
	public BufferedImage process(BufferedImage image, String text, String type) {
		Graphics2D g2d_back = image.createGraphics();
		g2d_back.drawImage(image, 0, 0, null);
		g2d_back.setPaint(Color.BLACK);
		g2d_back.setFont(new Font("Impact", Font.PLAIN, 51));
		int x = 90;
		int y = 120;
		List<String> resFinish = divideText(text);
		drawStringToImage(g2d_back, resFinish, x, y);
		g2d_back.dispose();
		return image;
	}

	private List<String> divideText(String text) {
		String newTextWithoutEnter = charResultNoEnter(text);
		String[] resSplit = newTextWithoutEnter.split(" ");
		List<String> res = new LinkedList<>();
		String resultForRes = "";
		for (String aResSplit : resSplit) {
			resultForRes += aResSplit + " ";
			if (resultForRes.length() >= 19) {
				res.add(0, resultForRes.length() > 25 ? StringUtils.abbreviate(resultForRes, 0, 20) : resultForRes);
				resultForRes = "";
			}
		}
		if (!resultForRes.isEmpty()) {
			res.add(0, resultForRes);
		}
		return res;
	}

	int step = 50;

	private void drawStringToImage(Graphics2D g2d, List<String> res, int x, int y) {
		for (int i = res.size() - 1; i >= 0; i--, y += this.step) {
			g2d.drawString(res.get(i), x, y);
		}
	}

	private String charResultNoEnter(String text) {
		char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '\n') {
				chars[i] = ' ';
			}
		}
		return new String(chars).replaceAll("\\s{2,}", " ");
	}

}
