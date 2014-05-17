package ru.nsu.fit.santaev.filters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.ObjectInputStream.GetField;
import java.util.Vector;

public class Sobel {
	
	private static double[][] matrix = new double[3][3]; 
	private static double norm = 5;
	
	static{

		matrix[0][0] = -1;
		matrix[0][1] = -2;
		matrix[0][2] = -1;
		
		matrix[1][0] = 0;
		matrix[1][1] = 0;
		matrix[1][2] = 0;
		
		matrix[2][0] = 1;
		matrix[2][1] = 2;
		matrix[2][2] = 1;
			
		norm = 1;
	}
	public static BufferedImage doFilter(BufferedImage img, double kk) {
		BufferedImage img2 = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_3BYTE_BGR);
		int[][] aar = new int[img.getWidth()][img.getHeight()];
		int[][] aag = new int[img.getWidth()][img.getHeight()];
		int[][] aab = new int[img.getWidth()][img.getHeight()];
		int[][] bbr = new int[img.getWidth()][img.getHeight()];
		int[][] bbg = new int[img.getWidth()][img.getHeight()];
		int[][] bbb = new int[img.getWidth()][img.getHeight()];
		for (int i = 0; i < img2.getWidth(); i++) {
			for (int j = 0; j < img2.getHeight(); j++) {
				//Color tmp0 = getPixel(img, i, j);
				double r = 0;
				double g = 0;
				double b = 0;
				double r2 = 0;
				double g2 = 0;
				double b2 = 0;
				for (int k = 0; k < 3; k++){
					for (int l = 0; l < 3; l++){
						int x = i + k - 1;
						int y = j + l - 1;
						if (x < 0){
							x = 0;
						}
						if (x >= img.getWidth()){
							x = img.getWidth() - 1;
						}
						if (y < 0){
							y = 0;
						}
						if (y >= img.getHeight()){
							y = img.getHeight() - 1;
						}
						Color c = new Color(img.getRGB(x, y));
						
						r += ((double)c.getRed() * matrix[k][l] / norm );
						g += ((double)c.getGreen() * matrix[k][l] / norm);
						b += ((double)c.getBlue() * matrix[k][l] / norm);
						
						r2 += ((double)c.getRed() * matrix[l][k] / norm );
						g2 += ((double)c.getGreen() * matrix[l][k] / norm);
						b2 += ((double)c.getBlue() * matrix[l][k] / norm);
					}
				}
				aar[i][j] = (int) r;
				aag[i][j] = (int) g;
				aab[i][j] = (int) b;
				bbr[i][j] = (int) r2;
				bbg[i][j] = (int) g2;
				bbb[i][j] = (int) b2;
			}
		}	
		for (int i = 0; i < img2.getWidth(); i++) {
			for (int j = 0; j < img2.getHeight(); j++) {
				int r = (int) (Math.sqrt(aar[i][j] * aar[i][j] + bbr[i][j] * bbr[i][j]) * kk);
				int g = (int) (Math.sqrt(aag[i][j] * aag[i][j] + bbg[i][j] * bbg[i][j]) * kk);
				int b = (int) (Math.sqrt(aag[i][j] * aab[i][j] + bbb[i][j] * bbb[i][j]) * kk);
				if (r > 255) {
					r = 255;
				}
				if (b > 255) {
					b = 255;
				}
				if (g > 255) {
					g = 255;
				}
				setPixel(img2, i, j, new Color(r, g, b));
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

	public static Color getPixel(BufferedImage img, int x, int y) {
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

	public static void setPixel(BufferedImage img, int x, int y, Color c) {
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

	public static Color addToPixel(Color old, Error er, double k) {
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

