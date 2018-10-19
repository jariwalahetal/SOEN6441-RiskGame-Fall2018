package com.risk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;

import javax.swing.*;

/**
 * This class's purpose is to create a create map view
 * 
 * @author jasraj Singh Bedi
 * @since 1-October-2018
 */
public class MapCreateView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel pane = new JPanel();
	private JFrame frame = new JFrame(getClass().getSimpleName());
	public JButton button2 = new JButton("SAVE MAP");
	String saveNameText = "Enter Map Name here";
	private JTextArea textBox = new JTextArea("[Continents]\n\n\n[Territories]");
	private JTextField mapName = new JTextField(saveNameText);

	/**
	 * Creates Jframe to display create map window
	 */
	public void showCreateView() {
		createJframe();
	}

	/**
	 * This function destroys the Jframe.
	 */
	public void killFrame() {
		frame.dispose();
	}

	/**
	 * Returns map data
	 * @return String textbox text
	 */
	public String returnTextAreaText() {
		return textBox.getText();
	}

	/**
	 * Returns map name
	 * @return String mapName
	 */
	public String returnMapNameText() {
		return mapName.getText();
	}

	/**
	 * Creates Jframe
	 */
	private void createJframe() {

		pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));

		textBox.setSize(getMaximumSize());

		mapName.setForeground(Color.GRAY);
		mapName.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (mapName.getText().equals(saveNameText)) {
					mapName.setText("");
					mapName.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (mapName.getText().isEmpty()) {
					mapName.setForeground(Color.GRAY);
					mapName.setText(saveNameText);
				}
			}
		});

		pane.add(button2);
		pane.add(mapName);
		pane.add(Box.createHorizontalGlue());

		frame.add(textBox);
		frame.add(pane, BorderLayout.SOUTH);

		pane.setSize(getMaximumSize());
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
