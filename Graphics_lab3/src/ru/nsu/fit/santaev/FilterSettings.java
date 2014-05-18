package ru.nsu.fit.santaev;

import java.awt.Dialog;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Dialog.ModalExclusionType;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;


public class FilterSettings extends JDialog {
	
	JSlider slider = null;
	private JFrame mainFrame = null;
	public JButton ok = null;
	public JButton cancel = null;
	private JPanel panel = null;
	public FilterSettings(JFrame mainFrame, String title){
		slider = new JSlider();
		ok = new JButton("Ok");
		cancel = new JButton("Cancel");
		panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		panel.add(slider);
		panel.add(ok);
		panel.add(cancel);
		add(panel);
		this.mainFrame = mainFrame;
		this.setTitle(title);	
		setModal(true);
	}
	public void setVisible(boolean arg0) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(300, 200);
		this.setLocation((int) dim.getWidth() / 2
				- (int) this.getSize().getWidth() / 2, (int) dim.getHeight()
				/ 2 - (int) this.getSize().getHeight() / 2);

		if (arg0){
			slider.setValue(slider.getMaximum()/ 2);
		}
		super.setVisible(false);
		super.setVisible(arg0);
	}
	public void setChangeListener(ChangeListener c){
		slider.addChangeListener(c);
	}
}

