package ru.nsu.fit.santaev;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JButton;
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
	public JButton ok = null;
	public JButton cancel = null;

	public FilterSettings3(JFrame mainFrame, String title) {
		slider = new JSlider();
		slider2 = new JSlider();
		slider3 = new JSlider();
		ok = new JButton("Ok");
		cancel = new JButton("Cancel");
		panel = new JPanel();
		panel.setLayout(new GridLayout(5, 1));
		// panel.add(defaultButton);
		panel.add(slider);
		panel.add(slider2);
		panel.add(slider3);
		panel.add(ok);
		panel.add(cancel);
		add(panel);
		setModal(true);

		this.mainFrame = mainFrame;
		this.setTitle(title);

	}

	public void setVisible(boolean arg0) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(300, 300);
		this.setLocation((int) dim.getWidth() / 2
				- (int) this.getSize().getWidth() / 2, (int) dim.getHeight()
				/ 2 - (int) this.getSize().getHeight() / 2);

		// setMinimumSize(new Dimension(300, 100));
		if (arg0) {
			slider.setValue(slider.getMaximum() / 2);
			slider2.setValue(slider2.getMaximum() / 2);
			slider3.setValue(slider3.getMaximum() / 2);
		}
		super.setVisible(false);
		super.setVisible(arg0);
	}

	public void setChangeListener(ChangeListener c, ChangeListener c2,
			ChangeListener c3) {
		slider.addChangeListener(c);
		slider2.addChangeListener(c2);
		slider3.addChangeListener(c3);
	}
}
