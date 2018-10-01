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
	 * This is a constructor of Map Class which sets mapId and mapName.
	 * 
	 * @param mapName
	 */
	public Map() {
		super();
	}

	/**
	 * getters and setters
	 */
	public String getMapName() {
		return mapName;
	}

	/**
	 * This function sets the map name.
	 */
	public void setMapName(String mapName) {
		this.mapName = mapName;
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
					Country country = new Country(countryID++, parsedTerritoriesArray[0]);
					country.setxCoordiate(Integer.parseInt(parsedTerritoriesArray[1]));
					country.setyCoordiate(Integer.parseInt(parsedTerritoriesArray[2]));
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
			bufferedReader.close();
		} catch (Exception e) {
			IOHelper.printException(e);
		}

	}

	public void addContinent(Continent continent) {
		continentsList.add(continent);
	}

	public Boolean isMapValid() {
		ArrayList<String> listOfAllCountries = new ArrayList<String>();
		for (Continent singleContinent : this.continentsList) {
			for (Country singleCountry : singleContinent.getCountryList()) {
				if (!listOfAllCountries.contains(singleCountry.getCountryName())) {
					listOfAllCountries.add(singleCountry.getCountryName());
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
		DfsRecursive(sourceCountry);
		// 1.check if the graph is connected or not
		Collections.sort(visitedList);
		if (isTwoArrayListsWithSameValues(visitedList, listOfAllCountries)) {
			return true;
		} else {
			return false;
		}
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

	public void DfsRecursive(Country sourceCountry) {
		visitedList.add(sourceCountry.getCountryName());
		for (String neighbourCountry : sourceCountry.getNeighboursString()) {
			if (visitedList.contains(neighbourCountry)) {
				// nada
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
					DfsRecursive(countryyy);
				}
			}
		}
	}

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

		System.out.print(content);
		final Path path = Paths.get(this.mapPath + this.mapName + ".map");
		BufferedWriter writer = null;
		try {
			writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
			writer.write(new String(content));
			writer.close();
			System.out.println("\nFile Saved");
		} catch (Exception e) {
			IOHelper.printException(e);
		}
	}

	public ArrayList<Country> getCountryList()
	{
		ArrayList<Country> countries = new ArrayList<>();
		for(Iterator<Continent> continents= continentsList.iterator(); continents.hasNext(); )
		{
			countries.addAll(continents.next().getCountryList());
		}
		return countries;
	}
	
	public ArrayList<Continent> getContinentList()
	{
		return continentsList;
	}
}
