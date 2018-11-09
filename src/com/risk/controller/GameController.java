package com.risk.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import com.risk.helper.IOHelper;
import com.risk.helper.PhaseEnum;
import com.risk.model.*;
import com.risk.view.CardExchangeView;
import com.risk.view.GameView;
import com.risk.view.MapCreateView;

/**
 * This class is used to handle operations related to MAP.
 * 
 * @author Binay Kumar
 * @version 1.0.0
 * @since 27-September-2018
 *
 */
public class GameController {

	Game game;
	GameView gameView;
	CardExchangeView cardExchangeView;
	public static final String ANSI_RED = "\u001B[31m";

	/**
	 * This function asks user either to createmap or edit map, the user can also
	 * start the game form here.
	 */
	public void startGame() {
		IOHelper.print("+__________________________________________________________+");
		IOHelper.print("|=====_==============================================_=====|");
		IOHelper.print("|    (_)                                            (_)    |");
		IOHelper.print("|   (___)            WELCOME TO RISK GAME          (___)   |");
		IOHelper.print("|   _}_{_                                          _}_{_   |");
		IOHelper.print("|__[_____]________________________________________[_____]__|");
		IOHelper.print("+==========================================================+");
		IOHelper.print("+======_Game Menu_======+");
		IOHelper.print("1. Create Map");
		IOHelper.print("2. Edit Map");
		IOHelper.print("3. Play Game");
		IOHelper.print("4. Exit");
		try {
			int input = IOHelper.getNextInteger();

			switch (input) {
			case 1:
				createMap();
				break;
			case 2:
				editMap();
				break;
			case 3:
				Map map = initializeMap();
				initializeGame(map);
				break;
			// TODO: Play Game
			case 4:
				System.exit(0);
			default:
				IOHelper.print("\nInvalid choice. Select Again!\n");
			}
		} catch (Exception e) {
			//IOHelper.printException(e);
			e.printStackTrace();
			IOHelper.print("Please try again with the right option");
		}

	}

	/**
	 * This function gives the user an editor to create the map and it saves the map
	 * to the disk.
	 */
	private void createMap() {
		MapCreateView mapView = new MapCreateView();
		mapView.showCreateView();
		mapView.saveMapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StringBuffer mapContent = new StringBuffer(mapView.returnTextAreaText());
				String mapname = mapView.returnMapNameText();
				Map map = new Map(mapname);
				boolean isMapCreated = map.validateAndCreateMap(mapContent, mapname);
				if (isMapCreated) {
					IOHelper.print("Map Created successfully!");
				} else {
					IOHelper.print("Map is not valid.Please try again");
				}
				mapView.killFrame();
				startGame();
			}
		});
	}

	/**
	 * Method for edit map functionality for all the cases
	 */
	private void editMap() {
		IOHelper.print("List of Maps :- ");
		ArrayList<String> mapList = getListOfMaps();
		int i = 1;
		for (String nameOfMap : mapList) {
			IOHelper.print(i + ")" + nameOfMap);
			i++;
		}
		IOHelper.print("\nEnter Map_Number that you want to edit from above list:");
		int mapNumber = IOHelper.getNextInteger();
		String selectedMapName = mapList.get(mapNumber - 1);
		Map map = new Map(selectedMapName);
		IOHelper.print("'" + selectedMapName + "'");
		map.readMap();
		if (!map.isMapValid()) {
			IOHelper.print("Map is Invalid !");
		}
		while (true) {
			IOHelper.print("+------------------------------+");
			IOHelper.print("|________ Edit Map Menu________| ");
			IOHelper.print("|    1. Delete Continent       |");
			IOHelper.print("|    2. Delete Country         |");
			IOHelper.print("|    3. Add Continent          |");
			IOHelper.print("|    4. Add Country            |");
			IOHelper.print("|    5. Save Map               |");
			IOHelper.print("|    6. Exit                   |");
			IOHelper.print("+------------------------------+");
			IOHelper.print(" Enter option:");
			int input = IOHelper.getNextInteger();
			switch (input) {
			case 1:
				IOHelper.print("List of Continents:");
				ArrayList<Continent> continentList = map.getContinentList();
				for (Continent nameOfContinent : continentList) {
					IOHelper.print("->" + nameOfContinent.getContName());
				}
				IOHelper.print("Enter name of the Continent you want to delete:");
				String continentToDelete = IOHelper.getNextString();
				map.deleteContinent(continentToDelete);
				IOHelper.print("Continent '" + continentToDelete + "' is deleted successfuly!");
				break;
			case 2:
				IOHelper.print("List of Countries:");
				ArrayList<Country> countryList = map.getCountryList();
				for (Country nameOfCountry : countryList) {
					IOHelper.print("->" + nameOfCountry.getCountryName());
				}
				IOHelper.print("Enter name of the Country you want to delete from the list given below:");
				String countryToDelete = IOHelper.getNextString();
				map.deleteCountry(countryToDelete);
				IOHelper.print("Country '" + countryToDelete + "' is deleted successfuly!");
				break;
			case 3:
				map.addContinentToMap();
				IOHelper.print("Continent added successfully!");
				break;
			case 4:
				IOHelper.print("List of Continents:-");
				ArrayList<Continent> continentsList = map.getContinentList();
				int continentID = 0;
				for (Continent continent : continentsList) {
					IOHelper.print("-> " + continent.getContName());
					continentID = continent.getContId();
				}
				IOHelper.print("Enter name of the continent where you want to add new country(from above list): ");
				String continentName = IOHelper.getNextString();
				map.addCountryToContinent(continentName, continentID);
				IOHelper.print("Country added successfuly!");
				break;
			case 5:
				if (map.isMapValid()) {
					map.saveMap();
					IOHelper.print("Map saved!");
				} else {
					IOHelper.print("Map saved is invalid!");
				}
				break;
			case 6:
				startGame();
				break;
			default:
				IOHelper.print("Option not Available. Select Again!");
				break;
			}
		}
	}

	/**
	 * This function validates the map and initializes the map.
	 */
	private Map initializeMap() {
		int i = 1;
		IOHelper.print("List of Maps:-");
		ArrayList<String> maps = getListOfMaps();
		for (String file : maps) {
			IOHelper.print(i + ")" + file);
			i++;
		}
		IOHelper.print("\nEnter Map number to load Map file:\n");
		int mapNumber = IOHelper.getNextInteger();
		String selectedMapName = maps.get(mapNumber - 1);
		Map map = new Map(selectedMapName);
		map.readMap();

		if (!map.isMapValid()) {
			IOHelper.print("\nInvalid Map. Select Again!");
			map = initializeMap();
		}
		return map;
	}

	/**
	 * This function creates the player objects for initializing Game
	 */
	private void initializeGame(Map map) {		
		game = new Game(map);
		cardExchangeView=new CardExchangeView();
		gameView = new GameView();
		
		game.addObserver(gameView);
		
		IOHelper.print("\nEnter the number of Players between 3 to 5");
		
		int playerCount = IOHelper.getNextInteger();
        if(3<=playerCount&&playerCount<=5) {
		for (int i = 0; i < playerCount; i++) {
			IOHelper.print("\nEnter the name of Player " + (i + 1));
			String playerName = IOHelper.getNextString();
			Player player = new Player(i, playerName);
			game.addPlayer(player);
		}
		game.startUpPhase();
		gameView.gameInitializer();
		activateListenersOnView();
		game.addObserver(cardExchangeView);

	}
        else {
        	IOHelper.print("Players count cannot be less than 3 and more than 5");	
        	startGame();
        	
        }
	}
        

	private void activateListenersOnView() {
		addArmyImageClickListener();
		addAttackButtonListener();
		addAllOutButtonListener();
		addEndAttackButtonListener();
		addSourceCountriesListener();
		addMoveArmyButtonListener();
		addAttackerCountryListener();
		addDefenderCountryListener();
		addAttackArmyMoveButtonListner();
	}

	/**
	 * to update view
	 */
	public void addArmyImageClickListener() {
		gameView.addActionListenToMapLabels(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				JLabel jLabel = (JLabel) e.getSource();
				String string = jLabel.getToolTipText();
				if (game.getGamePhase() == PhaseEnum.Startup || game.getGamePhase() == PhaseEnum.Reinforcement)
					game.addArmyToCountry(string);
			}
		});
	}

	/**
	 * to update view
	 */
	public void addAttackerCountryListener() {
		gameView.addActionListenToAttackerCountryList(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String countryName = gameView.getAttackerCountry();
				if (countryName != null) {
					ArrayList<String> neighborCountries = game.getCurrentPlayer()
							.getUnAssignedNeighbouringCountries(countryName);
					gameView.setDefenderCountryComboBox(neighborCountries);
					int diceCount = game.getMaximumAllowableDices(countryName, "Attacker");
					gameView.setAttackingDiceComboBox(diceCount);
				}
			}
		});
	}

	/**
	 * to update view
	 */
	public void addDefenderCountryListener() {
		gameView.addActionListenToDefendingCountryList(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String countryName = gameView.getDefenderCountry();
				if (countryName != null) {
					int diceCount = game.getMaximumAllowableDices(countryName, "Defender");
					gameView.setDefendingDiceComboBox(diceCount);
				}
			}
		});
	}

	/**
	 * to update view
	 */
	public void addSourceCountriesListener() {
		gameView.addActionListenToSourceCountryList(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String countryName = gameView.getSourceCountry();
				if (countryName != null) {
					ArrayList<String> neighborCountries = game.getCurrentPlayer()
							.getAssignedNeighbouringCountries(countryName);
					int armyCount = game.getArmiesAssignedToCountry(countryName);
					gameView.setDestinationCountryComboBox(neighborCountries);
					gameView.setNoOfArmyToMoveJcomboBox(armyCount);
				}
			}
		});
	}

	/**
	 * to update view
	 */
	public void addAttackButtonListener() {
		gameView.addActionListenToAttackButton(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String attackerCountry = gameView.getAttackerCountry();
				String defenderCountry = gameView.getDefenderCountry();
				if (attackerCountry != null && defenderCountry != null) {
					if (game.getGamePhase() == PhaseEnum.Attack) {
						Integer attackerDiceCount = Integer.parseInt(GameView.getAttackerNoOfDice());
						Integer defenderDiceCount = Integer.parseInt(GameView.getDefenderNoOfDice());
						game.attackPhase(attackerCountry, defenderCountry, attackerDiceCount, defenderDiceCount);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecting attacking and defending countries");
				}
			}
		});
	}

	/**
	 * to update view
	 */
	public void addAttackArmyMoveButtonListner() {
		gameView.addActionListenToAttackMoveArmiesButton(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GameView.getAttackMoveArmies() != null
						&& game.getCurrentPlayer().GetAllowableArmiesMoveFromAttackerToDefender() >= 0) {
					game.MoveArmyAfterAttack(Integer.parseInt(GameView.getAttackMoveArmies()));
				} else {
					JOptionPane.showMessageDialog(null, "Cannot perform action");
				}
			}
		});
	}

	/**
	 * to update view
	 */
	public void addAllOutButtonListener() {
		gameView.addActionListenToAllOutButton(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (game.getGamePhase() == PhaseEnum.Attack) {
					String attackerCountry = gameView.getAttackerCountry();
					String defenderCountry = gameView.getDefenderCountry();
					game.attackAllOutPhase(attackerCountry, defenderCountry);
				}
			}
		});
	}

	/**
	 * to update view
	 */
	public void addEndAttackButtonListener() {
		gameView.addActionListenToEndAttackButton(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (game.getGamePhase() == PhaseEnum.Attack) {
					game.updatePhase();
				}
			}
		});
	}

	/**
	 * to update view
	 */
	public void addMoveArmyButtonListener() {
		gameView.addActionListenToMoveArmyButton(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (game.getGamePhase() == PhaseEnum.Fortification)
					game.fortificationPhase(gameView.getSourceCountry(), gameView.getDestinationCountry(),
							gameView.getNoOfArmyToMoveJcomboBox());
			}
		});
	}

	/**
	 * This function returns the list of all the maps in the assets/map directory.
	 * 
	 * @return List of all the map files
	 */
	private ArrayList<String> getListOfMaps() {
		ArrayList<String> fileNames = new ArrayList<String>();
		File folder = new File("assets/maps");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				if (listOfFiles[i].getName().toLowerCase().contains(".map"))
					fileNames.add(listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
			}
		}
		return fileNames;
	}
}
