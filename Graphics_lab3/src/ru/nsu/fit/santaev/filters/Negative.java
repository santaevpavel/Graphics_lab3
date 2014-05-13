package ru.nsu.fit.santaev.filters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ObjectInputStream.GetField;

public class Negative {

	public static BufferedImage doFilter(BufferedImage img) {
		BufferedImage img2 = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_3BYTE_BGR);
		// img.getSubimage(0, 0, img.getWidth(), img.getHeight());
		for (int i = 0; i < img2.getWidth(); i++) {
			for (int j = 0; j < img2.getHeight(); j++) {
				int c = img.getRGB(i, j);
				Color c2 = new Color(c);
				Color newColor = new Color(255 - c2.getRed(), 255 - c2.getBlue(), 255 - c2.getGreen());
				img2.setRGB(i, j, newColor.getRGB());
			}
		}
		return img2;
	}
}
