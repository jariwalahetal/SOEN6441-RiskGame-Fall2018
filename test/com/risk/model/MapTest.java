package com.risk.model;

import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class MapTest {
	StringBuffer sb;
	
	@Before
	public void setUp()
	{
		sb = new StringBuffer();
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
		
		Path path = Paths.get(map.getMapPath() + map.getMapName() + ".map");
		//assertTrue(Files.exists(path));
				
	}

}
