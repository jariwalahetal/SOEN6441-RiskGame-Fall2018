package com.risk.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
/**
 * This class tests the Map methods for a valid map.
 */
public class MapTest {
	StringBuffer sb;
	StringBuffer sb2;
	Map map1;
	ArrayList<String> testMapContinents = new ArrayList<String>();
	ArrayList<String> testMapCountries = new ArrayList<String>();
	
	public  boolean equalLists(ArrayList<String> one, ArrayList<String> two){     
	    if (one == null && two == null){
	        return true;
	    }

	    if((one == null && two != null) 
	      || one != null && two == null
	      || one.size() != two.size()){
	        return false;
	    }

	    //to avoid messing the order of the lists we will use a copy
	    //as noted in comments by A. R. S.
	    one = new ArrayList<String>(one); 
	    two = new ArrayList<String>(two);   

	    Collections.sort(one);
	    Collections.sort(two);      
	    return one.equals(two);
	}
	
	@Before
	public void setUp()
	{
		sb = new StringBuffer();
		sb2 = new StringBuffer();
		map1 = new Map();
		map1.setMapPath("assets/maps/");
		map1.setMapName("Africa.map");
		testMapContinents.add("Northern Africa");
		testMapContinents.add("Southern Africa");
		testMapContinents.add("Western Africa");
		testMapContinents.add("Eastern Africa");
		testMapContinents.add("Central Africa");
		testMapContinents.add("The Horn");
		testMapContinents.add("The Congo");
		testMapCountries.add("Morocco");
		testMapCountries.add("Algeria");
		testMapCountries.add("Tunisia");
		testMapCountries.add("Libya");
		testMapCountries.add("Egypt");
		testMapCountries.add("Western Sahara");
		testMapCountries.add("Mauritania");
		testMapCountries.add("Senegal");
		testMapCountries.add("Mali");
		testMapCountries.add("Guinea");
		testMapCountries.add("Sierra Leone");
		testMapCountries.add("Liberia");
		testMapCountries.add("Ivory Coast");
		testMapCountries.add("Burkina Faso");
		testMapCountries.add("Benin");
		testMapCountries.add("Ghana");
		testMapCountries.add("Niger");
		testMapCountries.add("Nigeria");
		testMapCountries.add("Chad");
		testMapCountries.add("Cameroon");
		testMapCountries.add("Central African Rep.");
		testMapCountries.add("North Sudan");
		testMapCountries.add("South Sudan");
		testMapCountries.add("Eritrea");
		testMapCountries.add("Ethiopia");
		testMapCountries.add("Somalia");
		testMapCountries.add("Gabon");
		testMapCountries.add("Rep. of the Congo");
		testMapCountries.add("D.R.O.T. Congo");
		testMapCountries.add("Angola");
		testMapCountries.add("Zambia");
		testMapCountries.add("Uganda");
		testMapCountries.add("Kenya");
		testMapCountries.add("Tanzania");
		testMapCountries.add("Malawi");
		testMapCountries.add("Namibia");
		testMapCountries.add("Botswana");
		testMapCountries.add("Zimbabwe");
		testMapCountries.add("Mozambique");
		testMapCountries.add("Madagascar");
		testMapCountries.add("South Africa");
		
		Collections.sort(testMapContinents);
		String inValidMapString ="[Map]\n" + 
				 "author=SOEN6441 - Team8\n" +  
				 "image=Africa.bmp\n" +  
				 "[Continents]\n" +  
				 "Continent1=10\n" +  
				 "Continent2=20\n" +  
				 "[Territories]\n" +  
				 "Country1,127,46,Continent2,Country2,Country3\n" +  
				 "Country1,127,46,Continent1,Country2,Country3\n" +  
				 "Country2,193,85,Continent1,Country1,Country3,Country4\n" +  
				 "Country3,252,33,Continent1,Country5,Country1,Country2\n" +  
				 "Country4,314,94,Continent2,Country2,Country5\n" +  
				 "Country5,415,99,Continent2,Country4,Country3";
		
		String validMapString = "[Map]\n" + 
				 "author=SOEN6441 - Team8\n" +  
				 "image=Africa.bmp\n" +  
				 "[Continents]\n" +  
				 "Continent1=10\n" +  
				 "Continent2=20\n" +  
				 "[Territories]\n" +  
				 "Country1,127,46,Continent1,Country2,Country3\n" +  
				 "Country2,193,85,Continent1,Country1,Country3,Country4\n" +  
				 "Country3,252,33,Continent1,Country5,Country1,Country2\n" +  
				 "Country4,314,94,Continent2,Country2,Country5\n" +  
				 "Country5,415,99,Continent2,Country4,Country3";
		
		sb.append(validMapString);
		sb2.append(inValidMapString);
	}
	
	/**
	 * Test creation of valid map varify file exist on drive
	 */
	@Test
	public void testValidCreateMap() {
		String mapName = "validMapTest";
		Map map = new Map();
		boolean isMapCreated = map.validateAndCreateMap(sb, mapName);
		assertTrue(isMapCreated);
	}
	/**
	 * Test if invalid map treated as 
	 */
	
	@Test
	public void testInValidCreateMap() {
		String mapName = "inValidMapTest";
		Map map = new Map();
		boolean isMapCreated = map.validateAndCreateMap(sb2, mapName);
		assertFalse(isMapCreated);
	}
	@Test
	public void readMapTest() {
		ArrayList<String> testContinents= new ArrayList<String>();
		ArrayList<String> testCountries= new ArrayList<String>();
		map1.readMap();
		ArrayList<Continent> a =map1.getContinentList();
		for(Continent i: a) {
			testContinents.add(i.getContName());
			for(Country x:i.getCountryList()) {
				if(testCountries.contains(x.getCountryName())) {
					//nada
				}else {
					testCountries.add(x.getCountryName());
				}
			}
		}
		Collections.sort(testContinents);
		assertTrue(equalLists(testContinents,testMapContinents));
		assertTrue(equalLists(testCountries,testMapCountries));
	}
}
