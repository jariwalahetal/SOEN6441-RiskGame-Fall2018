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
	 public static void print(String str) 
	 {
		 System.out.print(str);
	 }
     public static void main(String []args) throws IOException
     {
    	 Map m = new Map();
    	 GetInputs inp = new GetInputs();
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
    	 m.saveMap();
     }
}

class Map
{
	HashMap<String,Integer> controlValuesByContinents = new HashMap<String,Integer>();
	HashMap<String,ArrayList<String>> territories = new HashMap<String,ArrayList<String>>();
	public void addControlValues(String country,int controlValue)
	{
		controlValuesByContinents.put(country,controlValue);
	}
	public void addTeritorries(String teritorry,ArrayList<String> list)
	{
		territories.put(teritorry,list);
	}
	public void saveMap() throws IOException
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
	    final Path path = Paths.get("file.map");

	    try (
	        final BufferedWriter writer = Files.newBufferedWriter(path,
	            StandardCharsets.UTF_8, StandardOpenOption.CREATE);
	    ) {
	        writer.write(content);
	        writer.flush();
	    }
	}
}

class GetInputs{
	Scanner sc = new Scanner(System.in);
    public String getNextString()
    {
        return sc.next();
    }
    public int getNextInteger()
    {
    	return sc.nextInt();
    }
}