package com.risk.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import com.risk.helper.Common;
import com.risk.helper.EnumColor;
import com.risk.helper.IOHelper;
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
class ViewCountries {
	private int countryId;
	private String countryName;
	private int xCoordinate;
	private int yCoordinate;
	private int noOfArmies;
	private EnumColor CountryColor;
	private int playerID;
	private ArrayList<String> neighboursString = new ArrayList<>();
	
	/**
	 * This method return id of the country
	 * 
	 * @return countryId int
	 */
	public int getCountryId() {
		return countryId;
	}

	/**
	 * This method set id of the country
	 * 
	 * @param countryId
	 *            int
	 */
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	/**
	 * This method return name of the country
	 * 
	 * @return countryName String
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * This method set name of the country
	 * 
	 * @param countryName
	 *            String
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * This method return xcordinate of the country
	 * 
	 * @return xCoordinate int
	 */
	public int getxCoordinate() {
		return xCoordinate;
	}

	/**
	 * This method set xcordinate of the country
	 * 
	 * @param xCoordinate
	 *            int
	 */
	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	/**
	 * This method return ycordinate of the country
	 * 
	 * @return xCoordinate int
	 */
	public int getyCoordinate() {
		return yCoordinate;
	}

	/**
	 * This method set ycordinate of the country
	 * 
	 * @param yCoordinate
	 *            String
	 */
	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	/**
	 * This method return the number of armies
	 * 
	 * @return noOfArmies int
	 */
	public int getNoOfArmies() {
		return noOfArmies;
	}

	/**
	 * This method set the number of armies
	 * 
	 * @param noOfArmies
	 *            int
	 */
	public void setNoOfArmies(int noOfArmies) {
		this.noOfArmies = noOfArmies;
	}

	/**
	 * This method return the color of the country
	 * 
	 * @return EnumColor color
	 */
	public EnumColor getCountryColor() {
		return CountryColor;
	}

	/**
	 * This method set the color of the country
	 * 
	 * @param countryColor
	 *            EnumColor
	 */
	public void setCountryColor(EnumColor countryColor) {
		CountryColor = countryColor;
	}

	/**
	 * This method return id of the player
	 * 
	 * @return playerID int
	 */
	public int getPlayerID() {
		return playerID;
	}

	/**
	 * This method set id of the player
	 * 
	 * @param playerID
	 *            int
	 */
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	/**
	 * This method return neighbours of the countries
	 * 
	 * @return neighboursString
	 */
	public ArrayList<String> getNeighboursString() {
		return neighboursString;
	}

	/**
	 * This method set neighbours of the countries
	 * 
	 * @param neighboursString
	 *            ArrayList
	 */
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
public class GameView implements Observer {
	private static String defaultSelection = "--Select--";
	
	private static JFrame gameJframe = null;
	private static JPanel gameActionJpanel;

	// Map Label
	private static JLabel mapJlabel;
	private static JScrollPane mapScrollPane = null;
	private static HashMap<String, Component> mapLabels = new HashMap<>();

	// Phase label
	private static JLabel gamePhaseJLabel;
	private static JLabel gamePhaseNameJLabel;

	// Phase View Actions Label
	private static JLabel gamePhaseViewActionsJLabel;

	// Initialization Label
	private static JLabel initializationJlabel;
	private static JLabel playersTurnJlabel;
	private static JLabel armyLeftJlabel;

	// Reinforcement Label
	private static JLabel reinforcementsJlabel;
	private static JLabel reinforcementUnassignedUnit;

	// Attack Label
	private static JLabel attackJlabel;
	private static JComboBox<String> attackerCountry;
	private static JComboBox<String> defenderCountry;
	private static JComboBox<String> attackerNoOfDice;
	private static JComboBox<String> defenderNoOfDice;
	private static JComboBox<String> attackMoveArmies;
	private static JButton moveArmiesButton  = new JButton("Move");
	private static JButton attackButton = new JButton("Attack");
	private static JButton allOutButton = new JButton("All Out");
	private static JButton endAttackButton = new JButton("End Attack");

	// Fortification Label
	private static JLabel fortificationJlabel;
	private static JComboBox<String> sourceCountry;
	private static JComboBox<String> destinationCountry;
	private static JComboBox<String> noOfArmyToMoveJcomboBox;
	private static JButton fortificationMoveButton = new JButton("Move Army");

	// Player World Domination Button
	private static JButton playerWorldDominationViewJButton;

	String activePlayerName = null;
	int activePlayerId;
	EnumColor activePlayerColor = null;
	String activePlayerUnassignedArmiesCount, reinforcementUnassignedArmiesCount;
	String mapPath;
	ArrayList<ViewCountries> countryList = new ArrayList<ViewCountries>();
	ArrayList<ViewCountries> attackingCountryList = new ArrayList<>();
	PhaseEnum phase;

	/**
	 * Method use to initialize the view of game
	 */
	public void gameInitializer() {

		loadGameActionView();
		loadingInitializationLabel();
		loadingReinforcementLabel();
		loadingAttackLabel();
		loadingFortificationLabel();
		loadingPhaseLabel();
		loadingPhaseActionLabel();
		loadPlayerWorldDominationView();
		gameJframe.setSize(1250, 700);
		gameJframe.setVisible(true);
		gameJframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Method use to load image of map on view
	 */
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
			IOHelper.printException(e);
		}

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
			newLabel.setToolTipText(tempCountry.getCountryName());
			mapLabels.put(String.valueOf(tempCountry.getCountryId()), newLabel);
			mapJlabel.add(newLabel);
		}

		mapScrollPane = new JScrollPane(mapJlabel);
		mapScrollPane.setBounds(10, 10, 700, 650);
		mapScrollPane.setBorder(new TitledBorder(""));
		gameActionJpanel.add(mapScrollPane);

		gameJframe.add(gameActionJpanel);

	}

	/**
	 * Method used to perform initializtion phase of the game
	 */
	public void loadingInitializationLabel() {
		initializationJlabel = new JLabel();

		initializationJlabel.setBorder(
				BorderFactory.createTitledBorder(null, "Initialization Phase", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
		initializationJlabel.setBounds(mapScrollPane.getX() + 700, mapScrollPane.getY(), 490, 60);

		// Recreate every components in Label
		playersTurnJlabel = new JLabel(activePlayerName);
		Font font = new Font("Courier", Font.BOLD, 24);
		playersTurnJlabel.setFont(font);
		playersTurnJlabel.setForeground(Common.getColor(activePlayerColor));
		playersTurnJlabel.setBorder(new TitledBorder("Active Player"));
		playersTurnJlabel.setBounds(12, 15, 220, 40);

		armyLeftJlabel = new JLabel("" + activePlayerUnassignedArmiesCount);
		armyLeftJlabel.setBorder(new TitledBorder("Armies Left"));
		armyLeftJlabel.setBounds(playersTurnJlabel.getX() + 240, playersTurnJlabel.getY(), playersTurnJlabel.getWidth(),
				playersTurnJlabel.getHeight());

		initializationJlabel.add(playersTurnJlabel);
		initializationJlabel.add(armyLeftJlabel);

		gameActionJpanel.add(initializationJlabel);

	}

	/**
	 * Method used to perform reinforcement phase of game
	 */
	public void loadingReinforcementLabel() {
		reinforcementsJlabel = new JLabel();
		reinforcementsJlabel.setBorder(
				BorderFactory.createTitledBorder(null, "Reinforcement Phase", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
		reinforcementsJlabel.setBounds(initializationJlabel.getX(),
				initializationJlabel.getY() + 10 + initializationJlabel.getHeight(), initializationJlabel.getWidth(),
				60);

		reinforcementUnassignedUnit = new JLabel(reinforcementUnassignedArmiesCount);
		reinforcementUnassignedUnit.setBorder(new TitledBorder("Reinforced Army Unit"));
		reinforcementUnassignedUnit.setBounds(12, 15, 460, 40);

		reinforcementsJlabel.add(reinforcementUnassignedUnit);
		gameActionJpanel.add(reinforcementsJlabel);

	}

	/**
	 * Method used to perform Attack phase of game
	 */
	public void loadingAttackLabel() {
		attackJlabel = new JLabel();
		attackJlabel
				.setBorder(BorderFactory.createTitledBorder(null, "Attack Phase", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
		attackJlabel.setBounds(reinforcementsJlabel.getX(),
				reinforcementsJlabel.getY() + 10 + reinforcementsJlabel.getHeight(), reinforcementsJlabel.getWidth(),
				220);

		attackerCountry = new JComboBox();
		attackerCountry.setBorder(new TitledBorder("Attack From"));
		attackerCountry.setBounds(15, 15, 220, 50);

		defenderCountry = new JComboBox();
		defenderCountry.setBorder(new TitledBorder("Attack To"));
		defenderCountry.setBounds(attackerCountry.getX() + 20 + attackerCountry.getWidth() + 3, attackerCountry.getY(),
				attackerCountry.getWidth(), attackerCountry.getHeight());

		attackerNoOfDice = new JComboBox<>();
		attackerNoOfDice.setBorder(new TitledBorder("Attacker's No Of Dice"));
		attackerNoOfDice.setBounds(attackerCountry.getX(), attackerCountry.getY() + 10 + attackerCountry.getHeight(),
				attackerCountry.getWidth(), attackerCountry.getHeight());

		defenderNoOfDice = new JComboBox<>();
		defenderNoOfDice.setBorder(new TitledBorder("Defender's No Of Dice"));
		defenderNoOfDice.setBounds(attackerNoOfDice.getX() + 20 + attackerNoOfDice.getWidth() + 3,
				attackerNoOfDice.getY(), attackerNoOfDice.getWidth(), attackerNoOfDice.getHeight());

		attackMoveArmies = new JComboBox<>();
		attackMoveArmies.setBorder(new TitledBorder("Move armies"));
		attackMoveArmies.setBounds(attackerNoOfDice.getX(),
				attackerNoOfDice.getY() + attackerNoOfDice.getHeight(), 
				attackerNoOfDice.getWidth(), attackerNoOfDice.getHeight());
		
		moveArmiesButton.setBounds(attackerNoOfDice.getX() + attackMoveArmies.getWidth() + 15,
				attackerNoOfDice.getY() + attackerNoOfDice.getHeight() + 15, 
				100, 30);
		
		attackButton.setBounds(attackMoveArmies.getX(), attackMoveArmies.getY() + 10 + attackMoveArmies.getHeight(),
				100, 30);

		allOutButton.setBounds(attackMoveArmies.getX() + attackButton.getWidth() + 10, attackMoveArmies.getY() + 10 + attackMoveArmies.getHeight(),
				100, 30);

		endAttackButton.setBounds(allOutButton.getX() + allOutButton.getWidth() + 10, attackMoveArmies.getY() + 10 + attackMoveArmies.getHeight(),
				100, 30);
		 
		attackJlabel.add(attackerCountry);
		attackJlabel.add(defenderCountry);
		attackJlabel.add(attackerNoOfDice);
		attackJlabel.add(defenderNoOfDice);
		attackJlabel.add(attackMoveArmies);
		attackJlabel.add(moveArmiesButton);
		attackJlabel.add(attackButton);
		attackJlabel.add(allOutButton);
		attackJlabel.add(endAttackButton);

		gameActionJpanel.add(attackJlabel);

	}

	/**
	 * Method used to perform fortification phase
	 */
	public void loadingFortificationLabel() {
		fortificationJlabel = new JLabel();
		fortificationJlabel.setBorder(
				BorderFactory.createTitledBorder(null, "Fortification Phase", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
		fortificationJlabel.setBounds(attackJlabel.getX(), attackJlabel.getY() + 10 + attackJlabel.getHeight(),
				attackJlabel.getWidth(), 130);

		// sourceCountry = new JComboBox(conquerdCountries.toArray());
		sourceCountry = new JComboBox();
		sourceCountry.setBorder(new TitledBorder("Source Country"));
		sourceCountry.setBounds(15, 15, 220, 50);

		String destinationCountries[] = { " " };
		destinationCountry = new JComboBox<>(destinationCountries);
		destinationCountry.setBorder(new TitledBorder("Destination Country"));
		destinationCountry.setBounds(sourceCountry.getX() + 20 + sourceCountry.getWidth() + 3, sourceCountry.getY(),
				sourceCountry.getWidth(), sourceCountry.getHeight());

		ArrayList<Integer> NoOfArmies = new ArrayList<Integer>();
		for (int i = 1; i <= Integer.parseInt(activePlayerUnassignedArmiesCount); i++) {
			NoOfArmies.add(i);
		}

		noOfArmyToMoveJcomboBox = new JComboBox(NoOfArmies.toArray());

		noOfArmyToMoveJcomboBox.setBounds(sourceCountry.getX(), sourceCountry.getHeight() + sourceCountry.getY() + 7,
				sourceCountry.getWidth(), sourceCountry.getHeight());
		noOfArmyToMoveJcomboBox.setBorder(new TitledBorder("Total number of army to move"));

		fortificationMoveButton.setBounds(destinationCountry.getX(),
				destinationCountry.getHeight() + destinationCountry.getY() + 17, destinationCountry.getWidth(), 30);

		// Add all components in Label
		fortificationJlabel.add(sourceCountry);
		fortificationJlabel.add(destinationCountry);
		fortificationJlabel.add(noOfArmyToMoveJcomboBox);
		fortificationJlabel.add(fortificationMoveButton);
		// Adding Label to Panel
		gameActionJpanel.add(fortificationJlabel);
	}

	/**
	 * Method used to load the name of the phase
	 */
	public void loadingPhaseLabel() {
		gamePhaseJLabel = new JLabel();
		gamePhaseJLabel.setBorder(
				BorderFactory.createTitledBorder(null, "Phase Information", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
		gamePhaseJLabel.setBounds(reinforcementsJlabel.getX(),
				fortificationJlabel.getY() + 10 + fortificationJlabel.getHeight(), fortificationJlabel.getWidth(), 50);

		gamePhaseNameJLabel = new JLabel("Initialization");
		Font font = new Font("Courier", Font.BOLD, 24);
		gamePhaseNameJLabel.setFont(font);
		gamePhaseNameJLabel.setBounds(15, 15, 220, 40);

		gamePhaseJLabel.add(gamePhaseNameJLabel);

		gameActionJpanel.add(gamePhaseJLabel);
	}

	/**
	 * Method to display the actions performed during each phase
	 */
	public void loadingPhaseActionLabel() {
		gamePhaseViewActionsJLabel = new JLabel();
		gamePhaseViewActionsJLabel.setBorder(
				BorderFactory.createTitledBorder(null, "Phase Actions Performed", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
		gamePhaseViewActionsJLabel.setBounds(reinforcementsJlabel.getX(),
				gamePhaseJLabel.getY() + 10 + gamePhaseJLabel.getHeight(), gamePhaseJLabel.getWidth(), 70);
		gameActionJpanel.add(gamePhaseViewActionsJLabel);
		
		
	}

	public void loadPlayerWorldDominationView() {
		playerWorldDominationViewJButton = new JButton("Player World Domination View");
		playerWorldDominationViewJButton.setBounds(gamePhaseViewActionsJLabel.getX(),
				gamePhaseViewActionsJLabel.getY() + 10 + gamePhaseViewActionsJLabel.getHeight(),
				destinationCountry.getWidth(), destinationCountry.getHeight());
		playerWorldDominationViewJButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String[] columns_header = { "S.No.", "Player A", "Player B", "Player C", "Player D", "Player E" };
				String[][] rows = { { "percentage", "10", "2", "5", "6", "3" },
						{ "continents controlled", "1", "0", "0", "0", "0" },
						{ "total army", "50", "10", "12", "0", "18" } };
				JFrame playerWorldDominationViewJFrame = new JFrame("Player World Domination View");
				JPanel playerWorldDominationViewJPanel = new JPanel(new BorderLayout());
				JLabel playerRecordsJLabel = new JLabel();
				JTable playerRecordsJTable = new JTable(rows, columns_header);

				// JScrollPane playerJScrollPane = new JScrollPane(playerRecordsJTable);
				// playerJScrollPane.setSize(550,350);
				// playerJScrollPane.setVisible(true);

				playerRecordsJTable.setBounds(20, playerRecordsJLabel.getY() + 20 + playerRecordsJLabel.getHeight(),
						550, 350);

				playerRecordsJLabel.setBorder(BorderFactory.createTitledBorder(null, "Player Records Table",
						TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
						new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
				playerRecordsJLabel.setBounds(10, 10, 600, 400);
				playerRecordsJLabel.add(playerRecordsJTable);

				playerWorldDominationViewJFrame.setSize(600, 400);
				playerWorldDominationViewJFrame.setVisible(true);
				playerWorldDominationViewJFrame.add(playerWorldDominationViewJPanel);
				playerWorldDominationViewJPanel.add(playerRecordsJLabel);

			}
		});
		gameActionJpanel.add(playerWorldDominationViewJButton);
	}

	/**
	 * Update method called by the observable object to perform all the actions
	 */
	@Override
	public void update(Observable obj, Object arg1) {

		Game game = ((Game) obj);
		Map map = game.getMap();

		phase = game.getGamePhase();
		mapPath = map.getMapPath() + map.getMapName() + ".bmp";

		activePlayerName = game.getCurrentPlayer().getName();
		activePlayerId = game.getCurrentPlayerId();
		activePlayerColor = game.getCurrentPlayer().getColor();
		activePlayerUnassignedArmiesCount = Integer.toString(game.getCurrentPlayer().getNoOfUnassignedArmies());
		reinforcementUnassignedArmiesCount = Integer.toString(game.getCurrentPlayer().getNoOfReinforcedArmies());
		countryList.clear();
		attackingCountryList.clear();
		for (Country country : map.getCountryList()) {
			ViewCountries viewCountry = new ViewCountries();
			viewCountry.setCountryId(country.getCountryId());
			viewCountry.setCountryColor(country.getCountryColor());
			viewCountry.setCountryName(country.getCountryName());
			viewCountry.setNoOfArmies(country.getnoOfArmies());
			viewCountry.setxCoordinate(country.getxCoordiate());
			viewCountry.setyCoordinate(country.getyCoordiate());
			viewCountry.setNeighboursString(country.getNeighboursString());
			viewCountry.setPlayerID(country.getPlayerId());
			JLabel label = (JLabel) mapLabels.get(String.valueOf(country.getCountryId()));
			if (label != null) {
				label.setText(String.valueOf(viewCountry.getNoOfArmies()));
				label.setForeground(Common.getColor(viewCountry.getCountryColor()));
			}
			countryList.add(viewCountry);
		}
		if (playersTurnJlabel != null) {
			playersTurnJlabel.setText(activePlayerName);
			playersTurnJlabel.setForeground(Common.getColor(activePlayerColor));
			armyLeftJlabel.setText(activePlayerUnassignedArmiesCount);

			reinforcementUnassignedUnit.setText(reinforcementUnassignedArmiesCount);

			if (game.getGamePhase() == PhaseEnum.Startup) {
				gamePhaseNameJLabel.setText("Initialization");
			} else if (game.getGamePhase() == PhaseEnum.Reinforcement) {
				if(game.getCurrentPlayer().getCards().size()>= 3) {
				CardExchangeView cardExchangeView=new CardExchangeView();
				cardExchangeView.exchangeInitializerView(game);
				}
				gamePhaseNameJLabel.setText("Reinforcement");
			} else if (game.getGamePhase() == PhaseEnum.Attack) {
				gamePhaseNameJLabel.setText("Attack Phase");
				
				for (Country country : game.getCoutriesForAttack()) {
					ViewCountries viewCountry = new ViewCountries();
					viewCountry.setCountryId(country.getCountryId());
					viewCountry.setCountryColor(country.getCountryColor());
					viewCountry.setCountryName(country.getCountryName());
					attackingCountryList.add(viewCountry);
				}
				
				setAttackerCountry();
				setMoveArmies(game.GetAllowableArmiesMoveFromAttackerToDefender());
				// game.attackPhase();
			} else if (game.getGamePhase() == PhaseEnum.Fortification) {
				gamePhaseNameJLabel.setText("Fortification");
				setSourceCountryComboBox();
			}
			AddMessages();
		}
	}

	/**
	 * Method used to populate value in the destination phase combobox
	 * 
	 * @param destinationCountries
	 *            ArrayList
	 */
	public void populateDestinationCountryComboBox(ArrayList<String> destinationCountries) {
		destinationCountry.removeAllItems();
		for (String countryName : destinationCountries)
			destinationCountry.addItem(countryName);
	}

	/**
	 * Method used to populate value in the attackerCountry combobox
	 * 
	 * @param destinationCountries
	 *            ArrayList
	 */
	public void populateAttackerCountryComboBox(ArrayList<String> attackerCountries) {
		attackerCountry.removeAllItems();
		for (String countryName : attackerCountries)
			attackerCountry.addItem(countryName);
	}

	/**
	 * Method used to populate value in the defenderCountry combobox
	 * 
	 * @param destinationCountries
	 *            ArrayList
	 */
	public void populateDefenderCountryComboBox(ArrayList<String> defenderCountries) {
		defenderCountry.removeAllItems();
		defenderCountry.addItem(defaultSelection);
		for (String countryName : defenderCountries)
			defenderCountry.addItem(countryName);
	}

	/**
	 * Method used perform the on mouse click and add army in the country
	 * 
	 * @param listener
	 *            MouseListener
	 */
	public void addActionListenToMapLabels(MouseListener listener) {
		int n = mapJlabel.getComponentCount();
		for (int i = 0; i < n; i++) {
			JLabel jLabel = (JLabel) mapJlabel.getComponent(i);
			jLabel.addMouseListener(listener);
		}
	}

	/**
	 * Method used to add Action Listener to Attacker Country
	 * 
	 * @param listener
	 *            ActionListener
	 */
	public void addActionListenToAttackerCountryList(ActionListener listener) {
		attackerCountry.addActionListener(listener);
	}

	/**
	 * Method used to add Action Listener to Defending Country
	 * 
	 * @param listener
	 *            ActionListener
	 */
	public void addActionListenToDefendingCountryList(ActionListener listener) {
		defenderCountry.addActionListener(listener);
	}
	
	/**
	 * Method used to add Action Listener to Source Country
	 * 
	 * @param listener
	 *            ActionListener
	 */
	public void addActionListenToSourceCountryList(ActionListener listener) {
		sourceCountry.addActionListener(listener);
	}

	/**
	 * Method for performing action listener on move army button
	 * 
	 * @param listener
	 *            ActionListener
	 */
	public void addActionListenToMoveArmyButton(ActionListener listener) {
		fortificationMoveButton.addActionListener(listener);
	}
	
	/**
	 * Static method to Set the Attacker country
	 * 
	 * @return selectedCountry
	 */
	public void setAttackerCountry() {
		attackerCountry.removeAllItems();
		attackerCountry.addItem(defaultSelection);
		for (int i = 0; i < attackingCountryList.size(); i++) {
			ViewCountries tempCountry = attackingCountryList.get(i);
			attackerCountry.addItem(tempCountry.getCountryName());
		}
	}
	
	public void setMoveArmies(int count) {
		attackMoveArmies.removeAllItems();
		for(int i=1; i<=count; i++) {
			attackMoveArmies.addItem(String.valueOf(i));
		}
	}


	/**
	 * Static method to get the Attacker country
	 * 
	 * @return selectedCountry
	 */
	public static String getAttackerCountry() {
		if(attackerCountry.getSelectedItem() == null || attackerCountry.getSelectedItem().equals(defaultSelection))
			return null;
		else 
			return (String) attackerCountry.getSelectedItem();
	}

	/**
	 * Static method to get the Defender country
	 * 
	 * @return selectedCountry
	 */
	public static String getDefenderCountry() {
		return (String) defenderCountry.getSelectedItem();
	}

	/**
	 * Static method to get the attackerNoOfDice
	 * 
	 * @return
	 */
	public static String getAttackerNoOfDice() {
		return (String) attackerNoOfDice.getSelectedItem();
	}

	/**
	 * Static method to get the attackerNoOfDice
	 * 
	 * @return
	 */
	public static String getDefenderNoOfDice() {
		return (String) defenderNoOfDice.getSelectedItem();
	}

	/**
	 * Method for performing action listener on attack Button
	 * 
	 * @param listener
	 *            ActionListener
	 */
	public void addActionListenToAttackButton(ActionListener listener) {
		this.attackButton.addActionListener(listener);
	}

	/**
	 * Method for performing action listener on allOutButton
	 * 
	 * @param listener
	 *            ActionListener
	 */
	public void addActionListenToAllOutButton(ActionListener listener) {
		this.allOutButton.addActionListener(listener);
	}

	/**
	 * Method for performing action listener on endAttack button
	 * 
	 * @param listener
	 *            ActionListener
	 */
	public void addActionListenToEndAttackButton(ActionListener listener) {
		this.endAttackButton.addActionListener(listener);
	}
	

	/*
	 * public static void setAddArmyToCountryJcomboBox(JComboBox<String>
	 * addArmyToCountryJcomboBox) { GameView.addArmyToCountryJcomboBox =
	 * addArmyToCountryJcomboBox; }
	 */

	public void setSourceCountryComboBox() {
		sourceCountry.removeAllItems();
		for (int i = 0; i < countryList.size(); i++) {
			ViewCountries tempCountry = countryList.get(i);
			if (activePlayerId == tempCountry.getPlayerID()) {
				sourceCountry.addItem(tempCountry.getCountryName());
			}
		}

	}

	/**
	 * Static method to get the source country
	 * 
	 * @return selectedCountry
	 */
	public static String getSourceCountry() {

		return (String) sourceCountry.getSelectedItem();

	}

	/**
	 * Static method to get all destination countries
	 * 
	 * @return selectedCountry
	 */
	public static String getDestinationCountry() {

		Object selectedItem = destinationCountry.getSelectedItem();
		if (selectedItem != null) {
			String selectedCountry = (String) selectedItem;
			return selectedCountry;
		} else {
			return "";
		}
	}

	/**
	 * Static method to get number of army the player wants to move
	 * 
	 * @return NoOfArmies
	 */
	public static Integer getNoOfArmyToMoveJcomboBox() {
		Object selectedItem = noOfArmyToMoveJcomboBox.getSelectedItem();
		if (selectedItem != null) {
			Integer NoOfArmies = (Integer.parseInt((String) selectedItem));
			return NoOfArmies;
		}
		return 0;
	}

	/**
	 * Static method to show number of army the player wants to move in combobox
	 * 
	 * @param NoOfArmies
	 *            int
	 * 
	 */
	public void populateNoOfArmyToMoveJcomboBox(int NoOfArmies) {
		noOfArmyToMoveJcomboBox.removeAllItems();
		for (Integer i = 0; i < NoOfArmies; i++)
			noOfArmyToMoveJcomboBox.addItem(i.toString());
	}
	
	/**
	 * Method used to populate value in the attacking dice
	 * 
	 * @param allowableDices
	 *            int
	 */
	public void populateAttackingDiceComboBox(int allowableDices) {
		attackerNoOfDice.removeAllItems();
		for(int i=1; i<= allowableDices;i++) {
			attackerNoOfDice.addItem(Integer.toString(i));
		}
	}
	
	/**
	 * Method used to populate value in the defending dice
	 * 
	 * @param allowableDices
	 *            int
	 */
	public void populateDefendingDiceComboBox(int allowableDices) {
		defenderNoOfDice.removeAllItems();
		for(int i=1; i<= allowableDices;i++) {
			defenderNoOfDice.addItem(Integer.toString(i));
		}
	}
	
	/**
	 * Method for performing action listener on move armies attack Button
	 * 
	 * @param listener
	 *            ActionListener
	 */
	public void addActionListenToAttackMoveArmiesButton(ActionListener listener) {
		this.moveArmiesButton.addActionListener(listener);
	}
	
	/**
	 * Static method to get the move armies country
	 * 
	 * @return selectedCountry
	 */
	public static String getAttackMoveArmies() {
		if(attackMoveArmies.getSelectedItem() == null)
			return null;
		else 
			return (String) attackMoveArmies.getSelectedItem();
	}
	
	public static void AddMessages()
	{
		gamePhaseViewActionsJLabel.removeAll();
		int strartY = 5;
		//TOOO: Add JScrollPanel
		for(String message: Common.PhaseActions) {
			JLabel textLabel = new JLabel(message)	;
			Font font = new Font("Courier", Font.ITALIC, 10);
			textLabel.setFont(font);
			textLabel.setBounds(15, strartY, 220, 40);
			strartY = strartY + 15;
			gamePhaseViewActionsJLabel.add(textLabel);
		}
	}

}
