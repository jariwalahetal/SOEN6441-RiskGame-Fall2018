package com.risk.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

public class MapCreateView extends JFrame {
	private static final long serialVersionUID = 1L;
	public MapCreateView() {
		createJframe();
	} 
	private void createJframe() {
		String saveNameText = "Enter Map Name here";
		JFrame frame = new JFrame(getClass().getSimpleName());
	
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));

        JTextArea textBox = new JTextArea("[Continents]\n\n\n[Territories]");
        textBox.setSize(getMaximumSize());
        JButton button2 = new JButton("SAVE MAP");
        JTextField mapName = new JTextField(saveNameText);
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
