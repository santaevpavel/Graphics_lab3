package ru.nsu.fit.santaev;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class BitmapResizer {

	public static BufferedImage getResizeTo256(BufferedImage img) {
		int height = 0;
		int width = 0;
		double rate = (double)width / height;
		if (img.getHeight() > img.getWidth()) {
			width = (int) (256f / img.getHeight() * img.getWidth());
			height = 256;
		} else {
			height = (int) (256f / img.getWidth() * img.getHeight());
			width = 256;
		}
		BufferedImage imgResized = new BufferedImage(img.getWidth(), height,
				BufferedImage.TYPE_3BYTE_BGR);
		double start = 0;
		double end = 0;
		double k = ((double) height) / img.getHeight();
		for (int j = 0; j < img.getWidth(); j++) {
			for (int i = 0; i < img.getHeight(); i++) {
				start = i * k;
				end = (i + 1) * k;
				if ((int) end >= imgResized.getHeight()) {
					end = imgResized.getHeight() - 1;
				}
				if ((int) start >= imgResized.getHeight()) {
					start = imgResized.getHeight() - 1;
				}
				if ((int) start == (int) end) {
					Color cOld = new Color(imgResized.getRGB(j, (int) start));
					int cInt = (int) ((double) img.getRGB(j, i));
					Color cNew = new Color(cOld.getRed()
							+ (int) ((new Color(cInt)).getRed() * k),
							cOld.getGreen()
									+ (int) ((new Color(cInt)).getGreen() * k),
							cOld.getBlue()
									+ (int) ((new Color(cInt)).getBlue() * k));
					imgResized.setRGB(j, (int) start, cNew.getRGB());
				} else {
					double part1 = (double) ((int) end - start) / (end - start);
					double part2 = (double) (end - (int) end) / (end - start);
					int c2 = imgResized.getRGB(j, (int) start);
					int c22 = imgResized.getRGB(j, (int) end);
					c2 = c2 + (int) ((double) img.getRGB(j, i) * k * part1);
					c2 = c22
							+ (int) ((double) img.getRGB(j, i + 1) * k * part2);
					// Color c3 = new Color(c2);
					// Color c33 = new Color(c22);
					Color cOld = new Color(imgResized.getRGB(j, (int) start));
					int cInt = img.getRGB(j, i);
					Color cNew = new Color(
							cOld.getRed()
									+ (int) ((new Color(cInt)).getRed() * k * part1),
							cOld.getGreen()
									+ (int) ((new Color(cInt)).getGreen() * k * part1),
							cOld.getBlue()
									+ (int) ((new Color(cInt)).getBlue() * k * part1));
					imgResized.setRGB(j, (int) start, cNew.getRGB());

					cOld = new Color(imgResized.getRGB(j, (int) end));
					cInt = img.getRGB(j, i + 1);
					cNew = new Color(
							cOld.getRed()
									+ (int) ((new Color(cInt)).getRed() * k * part2),
							cOld.getGreen()
									+ (int) ((new Color(cInt)).getGreen() * k * part2),
							cOld.getBlue()
									+ (int) ((new Color(cInt)).getBlue() * k * part2));
					imgResized.setRGB(j, (int) end, cNew.getRGB());
				}
			}
		}
		start = 0;
		end = 0;
		img = imgResized;
		imgResized = new BufferedImage(width, height,
				BufferedImage.TYPE_3BYTE_BGR);
		k = ((double) width) / img.getWidth();
		for (int j = 0; j < img.getHeight(); j++) {
			for (int i = 0; i < img.getWidth(); i++) {
				start = i * k;
				end = (i + 1) * k;
				if ((int) end >= width) {
					end = width - 1;
				}
				if ((int) start >= width) {
					start = width - 1;
				}
				if ((int) start == (int) end || ((int) start == start && (int)start + 1 == (int) end && (int) end == end)) {
					//if (()start)
					Color cOld = new Color(imgResized.getRGB((int) start, j));
					int cInt = (int) ((double) img.getRGB(i, j));
					Color cNew = new Color(cOld.getRed()
							+ (int) ((new Color(cInt)).getRed() * k),
							cOld.getGreen()
									+ (int) ((new Color(cInt)).getGreen() * k),
							cOld.getBlue()
									+ (int) ((new Color(cInt)).getBlue() * k));
					imgResized.setRGB((int) start, j, cNew.getRGB());
				} else {
					double part1 = (double) ((int) end - start) / (end - start);
					double part2 = (double) (end - (int) end) / (end - start);
					int c2 = imgResized.getRGB((int) start, j);
					int c22 = imgResized.getRGB((int) end, j);
					c2 = c2 + (int) ((double) img.getRGB(i, j) * k * part1);
					c2 = c22
							+ (int) ((double) img.getRGB(i + 1, j) * k * part2);
					// Color c3 = new Color(c2);
					// Color c33 = new Color(c22);
					Color cOld = new Color(imgResized.getRGB((int) start, j));
					int cInt = img.getRGB(i, j);
					Color cNew = new Color(
							cOld.getRed()
									+ (int) ((new Color(cInt)).getRed() * k * part1),
							cOld.getGreen()
									+ (int) ((new Color(cInt)).getGreen() * k * part1),
							cOld.getBlue()
									+ (int) ((new Color(cInt)).getBlue() * k * part1));
					
					imgResized.setRGB((int) start, j, cNew.getRGB());

					cOld = new Color(imgResized.getRGB((int) end, j));
					cInt = img.getRGB(i + 1, j);
					cNew = new Color(
							cOld.getRed()
									+ (int) ((new Color(cInt)).getRed() * k * part2),
							cOld.getGreen()
									+ (int) ((new Color(cInt)).getGreen() * k * part2),
							cOld.getBlue()
									+ (int) ((new Color(cInt)).getBlue() * k * part2));
					imgResized.setRGB((int) end, j, cNew.getRGB());
				}
			}
		}
		
		return imgResized;
	}
}
