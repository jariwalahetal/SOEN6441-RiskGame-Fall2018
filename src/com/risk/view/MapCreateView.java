package com.risk.view;

import java.awt.BorderLayout;

import javax.swing.*;

public class MapCreateView extends JFrame {
	private static final long serialVersionUID = 1L;
	public MapCreateView() {
		createJframe();
	} 
	private void createJframe() {
		 JFrame frame = new JFrame();
		 frame.add(createMainPanel());
		 frame.add(createMainPanel2());
		 frame.setVisible(true);
	}
	private JPanel createMainPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JButton myButton = new JButton("My Button");
		panel.add(myButton);
//		panel.add(myButton);
		return panel;
	}
	private JPanel createMainPanel2() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JTextArea textArea = new JTextArea("text area");
		panel.add(textArea);
//		panel.add(myButton);
		return panel;
	}

}
