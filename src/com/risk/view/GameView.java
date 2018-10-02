package com.risk.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
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
			    loadingReinforcementLabel();
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
		 /*
		  *NEED THIS INFO FROM CONTROLLER
		  *    for (int i = 0; i < Map.getCountryList().size(); i++) {
		        Country tempCountry = Map.getCountryList().get(i);
		        int[] coordinate = tempCountry.getCoordinate();
		        tempCountry.setPointInMapLabel(new JLabel("" + tempCountry.getSoilders()));
		        tempCountry.getPointInMapLabel().setFont(new Font("Courier", Font.BOLD, 20));
		        tempCountry.getPointInMapLabel().setForeground(tempCountry.getPlayer().getColor());
		        tempCountry.getPointInMapLabel().setBounds(coordinate[0], coordinate[1], 25, 25);
		        mapLabel.add(tempCountry.getPointInMapLabel());
		      } */
		    mapLabel = new JLabel(icon);
		    mapScrollPane = new JScrollPane(mapLabel);
		    mapScrollPane.setBounds(10, 10, 500, 500);
		    mapScrollPane.setBorder(new TitledBorder("Map"));
		    gameActionJpanel.add(mapScrollPane);
		    
	  }
	  
	  private  void loadingReinforcementLabel(){
		    reinforcementLabel.removeAll();
		    reinforcementLabel = null;
		    reinforcementLabel = new JLabel();
		    reinforcementLabel.setBorder(BorderFactory.createTitledBorder(null, "ReinforcementPhase",
		        TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
		        new Font("SansSerif", Font.PLAIN, 12), Color.RED));
		    reinforcementLabel.setBounds(mapScrollPane.getX()+500,
		        mapScrollPane.getY(), mapScrollPane.getWidth()+200,
		     200);
		 // Recreate every components in Label
		    playersTurnLabel = new JLabel(
		        "Player A");
		    playersTurnLabel.setBorder(new TitledBorder("Player's Turn"));
		    playersTurnLabel.setBounds(5, 20, reinforcementLabel.getWidth() / 2 - 8,
		        reinforcementLabel.getHeight() / 3 - 15);
		    
		    // listOfCountriesPlayerOccupied(Map.playerWithTurn());
		  //  String[] countryNameList = Map.playerWithTurn().listOfCountriesPlayerConqueredInStringArray();
		    String[] countryNameList={"INDIA","PAKISTAN","NEPAL"};
		    addUnitToCountryComboBox = new JComboBox<>(countryNameList);
		    addUnitToCountryComboBox.setBorder(new TitledBorder("Add Unit To Country"));
		    addUnitToCountryComboBox.setBounds(playersTurnLabel.getX() + playersTurnLabel.getWidth() + 3,
		        playersTurnLabel.getY(), playersTurnLabel.getWidth(), playersTurnLabel.getHeight());
		    
		    unassignedUnit = new JLabel("UNASSIGNED VALUE=10");
		    unassignedUnit.setBorder(new TitledBorder("Unassigned Unit"));
		    unassignedUnit.setBounds(playersTurnLabel.getX(),
		    		playersTurnLabel.getY() + playersTurnLabel.getHeight() + 5, playersTurnLabel.getWidth(),
		        playersTurnLabel.getHeight());
		    addUnitButton.setBounds(addUnitToCountryComboBox.getX(), unassignedUnit.getY(),
		            unassignedUnit.getWidth(), unassignedUnit.getHeight());

		    reinforcementLabel.add(playersTurnLabel);
		    reinforcementLabel.add(playersTurnLabel);
		    reinforcementLabel.add(unassignedUnit);
		    reinforcementLabel.add(addUnitToCountryComboBox);
		    reinforcementLabel.add(addUnitButton);
		    gameActionJpanel.add(reinforcementLabel);
		  
	  }
	  
	  public static void main(String[] args) {
			 GameView gameView=new GameView();
	    	 gameView.gameInitializer();
			
	}

	 

}
