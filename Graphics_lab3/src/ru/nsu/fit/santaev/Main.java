package ru.nsu.fit.santaev;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.SliderUI;

import ru.nsu.fit.santaev.filters.Aquarelle;
import ru.nsu.fit.santaev.filters.Aquarelle2;
import ru.nsu.fit.santaev.filters.BlackAndWhite;
import ru.nsu.fit.santaev.filters.DoubleImg;
import ru.nsu.fit.santaev.filters.Floyd;
import ru.nsu.fit.santaev.filters.GaussBlur;
import ru.nsu.fit.santaev.filters.Grey;
import ru.nsu.fit.santaev.filters.Negative;
import ru.nsu.fit.santaev.filters.OrdDithering;
import ru.nsu.fit.santaev.filters.Roberts;
import ru.nsu.fit.santaev.filters.Sobel;
import ru.nsu.fit.santaev.filters.Stamp;

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
	private static Bitmap bmp = null;
	private static MyFileChooser fileChooser = null;
	private static BufferedImage copy = null;

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
				// imgFiltered = GaussBlur.doFilter(imgResized);
				imgFiltered = GaussBlur.doFilter(imgResized);
				mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
				mainFrame.repaint();
			}
		};
		ImageIcon iconNeg = new ImageIcon("res/images/icon_neg.png");
		Action neg = new AbstractAction("New", iconNeg) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// imgFiltered = GaussBlur.doFilter(imgResized);
				imgFiltered = Negative.doFilter(imgResized);
				mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
				mainFrame.repaint();
			}
		};
		final FilterSettings bwSettings = new FilterSettings(mainFrame,
				"Black and White");
		
		bwSettings.slider.setPaintLabels(true);
		Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel>();
		table.put(0, new JLabel("0"));
		table.put(50, new JLabel("128"));
		table.put(100, new JLabel("255"));
		bwSettings.slider.setLabelTable(table);
		
		ImageIcon iconBW = new ImageIcon("res/images/icon_bw.png");
		Action bw = new AbstractAction("New", iconBW) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// imgFiltered = GaussBlur.doFilter(imgResized);
				float def = 0.5f;

				copy = imgFiltered;// copy = imgFiltered.getSubimage(0, 0,
									// imgFiltered.getWidth(),
									// imgFiltered.getHeight());
				bwSettings.cancel.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						imgFiltered = copy;
						mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
						mainFrame.repaint();
						bwSettings.setVisible(false);
					}
				});
				bwSettings.ok.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						bwSettings.setVisible(false);
					}
				});
				bwSettings.setChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent e) {
						int i = bwSettings.slider.getMaximum();
						int j = bwSettings.slider.getValue();
						float k = (float) j / i;
						imgFiltered = BlackAndWhite.doFilter(imgResized, k);
						mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
						mainFrame.repaint();
					}
				});

				imgFiltered = BlackAndWhite.doFilter(imgResized, def);
				mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
				mainFrame.repaint();
				bwSettings.setVisible(true);
			}
		};
		ImageIcon iconGrey = new ImageIcon("res/images/icon_grey.png");
		Action grey = new AbstractAction("New", iconGrey) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// imgFiltered = GaussBlur.doFilter(imgResized);
				imgFiltered = Grey.doFilter(imgResized);
				mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
				mainFrame.repaint();
			}
		};
		ImageIcon iconFloyd = new ImageIcon("res/images/icon_floyd.png");
		final FilterSettings3 floydSettings = new FilterSettings3(mainFrame,
				"1 - red 2 - green 3 - blue");
		
		floydSettings.slider.setPaintLabels(true);
		table = new Hashtable<Integer, JLabel>();
		table.put(0, new JLabel("red max quality"));
		table.put(100, new JLabel("min quality"));
		floydSettings.slider.setLabelTable(table);
		floydSettings.slider2.setPaintLabels(true);
		table = new Hashtable<Integer, JLabel>();
		table.put(0, new JLabel("green max quality"));
		table.put(100, new JLabel("min quality"));
		floydSettings.slider2.setLabelTable(table);
		floydSettings.slider3.setPaintLabels(true);
		table = new Hashtable<Integer, JLabel>();
		table.put(0, new JLabel("blue max quality"));
		table.put(100, new JLabel("min quality"));
		floydSettings.slider3.setLabelTable(table);
		// final FilterSettings floydSettings2 = new FilterSettings(mainFrame,
		// "green");
		// final FilterSettings floydSettings3 = new FilterSettings(mainFrame,
		// "blue");
		Action floyd = new AbstractAction("New", iconFloyd) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				float def = 0.5f;
				copy = imgFiltered;// copy = imgFiltered.getSubimage(0, 0,
									// imgFiltered.getWidth(),
									// imgFiltered.getHeight());
				floydSettings.cancel.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						imgFiltered = copy;
						mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
						mainFrame.repaint();
						floydSettings.setVisible(false);
					}
				});
				floydSettings.ok.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						floydSettings.setVisible(false);
					}
				});
				ChangeListener cl = new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent e) {
						int ir = floydSettings.slider.getMaximum();
						int jr = floydSettings.slider.getValue();
						int kr = (int) ((float) jr / ir * 255);
						int ig = floydSettings.slider2.getMaximum();
						int jg = floydSettings.slider2.getValue();
						int kg = (int) ((float) jg / ig * 255);
						int ib = floydSettings.slider3.getMaximum();
						int jb = floydSettings.slider3.getValue();
						int kb = (int) ((float) jb / ib * 255);
						imgFiltered = Floyd.doFilter(imgResized, kr, kg, kb);
						mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
						mainFrame.repaint();
					}
				};
				floydSettings.setChangeListener(cl, cl, cl);

				// floydSettings2.setChangeListener(cl);
				// floydSettings3.setChangeListener(cl);
				imgFiltered = Floyd.doFilter(imgResized, 128, 128, 128);
				mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
				mainFrame.repaint();
				floydSettings.setVisible(false);
				floydSettings.setVisible(true);
			}
		};
		ImageIcon iconOrdDith = new ImageIcon("res/images/icon_orddith.png");
		Action ordDith = new AbstractAction("New", iconOrdDith) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// imgFiltered = GaussBlur.doFilter(imgResized);
				imgFiltered = OrdDithering.doFilter(imgResized);
				mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
				mainFrame.repaint();
			}
		};
		final FilterSettings robertSettings = new FilterSettings(mainFrame,
				"Robert");
		robertSettings.slider.setPaintLabels(true);
		table = new Hashtable<Integer, JLabel>();
		table.put(0, new JLabel("0"));
		table.put(50, new JLabel("128"));
		table.put(100, new JLabel("255"));
		robertSettings.slider.setLabelTable(table);
		ImageIcon iconRobert = new ImageIcon("res/images/icon_robert.png");
		Action robert = new AbstractAction("New", iconRobert) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				float def = 0.5f;
				copy = imgFiltered;// imgFiltered.getSubimage(0, 0,
									// imgFiltered.getWidth(),
									// imgFiltered.getHeight());
				robertSettings.cancel.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						imgFiltered = copy;
						mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
						mainFrame.repaint();
						robertSettings.setVisible(false);
					}
				});
				robertSettings.ok.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						robertSettings.setVisible(false);
					}
				});
				robertSettings.setChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent e) {
						int i = robertSettings.slider.getMaximum();
						int j = robertSettings.slider.getValue();
						float k = (float) j / i;
						// imgFiltered = Roberts.doFilter(imgResized, k);
						imgFiltered = BlackAndWhite.doFilter(
								Roberts.doFilter(imgResized, 1), k);
						mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
						mainFrame.repaint();
					}
				});

				// imgFiltered = Roberts.doFilter(imgResized, def);
				imgFiltered = BlackAndWhite.doFilter(
						Roberts.doFilter(imgResized, 1), def);
				mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
				mainFrame.repaint();
				robertSettings.setVisible(true);
			}
		};
		ImageIcon iconSobel = new ImageIcon("res/images/icon_sobel.png");
		
		final FilterSettings sobelSettings = new FilterSettings(mainFrame,
				"Sobel");
		
		sobelSettings.slider.setPaintLabels(true);
		table = new Hashtable<Integer, JLabel>();
		table.put(0, new JLabel("0"));
		table.put(50, new JLabel("128"));
		table.put(100, new JLabel("255"));
		sobelSettings.slider.setLabelTable(table);
		Action sobel = new AbstractAction("New", iconSobel) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				float def = 0.5f;
				copy = imgFiltered;// copy = imgFiltered.getSubimage(0, 0,
									// imgFiltered.getWidth(),
									// imgFiltered.getHeight());
				System.out.println("_________++++");
				sobelSettings.cancel.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						System.out.println("_________-----");
						imgFiltered = copy;
						mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
						mainFrame.repaint();
						sobelSettings.setVisible(false);
					}
				});
				sobelSettings.ok.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						sobelSettings.setVisible(false);
					}
				});
				sobelSettings.setChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent e) {
						System.out.println("__________");
						int i = sobelSettings.slider.getMaximum();
						int j = sobelSettings.slider.getValue();
						float k = (float) j / i;
						imgFiltered = BlackAndWhite.doFilter(
								Sobel.doFilter(imgResized, 1), k);
						mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
						mainFrame.repaint();
					}
				});

				imgFiltered = BlackAndWhite.doFilter(
						Sobel.doFilter(imgResized, 1), def);
				mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
				mainFrame.repaint();
				sobelSettings.setVisible(true);

			}
		};
		ImageIcon iconAqua = new ImageIcon("res/images/icon_aqua.png");
		Action aqua = new AbstractAction("New", iconAqua) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// imgFiltered = GaussBlur.doFilter(imgResized);
				imgFiltered = Aquarelle2.doFilter(imgResized);
				mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
				mainFrame.repaint();
			}
		};
		ImageIcon iconDouble = new ImageIcon("res/images/icon_double.png");
		Action doublef = new AbstractAction("New", iconDouble) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// imgFiltered = GaussBlur.doFilter(imgResized);
				imgFiltered = DoubleImg.doFilter(imgResized);
				mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
				mainFrame.repaint();
			}
		};
		ImageIcon iconStamp = new ImageIcon("res/images/icon_stamp.png");
		Action stamp = new AbstractAction("New", iconStamp) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// imgFiltered = GaussBlur.doFilter(imgResized);
				imgFiltered = Stamp.doFilter(imgResized);
				mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
				mainFrame.repaint();
			}
		};
		ImageIcon iconSave = new ImageIcon("res/images/save_file.png");
		Action save = new AbstractAction("New", iconSave) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//BitmapLoaderSaver.saveBmpFile("res/savedBmp.bmp", bmp,
				//		imgResized);
				final MyFileChooser fileChooser = new MyFileChooser();
				fileChooser.setVisible(true);
				fileChooser.setFile("res/savedBmp.bmp");
				fileChooser.setFileListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						String str = arg0.getSource().toString();
						int i = str.lastIndexOf("selectedFile=");
						if (-1 == i) {
							return;
						}
						String file = "";
						i += 13;
						while (',' != str.charAt(i)) {
							file = file + str.charAt(i);
							i++;
						}
						if (file == "") {
							fileChooser.setVisible(false);
							return;
						}
						System.out.println("file =|" + file + "|");
						//Bitmap bmp = null;
						try {
							//bmp = BitmapLoaderSaver.loadBMP(file);
							BitmapLoaderSaver.saveBmpFile(file, bmp,
									imgResized);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						BufferedImage buf = BitmapResizer.getResizeTo256(bmp.pixels);
						Main.bmp = bmp;
						// BufferedImage buf2 = GaussBlur.doFilter(buf);
						imgBig = bmp.pixels;
						imgOrigin = buf;
						imgResized = buf;
						imgFiltered = buf;

						mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
						mainFrame.repaint();
						fileChooser.setVisible(false);
					}
				});
			}
		};
		ImageIcon iconOpen = new ImageIcon("res/images/new_file.png");
		Action open = new AbstractAction("New", iconOpen) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fileChooser.setVisible(true);
			}
		};

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
				onMouseEvent(e);
			}
		}, new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				onMouseEvent(e);
			}
		});

		mainFrame.addSeparatorToToolbar();
		// mainFrame.addButtonToToolbar(settings);
		mainFrame.addButtonToToolbar(save);
		mainFrame.addButtonToToolbar(blur);
		mainFrame.addButtonToToolbar(grey);
		mainFrame.addButtonToToolbar(neg);
		mainFrame.addButtonToToolbar(floyd);
		mainFrame.addButtonToToolbar(ordDith);
		mainFrame.addButtonToToolbar(bw);
		mainFrame.addButtonToToolbar(robert);
		mainFrame.addButtonToToolbar(sobel);
		mainFrame.addButtonToToolbar(aqua);
		mainFrame.addButtonToToolbar(doublef);
		mainFrame.addButtonToToolbar(stamp);

		mainFrame.addButtonToToolbar(apply);
		mainFrame.addButtonToToolbar(open);

		apply.putValue("tip", "Скопировать 3ю картинку в 2ую");
		grey.putValue("tip", "Оттенки серого");
		neg.putValue("tip", "Негатив");
		floyd.putValue("tip", "Флойд");
		ordDith.putValue("tip", "Ordered dithering");
		bw.putValue("tip", "Черно-белый");
		robert.putValue("tip", "Роберт");
		aqua.putValue("tip", "Акварель");
		doublef.putValue("tip", "Увеличение");
		stamp.putValue("tip", "Штамп");

		Bitmap bmp = BitmapLoaderSaver.loadBMP("res/8.bmp");
		BufferedImage buf = BitmapResizer.getResizeTo256(bmp.pixels);
		Main.bmp = bmp;
		// BufferedImage buf2 = GaussBlur.doFilter(buf);
		imgBig = bmp.pixels;
		imgOrigin = buf;
		imgResized = buf;
		imgFiltered = buf;

		mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
		mainFrame.repaint();

		fileChooser = new MyFileChooser();
		fileChooser.setVisible(true);
		fileChooser.setFileListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String str = arg0.getSource().toString();
				int i = str.lastIndexOf("selectedFile=");
				if (-1 == i) {
					return;
				}
				String file = "";
				i += 13;
				while (',' != str.charAt(i)) {
					file = file + str.charAt(i);
					i++;
				}
				if (file == "") {
					fileChooser.setVisible(false);
					return;
				}
				System.out.println("file =|" + file + "|");
				Bitmap bmp = null;
				try {
					bmp = BitmapLoaderSaver.loadBMP(file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				BufferedImage buf = BitmapResizer.getResizeTo256(bmp.pixels);
				Main.bmp = bmp;
				// BufferedImage buf2 = GaussBlur.doFilter(buf);
				imgBig = bmp.pixels;
				imgOrigin = buf;
				imgResized = buf;
				imgFiltered = buf;

				mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
				mainFrame.repaint();
				fileChooser.setVisible(false);
			}
		});

	}

	public static void onMouseEvent(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int maxSize = Math.max(imgBig.getHeight(), imgBig.getWidth());
		int xWidth = 0;
		int yHeight = 0;
		if (imgBig.getHeight() > imgBig.getWidth()) {
			yHeight = 256;
			xWidth = (int) ((double) imgBig.getWidth() / imgBig.getHeight() * 256);
		} else {
			xWidth = 256;
			yHeight = (int) ((double) imgBig.getHeight() / imgBig.getWidth() * 256);
		}
		int width = (int) (256f / maxSize * 256);
		int xRect = x - width / 2;
		int yRect = y - width / 2;
		if (xRect + width >= xWidth) {
			xRect = xWidth - 1 - width;
		}
		if (yRect + width >= yHeight) {
			yRect = yHeight - 1 - width;
		}
		if (xRect < 0) {
			xRect = 0;
		}
		if (yRect < 0) {
			yRect = 0;
		}
		
		imgResized = imgBig.getSubimage(
				(int) ((double) xRect * imgBig.getWidth() / xWidth),
				(int) ((double) yRect / (yHeight) * imgBig.getHeight()), 256,
				256);

		mainFrame.drawRect(true, new Rectangle(xRect, yRect, width, width));
		mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
		mainFrame.repaint();
		System.out.println(" " + ((double) yRect / 256) + " "
				+ ((double) (yRect + width) / 256));
	}
}
