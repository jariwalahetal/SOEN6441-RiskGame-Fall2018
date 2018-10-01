package com.risk.view;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import com.risk.model.Game;



/**
 * Initiate the game view in java swings
 * @author sadgi
 *
 */
public class GameView {
	
	 private static JPanel gameActionJpanel = new JPanel(null);
	  private static JFrame gameJframe= null;
	  private static boolean firstTurn = true;

	  // Map Label
	  private static JLabel mapLabel = new JLabel();
	  private static JScrollPane mapScrollPane = null;

	  // Reinforcement Label
	  private static JLabel reinforcementLabel = new JLabel();
	  private static JLabel playersTurnLabel = new JLabel("Default");
	  private static JLabel unassignedUnit = new JLabel("0");
	  private static JComboBox<String> addUnitToCountryComboBox = new JComboBox<>();
	  private static JButton addUnitButton = new JButton("Add Unit");

	  // Phase View Label
	  private static JLabel phaseViewLabel = new JLabel();


	  // Fortification Label
	  private static JLabel fortificationLabel = new JLabel();
	  private static JComboBox<String> soldierMoveFromComboBox = new JComboBox<>();
	  private static JComboBox<String> soldierMoveToComboBox = new JComboBox<>();
	  private static JComboBox<String> noOfSoldierMoveComboBox = new JComboBox<>();
	  private static JButton fortificationMoveButton = new JButton("Move");

	  // Save Game Button
	  private static JLabel saveButtonLabel = new JLabel();
	  private static JButton saveButton = new JButton("Save Game");
	  
	  
	  
	  public void gameInitializer(){
			 gameJframe = new JFrame("Risk Game");
			    loadGameActionView();
			    gameJframe.add(gameActionJpanel);				  
			    gameJframe.setSize(1200, 700);
			    gameJframe.setVisible(true);
			    gameJframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 }
	  
	  public void loadGameActionView(){
		  gameActionJpanel.removeAll();
		  gameActionJpanel = new JPanel(null);
		  File imageFile = null;
		    imageFile = new File("assets/maps/"+"Africa" + ".bmp");
		    Image image;
		    ImageIcon icon = null;
		    try {
		      image = ImageIO.read(imageFile);
		      icon = new ImageIcon(image);
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		    mapLabel = new JLabel(icon);
		    mapScrollPane = new JScrollPane(mapLabel);
		    mapScrollPane.setBounds(10, 10, 500, 500);
		    mapScrollPane.setBorder(new TitledBorder("Map"));
		    gameActionJpanel.add(mapScrollPane);
		    
	  }
	  
	  public static void main(String[] args) {
			 GameView gameView=new GameView();
	    	 gameView.gameInitializer();
			
	}

	 

}
