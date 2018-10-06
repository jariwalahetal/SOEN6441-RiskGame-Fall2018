package com.risk.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

import com.risk.controller.GameController;
import com.risk.helper.Common;
import com.risk.model.Country;
import com.risk.model.Map;
import com.risk.viewmodel.CountryAdorner;
import com.risk.viewmodel.PlayerAdorner;

/**
 * Initiate the risk game view in java swings
 * @author sadgi
 *
 */
public class GameView {
	
	Common common=new Common();
	 private static JPanel gameActionJpanel = new JPanel(null);
	  private static JFrame gameJframe= null;

	  // Map Label
	  private static JLabel mapJlabel = new JLabel();
	  private static JScrollPane mapScrollPane = null;

	 // Initialization Label
	  private static JLabel initializationJlabel = new JLabel();
	  private static JLabel playersTurnJlabel = new JLabel("Default");
	  private static JLabel armyLeftJlabel = new JLabel("0");
	  
	  // Reinforcement Label
	  private static JLabel reinforcementsJlabel = new JLabel();
	  private static JLabel reinforcementplayersTurnJlabel = new JLabel("Default"); 
	  private static JLabel reinforcementUnassignedUnit = new JLabel("0"); 
	  private static JComboBox<String> addArmyToCountryJcomboBox = new JComboBox<>();
	  private static JButton addArmy = new JButton("Add Army");

	  // Fortification Label
	  private static JLabel fortificationJlabel = new JLabel();
	  private static JComboBox<String> sourceCountry = new JComboBox<>();
	  private static JComboBox<String> destinationCountry = new JComboBox<>();
	  private static JComboBox<String> noOfArmyToMoveJcomboBox = new JComboBox<>();
	  private static JButton fortificationMoveButton = new JButton("Move Army");

	  // Save Game Button
	  private static JLabel saveButtonJlabel = new JLabel();
	  private static JButton saveButton = new JButton("Save");
	  	  
	  public void gameInitializer(PlayerAdorner activePlayer,ArrayList<CountryAdorner> arrayList,Map map){
			 gameJframe = new JFrame("Risk Game");
			    loadGameActionView(arrayList, map,activePlayer);
			    gameJframe.add(gameActionJpanel);	
			    loadingInitializationLabel(activePlayer);
			    loadingReinforcementLabel();
			    loadingFortificationLabel();
			    loadingSaveGameButton();			    
			    gameJframe.setSize(1200, 700);
			    gameJframe.setVisible(true);
			    gameJframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 }
	  
	  public void loadGameActionView(ArrayList<CountryAdorner> arrayList,Map map,PlayerAdorner activePlayer){
		  gameActionJpanel.removeAll();
		  gameActionJpanel = new JPanel(null);
		  File imageFile = null;
		  imageFile = new File(map.getMapPath()+map.getMapName() + ".bmp");
		  //  imageFile = new File("assets/maps/"+"Africa" + ".bmp");
		    Image image;
		    ImageIcon icon = null;
		    try {
		      image = ImageIO.read(imageFile);
		      icon = new ImageIcon(image);
		    } catch (IOException e) {
		      e.printStackTrace();
		    }

		    mapJlabel = new JLabel(icon);
		    GameController gameController=new GameController();
		    for (int i = 0; i < arrayList.size(); i++) {
		        CountryAdorner tempCountry = arrayList.get(i);
		        int xCoordinate =tempCountry.getxCoordiate();
		        int yCoordinate=tempCountry.getyCoordiate();
		        JLabel newLabel = new JLabel("" + tempCountry.getnoOfArmies());
		        newLabel.setFont(new Font("Courier", Font.BOLD, 20));
		        newLabel.setForeground(common.getColor(tempCountry.getPlayerColor()));
		        newLabel.setBounds(xCoordinate, yCoordinate, 25, 25);
		        newLabel.setToolTipText(tempCountry.getCountryId() + "--" +tempCountry.getCountryName());
		        mapJlabel.add(newLabel);
		       
		      
		    }
		   
		    mapScrollPane = new JScrollPane(mapJlabel);
		    mapScrollPane.setBounds(10, 10, 700, 650);
		    mapScrollPane.setBorder(new TitledBorder(""));
		    gameActionJpanel.add(mapScrollPane);
		    
	  }
	  
	  public void addActionListenToMapLabels(MouseListener listener){
		int n=  mapJlabel.getComponentCount();
		  for(int i=0;i<n;i++)
		  {
			JLabel jLabel= (JLabel) mapJlabel.getComponent(i); 
			jLabel.addMouseListener(listener);
		  }
	  }
	  
	  public  void loadingInitializationLabel(PlayerAdorner activePlayer){
		    initializationJlabel.removeAll();
		    initializationJlabel = null;
		    initializationJlabel = new JLabel();
		    initializationJlabel.setBorder(BorderFactory.createTitledBorder(null, "Initialization Phase",
		        TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
		        new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
		    initializationJlabel.setBounds(mapScrollPane.getX()+700,
		        mapScrollPane.getY(), 490,
		     100);
		 // Recreate every components in Label
		    playersTurnJlabel = new JLabel(activePlayer.getName()+" "+activePlayer.getColor());
		    playersTurnJlabel.setBorder(new TitledBorder("Active Player"));
		    playersTurnJlabel.setBounds(15, 25,  220,
		        70);
		    
		    armyLeftJlabel = new JLabel(""+activePlayer.getNoOfUnassignedArmies());
		    armyLeftJlabel.setBorder(new TitledBorder("Armies Left"));
		    armyLeftJlabel.setBounds(playersTurnJlabel.getX()+240,
		    playersTurnJlabel.getY() -70+ playersTurnJlabel.getHeight(), playersTurnJlabel.getWidth(),
		    playersTurnJlabel.getHeight());
		   

		    initializationJlabel.add(playersTurnJlabel);
		    initializationJlabel.add(playersTurnJlabel);
		    initializationJlabel.add(armyLeftJlabel);
		   
		    gameActionJpanel.add(initializationJlabel);
		  
	  }
	  
	  public void loadingReinforcementLabel(){
		  reinforcementsJlabel.removeAll();
		  reinforcementsJlabel=null;
		  reinforcementsJlabel=new JLabel();
		  reinforcementsJlabel.setBorder(
		 BorderFactory.createTitledBorder(null, "Reinforcement Phase", TitledBorder.DEFAULT_JUSTIFICATION,
			       TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
		  reinforcementsJlabel.setBounds(initializationJlabel.getX(),
				  initializationJlabel.getY() + 10 + initializationJlabel.getHeight(),
				  initializationJlabel.getWidth(), 140);
		  
		  reinforcementplayersTurnJlabel = new JLabel("Player turn");
		  reinforcementplayersTurnJlabel.setBorder(new TitledBorder("Player's Turn"));
		  reinforcementplayersTurnJlabel.setBounds(15, 25, 220,
				  50 );
		  
		  String[] countryNameList = {"Country A", "country B", "country C", "Country D"};
		  addArmyToCountryJcomboBox = new JComboBox<>(countryNameList);
		  addArmyToCountryJcomboBox.setBorder(new TitledBorder("Add Unit To Country"));
		  addArmyToCountryJcomboBox.setBounds(reinforcementplayersTurnJlabel.getX()+20 + reinforcementplayersTurnJlabel.getWidth() + 3,
		    		reinforcementplayersTurnJlabel.getY(), reinforcementplayersTurnJlabel.getWidth(), reinforcementplayersTurnJlabel.getHeight());
		 
		  reinforcementUnassignedUnit = new JLabel("Assign army=10");
		  reinforcementUnassignedUnit.setBorder(new TitledBorder("Reinforced Army Unit"));
		  reinforcementUnassignedUnit.setBounds(reinforcementplayersTurnJlabel.getX(),
				  reinforcementplayersTurnJlabel.getY() + reinforcementplayersTurnJlabel.getHeight() + 5, reinforcementplayersTurnJlabel.getWidth(),
				  reinforcementplayersTurnJlabel.getHeight());

		  addArmy.setBounds(addArmyToCountryJcomboBox.getX(), reinforcementUnassignedUnit.getY(),
				  reinforcementUnassignedUnit.getWidth(), reinforcementUnassignedUnit.getHeight());
		  
		  reinforcementsJlabel.add(reinforcementplayersTurnJlabel);
		  reinforcementsJlabel.add(addArmyToCountryJcomboBox);
		  reinforcementsJlabel.add(reinforcementUnassignedUnit);
		  reinforcementsJlabel.add(addArmy);
		   gameActionJpanel.add(reinforcementsJlabel); 
		  
	  }
	  
	  public  void loadingFortificationLabel() {
		  fortificationJlabel.removeAll();
		    fortificationJlabel = null;
		    fortificationJlabel = new JLabel();
		    fortificationJlabel.setBorder(
		        BorderFactory.createTitledBorder(null, "Fortification Phase", TitledBorder.DEFAULT_JUSTIFICATION,
		            TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
		    fortificationJlabel.setBounds(reinforcementsJlabel.getX(),
		    		reinforcementsJlabel.getY() + 10 + reinforcementsJlabel.getHeight(),
		    		reinforcementsJlabel.getWidth(), 140);
		 
		    String conquerdCountries[]={"Country A","Country B","Country C","Country D"};
		    sourceCountry =
		    new JComboBox<>(conquerdCountries);
		    sourceCountry.setBorder(new TitledBorder("Source Country"));
		    sourceCountry.setBounds(15, 25, 220, 50);
	        String destinationCountries[]={"Country A","Country B","Country C","Country D"};
		    destinationCountry = new JComboBox<>(destinationCountries);
		    destinationCountry.setBorder(new TitledBorder("Destination Country"));
		    destinationCountry.setBounds(
		    		sourceCountry.getX()+20+sourceCountry.getWidth()+3,
		    		sourceCountry.getY(), sourceCountry.getWidth(),
		    		sourceCountry.getHeight());

		    noOfArmyToMoveJcomboBox.setBounds(sourceCountry.getX(),
		    sourceCountry.getHeight() + sourceCountry.getY() + 7,
		    sourceCountry.getWidth(), sourceCountry.getHeight());
		    noOfArmyToMoveJcomboBox.setBorder(new TitledBorder("Total number of army to move"));

		    fortificationMoveButton.setBounds(destinationCountry.getX(), noOfArmyToMoveJcomboBox.getY(),
		        destinationCountry.getWidth(), destinationCountry.getHeight());
		    

		    // Add all components in Label
		    fortificationJlabel.add(sourceCountry);
		    fortificationJlabel.add(destinationCountry);
		    fortificationJlabel.add(noOfArmyToMoveJcomboBox);
		    fortificationJlabel.add(fortificationMoveButton);
		    // Adding Label to Panel
		    gameActionJpanel.add(fortificationJlabel);  
		  
		  
	  }
	  
	  public  void loadingSaveGameButton() {
		    saveButtonJlabel.removeAll();
		    saveButtonJlabel = null;
		    saveButtonJlabel = new JLabel();
		    saveButtonJlabel
		        .setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION,
		            TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
		    saveButtonJlabel.setBounds(fortificationJlabel.getX(),
		        fortificationJlabel.getY() + 10 + fortificationJlabel.getHeight(),
		        fortificationJlabel.getWidth(), fortificationJlabel.getHeight() - 13);

		    saveButton = new JButton("Save Game");
		    int buttonHeight = 25;
		    int buttonWidth = 100;
		    saveButton.setBounds(saveButtonJlabel.getWidth() / 2 - buttonWidth / 2,
		        saveButtonJlabel.getHeight() / 2 - buttonHeight / 2, buttonWidth, buttonHeight);
		    
		    saveButtonJlabel.add(saveButton);

		    gameActionJpanel.add(saveButtonJlabel);
		  }
	  
	 
}
