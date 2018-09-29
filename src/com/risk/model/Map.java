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
	private ArrayList<Continent> continentsList = new ArrayList<>();
	
	/**
	 * This is a constructor of Map Class which sets mapId and mapName.
	 * @param mapName
	 */
	public Map() 
	{
		super();
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
	
	public void readMap()
	{
		try
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
		catch (Exception e) {
			IOHelper.printException(e);
		}
		
	}
	
	public void addControlValues(String country,int controlValue)
	{
		controlValuesByContinents.put(country,controlValue);
	}
	
	public void addContinent(Continent continent)
	{
		continentsList.add(continent);
	}
	
	public void addTeritorries(String teritorry,ArrayList<String> list)
	{
		territories.put(teritorry,list);
	}
	
	
	public Boolean isMapValid()
	{
		IOHelper.print("Validate map");
		return true;
	}
	
	public void saveMap()
	{
		StringBuffer content = new StringBuffer();
		content.append("[Continents]\n");
		for (Continent induvidualContinentObject : this.continentsList) 
		{
			content.append(induvidualContinentObject.getContName()+"="+induvidualContinentObject.getControlValue()+"\n");
		}
		content.append("\n[Territories]\n");
		for (Continent induvidualContinentObject : this.continentsList) 
		{
			for(Country induvidualCountry : induvidualContinentObject.getCountryList()) {
				content.append(induvidualCountry.getCountryName()+","+induvidualCountry.getxCoordiate()+","+induvidualCountry.getyCoordiate()+","+induvidualContinentObject.getContName());
				for(String neighbouringCountry : induvidualCountry.getNeighboursString())
				{
					content.append(","+neighbouringCountry);
				}				
				content.append("\n");
			};
		}
		
		System.out.print(content);
		//TODO: Change this logic
//		String content = "[Map]\r\n \r\n[Continents]";
//		for (String key : controlValuesByContinents.keySet()) 
//		{
//			content = content +"\r\n"+key+"="+controlValuesByContinents.get(key);
//		}
//		content = content +"\r\n";
//		content = content +"\r\n";
//		content = content +"[Territories]\r\n";
//		for (String key : territories.keySet())
//		{
//			String countryVal = "";
//			content = content +key+",";
//			for (int k = 0; k < territories.get(key).size(); k++) 
//			{
//				countryVal = countryVal + territories.get(key).get(k);
//				if(k==territories.get(key).size() - 1) {
//					
//				}
//				else 
//				{
//					countryVal = countryVal +",";
//				}
//			}
//			content = content + countryVal+"\r\n";
//		}
//	    final Path path = Paths.get("assets/maps/"+mapName+".map");
//	    BufferedWriter writer = null;
//	    try 
//	    {
//	        writer = Files.newBufferedWriter(path,
//            StandardCharsets.UTF_8, StandardOpenOption.CREATE);
//	    	writer.write(content);
//	    	writer.close();
//	    }
//	    catch (Exception e) 
//	    {
//			IOHelper.printException(e);	    	
//	    }       
	    
	}
}
