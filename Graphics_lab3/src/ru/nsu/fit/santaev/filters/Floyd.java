package ru.nsu.fit.santaev.filters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.ObjectInputStream.GetField;
import java.util.Vector;

public class Floyd {

	public static BufferedImage doFilter(BufferedImage img) {
		BufferedImage img2 = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_3BYTE_BGR);
		// img.getSubimage(0, 0, img.getWidth(), img.getHeight());
		Error error = null;
		for (int i = 0; i < img2.getWidth(); i++) {
			for (int j = 0; j < img2.getHeight(); j++) {
				img2.setRGB(i, j, img.getRGB(i, j));
				// img2.setRGB(i, j, Color.BLACK.getRGB());
			}
		}

		for (int j = 0; j < img2.getHeight(); j++) {
			for (int i = 0; i < img2.getWidth(); i++) {
				int c = img2.getRGB(i, j);
				Color c2 = new Color(c);
				Color c3 = getClosestPixel(c2);
				error = getError(c2, c3);
				// Color newPixel = getPixel(img2, i, j);
				setPixel(img2, i, j, c3);

				Color p = addToPixel(getPixel(img2, i, j + 1), error, 7f / 16);
				setPixel(img2, i, j + 1, p);
				//p = getPixel(img2, i, j + 1);
				setPixel(
						img2,
						i + 1,
						j - 1,
						addToPixel(getPixel(img2, i + 1, j - 1), error, 3f / 16));
				setPixel(img2, i + 1, j,
						addToPixel(getPixel(img2, i + 1, j), error, 6f / 16));
				setPixel(
						img2,
						i + 1,
						j + 1,
						addToPixel(getPixel(img2, i + 1, j + 1), error, 5f / 16));
				/*
				 * pixel[x+1][y ] := pixel[x+1][y ] + quant_error * 7/16
				 * pixel[x-1][y+1] := pixel[x-1][y+1] + quant_error * 3/16
				 * pixel[x ][y+1] := pixel[x ][y+1] + quant_error * 5/16
				 * pixel[x+1][y+1] := pixel[x+1][y+1] + quant_error * 1/16
				 */
				// int sum = (c2.getRed() + c2.getBlue() + c2.getGreen()) / 3;
				// Color newColor = new Color(sum, sum, sum);
				// img2.setRGB(i, j, newColor.getRGB());
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
