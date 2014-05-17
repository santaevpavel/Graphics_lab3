package ru.nsu.fit.santaev.filters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ObjectInputStream.GetField;

public class Aquarelle2 {

	private static double[][] matrix = new double[3][3]; 
	private static double norm = 1;
	private static double[][] matrix2 = new double[3][3]; 
	private static double norm2 = 1;
	
	static{
		matrix[0][0] = 1;
		matrix[0][1] = 1;
		matrix[0][2] = 1;
		
		matrix[1][0] = 1;
		matrix[1][1] = 1;
		matrix[1][2] = 1;
		
		matrix[2][0] = 1;
		matrix[2][1] = 1;
		matrix[2][2] = 1;
			
		norm = 9;
	}
	static{
		matrix2[0][0] = -1;
		matrix2[0][1] = -1;
		matrix2[0][2] = -1;
		
		matrix2[1][0] = -1;
		matrix2[1][1] = 11;
		matrix2[1][2] = -1;
		
		matrix2[2][0] = -1;
		matrix2[2][1] = -1;
		matrix2[2][2] = -1;
			
		norm2 = 3;
	}
	
	public static BufferedImage doFilter(BufferedImage img){
		BufferedImage img2 = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics gr = img2.createGraphics();
		gr.setColor(Color.BLACK);
		gr.drawRect(0, 0, img.getWidth(), img.getHeight());
		BufferedImage img3 = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics gr2 = img3.createGraphics();
		gr2.setColor(Color.BLACK);
		gr2.drawRect(0, 0, img.getWidth(), img.getHeight());
		BufferedImage img4 = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics gr3 = img4.createGraphics();
		gr3.setColor(Color.BLACK);
		gr3.drawRect(0, 0, img.getWidth(), img.getHeight());
		
		
		img2 = Mediana.doFilter(img);
		//img2 = GaussBlur.doFilter(img2);
		for (int i = 0; i < img.getWidth(); i++){
			for (int j = 0; j < img.getHeight(); j++){
				
				Color cImg3 = new Color(img3.getRGB(i, j));
				double r = cImg3.getRed();
				double g = cImg3.getGreen();
				double b = cImg3.getBlue();
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
						Color c = new Color(img2.getRGB(x, y));
						r += ((double)c.getRed() * matrix2[k][l] / norm2 );
						g += ((double)c.getGreen() * matrix2[k][l] / norm2);
						b += ((double)c.getBlue() * matrix2[k][l] / norm2);
					}
				}
				if (r < 0){
					r = 0;
				}
				if (g < 0){
					g = 0;
				}
				if (b < 0){
					b = 0;
				}
				if (r > 255){
					r = 255;
				}
				if (g > 255){
					g = 255;
				}
				if (b > 255){
					b = 255;
				}
				img3.setRGB(i, j, new Color((int)r, (int)g, (int)b).getRGB());
			}
		}
		
		return img3;
	}
	public static void f(BufferedImage img3, Color cImg3, int i, int j){
		//Color cImg3 = new Color(img3.getRGB(i, j));
		double r = cImg3.getRed();
		double g = cImg3.getGreen();
		double b = cImg3.getBlue();
		Color cImg4 = Sobel.getPixel(img3, i, j);
		Error er = new Error();
		er = Roberts.getError(cImg3, cImg4);
		if ( Math.sqrt((er.r * er.r + er.g * er.g + er.b * er.b)) < 50){
			er.r = 1 * er.r;
			er.g = 1 * er.g;
			er.b = 1 * er.b;
			cImg4 = Roberts.addToPixel(cImg4, er, 0.5);
		}else{
			
		}
		Roberts.addToPixel(cImg3, er, 0.5);
		//img3.setRGB(i, j + 1, cImg4.getRGB());
		Roberts.setPixel(img3, i, j, cImg4);
	}
}
