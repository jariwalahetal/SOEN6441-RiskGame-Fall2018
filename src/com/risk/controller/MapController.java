package com.risk.controller;

import java.io.IOException;

import com.risk.helper.IOHelper;
import com.risk.model.*;

/**
 * @author Binay Kumar
 * @version 1.0.0
 * @since 27-September-2018
 * This class is used to handle operations related to MAP.
 */

public class MapController {

	private void createMap()
	{
		System.out.println("Create Map in MapController called");
	}
	
	private void editMap()
	{
		System.out.println("Edit Map in MapController called");
	}

	private void validateMap()
	{
		System.out.println("Validate Map in MapController called");
	}

	public static void parseMap(String mapName) 
	{		
		Map m = new Map(mapName);
		try 
		{
			m.parseMap();
			System.out.print(4);
			
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
