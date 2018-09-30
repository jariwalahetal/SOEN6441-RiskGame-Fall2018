package com.risk.controller;

import java.io.File;
import java.util.ArrayList;

import com.risk.helper.IOHelper;
import com.risk.model.*;

/**
 * @author Binay Kumar
 * @version 1.0.0
 * @since 27-September-2018
 * This class is used to handle operations related to MAP.
 */

public class MapController {

	Map map;

	public void startGame()
	{
		map = new Map();
		
		IOHelper.print("1. Create Map");
		IOHelper.print("2. Edit Map");
		IOHelper.print("3. Play Game");

		int input = IOHelper.getNextInteger();
		if(input == 1)
			createMap();
		else if(input == 2)
			editMap();
		else if(input == 3)
		{
			initializeMap();
			//TODO: initialize game
		}
	}
	
	public void createMap() {

		IOHelper.print("\nEnter the name of the map ");
		String mapName = IOHelper.getNextString();	
		map.setMapName(mapName);
		
		IOHelper.print("\nEnter the number of continents you want to create\n");
		int totalNumberOfContinents = IOHelper.getNextInteger();
		for(int i = 0; i < totalNumberOfContinents ; i++) 
		{
			IOHelper.print("\nEnter continent name for continent number"+(i+1)+" (press enter and then input the control value)\n");
			String continentName = IOHelper.getNextString();
			int controlValue = IOHelper.getNextInteger();
			Continent continent = new Continent(i, continentName , controlValue);

			IOHelper.print("Enter the number of country you want to create in this continent\n");
			int numberOfCountries = IOHelper.getNextInteger();
			for(int j=0;j<numberOfCountries;j++) 
			{
				IOHelper.print("Enter country name for country number "+(j+1));
				String countryName = IOHelper.getNextString();

				IOHelper.print("Enter x coordinate and y coordinate)");
				int x = IOHelper.getNextInteger();
				int y = IOHelper.getNextInteger();

				Country country = new Country(j, countryName);
                country.setxCoordiate(x);
                country.setyCoordiate(y);
                country.setContId(i);
				IOHelper.print("\nEnter the number of adjacent countries you want to enter\n");
				int adjacentCountries = IOHelper.getNextInteger();
				for(int k=0 ; k < adjacentCountries ; k++) 
				{
					IOHelper.print("\nEnter country name for adjacency country number "+(k+1)+"\n");
					String neighbourName = IOHelper.getNextString();
					country.addNeighboursString(neighbourName);
				}
				continent.addCountry(country);
			}
			map.addContinent(continent);   	 	
		}

		if(map.isMapValid())
			map.saveMap();
		else
			IOHelper.print("Map is not valid");
		
	}

	private void editMap()
	{
		//TODO: For mandeep - select map to edit and create functionality
		System.out.println("Edit Map in MapController called");
	}

	private void initializeMap()
	{
		int i=1;
		ArrayList<String> maps = getListOfMaps();
		IOHelper.print("\nPress number to load file.\n");
		for (String file : maps) {
			IOHelper.print("\n"+i+")"+file);
			i++;
		}
		IOHelper.print("\n");
		int mapNumber = IOHelper.getNextInteger();
		String selectedMapName = maps.get(mapNumber-1);
		map.setMapName(selectedMapName);
		map.readMap();
		System.out.print("is map valid:"+map.isMapValid());
	}
	
	private ArrayList<String> getListOfMaps() {
		ArrayList<String> fileNames = new ArrayList<String>();
		File folder = new File("assets/maps");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				fileNames.add(listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				//nada
			}
		}
		return fileNames;
	}
	
}
