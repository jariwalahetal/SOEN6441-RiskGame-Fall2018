/**
 * @author Bedi
 * @version 1.0.0
 * @since 25-September-2018
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner; 
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class Main{
    public static void main(String []args) throws IOException
     {
    	Map m = new Map();
//    	m.createMap();
    	print("Map Created!!!");
    	m.loadMap();
     }

	private static PrintStream print(String string) {
		return System.out.printf(string);
	}
}
/**
 * This class handles the map entity of the game. It handles functionality like creating a new map, 
 * editing an existing map , loading an existing map for gameplay.
 */
class Map
{
	GetInputs inp = new GetInputs();
	private HashMap<String,Integer> controlValuesByContinents = new HashMap<String,Integer>();
	private HashMap<String,ArrayList<String>> territories = new HashMap<String,ArrayList<String>>();
	private HashMap<String,Integer> parsedControlValuesByContinents = new HashMap<String,Integer>();
	
	/**
	 * This method adds a country and the corresponding control value to the controlValuesByContinents hashmap.
	 * @param country country as a string
	 * @param controlValue the control value of the country.
	 */
	public void addControlValues(String country,int controlValue)
	{
		controlValuesByContinents.put(country,controlValue);
	}
	
	/**
	 * This method adds the territory with the corresponding information regarding the territory into the arraylist.
	 * @param teritorry The name of the territory
	 * @param list The list of territory information.
	 */
	public void addTeritorries(String teritorry,ArrayList<String> list)
	{
		territories.put(teritorry,list);
	}
	
	/**
	 * This function saves the map file generated to the disk.
	 * @param mapName The name of the map .
	 * @throws IOException 
	 */
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
	/**
	 *This function returns the string passed. 
	 * @param str Takes the string to be printed as a parameter.
	 * 
	 * @return string
	 */
	public static void print(String str) 
	{
		System.out.print(str);
	}
	
	/**
	 *This function creates a map object and asks the user to input values for the map.
	 * @return A string which is passed to the function.
	 * @throws IOException 
	 */
	public void createMap() throws IOException {
		 Map m = new Map();
    	 print("\nEnter the number of continents you want to create\n");
    	 int totalNumberOfCountries = inp.getNextInteger();
    	 for(int i = 0; i < totalNumberOfCountries ; i++) 
    	 {
    		 print("\nEnter continent name for continent number"+(i+1)+" (press enter and then input the control value)\n");
    		 String continent = inp.getNextString();
        	 int controlValue = inp.getNextInteger();
             m.addControlValues(continent,controlValue);
    	 }
    	 print("\nEnter the number of territories you want to create\n");
    	 int totalNumberOfTerritories = inp.getNextInteger();
    	 for(int i = 0; i < totalNumberOfTerritories ; i++) 
    	 {
    		 print("\nEnter territory name for territory number "+(i+1)+" (press enter and then x coordinate , y, continent)\n");
    		 String territory = inp.getNextString();
    		 ArrayList<String> territoryValues = new ArrayList<String>();
        	 String x = inp.getNextString();
        	 String y = inp.getNextString();
        	 territoryValues.add(x);
        	 territoryValues.add(y);
        	 String continentContainedIn = inp.getNextString();
        	 territoryValues.add(continentContainedIn);
        	 print("\nEnter the number of adjacent countries you want to enter\n");
        	 int adjacentCountries = inp.getNextInteger();
        	 for(int j=0 ; j < adjacentCountries ; j++) 
        	 {
        		 print("\nEnter territory name for adjacency country number "+(j+1)+"\n");
        		 territoryValues.add(inp.getNextString());
        	 }
        	 m.territories.put(territory, territoryValues);
    	 }
    	 print("\nEnter the name of the map ");
    	 String mapName = inp.getNextString();
    	 m.saveMap(mapName);
	}
	/**
	 *This function parses the map file and stores it in variables.
	 * @param mapName The name of the map passed.
	 * @return A string which is passed to the function.
	 * @throws IOException 
	 */
	public void parseMap(String mapName) throws IOException {
		int captureContinents = 0;
		File file = new File("assets/maps/"+mapName); 
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		String st; 
		while ((st = br.readLine()) != null) {
//			print(st+"\n");
//			print(""+captureContinents);
			if(captureContinents == 1) {
				if((st.trim()).equals("")) {
					captureContinents =0;
				}else {
					String[] parsedControlValuesByContinentsArray = st.split("=");
					parsedControlValuesByContinents.put(parsedControlValuesByContinentsArray[0], Integer.parseInt(parsedControlValuesByContinentsArray[1]));
				}
			}
			if((st.trim()).equals("[Continents]")) {
				captureContinents =1;
			}
		} 
		br.close();
	}
	public void editMap() {
		
	}
	/**
	 *This function lets the user select a map he/she wants to load
	 * @throws IOException 
	 */
	public void loadMap() throws IOException {
		int i=1;
		ArrayList<String> maps = returnArrayListOfMapNames();
		print("\nPress number to load file.\n");
		for (String file : maps) {
			print("\n"+i+")"+file);
			i++;
		}
		print("\n");
		int mapNumber = inp.getNextInteger();
		String selectedMapName = maps.get(mapNumber-1);
		parseMap(selectedMapName);
	}

	/**
	 * Returns an array list of all the maps in the Maps folder directory.
	 * <p>
	 * Returns null if there are no maps in the map folder.

	 * @return      an arraylist of all the maps in the map folder
	 * @see         list
	 */
	public ArrayList<String> returnArrayListOfMapNames() {
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
/**
 * This class has the Scanner class object ; used to capture inputs easily.
 */
class GetInputs{
	Scanner sc = new Scanner(System.in);
	/**
	 * This method returns the next input string
	 * @return string
	 */
    public String getNextString()
    {
        return sc.next();
    }
    /**
	 * This method returns the next input integer
	 * @return Integer
	 */
    public int getNextInteger()
    {
    	return sc.nextInt();
    }
}

