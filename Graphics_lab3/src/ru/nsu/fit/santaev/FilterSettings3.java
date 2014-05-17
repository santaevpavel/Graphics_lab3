package ru.nsu.fit.santaev;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;


public class FilterSettings3 extends JDialog {
	
	JSlider slider = null;
	JSlider slider2 = null;
	JSlider slider3 = null;
	JPanel panel = null;
	private JFrame mainFrame = null;
	
	public FilterSettings3(JFrame mainFrame){
		slider = new JSlider();
		slider2 = new JSlider();
		slider3 = new JSlider();
		panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		//panel.add(defaultButton);
		panel.add(slider);
		panel.add(slider2);
		panel.add(slider3);
		add(panel);
		setModal(true);
		
		this.mainFrame = mainFrame;
	}
	public FilterSettings3(JFrame mainFrame, String title){
		slider = new JSlider();
		slider2 = new JSlider();
		slider3 = new JSlider();
		panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		//panel.add(defaultButton);
		add(panel);
		panel.add(slider);
		panel.add(slider2);
		panel.add(slider3);
		this.mainFrame = mainFrame;
		this.setTitle(title);
		setModal(true);
		
	}
	public void setVisible(boolean arg0) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(300, 100);
		this.setLocation((int) dim.getWidth() / 2
				- (int) this.getSize().getWidth() / 2, (int) dim.getHeight()
				/ 2 - (int) this.getSize().getHeight() / 2);
		
		//setMinimumSize(new Dimension(300, 100));
		
		super.setVisible(false);
		super.setVisible(arg0);
	}
	public void setChangeListener(ChangeListener c, ChangeListener c2, ChangeListener c3){
		slider.addChangeListener(c);
		slider2.addChangeListener(c2);
		slider3.addChangeListener(c3);
	}
}

