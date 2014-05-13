package ru.nsu.fit.santaev.filters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.ObjectInputStream.GetField;
import java.util.Vector;

public class OrdDithering {
	
	private static double[][] matrix = new double[2][2]; 
	private static double norm = 5;
	
	static{

		matrix[0][0] = 1f / 5;
		matrix[0][1] = 3f / 5;
		matrix[1][0] = 4f / 5;
		matrix[1][1] = 2f / 5;
			
		norm = 5;
	}
	public static BufferedImage doFilter(BufferedImage img) {
		BufferedImage img2 = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_3BYTE_BGR);
		// img.getSubimage(0, 0, img.getWidth(), img.getHeight());
		for (int i = 0; i < img2.getWidth(); i++) {
			for (int j = 0; j < img2.getHeight(); j++) {
				img2.setRGB(i, j, img.getRGB(i, j));
				// img2.setRGB(i, j, Color.BLACK.getRGB());
			}
		}
		int a = 32;
		for (int j = 0; j < img2.getHeight(); j++) {
			for (int i = 0; i < img2.getWidth(); i++) {
				int c = img.getRGB(i, j);
				Color c2 = new Color(c);
				Error er = new Error();
				
				er.r = (int) (a * matrix[i % 2][j % 2]);
				er.g = er.r;
				er.b = er.r;
				c2 = addToPixel(c2, er, 1);
				Color c3 = getClosestPixel(c2);
				setPixel(img2, i, j, c3);
				
				//oldpixel := pixel[x][y] + threshold_map_4x4[x mod 4][y mod 4]
				//newpixel := find_closest_palette_color(oldpixel)
				//pixel[x][y] := newpixel
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
