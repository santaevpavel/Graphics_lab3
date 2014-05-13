package ru.nsu.fit.santaev.filters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.ObjectInputStream.GetField;
import java.util.Vector;

public class Roberts {
	
	private static double[][] matrix = new double[2][2]; 
	private static double norm = 5;
	
	static{

		matrix[0][0] = 1;
		matrix[0][1] = 0;
		matrix[1][0] = 0;
		matrix[1][1] = -1;
			
		norm = 1;
	}
	public static BufferedImage doFilter(BufferedImage img, double k) {
		BufferedImage img2 = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_3BYTE_BGR);
		for (int i = 0; i < img2.getWidth(); i++) {
			for (int j = 0; j < img2.getHeight(); j++) {
				//img2.setRGB(i, j, img.getRGB(i, j));
				Color tmp0 = getPixel(img, i, j);
				Color tmp1 = getPixel(img, i + 1, j + 1);
				Color tmp2 = getPixel(img, i + 1, j);
				Color tmp3 = getPixel(img, i, j + 1);
				Color tmp01 = subColors(tmp0, tmp1);
				Color tmp23 = subColors(tmp2, tmp3);
				Color result = sqrt(tmp01, tmp23, k);
				img2.setRGB(i, j, result.getRGB());
			}
		}	
		return img2;
	}

	private static Color getClosestPixel(Color c) {
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		int k = 32;
		int sum = (r + g + b) / 3;

		r = (r / k) * k;
		g = (g / k) * k;
		b = (b / k) * k;
		for (int i = 0; i < 255; i++) {
			int x = (i / 32) * 32;
			if (Math.abs(x - r) < 4) {
				r = x;
			}
			if (Math.abs(x - g) < 4) {
				g = x;
			}
			if (Math.abs(x - b) < 4) {
				b = x;
			}
		}
		return new Color(r, g, b);

	}
	private static Color subColors(Color c1, Color c2){
		int r = Math.abs(c1.getRed() - c2.getRed());
		int g = Math.abs(c1.getGreen() - c2.getGreen());
		int b = Math.abs(c1.getBlue() - c2.getBlue());
		return new Color(r, g, b);
	}
	private static Color sqrt(Color c1, Color c2m, double k){
		int r = c1.getRed();
		int g = c1.getGreen();
		int b = c1.getBlue();
		int r1 = c1.getRed();
		int g2 = c1.getGreen();
		int b3 = c1.getBlue();
		int r0 = (int) ((int)Math.sqrt(r * r + r1 * r1) * k);
		int g0 = (int)(Math.sqrt(g * g + g2 * g2) * k);
		int b0 = (int)(Math.sqrt(b * b + b3 * b3) * k);
		if (r0 > 255) {
			r0 = 255;
		}
		if (b0 > 255) {
			b0 = 255;
		}
		if (g0 > 255) {
			g0 = 255;
		}
		return new Color(r0, g0, b0);
	}
	private static Error getError(Color c1, Color c2) {
		int r = c1.getRed();
		int g = c1.getGreen();
		int b = c1.getBlue();
		r = r - c2.getRed();
		g = g - c2.getGreen();
		b = b - c2.getBlue();
		Error er = new Error();
		er.r = r;
		er.g = g;
		er.b = b;
		return er;
		
	}

	private static Color getPixel(BufferedImage img, int x, int y) {
		if (x < 0) {
			x = 0;
		}
		if (x >= img.getWidth()) {
			x = img.getWidth() - 1;
		}
		if (y < 0) {
			y = 0;
		}
		if (y >= img.getHeight()) {
			y = img.getHeight() - 1;
		}
		return new Color(img.getRGB(x, y));
	}

	private static void setPixel(BufferedImage img, int x, int y, Color c) {
		if (x < 0) {
			return;
		}
		if (x >= img.getWidth()) {
			return;
		}
		if (y < 0) {
			return;
		}
		if (y >= img.getHeight()) {
			return;
		}
		img.setRGB(x, y, c.getRGB());
	}

	private static Color addToPixel(Color old, Error er, double k) {
		int r = (int) (old.getRed() + er.r * k);
		int g = (int) (old.getGreen() + er.g * k);
		int b = (int) (old.getBlue() + er.b * k);
		if (r > 255) {
			r = 255;
		}
		if (r < 0) {
			r = 0;
		}
		if (g > 255) {
			g = 255;
		}
		if (g < 0) {
			g = 0;
		}
		if (b > 255) {
			b = 255;
		}
		if (b < 0) {
			b = 0;
		}
		return new Color(r, g, b);
	}
}
