package com.risk.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
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
			    loadingFortificationLabel();
			    loadingSaveGameButton();
			    
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
		    mapScrollPane.setBounds(10, 10, 700, 650);
		    mapScrollPane.setBorder(new TitledBorder(""));
		    gameActionJpanel.add(mapScrollPane);
		    
	  }
	  
	  private  void loadingReinforcementLabel(){
		    reinforcementLabel.removeAll();
		    reinforcementLabel = null;
		    reinforcementLabel = new JLabel();
		    reinforcementLabel.setBorder(BorderFactory.createTitledBorder(null, "ReinforcementPhase",
		        TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
		        new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
		    reinforcementLabel.setBounds(mapScrollPane.getX()+700,
		        mapScrollPane.getY(), 490,
		     180);
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
	  
	  private static void loadingFortificationLabel() {
		  fortificationLabel.removeAll();
		    fortificationLabel = null;
		    fortificationLabel = new JLabel();
		    fortificationLabel.setBorder(
		        BorderFactory.createTitledBorder(null, "Fortification", TitledBorder.DEFAULT_JUSTIFICATION,
		            TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
		    fortificationLabel.setBounds(reinforcementLabel.getX(),
		    		reinforcementLabel.getY() + 10 + reinforcementLabel.getHeight(),
		    		reinforcementLabel.getWidth(), 140);
		    // Recreate every components in Label
		    String conquerdCountries[]={"Country A","Country B","Country C","Country D"};
		    soldierMoveFromComboBox =
		        new JComboBox<>(conquerdCountries);
		    soldierMoveFromComboBox.setBorder(new TitledBorder("Source Country"));
		    soldierMoveFromComboBox.setBounds(20, 20, 252, 52);
		   

	

		    soldierMoveToComboBox = new JComboBox<>();
		    soldierMoveToComboBox.setBorder(new TitledBorder("Destination Country"));
		    soldierMoveToComboBox.setBounds(
		        soldierMoveFromComboBox.getX() + soldierMoveFromComboBox.getWidth() + 10,
		        soldierMoveFromComboBox.getY(), 200,
		        soldierMoveFromComboBox.getHeight());

		    noOfSoldierMoveComboBox.setBounds(soldierMoveFromComboBox.getX(),
		        soldierMoveFromComboBox.getHeight() + soldierMoveFromComboBox.getY() + 7,
		        soldierMoveFromComboBox.getWidth(), soldierMoveFromComboBox.getHeight());
		    noOfSoldierMoveComboBox.setBorder(new TitledBorder("Total number of army to move"));

		    fortificationMoveButton.setBounds(soldierMoveToComboBox.getX(), noOfSoldierMoveComboBox.getY(),
		        soldierMoveToComboBox.getWidth(), soldierMoveToComboBox.getHeight());
		    

		    // Add all components in Label
		    fortificationLabel.add(soldierMoveFromComboBox);
		    fortificationLabel.add(soldierMoveToComboBox);
		    fortificationLabel.add(noOfSoldierMoveComboBox);
		    fortificationLabel.add(fortificationMoveButton);
		    // Adding Label to Panel
		    gameActionJpanel.add(fortificationLabel);  
		  
		  
	  }
	  
	  private static void loadingSaveGameButton() {
		    saveButtonLabel.removeAll();
		    saveButtonLabel = null;
		    saveButtonLabel = new JLabel();
		    saveButtonLabel
		        .setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION,
		            TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
		    saveButtonLabel.setBounds(fortificationLabel.getX(),
		        fortificationLabel.getY() + 10 + fortificationLabel.getHeight(),
		        fortificationLabel.getWidth(), fortificationLabel.getHeight() - 13);

		    saveButton = new JButton("Save Game");
		    int buttonHeight = 25;
		    int buttonWidth = 100;
		    saveButton.setBounds(saveButtonLabel.getWidth() / 2 - buttonWidth / 2,
		        saveButtonLabel.getHeight() / 2 - buttonHeight / 2, buttonWidth, buttonHeight);
		    
		    saveButtonLabel.add(saveButton);

		    gameActionJpanel.add(saveButtonLabel);
		  }
	  public static void main(String[] args) {
			 GameView gameView=new GameView();
	    	 gameView.gameInitializer();
			
	}

	 

}
