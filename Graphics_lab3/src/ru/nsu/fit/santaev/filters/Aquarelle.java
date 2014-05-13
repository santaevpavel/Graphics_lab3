package ru.nsu.fit.santaev.filters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Aquarelle {

	private static double[][] matrix = new double[3][3]; 
	private static double norm = 1;
	private static double[][] matrix2 = new double[3][3]; 
	private static double norm2 = 1;
	
	static{
		matrix[0][0] = 1;
		matrix[0][1] = 1;
		matrix[0][2] = 1;
		
		matrix[1][0] = 1;
		matrix[1][1] = 0.5;
		matrix[1][2] = 1;
		
		matrix[2][0] = 1;
		matrix[2][1] = 1;
		matrix[2][2] = 1;
			
		norm = 8.5;
	}
	static{
		matrix2[0][0] = -1;
		matrix2[0][1] = -1;
		matrix2[0][2] = -1;
		
		matrix2[1][0] = -1;
		matrix2[1][1] = 10;
		matrix2[1][2] = -1;
		
		matrix2[2][0] = -1;
		matrix2[2][1] = -1;
		matrix2[2][2] = -1;
			
		norm2 = 2;
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
		
		for (int i = 0; i < img.getWidth(); i++){
			for (int j = 0; j < img.getHeight(); j++){
				
				//Color cImg2 = new Color(img2.getRGB(i, j));
				double r = 0;
				double g = 0;
				double b = 0;
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
					}
				}
				
				img2.setRGB(i, j, new Color((int)r, (int)g, (int)b).getRGB());
			}
		}
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

}
