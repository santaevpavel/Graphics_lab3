package ru.nsu.fit.santaev;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.transaction.xa.Xid;

import ru.nsu.fit.santaev.filters.GaussBlur;
import ru.nsu.fit.santaev.filters.MyFilter;

public class Main {

	public static class MySettings extends JFrame {

		private static final long serialVersionUID = 1L;

		public static final int MIN_WIDTH = 400;
		public static final int MIN_HEIGHT = 300;

		private JSlider sliderZoom = null;
		private JSlider sliderMove = null;

		private JButton defaultButton = null;

		private JPanel panel = new JPanel();

		public MySettings(String title) {
			super(title);
			setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

			this.setLocation(mainFrame.getLocation().x + mainFrame.getWidth(),
					mainFrame.getLocation().y);
			JTextField text1 = new JTextField("Zoom step");
			JTextField text2 = new JTextField("Move step");

			defaultButton = new JButton();
			defaultButton.setText("Set default params");

			text1.setEditable(false);
			text1.setPreferredSize(new Dimension(100, 30));
			text2.setEditable(false);
			text2.setPreferredSize(new Dimension(100, 30));

			add(panel);

			panel.setLayout(new GridLayout(5, 1));
			panel.add(defaultButton);

		}

		@Override
		public void setVisible(boolean arg0) {
			this.setLocation(mainFrame.getLocation().x + mainFrame.getWidth(),
					mainFrame.getLocation().y);
			super.setVisible(arg0);
		}
	}

	public static final String MAIN_FRAME_TITLE = "Lab #3";
	private static final MainFrame mainFrame = new MainFrame(MAIN_FRAME_TITLE);
	private static Action settings;
	private static BufferedImage imgOrigin = null;
	private static BufferedImage imgResized = null;
	private static BufferedImage imgFiltered = null;
	private static BufferedImage imgBig = null;

	public static void main(String[] args) throws IOException {

		final MySettings settingsFrame = new MySettings("Settings");

		ImageIcon iconNew = new ImageIcon("res/images/draw_square.png");

		Action actionDrawSquare = new AbstractAction("New", iconNew) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.repaint();
			}
		};

		ImageIcon iconSettings = new ImageIcon("res/images/settings.png");
		settings = new AbstractAction("New", iconSettings) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				settingsFrame.setVisible(true);
			}
		};
		ImageIcon iconBlur = new ImageIcon("res/images/icon_blur.png");
		Action blur = new AbstractAction("New", iconBlur) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//imgFiltered = GaussBlur.doFilter(imgResized);
				imgFiltered = GaussBlur.doFilter(imgResized);
				mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
				mainFrame.repaint();
			}
		};
		blur.putValue("tip", "Размытие по Гауссу");
		ImageIcon iconApply = new ImageIcon("res/images/icon_apply.png");
		Action apply = new AbstractAction("New", iconApply) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				imgResized = imgFiltered.getSubimage(0, 0,
						imgFiltered.getWidth(), imgFiltered.getHeight());
				mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
				mainFrame.repaint();
			}
		};
		apply.putValue("tip", "Скопировать 3ю картинку в 2ую");
		mainFrame.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent arg0) {
			}

			@Override
			public void componentResized(ComponentEvent arg0) {
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {

				settingsFrame.setLocation(
						mainFrame.getLocation().x + mainFrame.getWidth(),
						mainFrame.getLocation().y);
				settingsFrame.invalidate();
			}

			@Override
			public void componentHidden(ComponentEvent arg0) {

			}
		});
		mainFrame.setOnClickListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				int maxSize = Math.max(imgBig.getHeight(), imgBig.getWidth());
				int xWidth = 0;
				int yHeight = 0;
				if (imgBig.getHeight() > imgBig.getWidth()){
					yHeight = 256;
					xWidth = (int) ((double)imgBig.getWidth() / imgBig.getHeight() * 256);
				}else{
					xWidth = 256;
					yHeight = (int) ((double)imgBig.getHeight() / imgBig.getWidth() * 256);
				}
				int width = (int)(256f / maxSize * 256);
				int xRect = x - width / 2;
				int yRect = y - width / 2;
				if (xRect < 0){
					xRect = 0;
				}
				if (yRect < 0){
					yRect = 0;
				}
				if (xRect + width >= xWidth){
					xRect = xWidth - 1 - width;
				}
				if (yRect + width >= yHeight){
					yRect = yHeight - 1 - width;
				}

				imgResized = imgBig.getSubimage((int)((double)xRect * imgBig.getWidth() / 256),
						(int)((double)yRect / 256 * imgBig.getHeight()), 256, 256);
				
				mainFrame.drawRect(true, new Rectangle(xRect, yRect , width, width));
				mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
				mainFrame.repaint();
				System.out.println("Mouse clicked");
			}
		}, new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				int maxSize = Math.max(imgBig.getHeight(), imgBig.getWidth());
				int xWidth = 0;
				int yHeight = 0;
				if (imgBig.getHeight() > imgBig.getWidth()){
					yHeight = 256;
					xWidth = (int) ((double)imgBig.getWidth() / imgBig.getHeight() * 256);
				}else{
					xWidth = 256;
					yHeight = (int) ((double)imgBig.getHeight() / imgBig.getWidth() * 256);
				}
				int width = (int)(256f / maxSize * 256);
				int xRect = x - width / 2;
				int yRect = y - width / 2;
				if (xRect + width >= xWidth){
					xRect = xWidth - 1 - width;
				}
				if (yRect + width >= yHeight){
					yRect = yHeight - 1 - width;
				}
				if (xRect < 0){
					xRect = 0;
				}
				if (yRect < 0){
					yRect = 0;
				}
				
				imgResized = imgBig.getSubimage((int)((double)xRect * imgBig.getWidth() / xWidth),
						(int)((double)yRect / (yHeight) * imgBig.getHeight()), 256, 256);
				
				mainFrame.drawRect(true, new Rectangle(xRect, yRect , width, width));
				mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
				mainFrame.repaint();
				System.out.println(" " + ((double)yRect / 256) 
						+ " " + ((double)(yRect + width) / 256));
			}
		});

		mainFrame.addSeparatorToToolbar();
		mainFrame.addButtonToToolbar(settings);
		mainFrame.addButtonToToolbar(blur);
		mainFrame.addButtonToToolbar(apply);
		
		Bitmap bmp = BitmapLoaderSaver.loadBMP("res/7.bmp");
		BufferedImage buf = BitmapResizer.getResizeTo256(bmp.pixels);
		// BufferedImage buf2 = GaussBlur.doFilter(buf);
		imgBig = bmp.pixels;
		imgOrigin = buf;
		imgResized = buf;
		imgFiltered = buf;

		mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
		mainFrame.repaint();
		// mainFrame.updateImgs();

	}
}
