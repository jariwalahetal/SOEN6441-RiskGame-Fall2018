package com.risk.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.risk.helper.IOHelper;

/**
 * Map Class
 * @author sadgi
 * @version 1.0.0
 * @since 27-September-2018
 */
public class Map {
	private String mapName;
	
	/**
	 * This is a constructor of Map Class which sets mapId and mapName.
	 * @param mapName
	 */
	public Map(String mapName) 
	{
		super();
		this.mapName = mapName;
	}
	
	/**
	 * getters and setters
	 */
	public String getMapName() 
	{
		return mapName;
	}
	public void setMapName(String mapName) 
	{
		this.mapName = mapName;
	}


	private HashMap<String,Integer> controlValuesByContinents = new HashMap<String,Integer>();
	private HashMap<String,ArrayList<String>> territories = new HashMap<String,ArrayList<String>>();
	private HashMap<String,Integer> parsedControlValuesByContinents = new HashMap<String,Integer>();
	private HashMap<String,ArrayList<String>> parsedTerritories = new HashMap<String,ArrayList<String>>();
	
	public void parseMap() throws IOException 
	{
		boolean captureContinents = false;
		boolean captureTerritoryData = false;
		File file = new File("assets/maps/"+this.mapName); 
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		String st;
		
		while ((st = br.readLine()) != null) 
		{
//			print(st+"\n");
//			print(""+captureContinents);
			if(captureContinents) 
			{
				if((st.trim()).equals("")) 
				{
					captureContinents = false;
				}else 
				{
					String[] parsedControlValuesByContinentsArray = st.split("=");
					parsedControlValuesByContinents.put(parsedControlValuesByContinentsArray[0], Integer.parseInt(parsedControlValuesByContinentsArray[1]));
				}
			}
			if((st.trim()).equals("[Continents]")) 
			{
				captureContinents = true;
			}
			
			
			
			
			if(captureTerritoryData) 
			{
				if((st.trim()).equals("")) 
				{
					captureTerritoryData = false;
				}else 
				{
					String[] parsedTerritoriesArray = st.split(",");
					String key = parsedTerritoriesArray[0];
					String[] modifiedParsedTerritoriesArray = Arrays.copyOfRange(parsedTerritoriesArray, 1, parsedTerritoriesArray.length);
					ArrayList<String> parsedTerritoryVal = new ArrayList<String>();
					for(String x: modifiedParsedTerritoriesArray)
					{
						parsedTerritoryVal.add(x);
					}
					parsedTerritories.put(key, parsedTerritoryVal);
//					parsedControlValuesByContinents.put(parsedControlValuesByContinentsArray[0], Integer.parseInt(parsedControlValuesByContinentsArray[1]));
				}
			}
			if((st.trim()).equals("[Territories]")) 
			{
				captureTerritoryData = true;
			}
		} 
		br.close();
	}
	
	public void addControlValues(String country,int controlValue)
	{
		controlValuesByContinents.put(country,controlValue);
	}
	
	public void addTeritorries(String teritorry,ArrayList<String> list)
	{
		territories.put(teritorry,list);
	}
	
	public void createMap() {
		IOHelper.print("\nEnter the number of continents you want to create\n");
		int totalNumberOfCountries = IOHelper.getNextInteger();
   	 	for(int i = 0; i < totalNumberOfCountries ; i++) 
   	 	{
   	 		IOHelper.print("\nEnter continent name for continent number"+(i+1)+" (press enter and then input the control value)\n");
   	 		String continent = IOHelper.getNextString();
   	 		int controlValue = IOHelper.getNextInteger();
            this.addControlValues(continent,controlValue);
   	 	}
   	 	IOHelper.print("\nEnter the number of territories you want to create\n");
   	 	int totalNumberOfTerritories = IOHelper.getNextInteger();
   	 	for(int i = 0; i < totalNumberOfTerritories ; i++) 
 		{
   	 		IOHelper.print("\nEnter territory name for territory number "+(i+1)+" (press enter and then x coordinate , y, continent)\n");
   	 		String territory = IOHelper.getNextString();
   	 		ArrayList<String> territoryValues = new ArrayList<String>();
   	 		String x = IOHelper.getNextString();
   	 		String y = IOHelper.getNextString();
   	 		territoryValues.add(x);
   	 		territoryValues.add(y);
   	 		String continentContainedIn = IOHelper.getNextString();
   	 		territoryValues.add(continentContainedIn);
   	 		IOHelper.print("\nEnter the number of adjacent countries you want to enter\n");
   	 		int adjacentCountries = IOHelper.getNextInteger();
   	 		for(int j=0 ; j < adjacentCountries ; j++) 
   	 		{
   	 			IOHelper.print("\nEnter territory name for adjacency country number "+(j+1)+"\n");
   	 			territoryValues.add(IOHelper.getNextString());
   	 		}
   	 		this.territories.put(territory, territoryValues);
 		}
   	 	IOHelper.print("\nEnter the name of the map ");
   	 	String mapName = IOHelper.getNextString();
   	 	try {
			saveMap(mapName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveMap(String mapName) throws IOException
	{
		String content = "[Map]\r\n \r\n[Continents]";
		for (String key : controlValuesByContinents.keySet()) 
		{
			content = content +"\r\n"+key+"="+controlValuesByContinents.get(key);
		}
		content = content +"\r\n";
		content = content +"\r\n";
		content = content +"[Territories]\r\n";
		for (String key : territories.keySet())
		{
			String teritorryVal = "";
			content = content +key+",";
			for (int k = 0; k < territories.get(key).size(); k++) 
			{
				teritorryVal = teritorryVal + territories.get(key).get(k);
				if(k==territories.get(key).size() - 1) {
					
				}else {
					teritorryVal = teritorryVal +",";
				}
			}
			content = content + teritorryVal+"\r\n";
		}
	    final Path path = Paths.get("assets/maps/"+mapName+".map");

	    try (
	        final BufferedWriter writer = Files.newBufferedWriter(path,
	            StandardCharsets.UTF_8, StandardOpenOption.CREATE);
	    ) {
	        writer.write(content);
	        writer.flush();
	    }
	}
}
