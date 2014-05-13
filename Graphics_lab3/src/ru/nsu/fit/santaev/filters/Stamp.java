package ru.nsu.fit.santaev.filters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Stamp {

	private static double[][] matrix = new double[3][3]; 
	private static double norm = 1;
	
	static{
		matrix[0][0] = 0;
		matrix[0][1] = 1;
		matrix[0][2] = 0;
		
		matrix[1][0] = -1;
		matrix[1][1] = 0;
		matrix[1][2] = 1;
		
		matrix[2][0] = 0;
		matrix[2][1] = -1;
		matrix[2][2] = 0;
			
		norm = 1;
		
		//norm = 0.75 * 4 + 0.5 * 4 + 1;
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
				r += 128;
				g += 128;
				b += 128;
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
				img2.setRGB(i, j, new Color((int)r, (int)g, (int)b).getRGB());
			}
		}
		
		return img2;
	}
}
