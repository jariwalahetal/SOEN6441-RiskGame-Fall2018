package com.risk.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import com.risk.helper.IOHelper;
import com.risk.helper.InitialPlayerSetup;
import com.risk.model.*;
import com.risk.view.MapCreateView;

/**
 * @author Binay Kumar
 * @version 1.0.0
 * @since 27-September-2018 This class is used to handle operations related to
 *        MAP.
 */

public class MapController {

	Map map;
	public static final String ANSI_RED = "\u001B[31m";
	/**
	 * This function asks user either to createmap or edit map, the user can also start the game form here.
	 */
	public void startGame() {
		map = new Map();

		IOHelper.print("1. Create Map");
		IOHelper.print("2. Edit Map");
		IOHelper.print("3. Play Game");

		int input = IOHelper.getNextInteger();
		if (input == 1)
			createMap();
		else if (input == 2)
			editMap();
		else if (input == 3) {
			initializeMap();
			initializeGame();
			// TODO: Play game
		}
	}
	/**
	 * This function gives the user an editor to create the map and it saves the map to the disk.
	 */
	private void createMap() {
		MapCreateView v = new MapCreateView();
   	 	v.showCreateView();
	   	v.button2.addActionListener(new ActionListener() {
	         @Override
	         public void actionPerformed(ActionEvent e) {
	        	 map.writeMapToDisk(new StringBuffer(v.returnTextAreaText()), v.returnMapNameText());
	        	 v.killFrame();
	        	 MapController map = new MapController();
	        	 map.startGame();
	         }
	     });
//		IOHelper.print("\nEnter the name of the map ");
//		String mapName = IOHelper.getNextString();
//		map.setMapName(mapName);
//
//		IOHelper.print("\nEnter the number of continents you want to create\n");
//		int totalNumberOfContinents = IOHelper.getNextInteger();
//		for (int i = 0; i < totalNumberOfContinents; i++) {
//			IOHelper.print("\nEnter continent name for continent number " + (i + 1)
//					+ " (press enter and then input the control value)\n");
//			String continentName = IOHelper.getNextString();
//			int controlValue = IOHelper.getNextInteger();
//			Continent continent = new Continent(i, continentName, controlValue);
//
//			IOHelper.print("Enter the number of country you want to create in this continent\n");
//			int numberOfCountries = IOHelper.getNextInteger();
//			for (int j = 0; j < numberOfCountries; j++) {
//				IOHelper.print("Enter country name for country number " + (j + 1));
//				String countryName = IOHelper.getNextString();
//
//				IOHelper.print("Enter x coordinate and y coordinate)");
//				int x = IOHelper.getNextInteger();
//				int y = IOHelper.getNextInteger();
//
//				Country country = new Country(j, countryName);
//				country.setxCoordiate(x);
//				country.setyCoordiate(y);
//				country.setContId(i);
//				IOHelper.print("\nEnter the number of adjacent countries you want to enter\n");
//				int adjacentCountries = IOHelper.getNextInteger();
//				for (int k = 0; k < adjacentCountries; k++) {
//					IOHelper.print("\nEnter country name for adjacency country number " + (k + 1) + "\n");
//					String neighbourName = IOHelper.getNextString();
//					country.addNeighboursString(neighbourName);
//				}
//				continent.addCountry(country);
//			}
//			map.addContinent(continent);
//		}

		/*if (map.isMapValid())
			map.saveMap();
		else
			IOHelper.print("Map is not valid");
*/	}
	/**
	 * This function lets the user edit the map.
	 */
	private void editMap() {
		// TODO: For mandeep - select map to edit and create functionality
		System.out.println("Edit Map in MapController called");
		IOHelper.print("\n Choose the name of map you want to edit from the list given below:");
		ArrayList<String> mapList = getListOfMaps();
		for (String nameOfMap: mapList)
		{
			System.out.println(nameOfMap);
		}
		String newEditMap = IOHelper.getNextString();
		System.out.println(newEditMap);

		map.setMapName(newEditMap);
		Map tempMap = map;
		tempMap.readMap();
		IOHelper.print("^_____Edit_Map_Menu_____^");
		IOHelper.print("1. Delete Continent");
		IOHelper.print("2. Delete Country");

		int input = IOHelper.getNextInteger();
		if (input == 1)
		{
			IOHelper.print("Enter name of the Continent you wish to delete:");
			ArrayList<Continent> continentList = map.getContinentList();
			for (Continent nameOfContinent: continentList )
			{
				IOHelper.print(nameOfContinent.getContName());
			}
			String continentToDelete = IOHelper.getNextString();
			map.deleteContinent(continentToDelete);
			//map.saveMap();
			IOHelper.print("New Continent List");
			for (Continent nameOfContinent: continentList )
			{
				IOHelper.print(nameOfContinent.getContName());
			}
		}
		else if (input == 2)
		{	// In Progress
			IOHelper.print("Enter name of the Country you wish to delete:");
			ArrayList<Country> countryList = map.getCountryList();
			for (Country nameOfCountry: countryList )
			{
				IOHelper.print(nameOfCountry.getCountryName());
			}
			String deleteCountry = IOHelper.getNextString();
		}


/*
			//validate Map
			if (tempMap.isMapValid())
			{	// yes
				IOHelper.print("Valid Map!");
			}
			else
			{	// No
				IOHelper.print("Map is not valid!");
			}
*/


	}
	/**
	 * This function validates the map and initializes the map.
	 */
	private void initializeMap() {
		int i = 1;
		ArrayList<String> maps = getListOfMaps();
		IOHelper.print("\nPress number to load file.\n");
		for (String file : maps) {
			IOHelper.print("\n" + i + ")" + file);
			i++;
		}
		IOHelper.print("\n");
		int mapNumber = IOHelper.getNextInteger();
		String selectedMapName = maps.get(mapNumber - 1);
		map.setMapName(selectedMapName);
		map.readMap();
		System.out.print("is map valid:" + map.isMapValid());
	}
	/**
	 * This function creates the player objects
	 */
	private void initializeGame() {
		IOHelper.print("\nEnter the number of Players:");
		int playerCount = IOHelper.getNextInteger();
		Game game = new Game(map);
		for (int i = 1; i <= playerCount; i++) {
			IOHelper.print("\nEnter the name of Player " + i);
			String playerName = IOHelper.getNextString();
			Player player = new Player(i, playerName, InitialPlayerSetup.getPlayerColor(i));
			player.setNoOfArmies(InitialPlayerSetup.getInitialArmyCount(playerCount));
			game.addPlayer(player);
		}
		// game.initialArmyAssignment();
		game.assignCountriesToPlayer();

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
				fileNames.add(listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				// nada
			}
		}
		return fileNames;
	}

}
