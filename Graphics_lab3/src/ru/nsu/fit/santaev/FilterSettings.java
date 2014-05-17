package ru.nsu.fit.santaev;

import java.awt.Dialog;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Dialog.ModalExclusionType;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;


public class FilterSettings extends JDialog {
	
	JSlider slider = null;
	private JFrame mainFrame = null;
	
	
	public FilterSettings(JFrame mainFrame){
		slider = new JSlider();
		add(slider);
		this.mainFrame = mainFrame;
		//setModal(true);
	}
	public FilterSettings(JFrame mainFrame, String title){
		slider = new JSlider();
		add(slider);
		this.mainFrame = mainFrame;
		this.setTitle(title);
		//setModal(true);
	}
	public void setVisible(boolean arg0) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(300, 100);
		this.setLocation((int) dim.getWidth() / 2
				- (int) this.getSize().getWidth() / 2, (int) dim.getHeight()
				/ 2 - (int) this.getSize().getHeight() / 2);
	
		super.setVisible(false);
		super.setVisible(arg0);
	}
	public void setChangeListener(ChangeListener c){
		slider.addChangeListener(c);
	}
}
