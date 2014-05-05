package ru.nsu.fit.santaev;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

public class MyFileChooser extends JFrame {
	JFileChooser jFileChooser = null;

	public MyFileChooser() {
		jFileChooser = new JFileChooser();
		add(jFileChooser);
		jFileChooser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("  " + arg0.paramString() + " " + arg0.getSource());
			}
		});	
		jFileChooser.setVisible(true);
		
		FileFilter ff = new FileFilter() {
			
			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean accept(File arg0) {
				String ss = arg0.getAbsolutePath();
				if (arg0.isDirectory()){
					return true;
				}
				String s2 = ss.substring(ss.length() - 3,ss.length());
				System.out.println("s2 " + s2);
				if (s2.equals("bmp")){
					return true;
				}
				return false;
			}
		};
		jFileChooser.setFileFilter(ff);
		
	}

	@Override
	public void setVisible(boolean arg0) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(600, 400);
		this.setLocation((int) dim.getWidth() / 2
				- (int) this.getSize().getWidth() / 2, (int) dim.getHeight()
				/ 2 - (int) this.getSize().getHeight() / 2);

		jFileChooser.setCurrentDirectory(new File("res/"));
		super.setVisible(arg0);
	}

	public void setFileListener(ActionListener aa) {
		jFileChooser.addActionListener(aa);
	}
}
