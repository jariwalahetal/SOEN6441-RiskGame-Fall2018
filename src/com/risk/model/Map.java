package com.risk.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

import com.risk.helper.IOHelper;

/**
 * Map Class
 *
 * @author sadgi
 * @version 1.0.0
 * @since 27-September-2018
 */
public class Map {
	private String mapName;
	private String mapPath = "assets/maps/";
	private ArrayList<Continent> continentsList = new ArrayList<>();
	private ArrayList<String> visitedList = new ArrayList<String>();

	/**
	 * This is a constructor of Map Class which sets mapId and mapName
	 * @param mapName, Name of the map
	 */
	public Map(String mapName) {
		super();
		this.mapName = mapName;

	}

	/**
	 * Method to get name of the map
	 * 
	 * @return mapName, only name of map without extension
	 */
	public String getMapName() {

		return mapName.replace(".map", "");
	}

	/**
	 * This function loads the Map into the memory
	 */
	public void readMap() {
		try {
			boolean captureContinents = false;
			boolean captureCountries = false;
			File file = new File(mapPath + mapName);
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String readLine;
			int continentID = 0;
			int countryID = 0;
			HashMap<String,Country> countries = new HashMap<String,Country>();
			HashMap<Country,String[]> countryNeighbor = new HashMap<Country,String[]>();

			while ((readLine = bufferedReader.readLine()) != null) {
				if (readLine.trim().length() == 0)
					continue;
				else if ((readLine.trim()).equals("[Continents]")) {
					captureContinents = true;
					continue;
				} else if ((readLine.trim()).equals("[Territories]")) {
					captureContinents = false;
					captureCountries = true;
					continue;
				}
				
			
				if (captureContinents) {
					String[] parsedControlValuesByContinentsArray = readLine.split("=");
					Continent continent = new Continent(continentID++, parsedControlValuesByContinentsArray[0],
							Integer.parseInt(parsedControlValuesByContinentsArray[1]));
					continentsList.add(continent);
				} else if (captureCountries) {
					String[] parsedTerritoriesArray = readLine.split(",");
					String continentName = parsedTerritoriesArray[3];
					int xCoordinate = Integer.parseInt(parsedTerritoriesArray[1]);
					int yCoordinate = Integer.parseInt(parsedTerritoriesArray[2]);
					Country country = new Country(countryID++, parsedTerritoriesArray[0], xCoordinate, yCoordinate);
					
					countries.put(parsedTerritoriesArray[0], country);
					countryNeighbor.put(country, parsedTerritoriesArray);
					
					int k = 0;
					for (String neighborCountry : parsedTerritoriesArray) {
						if (k > 3) {
							country.addNeighboursString(neighborCountry);
						}
						k++;
					}

					for (int i = 0; i < continentsList.size(); i++) {
						if (continentsList.get(i).getContName().equals(continentName)) {
							continentsList.get(i).addCountry(country);
							country.setContId(continentsList.get(i).getContId());
							break;
						}
					}
				}
			}
	
			Country neighbour;
			Iterator it = countryNeighbor.entrySet().iterator();
		    while (it.hasNext()) {
		    	HashMap.Entry entry = (HashMap.Entry) it.next();
		        Country country = (Country)entry.getKey();
		        String[] neighbours = (String[])entry.getValue();
		       for(int i=4;i<neighbours.length;i++)
               { neighbour = countries.get(neighbours[i]);
		         country.addNeighboursCountries(neighbour);             	   
               }
		    }	
						
			bufferedReader.close();
		} catch (Exception e) {
			IOHelper.printException(e);
		}
	}

	/**
	 * This adds a continent to the continent list
	 *
	 * @param continent,
	 *            object of the continent
	 */
	public void addContinent(Continent continent) {
		continentsList.add(continent);
	}

	/**
	 * This method allows user to to edit map and add new continent.
	 */
	public void addContinentToMap() {
		IOHelper.print("\nEnter the number of continents you want to create:\n");
		int totalNumberOfContinents = IOHelper.getNextInteger();
		for (int i = 0; i < totalNumberOfContinents; i++) {
			IOHelper.print("\nEnter continent name for continent number " + (i + 1)
					+ " (press enter and then input the control value)\n");
			String continentName = IOHelper.getNextString();
			int controlValue = IOHelper.getNextInteger();
			Continent continent = new Continent(i, continentName, controlValue);
			addContinent(continent);
		}
	}

	/**
	 * This method allows user to edit map and add country to the existing continent
	 * in the map.
	 * 
	 * @param continentName,
	 *            name of the continent
	 * @param contID,
	 *            id of the continent
	 */
	public void addCountryToContinent(String continentName, int contID) {

		Continent currentContinent = continentsList.stream()
				.filter(x -> x.getContName().equalsIgnoreCase(continentName)).findAny().orElse(null);

		IOHelper.print("Enter the number of countries you want to create in this continent:\n");
		int numberOfCountries = IOHelper.getNextInteger();
		for (int j = 0; j < numberOfCountries; j++) {
			IOHelper.print("Enter country name for country number: " + (j + 1));
			String countryName = IOHelper.getNextString();
			IOHelper.print("Enter x coordinate:");
			int x = IOHelper.getNextInteger();
			IOHelper.print("Enter y coordinate:");
			int y = IOHelper.getNextInteger();

			Country country = new Country(j, countryName, x, y);
			country.setContId(contID);
			IOHelper.print("\nEnter the number of adjacent countries you want to create:\n");
			int adjacentCountries = IOHelper.getNextInteger();
			for (int k = 0; k < adjacentCountries; k++) {
				IOHelper.print("\nEnter country name for adjacency country number: " + (k + 1) + "\n");
				String neighbourName = IOHelper.getNextString();
				country.addNeighboursString(neighbourName);
			}
			currentContinent.addCountry(country);
		}
	}

	/**
	 * @author Mandeep Kaur
	 *
	 *         This function deletes the Continent from the existing Map file.
	 * @param continentToDelete,
	 *            name of the continent to be deleted
	 * @return true, if continent deleted
	 */
	public boolean deleteContinent(String continentToDelete) {

		ArrayList<Country> countriesListOfCurrentContinent = new ArrayList<>();
		Continent currentContinent = continentsList.stream()
				.filter(x -> x.getContName().equalsIgnoreCase(continentToDelete)).findAny().orElse(null);
		if (currentContinent == null) {
			IOHelper.print("Continent name is invalid");
			return false;
		}
		countriesListOfCurrentContinent = currentContinent.getCountryList();
		for (Continent continent : continentsList) {
			for (Country country : continent.getCountryList()) {
				for (int i = 0; i < country.getNeighboursString().size(); i++) {
					String coutryNameToDelete = country.getNeighboursString().get(i);
					Country c = countriesListOfCurrentContinent.stream()
							.filter(x -> x.getCountryName().equalsIgnoreCase(coutryNameToDelete)).findAny()
							.orElse(null);
					if (c != null) {
						country.getNeighboursString().remove(i);
					} 
				}
			}
		}
		continentsList.remove(currentContinent);

		return true;
	}

	/**
	 * @author Mandeep Kaur This function deletes the Country from the existing Map
	 *         file.
	 * @param countryToDelete,
	 *            name of the country need to be deleted
	 * @return true if country is deleted
	 */
	public boolean deleteCountry(String countryToDelete) {
		ArrayList<Country> countriesList = getCountryList();
		Country currentCountry = countriesList.stream()
				.filter(x -> x.getCountryName().equalsIgnoreCase(countryToDelete)).findAny().orElse(null);

		if (currentCountry == null) {
			IOHelper.print("Country name is invalid");
			return false;
		}
		for (Country country : countriesList) {
			for (int i = 0; i < country.getNeighboursString().size(); i++) {
				if (country.getNeighboursString().get(i).equalsIgnoreCase(countryToDelete)) {
					country.getNeighboursString().remove(i);
				} else {
				}
			}
		}
		for (Continent continent : continentsList) {
			continent.getCountryList().remove(currentCountry);
		}
		return true;
	}

	/**
	 * This function checks and returns if the map is a valid map or not.
	 *
	 * @return true
	 */
	public boolean isMapValid() {
		String oneCountryInTwoContinentsCountryName = null;
		String atLeastOneCountryInAllContinentsContinentName = null;
		try {
			boolean oneCountryInTwoContinents = false;
			boolean atLeastOneCountryInAllContinents = true;
			ArrayList<String> listOfAllCountries = new ArrayList<String>();
			ArrayList<String> listOfMainCountries = new ArrayList<String>();
			for (Continent singleContinent : this.continentsList) {
				if (singleContinent.getCountryList().isEmpty()) {
					atLeastOneCountryInAllContinents = false;
					atLeastOneCountryInAllContinentsContinentName = singleContinent.getContName();
				}
				for (Country singleCountry : singleContinent.getCountryList()) {
					if (!listOfAllCountries.contains(singleCountry.getCountryName())) {
						listOfAllCountries.add(singleCountry.getCountryName());
					}
					if (listOfMainCountries.contains(singleCountry.getCountryName())) {
						oneCountryInTwoContinentsCountryName = singleCountry.getCountryName();
						oneCountryInTwoContinents = true;
						if (oneCountryInTwoContinents) {
							System.out.println("Same country (" + oneCountryInTwoContinentsCountryName
									+ ") cannot be in two continents.");
							return false;
						}
					} else {
						listOfMainCountries.add(singleCountry.getCountryName());
					}
					for (String eachNeighbourCountry : singleCountry.getNeighboursString()) {
						if (listOfAllCountries.contains(eachNeighbourCountry)) {
							// nada
						} else {
							listOfAllCountries.add(eachNeighbourCountry);
						}
					}
				}
			}
			Collections.sort(listOfAllCountries);

			Country sourceCountry = ((this.continentsList.get(0)).getCountryList()).get(0);
			visitedList.clear();
			dfsRecursive(sourceCountry);
			// 1.check if the graph is connected or not
			Collections.sort(visitedList);
			if (!atLeastOneCountryInAllContinents) {
				System.out.println("Each continent should have atleast one country. "
						+ atLeastOneCountryInAllContinentsContinentName + " is empty.");
				return false;
			}
			if (isTwoArrayListsWithSameValues(visitedList, listOfAllCountries)) {
				boolean connectedGraphContinentLevel = checkConnectedGraphOnContinentLevel();
				if(connectedGraphContinentLevel) {
					return true;
				}
				else {
					System.out.print("Graph not connected at continent level");
					return false;
				}
			} else {
				System.out.println("List of disconnected countires:");
				if (visitedList.size() > listOfAllCountries.size()) {
					visitedList.removeAll(listOfAllCountries);
					for (String list : visitedList) {
						System.out.print(list + " ");
					}
				} else {
					listOfAllCountries.removeAll(visitedList);
					for (String list : visitedList) {
						System.out.print(list + " ");
					}
				}
				System.out.println();
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Checks if two array lists are same or not
	 *
	 * @param list1
	 *            arraylist1
	 * @param list2
	 *            arraylist2
	 *
	 * @return boolean
	 */
	boolean checkConnectedGraphOnContinentLevel() {
		return true;
//		for(Continent cont:this.continentsList) {
//			if(!checkIfContinentConnected(cont)) {
//				return false;
//			}
//		}
//		return true;
	}
	public boolean checkIfContinentConnected(Continent induvidualCont) {
		ArrayList<Country> totalCountries = new ArrayList<Country>();
		Stack<Country> s = new Stack<Country>();
		ArrayList<Country> visitedCountries = new ArrayList<Country>();
		for(Country country:induvidualCont.getCountryList()) {
			totalCountries.add(country);
		}
		s.push(totalCountries.get(0));
		visitedCountries.add(totalCountries.get(0));
		while(!s.isEmpty()) {
			Country v = s.pop();
			for(Country neighbouringCountry :v.getNeighbourCountries()) {
				if(!visitedCountries.contains(neighbouringCountry)) {
					s.push(neighbouringCountry);
					visitedCountries.add(neighbouringCountry);
				}
			}
		}
		return false;
		
		
	}
	public boolean isTwoArrayListsWithSameValues(ArrayList<String> list1, ArrayList<String> list2) {
		if (list1 == null && list2 == null)
			return true;
		if ((list1 == null && list2 != null) || (list1 != null && list2 == null))
			return false;

		if (list1.size() != list2.size())
			return false;
		for (String itemList1 : list1) {
			if (!list2.contains(itemList1))
				return false;
		}
		return true;
	}

	/**
	 * This function checks if the map is connected or not ; it is a recursive
	 * function. *
	 * 
	 * @param sourceCountry,
	 *            name of the source country
	 */
	public void dfsRecursive(Country sourceCountry) {
		visitedList.add(sourceCountry.getCountryName());
		for (String neighbourCountry : sourceCountry.getNeighboursString()) {
			if (visitedList.contains(neighbourCountry)) {
			} else {
				Country countryyy = null;
				for (Continent cont : this.continentsList) {
					for (Country countryy : cont.getCountryList()) {
						if (countryy.getCountryName().equals(neighbourCountry)) {
							countryyy = countryy;
						}
					}
				}
				if (countryyy != null) {
					dfsRecursive(countryyy);
				}
			}
		}
	}

	/**
	 * This method saves the map into the disk. *
	 */
	public void saveMap() {
		StringBuffer content = new StringBuffer();
		content.append("[Continents]\r\n");
		for (Continent induvidualContinentObject : this.continentsList) {
			content.append(induvidualContinentObject.getContName() + "=" + induvidualContinentObject.getControlValue()
					+ "\r\n");
		}
		content.append("\n[Territories]\r\n");
		for (Continent induvidualContinentObject : this.continentsList) {
			for (Country induvidualCountry : induvidualContinentObject.getCountryList()) {
				content.append(induvidualCountry.getCountryName() + "," + induvidualCountry.getxCoordiate() + ","
						+ induvidualCountry.getyCoordiate() + "," + induvidualContinentObject.getContName());
				for (String neighbouringCountry : induvidualCountry.getNeighboursString()) {
					content.append("," + neighbouringCountry);
				}
				content.append("\r\n");
			}
		}
		// write map to disk
		Path path = Paths.get(this.mapPath + this.mapName);
		BufferedWriter writer = null;
		try {
			// Delete temporary file
			Path tempFilePath = Paths.get(this.mapPath + "temp" + ".map");
			Files.deleteIfExists(tempFilePath);
			writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
			writer.write(new String(content));
			writer.close();
		} catch (Exception e) {
			IOHelper.printException(e);
		}
		System.out.println(content);
	}

	public boolean validateAndCreateMap(StringBuffer content, String nameOfTheMap) {

		if (this.writeMapToDisk(content, "temp")) {
			this.mapName = "temp.map";
			try {
				this.readMap();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (this.isMapValid()) {
				this.mapName = nameOfTheMap;
				this.writeMapToDisk(content, nameOfTheMap);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * This writes the content to the disk with the name passed to the funciton.
	 *
	 * @param content,
	 *            content need to be added
	 * @param nameOfTheMap,
	 *            name of the map
	 * @return true, if data is written on file otherwise false
	 */
	private boolean writeMapToDisk(StringBuffer content, String nameOfTheMap) {
		Path path = Paths.get(this.mapPath + nameOfTheMap + ".map");
		BufferedWriter writer = null;
		try {
			// Delete temp file
			Path tempFilePath = Paths.get(this.mapPath + "temp" + ".map");
			Files.deleteIfExists(tempFilePath);

			writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
			writer.write(new String(content));
			writer.close();
			return true;
		} catch (Exception e) {
			IOHelper.printException(e);
			return false;
		}
	}

	/**
	 * This function returns the country list.
	 * 
	 * @return ArrayList,Arraylist of countries
	 *
	 */
	public ArrayList<Country> getCountryList() {
		ArrayList<Country> countriesList = new ArrayList<>();
		for (Iterator<Continent> continents = continentsList.iterator(); continents.hasNext();) {
			countriesList.addAll(continents.next().getCountryList());
		}
		return countriesList;
	}

	/**
	 * This function returns the continent list.
	 *
	 * @return ArrayList, array list of continent
	 */
	public ArrayList<Continent> getContinentList() {
		return continentsList;
	}

	/**
	 * Method to get path of the map
	 * 
	 * @return mapPath, path of the math
	 */
	public String getMapPath() {
		return mapPath;
	}

	/**
	 * Method to set path of the map
	 * 
	 * @param mapPath,
	 *            path of the map
	 */
	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}
}
