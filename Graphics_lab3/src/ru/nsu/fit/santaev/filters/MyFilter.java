package ru.nsu.fit.santaev.filters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class MyFilter {

	private static double[][] matrix = new double[3][3]; 
	private static double norm = 1;
	
	static{
		double i1 = 0f;
		double i2 = 0f;
		matrix[0][0] = 0;
		matrix[2][2] = 0;
		matrix[2][0] = 0;
		matrix[0][2] = 0;
		
		matrix[1][1] = 0;
		matrix[1][0] = 1;
		matrix[0][1] = 1;
		matrix[2][1] = 1;
		matrix[1][2] = 0;
		
		norm = 3;
	}
	
	public static BufferedImage doFilter(BufferedImage img){
		BufferedImage img2 = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics gr = img2.createGraphics();
		gr.setColor(Color.BLACK);
		gr.drawRect(0, 0, img.getWidth(), img.getHeight());
		for (int i = 0; i < img.getWidth(); i++){
			for (int j = 0; j < img.getHeight(); j++){
				
				Color cImg2 = new Color(img2.getRGB(i, j));
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
						Color c = new Color(img.getRGB(x, y));
						
						
						r += ((double)c.getRed() * matrix[k][l] / norm );
						
						g += ((double)c.getGreen() * matrix[k][l] / norm);
						
						b += ((double)c.getBlue() * matrix[k][l] / norm);
						
						
					}
				}
				//System.out.println(" "  + r + " " + g + " " + b);
				img2.setRGB(i, j, new Color((int)r, (int)g, (int)b).getRGB());
			}
		}
		/*for (int i = 1; i < img.getHeight() - 1; i++){
			img2.setRGB(0, i, img.getRGB(0, i));
			img2.setRGB(img.getWidth() - 1, i, img.getRGB(img.getWidth() - 1, i));
		}
		for (int i = 1; i < img.getWidth() - 1; i++){
			img2.setRGB(i, 0, img.getRGB(i, 0));
			img2.setRGB(i, img.getHeight() - 1,img.getRGB(i, img.getHeight() - 1));
		}*/
		return img2;
	}
}
