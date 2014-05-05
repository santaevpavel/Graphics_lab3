package ru.nsu.fit.santaev;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;

import javax.jws.Oneway;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public static final int MIN_WIDTH = 800;
	public static final int MIN_HEIGHT = 700;

	private static String menuFileTitle = "File";
	private static String subMenuFileTitle = "New file";
	private static String subMenuEXitTitle = "Exit";
	private static String menuHelpTitle = "Help";
	private static String subMenuAbouTitle = "About";

	private static String aboutString = "About";

	private JToolBar toolBar = null;
	private JPanel drawPanel = null;

	private int width = 0;
	private int height = 0;
	
	private boolean isDrawRect = false;
	private Rectangle rect = new Rectangle(0, 0, 100, 100);
	
	private BufferedImage img1 = null;
	private BufferedImage img2 = null;
	private BufferedImage img3 = null;

	private BufferedImage imgOriginal1 = null;
	private BufferedImage imgOriginal2 = null;
	private BufferedImage imgOriginal3 = null;
	
	private JPanel panel1 = null;
	private JPanel panel2 = null;
	private JPanel panel3 = null;

	public MainFrame(String title) {
		super(title);
		setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createGUI();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int) dim.getWidth() / 2
				- (int) this.getSize().getWidth() / 2, (int) dim.getHeight()
				/ 2 - (int) this.getSize().getHeight() / 2);
		this.setVisible(true);
		height = getHeight();
		width = getWidth();
		img1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// clickedPointPref = e.getPoint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {

			}

			@Override
			public void mouseDragged(MouseEvent e) {

			}
		});
		addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {

			}
		});
		img1 = new BufferedImage(width, height,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics graphImg = img1.createGraphics();
		graphImg.setColor(Color.RED);
		graphImg.drawLine(0, 0, 100, 100);
		img2 = new BufferedImage(width, height,
				BufferedImage.TYPE_3BYTE_BGR);
		img3 = new BufferedImage(width, height,
				BufferedImage.TYPE_3BYTE_BGR);
		
	}

	protected void createGUI() {
		createMenu();
		createToolBar();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		
		panel1 = new DrawJPanel(PanelType.ORIGINAL);
		panel2 = new DrawJPanel(PanelType.SCALED);
		panel3 = new DrawJPanel(PanelType.FILTERED);
		
		p1.add(panel1);
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p2.add(panel2);
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
		p3.add(panel3);
		p3.setLayout(new BoxLayout(p3, BoxLayout.X_AXIS));
		panel1.setMaximumSize(new Dimension(256, 256));
		panel2.setMaximumSize(new Dimension(256, 256));
		panel3.setMaximumSize(new Dimension(256, 256));
		
		panel1.setMinimumSize(new Dimension(256, 256));
		panel2.setMinimumSize(new Dimension(256, 256));
		panel3.setMinimumSize(new Dimension(256, 256));
		
		
		
		drawPanel = new JPanel();
		getContentPane().add(drawPanel);
		drawPanel.setLayout(new GridLayout(2, 2));
		drawPanel.setMaximumSize(new Dimension(512, 512));
		drawPanel.setMinimumSize(new Dimension(512, 512));
		
		drawPanel.add(p1);
		drawPanel.add(p2);
		drawPanel.add(p3);
		
		
	}
	
	protected void createMenu() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu(menuFileTitle);
		menuBar.add(fileMenu);

		JMenuItem fileMenuSubItem1 = new JMenuItem(subMenuFileTitle);
		fileMenu.add(fileMenuSubItem1);
		JMenuItem fileMenuSubItem3 = new JMenuItem(subMenuEXitTitle);
		fileMenu.add(fileMenuSubItem3);
		fileMenuSubItem3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(NORMAL);
			}
		});
		JMenu helpMenu = new JMenu(menuHelpTitle);
		JMenuItem fileMenuSubItem2 = new JMenuItem(subMenuAbouTitle);
		helpMenu.add(fileMenuSubItem2);
		fileMenuSubItem2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				JOptionPane.showMessageDialog(MainFrame.this, aboutString);
			}
		});
		menuBar.add(helpMenu);

		setJMenuBar(menuBar);
	}

	protected void createToolBar() {
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		ImageIcon iconNew = new ImageIcon("res/images/new_file.png");
		ImageIcon iconSave = new ImageIcon("res/images/save_file.png");
		ImageIcon iconAbout = new ImageIcon("res/images/about.png");
		ImageIcon iconExit = new ImageIcon("res/images/exit.png");

		Action actionNew = new AbstractAction("New", iconNew) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setTitle("Clicked " + "new file");
			}
		};
		Action actionSave = new AbstractAction("Save", iconSave) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setTitle("Clicked " + "save file");
			}
		};
		Action actionAbout = new AbstractAction("About", iconAbout) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(MainFrame.this, aboutString);
			}
		};
		Action actionExit = new AbstractAction("Exit", iconExit) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(NORMAL);
			}
		};
		// addButtonToToolbar(actionNew);
		// addButtonToToolbar(actionSave);
		addButtonToToolbar(actionAbout);
		addButtonToToolbar(actionExit, 1);
		getContentPane().add(toolBar, BorderLayout.NORTH);
	}
	public void setOnClickListener(MouseListener m, MouseMotionListener mm){
		panel1.addMouseListener(m);
		panel1.addMouseMotionListener(mm);
	}
	public void addButtonToToolbar(Action action) {
		JButton button = new SmallButton(action);
		button.setToolTipText((String) action.getValue("tip"));
		toolBar.add(button, 0);
		toolBar.repaint();
		revalidate();
	}

	public void addSeparatorToToolbar() {
		JSeparator sep = new JToolBar.Separator();
		sep.setSize(10, 30);
		toolBar.add(sep, 0);
		toolBar.repaint();
		revalidate();
	}

	public void addButtonToToolbar(Action action, int index) {
		JButton button = new SmallButton(action);
		toolBar.add(button, index);
	}
	public void setImgs(BufferedImage img1, BufferedImage img2, BufferedImage img3){
		//this.imgOriginal1 = img1;
		//this.imgOriginal2 = img2;
		//this.imgOriginal3 = img3;
		this.img1 = img1;
		//this.img1.setRGB(100, 100, Color.BLUE.getRGB());
		this.img2 = img2;
		this.img3 = img3;
		
		//this.img1 = null;
		//this.img2 = null;
		//this.img3 = null;
	}
	public void drawRect(boolean isDraw, Rectangle rect){
		this.isDrawRect = isDraw;
		this.rect = rect;
	}
	public void updateImgs(){
		img1 = imgOriginal1;
		img2 = imgOriginal2;
		img3 = imgOriginal3;
	}
	class SmallButton extends JButton {

		private static final long serialVersionUID = 1L;

		public SmallButton(Action act) {
			super((Icon) act.getValue(Action.SMALL_ICON));

			setMargin(new Insets(1, 1, 1, 1));
			addActionListener(act);
			// addMouseListener(act);
		}

		public float getAlignmentY() {
			return 0.5f;
		}

	}

	enum PanelType {
		ORIGINAL, SCALED, FILTERED,
	}
	
	class DrawJPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		public PanelType type = PanelType.ORIGINAL;

		private int height = 0;
		private int width = 0;

		public DrawJPanel(PanelType type) {
			this.type = type;
			setBackground(Color.WHITE);
			System.out.println("OnCreate");
			addComponentListener(new ComponentListener() {
				
				@Override
				public void componentShown(ComponentEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void componentResized(ComponentEvent arg0) {
					if (height > 0 || width > 0){
						//setSize(256, 256);
					}
				}
				
				@Override
				public void componentMoved(ComponentEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void componentHidden(ComponentEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}

		protected void updateGraphics(Graphics graph) {
			//log("Update " + type);
			if (!(height == getHeight() && width == getWidth())) {
				height = getHeight();
				width = getWidth();
				return;
				/*switch (type) {
				case ORIGINAL:
					log("Update " + height);
					img1 = new BufferedImage(width, height,
							BufferedImage.TYPE_3BYTE_BGR);
					break;
				case SCALED:
					img2 = new BufferedImage(width, height,
							BufferedImage.TYPE_3BYTE_BGR);
					break;
				case FILTERED:
					img3 = new BufferedImage(width, height,
							BufferedImage.TYPE_3BYTE_BGR);
					break;
				}
				*/
			
			}
			graph.drawImage(getImg(), 0, 0, null);
			height = getHeight();
			width = getWidth();
			if (isDrawRect && type == PanelType.ORIGINAL){
				Graphics gr = graph;
				gr.setColor(Color.RED);
				gr.drawLine(rect.x, rect.y, rect.x, rect.y + rect.height);
				gr.drawLine(rect.x, rect.y, rect.x + rect.width, rect.y);
				gr.drawLine(rect.x + rect.width, rect.y, rect.x + rect.width, rect.y + rect.height);
				gr.drawLine(rect.x, rect.y + rect.height, rect.x + rect.width, rect.y + rect.height);
			}

			//log("Update!! " + getImg().getWidth());
			//graph.drawLine(0, 0, 100, 100);
			
			
		}
		public BufferedImage getImg(){
			switch (type) {
			case ORIGINAL:
				return img1;
			case SCALED:
				return img2;
			case FILTERED:
				return img3;
			}
			return img1;
		}
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			updateGraphics(g);
		}
	}

	public static void log(String str) {
		System.out.println(str);
	}
}
