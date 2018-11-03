package com.risk.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.risk.helper.Common;

public class CardExchangeView {
	private static JFrame cardFrame = null;
	private static JPanel cardPanel;
	private static JLabel cardExchangeLabel;
	private static JLabel playersTurnJlabel;
	private static JComboBox<String> palyerOwnedCard;
	private static JLabel totalNewArmies;
	private static JButton exchangeButton = new JButton("Exchange Cards");
	private static JButton exitButton = new JButton("Exit");
		
	
	public void exchangeInitializerView() {
		cardFrame = new JFrame("Card Exchange View");
		cardPanel = new JPanel(null);
		cardFrame.setSize(800, 700);
		
		cardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cardExchangeLabel = new JLabel();
		cardExchangeLabel.setBorder(
				BorderFactory.createTitledBorder(null, "Exchange Card", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
		cardExchangeLabel.setBounds(100, 100, 500, 500);
		playersTurnJlabel = new JLabel("Player Name");
		Font font = new Font("Courier", Font.BOLD, 24);
		playersTurnJlabel.setFont(font);
		playersTurnJlabel.setForeground(Color.RED);
		playersTurnJlabel.setBorder(new TitledBorder("Active Player"));
		playersTurnJlabel.setBounds(15, 25, 220, 70);
		cardExchangeLabel.add(playersTurnJlabel);
		cardPanel.add(cardExchangeLabel);
		cardFrame.add(cardPanel);
		cardFrame.setVisible(true);
		
	}
	
	public static void main(String args[]) {
		CardExchangeView cardExchangeView=new CardExchangeView();
		cardExchangeView.exchangeInitializerView();
	}

}
