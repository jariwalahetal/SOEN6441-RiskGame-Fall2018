 package com.risk.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
/**
 * This class tests the Map methods for a valid map.
 */
public class MapTest {
	StringBuffer sb;
	StringBuffer sb2;
	
	@Before
	public void setUp()
	{
		sb = new StringBuffer();
		sb2 = new StringBuffer();
		
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

}
