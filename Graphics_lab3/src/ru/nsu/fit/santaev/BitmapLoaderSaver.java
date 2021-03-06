package ru.nsu.fit.santaev;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.lang.ProcessBuilder.Redirect;
import java.nio.Buffer;
import java.util.ArrayList;

import javax.imageio.plugins.bmp.BMPImageWriteParam;

import org.omg.CORBA.OMGVMCID;

public class BitmapLoaderSaver {

	public static Bitmap loadBMP(String filename) throws IOException {
		Bitmap img = new Bitmap();
		File f = new File(filename);
		FileInputStream fis = new FileInputStream(f);
		// ObjectInputStream ois = new ObjectInputStream(fis);
		DataInputStream ois = new DataInputStream(fis);
		img.header.bfType = ois.readShort();
		img.header.bfSize = Integer.reverseBytes(ois.readInt());
		img.header.bfReserved1 = ois.readShort();
		img.header.bfReserved2 = ois.readShort();

		img.header.bfOffBits = Integer.reverseBytes(ois.readInt());

		img.infoHeader.biSize = readUnsignedInt(ois);
		img.infoHeader.biWidth = Integer.reverseBytes(ois.readInt());
		img.infoHeader.biHeight = Integer.reverseBytes(ois.readInt());
		img.infoHeader.biPlanes = ois.readShort();
		img.infoHeader.biBitCount = ois.readShort();
		img.infoHeader.biCompression = ois.readInt();
		img.infoHeader.biSizeImage = ois.readInt();
		img.infoHeader.biXPelsPerMeter = ois.readInt();
		img.infoHeader.biYPelsPerMeter = ois.readInt();
		img.infoHeader.biClrUsed = ois.readInt();
		img.infoHeader.biClrImportant = ois.readInt();

		// img.tag.rgbBlue = ois.readByte();
		// img.tag.rgbGreen = ois.readByte();
		// img.tag.rgbRed = ois.readByte();
		// img.tag.rgbReserved = ois.readByte();

		img.width = img.infoHeader.biWidth;
		img.height = img.infoHeader.biHeight;

		// img.pixels = new BitmapPixel[img.width * img.height];
		img.pixels = new BufferedImage(img.width, img.height,
				BufferedImage.TYPE_3BYTE_BGR);
		ois.close();
		long size = f.length();
		byte[] bytes = new byte[(int) size];
		fis = new FileInputStream(f);
		ois = new DataInputStream(fis);
		ois.readFully(bytes);
		// BufferedReader br = new BufferedReader(arg0);
		int offset = (int) img.header.bfOffBits;
		InputStream is = null;
		is = new ByteArrayInputStream(bytes);
		is.read(bytes, 0, offset);

		for (int i = 0; i < img.height; i++) {
			for (int j = 0; j < img.width; j++) {
				// System.out.println("= " + i + " " + j);
				// BitmapPixel p = new BitmapPixel();
				// p.b = bytes[offset + i * j * 3];
				// p.g = bytes[offset + i * j * 3 + 1];
				// p.r = bytes[offset + i * j * 24 + 2];
				// img.pixels[i * j] = p;
				int b = is.read();
				int g = is.read();
				int r = is.read();
				//System.out.println("= " + r + " " + g + " " + b);
				int color = (new Color(r, g, b)).getRGB();
				img.pixels.setRGB(j, img.height - i - 1, color);
			}
		}
		ois.close();
		return img;
	}

	public static long readUnsignedInt(DataInputStream ois) throws IOException {
		// long x1 = (long)ois.read();
		// long x2 = (long)ois.read();
		// long x3 = (long)ois.read();
		// long x4 = (long)ois.read();
		// int a = ois.readInt();
		// long s = a & 0xffffffff;
		// long b = a & 0xffffffffL;
		// long b = x1 * 8 * 8 * 8 + x2 * 8 * 8 + x3 * 8 + x4;
		byte[] bytes = new byte[4];
		ois.read(bytes, 0, 4);
		int index = 0;
		int firstByte = (0x000000FF & ((int) bytes[index]));
		int secondByte = (0x000000FF & ((int) bytes[index + 1]));
		int thirdByte = (0x000000FF & ((int) bytes[index + 2]));
		int fourthByte = (0x000000FF & ((int) bytes[index + 3]));
		long anUnsignedInt = ((long) (firstByte << 24 | secondByte << 16
				| thirdByte << 8 | fourthByte)) & 0xFFFFFFFFL;
		return anUnsignedInt;
	}
	public static void saveBmpFile(String filename, Bitmap imgOld, BufferedImage imgNew) throws IOException{
		File f = new File(filename);
		f.createNewFile();
		//FileInputStream fis = new FileInputStream(f);
		FileOutputStream fos = new FileOutputStream(f);
		//DataInputStream ois = new DataInputStream(fis);
		DataOutputStream ois = new DataOutputStream(fos);
		//ois.writeShort(img.header.bfType);
		ois.writeShort(imgOld.header.bfType);
		ois.writeInt(Integer.reverseBytes((int) imgNew.getHeight() * imgNew.getWidth() * 24 + 54));
		ois.writeShort(imgOld.header.bfReserved1);
		ois.writeShort(imgOld.header.bfReserved2);

		//ois.writeInt(Integer.reverseBytes((int) img.header.bfOffBits));
		ois.writeInt(Integer.reverseBytes(54));
		
		ois.writeInt((int) imgOld.infoHeader.biSize);
		ois.writeInt(Integer.reverseBytes(imgNew.getWidth()));
		ois.writeInt(Integer.reverseBytes(imgNew.getHeight()));
		ois.writeShort(imgOld.infoHeader.biPlanes);
		ois.writeShort(imgOld.infoHeader.biBitCount);
		ois.writeInt((int)imgOld.infoHeader.biCompression);
		ois.writeInt((int)imgNew.getHeight() * imgNew.getWidth() * 24);
		ois.writeInt(imgOld.infoHeader.biXPelsPerMeter);
		ois.writeInt(imgOld.infoHeader.biYPelsPerMeter);
		ois.writeInt((int)imgOld.infoHeader.biClrUsed);
		ois.writeInt((int)imgOld.infoHeader.biClrImportant);
		
		//byte[] pixel = new byte[img.height * img.width * 24];
		//int offset = ois.size();
		//ois.
		
		for (int i = 0; i < imgNew.getHeight(); i++) {
			for (int j = 0; j < imgNew.getWidth(); j++) {
				int color = imgNew.getRGB(j, imgNew.getHeight() - i - 1);
				Color c = new Color(color);
				int b = c.getBlue();
				int g = c.getGreen();
				int r = c.getRed();
				//img.pixels.setRGB(j, img.height - i - 1, color);
				ois.write(b);
				ois.write(g);
				ois.write(r);
			}
		}
		ois.close();
		
	}
	
	/*
	 * public static BufferedImage toBufferedImage256(Bitmap b) { BufferedImage
	 * img = new BufferedImage(256, 256, BufferedImage.TYPE_3BYTE_BGR); for (int
	 * i = 0; i < 256; i++) { for (int j = 0; j < 256; j++) { Color color = new
	 * Color(b.pixels[i * b.width + j].r, b.pixels[i b.width + j].g, b.pixels[i
	 * * b.width + j].b); img.setRGB(j, i, color.getRGB()); } } return img;
	 * 
	 * }
	 */
}

class Bitmap {
	public int width = 0;
	public int height = 0;

	BitmapHeader header = new BitmapHeader();
	BitmapInfoHeader infoHeader = new BitmapInfoHeader();
	// BitmapTagRGBQUAD tag = new BitmapTagRGBQUAD();
	// BitmapPixel[] pixels = null;
	BufferedImage pixels = null;
}

/*
 * BYTE � 8-������ ����������� �����. WORD � 16-������ ����������� �����. DWORD
 * � 32-������ ����������� �����. LONG � 32-������ ����� �� ������.
 */
class BitmapHeader {
	int bfType;
	long bfSize;
	int bfReserved1;
	int bfReserved2;
	long bfOffBits;
}

class BitmapInfoHeader {
	long biSize;
	int biWidth;
	int biHeight;
	short biPlanes;
	short biBitCount;
	long biCompression;
	long biSizeImage;
	int biXPelsPerMeter;
	int biYPelsPerMeter;
	long biClrUsed;
	long biClrImportant;
};

class BitmapTagRGBQUAD {
	byte rgbBlue;
	byte rgbGreen;
	byte rgbRed;
	byte rgbReserved;
};

class BitmapPixel {
	byte r;
	byte g;
	byte b;
}
