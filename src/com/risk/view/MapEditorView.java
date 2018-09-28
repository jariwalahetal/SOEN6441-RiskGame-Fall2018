package com.risk.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.risk.controller.MapController;
import com.risk.helper.*;

public class MapEditorView{

	/**
	 * Returns an array list of all the maps in the Maps folder directory.
	 * <p>
	 * Returns null if there are no maps in the map folder.

	 * @return      an arraylist of all the maps in the map folder
	 * @see         list
	 */
	private ArrayList<String> returnArrayListOfMapNames() {
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
	
	public void loadMap() throws IOException {
		int i=1;
		ArrayList<String> maps = returnArrayListOfMapNames();
		IOHelper.print("\nPress number to load file.\n");
		for (String file : maps) {
			IOHelper.print("\n"+i+")"+file);
			i++;
		}
		IOHelper.print("\n");
		int mapNumber = IOHelper.getNextInteger();
		String selectedMapName = maps.get(mapNumber-1);
		MapController.parseMap(selectedMapName);
	}
}
