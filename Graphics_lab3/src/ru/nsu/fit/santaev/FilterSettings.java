package ru.nsu.fit.santaev;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;


public class FilterSettings extends javax.swing.JFrame {
	
	JSlider slider = null;
	private JFrame mainFrame = null;
	
	public FilterSettings(JFrame mainFrame){
		slider = new JSlider();
		add(slider);
		this.mainFrame = mainFrame;
	}
	public void setVisible(boolean arg0) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(300, 100);
		this.setLocation((int) dim.getWidth() / 2
				- (int) this.getSize().getWidth() / 2, (int) dim.getHeight()
				/ 2 - (int) this.getSize().getHeight() / 2);

		super.setVisible(arg0);
	}
	public void setChangeListener(ChangeListener c){
		slider.addChangeListener(c);
	}
}
