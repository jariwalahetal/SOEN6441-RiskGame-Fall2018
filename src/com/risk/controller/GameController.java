package com.risk.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JLabel;
import com.risk.helper.IOHelper;
import com.risk.helper.InitialPlayerSetup;
import com.risk.helper.PhaseEnum;
import com.risk.model.*;
import com.risk.view.GameView;
import com.risk.view.MapCreateView;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

/**
 * @author Binay Kumar
 * @version 1.0.0
 * @since 27-September-2018 This class is used to handle operations related to
 *        MAP.
 */

public class GameController {

	Map map;
	Game game;
	GameView gameView;
	public static final String ANSI_RED = "\u001B[31m";
	/**
	 * This function asks user either to createmap or edit map, the user can also start the game form here.
	 */
	public void startGame() {
		map = new Map();
		IOHelper.print("\t\t+__________________________________________________________+");
		IOHelper.print("\t\t|=====_==============================================_=====|");
		IOHelper.print("\t\t|    (_)                                            (_)    |");
        IOHelper.print("\t\t|   (___)            WELCOME TO RISK GAME          (___)   |");
        IOHelper.print("\t\t|   _}_{_                                          _}_{_   |");
        IOHelper.print("\t\t|__[_____]________________________________________[_____]__|");
        IOHelper.print("\t\t+==========================================================+");

		IOHelper.print("\t\t\t\t\t\t\t+======_Game Menu_======+");
		IOHelper.print("\t\t\t\t\t\t\t1. Create Map");
		IOHelper.print("\t\t\t\t\t\t\t2. Edit Map");
		IOHelper.print("\t\t\t\t\t\t\t3. Play Game");
		IOHelper.print("\t\t\t\t\t\t\t4. Exit");
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
		else if(input == 4){
		    System.exit(0);
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
	        	 boolean isMapCreated = map.validateAndCreateMap(new StringBuffer(v.returnTextAreaText()), v.returnMapNameText());
	        	 if(isMapCreated)
	        	 {	        		 
	        		 IOHelper.print("Map Created successfully");
		        	 v.killFrame();
		        	 GameController map = new GameController();
		        	 map.startGame();
	        	 }
	        	 else 
	        	 {
	        		 IOHelper.print("Map is not valid.Please try again");
	        	 }
	         }
	     });
	}


	/**
	 * @author Mandeep Kaur
     * This method lets the user to edit the Map.
	 */
	private void editMap() {
		IOHelper.print("List of Maps :- ");
		ArrayList<String> mapList = getListOfMaps();
		int i = 1;
		for (String nameOfMap: mapList)
		{
			IOHelper.print( i + ")" + nameOfMap);
			i++;
		}
        IOHelper.print("\nEnter Map_Number that you want to edit from above list:");
		int mapNumber = IOHelper.getNextInteger();
		String selectedMapName = mapList.get(mapNumber - 1);
		map.setMapName(selectedMapName);
		IOHelper.print("'"+selectedMapName+"'");
        map.readMap();
        while (true){
            IOHelper.print("+------------------------------+");
            IOHelper.print("|________ Edit Map Menu________| ");
            IOHelper.print("|    1. Delete Continent       |");
            IOHelper.print("|    2. Delete Country         |");
            IOHelper.print("|    3. Add Continent          |");
            IOHelper.print("|    4. Add Country            |");
            IOHelper.print("|    5. Exit                   |");
            IOHelper.print("+------------------------------+");
            IOHelper.print(" Enter option:");
            int input = IOHelper.getNextInteger();
            switch (input){
                case 1: //Delete Continent
                    IOHelper.print("List of Continents:");
                    ArrayList<Continent> continentList = map.getContinentList();
                    for (Continent nameOfContinent: continentList )
                    {
                        IOHelper.print("->"+nameOfContinent.getContName());
                    }
                    IOHelper.print("Enter name of the Continent you want to delete:");
                    String continentToDelete = IOHelper.getNextString();
                    map.deleteContinent(continentToDelete);
                    try{
                        if (map.isMapValid()){
                            map.saveMap();
                            IOHelper.print("Continent '"+continentToDelete+"' is deleted successfuly!");
                        }
                        else{
                            IOHelper.print("Map is invalid!");
                        }
                    }catch (  Exception e){
                        IOHelper.print(" Empty Map !");
                    }
                    break;
                case 2: //Delete Country
                    IOHelper.print("List of Countries:");
                    ArrayList<Country> countryList = map.getCountryList();
                    for (Country nameOfCountry: countryList )
                    {
                        IOHelper.print("->"+nameOfCountry.getCountryName());
                    }
                    IOHelper.print("Enter name of the Country you want to delete from the list given below:");
                    String countryToDelete = IOHelper.getNextString();
                    map.deleteCountry(countryToDelete);
                    map.saveMap();
                    if (map.isMapValid()){
                        map.saveMap();
                        IOHelper.print("Country '"+countryToDelete+"' is deleted successfuly!");
                    }else {
                        IOHelper.print("Map is invalid!");
                    }
                    break;
                case 3: //Add Continent
                    map.addContinentToMap();
                    map.saveMap(); // to check continent is added or not
                    if(map.isMapValid()){
                        map.saveMap();
                        IOHelper.print("Continent added successfully!");
                    }else{
                        IOHelper.print("Map is invalid!");
                    }
                    break;
                case 4: //Add Country
                    IOHelper.print("Enter name of the continent where you want to add the country from the list given below: ");
                    ArrayList<Continent> continentsList = map.getContinentList();
                    int continentID = 0;
                    for (Continent continent: continentsList )
                    {
                        IOHelper.print("-> "+continent.getContName());
                        continentID = continent.getContId();
                    }
                    String continentName = IOHelper.getNextString();

                    map.addCountryToContinent(continentName,continentID);
                    map.saveMap();
                    if(map.isMapValid()){
                        map.saveMap();
                        IOHelper.print("Country added successfuly!");
                    }else {
                        IOHelper.print("Map is invalid!");
                    }
                    break;
                case 5: // Exit from EditMap
                    startGame();
                    break;
                default:
                    IOHelper.print("Option not Available! Enter Again");
                    break;
            }
        }
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
		if(map.isMapValid()){
			startGame();
		}
	}
	/**
	 * This function creates the player objects
	 */
	private void initializeGame() {
		game = new Game(map);
		gameView=new GameView();
		game.addObserver(gameView);
		
		IOHelper.print("\nEnter the number of Players:");
		int playerCount = IOHelper.getNextInteger();
		
		for (int i = 0; i < playerCount; i++) {
			IOHelper.print("\nEnter the name of Player " + (i+1));
			String playerName = IOHelper.getNextString();
			Player player = new Player(i, playerName);
			game.addPlayer(player);
		}
		game.startUpPhase();
		gameView.gameInitializer();
		activateListenersOnView();

	}
	private void activateListenersOnView(){
	    addArmyImageClickListener();
	    addSourceCountriesListener();
		addMoveArmyButtonListener();
	}
		
	/**
	 * to update view
	 */
	public void addArmyImageClickListener(){
		gameView.addActionListenToMapLabels(new MouseAdapter() {
       
            public void mouseClicked(MouseEvent e) {
            JLabel jLabel=	(JLabel) e.getSource();
            String string=jLabel.getToolTipText().substring(0,jLabel.getToolTipText().indexOf("--"));
            if (game.getGamePhase()==PhaseEnum.Startup || game.getGamePhase() == PhaseEnum.Reinforcement)
               game.addArmyToCountry(Integer.parseInt(string));
            }
        });
	}

	/**
	 * to update view
	 */
	public void addSourceCountriesListener(){
		gameView.addActionListenToSourceCountryList(new ActionListener() {
       
        public void actionPerformed(ActionEvent  e) {
           System.out.println("find neighbours of the selected country");        
        }
        });
	}

	/**
	 * to update view
	 */
	public void addMoveArmyButtonListener(){
		gameView.addActionListenToMoveArmyButton(new ActionListener() {
       
        public void actionPerformed(ActionEvent  e) {
            if (game.getGamePhase()==PhaseEnum.Fortification) 
            	game.fortificationPhase(gameView.getSourceCountry(),gameView.getDestinationCountry(),gameView.getNoOfArmyToMoveJcomboBox());
        
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
				fileNames.add(listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				// nada
			}
		}
		return fileNames;
	}

	
}
