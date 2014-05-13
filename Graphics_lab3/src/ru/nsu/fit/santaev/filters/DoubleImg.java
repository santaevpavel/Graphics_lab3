package ru.nsu.fit.santaev.filters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class DoubleImg {
	private static double[][] matrix = new double[3][3]; 
	private static double norm = 5;
	
	static{

		matrix[0][0] = 1;
		matrix[0][1] = 1;
		matrix[0][2] = 1;
		
		matrix[1][0] = 1;
		matrix[1][1] = 2;
		matrix[1][2] = 1;
		
		matrix[2][0] = 1;
		matrix[2][1] = 1;
		matrix[2][2] = 1;
			
		norm = 10;
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
		for (int i = 0; i < img.getWidth() / 2; i++){
			for (int j = 0; j < img.getHeight() / 2; j++){
				img2.setRGB(2 * i, 2 * j, img.getRGB(i, j));
				img2.setRGB(2 * i + 1, 2 * j, img.getRGB(i, j));
				img2.setRGB(2 * i, 2 * j + 1, img.getRGB(i, j));
				img2.setRGB(2 * i + 1, 2 * j + 1, img.getRGB(i, j));
			}
		}
		for (int i = 0; i < img2.getWidth(); i++){
			for (int j = 0; j < img2.getHeight(); j++){
				
				Color cImg2 = new Color(img3.getRGB(i, j));
				double r = cImg2.getRed();
				double g = cImg2.getGreen();
				double b = cImg2.getBlue();
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
						
						
						r += ((double)c.getRed() * matrix[k][l] / norm );
						
						g += ((double)c.getGreen() * matrix[k][l] / norm);
						
						b += ((double)c.getBlue() * matrix[k][l] / norm);
						
						
					}
				}
				//System.out.println(" "  + r + " " + g + " " + b);
				img3.setRGB(i, j, new Color((int)r, (int)g, (int)b).getRGB());
			}
		}
	
		return img3;
	}
}
