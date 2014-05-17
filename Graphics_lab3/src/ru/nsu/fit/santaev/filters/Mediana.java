package ru.nsu.fit.santaev.filters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Mediana {

	private static double[][] matrix = new double[3][3]; 
	private static double norm = 1;
	
	static{
		double i1 = 0.5f;
		double i2 = 0.75f;
		matrix[0][0] = i1;
		matrix[2][2] = i1;
		matrix[2][0] = i1;
		matrix[0][2] = i1;
		
		matrix[1][1] = 1f;
		matrix[1][0] = i2;
		matrix[0][1] = i2;
		matrix[2][1] = i2;
		matrix[1][2] = i2;
		
		norm = 0.75 * 4 + 0.5 * 4 + 1;
	}
	
	public static BufferedImage doFilter(BufferedImage img){
		BufferedImage img2 = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics gr = img2.createGraphics();
		gr.setColor(Color.BLACK);
		gr.drawRect(0, 0, img.getWidth(), img.getHeight());
		ArrayList<Integer> r = new ArrayList<>();
		ArrayList<Integer> g = new ArrayList<>();
		ArrayList<Integer> b = new ArrayList<>();
		Collections.sort(r);
		for (int i = 1; i < img.getWidth() - 1; i++){
			for (int j = 1; j < img.getHeight() - 1; j++){
				r.clear();
				g.clear();
				b.clear();
				for (int k = 0; k < 3; k++){
					for (int l = 0; l < 3; l++){
						Color c = new Color(img.getRGB(i + k - 1, j + l - 1));
						r.add(c.getRed());
						g.add(c.getGreen());
						b.add(c.getBlue());
					}
				}
				Collections.sort(r);
				Collections.sort(g);
				Collections.sort(b);
				img2.setRGB(i, j, new Color(r.get(4), g.get(4), b.get(4)).getRGB());
			}
		}
	
		return img2;
	}
}

