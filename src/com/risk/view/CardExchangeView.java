package com.risk.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.risk.helper.CardEnum;
import com.risk.model.Game;


public class CardExchangeView {
	private static JFrame cardFrame = null;
	private static JPanel cardPanel;
	private static JLabel cardExchangeLabel;
	private static JLabel playersTurnJlabel;
	private static JList<String> palyerOwnedCard;
	private static JLabel totalNewArmies;
	private static JButton exchangeButton = new JButton("Exchange Cards");
	private static JButton exitButton = new JButton("No Exchange And Exit");
		
	
	public void exchangeInitializerView(Game game) {
		cardFrame = new JFrame("Card Exchange View");
		cardPanel = new JPanel(null);
		cardFrame.setSize(800, 600);
		
		cardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cardExchangeLabel = new JLabel();
		cardExchangeLabel.setBorder(
				BorderFactory.createTitledBorder(null, "Exchange Card", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLACK	));
		cardExchangeLabel.setBounds(100, 100, 600, 400);
		playersTurnJlabel = new JLabel(game.getCurrentPlayer().getName());
		Font font = new Font("Courier", Font.BOLD, 24);
		playersTurnJlabel.setFont(font);
		playersTurnJlabel.setForeground(Color.RED);
		playersTurnJlabel.setBorder(new TitledBorder("Active Player"));
		playersTurnJlabel.setBounds(30, 45, 250, 70);
		ArrayList<CardEnum> typeOfCards= game.getCurrentPlayer().getCards();
		String cards[]=new String[typeOfCards.size()];
		for(int i=0;i<typeOfCards.size();i++) {
			cards[i]=typeOfCards.get(i).toString();
		}
		palyerOwnedCard = new JList<>(cards);;
		palyerOwnedCard.setBorder(new TitledBorder("Cards Owned"));
		palyerOwnedCard.setBounds(310, 45, 250, 70);
		totalNewArmies = new JLabel(""+game.getCurrentPlayer().getNoOfTradedArmies());
		totalNewArmies.setBorder(new TitledBorder("New  Armies Assigned"));
		totalNewArmies.setBounds(180,150,250,70);
		exchangeButton.setBounds(120,255,160,40);
		exchangeButton.addActionListener(new ActionListener() { 

			ArrayList<String> selectedCards= (ArrayList<String>) palyerOwnedCard.getSelectedValuesList();
		    public void actionPerformed(ActionEvent e) { 
		      game.tradeCards(selectedCards);
		    } 
		});
		exitButton.setBounds(310,255,160,40);
		exitButton.addActionListener(new ActionListener() { 
		    public void actionPerformed(ActionEvent e) { 
		        boolean cardBalance = game.getCurrentPlayer().IsCardsAvailableForTradeInReinforcement();
		       
		    } 
		});
		cardExchangeLabel.add(totalNewArmies);
		cardExchangeLabel.add(palyerOwnedCard);
		cardExchangeLabel.add(playersTurnJlabel);
		cardExchangeLabel.add(exchangeButton);
		cardExchangeLabel.add(exitButton);
		cardPanel.add(cardExchangeLabel);
		cardFrame.add(cardPanel);	
		cardFrame.setVisible(true);
		
	}
	

}
