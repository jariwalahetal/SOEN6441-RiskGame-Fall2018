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
	public JButton button2 = new JButton("SAVE MAP");
	String saveNameText = "Enter Map Name here";
	public JTextArea textBox = new JTextArea("[Continents]\n\n\n[Territories]");
	public JTextField mapName = new JTextField(saveNameText);
	public void showCreateView() {
		createJframe();
	} 
	/**
	 * Creates Jframe
	 */
	private void createJframe() {
		
		JFrame frame = new JFrame(getClass().getSimpleName());
	
        JPanel pane = new JPanel();
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
