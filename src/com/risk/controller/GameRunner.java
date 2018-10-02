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
    	 GameController gameController = new GameController();
    	 gameController.startGame();
     }
}
