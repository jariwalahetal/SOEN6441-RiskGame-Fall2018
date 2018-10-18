package com.risk.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
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
import com.risk.helper.Common;
import com.risk.helper.EnumColor;
import com.risk.helper.PhaseEnum;
import com.risk.model.Country;
import com.risk.model.Game;
import com.risk.model.Map;


/**
 * To hold the countries information for view
 * 
 * @author Binay
 *
 */
class ViewCountries
{   
	private int countryId;
	private String countryName;
	private int xCoordinate;
	private int yCoordinate; 
	private int noOfArmies;
	private EnumColor CountryColor;
	private int playerID;
	private ArrayList<String> neighboursString = new ArrayList<>();
	
	
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public int getxCoordinate() {
		return xCoordinate;
	}
	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	public int getyCoordinate() {
		return yCoordinate;
	}
	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	public int getNoOfArmies() {
		return noOfArmies;
	}
	public void setNoOfArmies(int noOfArmies) {
		this.noOfArmies = noOfArmies;
	}
	public EnumColor getCountryColor() {
		return CountryColor;
	}
	public void setCountryColor(EnumColor countryColor) {
		CountryColor = countryColor;
	}
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}	
	public ArrayList<String> getNeighboursString() {
		return neighboursString;
	}
	public void setNeighboursString(ArrayList<String> neighboursString) {
		this.neighboursString = neighboursString;
	}
	
}


/**
 * Initiate the risk game view in java swings
 * 
 * @author sadgi
 *
 */
public class GameView implements Observer{
	private static JFrame gameJframe = null;
	private static JPanel gameActionJpanel;
	
	// Map Label
	private static JLabel mapJlabel;
	private static JScrollPane mapScrollPane = null;
	private static HashMap<String,Component> mapLabels= new HashMap<>();

	// Initialization Label
	private static JLabel initializationJlabel;
	private static JLabel playersTurnJlabel; 
	private static JLabel armyLeftJlabel; 

	// Reinforcement Label
	private static JLabel reinforcementsJlabel;
	private static JLabel reinforcementplayersTurnJlabel;
	private static JLabel reinforcementUnassignedUnit;
	private static JComboBox<String> addArmyToCountryJcomboBox;
	private static JButton addArmy = new JButton("Add Army");

	// Fortification Label
	private static JLabel fortificationJlabel;
	private static JComboBox<String> sourceCountry;

	private static JComboBox<String> destinationCountry;
	private static JComboBox<String> noOfArmyToMoveJcomboBox;
	private static JButton fortificationMoveButton = new JButton("Move Army");
	
	
    String activePlayerName = null;
    int activePlayerId;
    EnumColor activePlayerColor = null;
	String activePlayerUnassignedArmiesCount;   
    String mapPath;
    ArrayList<ViewCountries> countryList = new ArrayList<ViewCountries>();
    PhaseEnum phase;
        
    public void gameInitializer() {
		//gameActionJpanel = new JPanel(null);
		loadGameActionView();
		loadingInitializationLabel();
	    loadingReinforcementLabel();
        loadingFortificationLabel();

		 
		/*if(phase == PhaseEnum.Startup)
		{ reinforcementsJlabel.setVisible(false);			
  		  fortificationJlabel.setVisible(false);
		}
		else
		{ reinforcementsJlabel.setVisible(true);			
	      fortificationJlabel.setVisible(true);		
		}*/		
			 
		gameJframe.setSize(1200, 700);
		gameJframe.setVisible(true);
		gameJframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void loadGameActionView() {
		gameJframe = new JFrame("Risk Game");
		gameActionJpanel = new JPanel(null);
		File imageFile = null;
		
		imageFile = new File(mapPath);
		Image image;
		ImageIcon icon = null;
		try {
			image = ImageIO.read(imageFile);
			icon = new ImageIcon(image);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
       // ArrayList<Country> countryList = map.getCountryList();
		mapJlabel = new JLabel(icon);
		for (int i = 0; i < countryList.size(); i++) {
			ViewCountries tempCountry = countryList.get(i);
			int xCoordinate = tempCountry.getxCoordinate();
			int yCoordinate = tempCountry.getyCoordinate();
			JLabel newLabel = new JLabel("" + tempCountry.getNoOfArmies());
			newLabel.setName("mapLabel" + tempCountry.getCountryId());
			newLabel.setFont(new Font("Courier", Font.BOLD, 20));
			newLabel.setForeground(Common.getColor(tempCountry.getCountryColor()));
			newLabel.setBounds(xCoordinate, yCoordinate, 25, 25);
			newLabel.setToolTipText(tempCountry.getCountryId() + "--" + tempCountry.getCountryName());
			mapLabels.put(String.valueOf(tempCountry.getCountryId()), newLabel);
			mapJlabel.add(newLabel);
		}


		mapScrollPane = new JScrollPane(mapJlabel);
		mapScrollPane.setBounds(10, 10, 700, 650);
		mapScrollPane.setBorder(new TitledBorder(""));
		gameActionJpanel.add(mapScrollPane);

		gameJframe.add(gameActionJpanel);
	    
	}

	public void loadingInitializationLabel() {
		initializationJlabel = new JLabel();
		initializationJlabel.setBorder(
				BorderFactory.createTitledBorder(null, "Initialization Phase", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
		initializationJlabel.setBounds(mapScrollPane.getX() + 700, mapScrollPane.getY(), 490, 100);
		
		// Recreate every components in Label
		playersTurnJlabel = new JLabel(activePlayerName);
		playersTurnJlabel.setBackground(Common.getColor(activePlayerColor));
		playersTurnJlabel.setBorder(new TitledBorder("Active Player"));
		playersTurnJlabel.setBounds(15, 25, 220, 70);

		armyLeftJlabel = new JLabel("" + activePlayerUnassignedArmiesCount);
		armyLeftJlabel.setBorder(new TitledBorder("Armies Left"));
		armyLeftJlabel.setBounds(playersTurnJlabel.getX() + 240,
				playersTurnJlabel.getY() - 70 + playersTurnJlabel.getHeight(), playersTurnJlabel.getWidth(),
				playersTurnJlabel.getHeight());

		initializationJlabel.add(playersTurnJlabel);
		initializationJlabel.add(playersTurnJlabel);
		initializationJlabel.add(armyLeftJlabel);

		gameActionJpanel.add(initializationJlabel);

	}

	public void loadingReinforcementLabel() {
		reinforcementsJlabel = new JLabel();
		reinforcementsJlabel.setBorder(
				BorderFactory.createTitledBorder(null, "Reinforcement Phase", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
		reinforcementsJlabel.setBounds(initializationJlabel.getX(),
				initializationJlabel.getY() + 10 + initializationJlabel.getHeight(), initializationJlabel.getWidth(),
				140);

		reinforcementplayersTurnJlabel = new JLabel(activePlayerName);
		reinforcementplayersTurnJlabel.setBorder(new TitledBorder("Player's Turn"));
		reinforcementplayersTurnJlabel.setBounds(15, 25, 220, 50);

		ArrayList<String> countryNameList = new ArrayList<String>();
		
		for (int i = 0; i < countryList.size(); i++) {
			ViewCountries tempCountry = countryList.get(i);
            if(activePlayerId == tempCountry.getPlayerID())
             { countryNameList.add(tempCountry.getCountryName());
             }
		}
		
		addArmyToCountryJcomboBox = new JComboBox(countryNameList.toArray());
		addArmyToCountryJcomboBox.setBorder(new TitledBorder("Add Unit To Country"));
		addArmyToCountryJcomboBox.setBounds(
				reinforcementplayersTurnJlabel.getX() + 20 + reinforcementplayersTurnJlabel.getWidth() + 3,
				reinforcementplayersTurnJlabel.getY(), reinforcementplayersTurnJlabel.getWidth(),
				reinforcementplayersTurnJlabel.getHeight());

		reinforcementUnassignedUnit = new JLabel(activePlayerUnassignedArmiesCount);
		reinforcementUnassignedUnit.setBorder(new TitledBorder("Reinforced Army Unit"));
		reinforcementUnassignedUnit.setBounds(reinforcementplayersTurnJlabel.getX(),
				reinforcementplayersTurnJlabel.getY() + reinforcementplayersTurnJlabel.getHeight() + 5,
				reinforcementplayersTurnJlabel.getWidth(), reinforcementplayersTurnJlabel.getHeight());

		addArmy.setBounds(addArmyToCountryJcomboBox.getX(), reinforcementUnassignedUnit.getY(),
				reinforcementUnassignedUnit.getWidth(), reinforcementUnassignedUnit.getHeight());

		reinforcementsJlabel.add(reinforcementplayersTurnJlabel);
		reinforcementsJlabel.add(addArmyToCountryJcomboBox);
		reinforcementsJlabel.add(reinforcementUnassignedUnit);
		reinforcementsJlabel.add(addArmy);
		gameActionJpanel.add(reinforcementsJlabel);

	}

	public void loadingFortificationLabel() {
		fortificationJlabel = new JLabel();
		fortificationJlabel.setBorder(
				BorderFactory.createTitledBorder(null, "Fortification Phase", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
		fortificationJlabel.setBounds(reinforcementsJlabel.getX(),
				reinforcementsJlabel.getY() + 10 + reinforcementsJlabel.getHeight(), reinforcementsJlabel.getWidth(),
				140);

		
        ArrayList<String> conquerdCountries = new ArrayList<String>();
		
		for (int i = 0; i < countryList.size(); i++) {
			ViewCountries tempCountry = countryList.get(i);
            if(activePlayerId == tempCountry.getPlayerID())
             { conquerdCountries.add(tempCountry.getCountryName());
             }
		}
			
		sourceCountry = new JComboBox(conquerdCountries.toArray());
		sourceCountry.setBorder(new TitledBorder("Source Country"));
		sourceCountry.setBounds(15, 25, 220, 50);
		
		String destinationCountries[] = {" "};
		destinationCountry = new JComboBox<>(destinationCountries);
		destinationCountry.setBorder(new TitledBorder("Destination Country"));
		destinationCountry.setBounds(sourceCountry.getX() + 20 + sourceCountry.getWidth() + 3, sourceCountry.getY(),
				sourceCountry.getWidth(), sourceCountry.getHeight());

		
	    ArrayList<Integer> NoOfArmies = new ArrayList<Integer>();		
		for (int i = 1 ; i <= Integer.parseInt(activePlayerUnassignedArmiesCount);i++) {
				 NoOfArmies.add(i);
	        }
		
		
		noOfArmyToMoveJcomboBox = new JComboBox(NoOfArmies.toArray());

		noOfArmyToMoveJcomboBox.setBounds(sourceCountry.getX(), sourceCountry.getHeight() + sourceCountry.getY() + 7,
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

	@Override
	public void update(Observable obj, Object arg1) {
		
	 Game game = ((Game)obj);
     Map map = game.getMap();
     
     phase = game.getGamePhase(); 
     mapPath = map.getMapPath() + map.getMapName() + ".bmp";
   
     activePlayerName = game.getCurrentPlayer().getName();
     activePlayerId = game.getCurrentPlayerId();
     activePlayerColor = game.getCurrentPlayer().getColor();
     activePlayerUnassignedArmiesCount = Integer.toString(game.getCurrentPlayer().getNoOfUnassignedArmies());  
     countryList.clear();
     for(Country country: map.getCountryList())
     {  ViewCountries viewCountry = new ViewCountries();
        viewCountry.setCountryId(country.getCountryId());
        viewCountry.setCountryColor(country.getCountryColor());
        viewCountry.setCountryName(country.getCountryName());
        viewCountry.setNoOfArmies(country.getnoOfArmies());
        viewCountry.setxCoordinate(country.getxCoordiate());
        viewCountry.setyCoordinate(country.getyCoordiate());
        viewCountry.setNeighboursString(country.getNeighboursString());
        viewCountry.setPlayerID(country.getPlayerId());
        JLabel label = (JLabel) mapLabels.get(String.valueOf(country.getCountryId()));
        if(label != null)
        { label.setText(String.valueOf(viewCountry.getNoOfArmies()));
        }
        countryList.add(viewCountry);
     }
     
     
/*     if (phase == PhaseEnum.Startup)
     {}
     else if (phase == PhaseEnum.Reinforcement)
     {}
     else if (phase == PhaseEnum.Reinforcement)
     {}*/
     
     if (gameJframe !=null)
       { //addArmyToCountryJcomboBox.removeAll();
 //   	 gameJframe.setVisible(false);       
  //       gameInitializer(); 
       }
	}
	
	public void addActionListenToMapLabels(MouseListener listener) {
		int n = mapJlabel.getComponentCount();
		for (int i = 0; i < n; i++) {
			JLabel jLabel = (JLabel) mapJlabel.getComponent(i);
			jLabel.addMouseListener(listener);
		}		
	}
	
	public void addActionListenToAddArmyButton(ActionListener listener) {
		addArmy.addActionListener(listener);
	}
		
	public void addActionListenToSourceCountryList(ActionListener listener) {
		sourceCountry.addActionListener (listener);
	}

	public void addActionListenToMoveArmyButton(ActionListener listener) {
		fortificationMoveButton.addActionListener(listener);
	}

	public static String getAddArmyToCountryJcomboBox() {
	    String selectedCountry = (String) addArmyToCountryJcomboBox.getSelectedItem();
		return selectedCountry;		
	}

/*
	public static void setAddArmyToCountryJcomboBox(JComboBox<String> addArmyToCountryJcomboBox) {
		GameView.addArmyToCountryJcomboBox = addArmyToCountryJcomboBox;
	}
*/
    
	public static String getSourceCountry() {
	    String selectedCountry = (String) sourceCountry.getSelectedItem();
		return selectedCountry;		
	}

	public static String getDestinationCountry() {
	    String selectedCountry = (String) destinationCountry.getSelectedItem();
		return selectedCountry;		
	}

	public static Integer getNoOfArmyToMoveJcomboBox() {
		Integer NoOfArmies = (Integer) noOfArmyToMoveJcomboBox.getSelectedItem();
		return NoOfArmies;		
	}
   
	
	
}
