package com.risk.controller;
/**
 * @author Bedi
 * @version 1.0.0
 * @since 25-September-2018
 */

import java.io.*;



public class GameRunner{
	
     /**
     * start and initilization of game 
     * @param args
     * @throws IOException
     */
    public static void main(String []args) throws IOException
     {
    	 MapController mapController = new MapController();
    	 mapController.startGame();
     }
}


