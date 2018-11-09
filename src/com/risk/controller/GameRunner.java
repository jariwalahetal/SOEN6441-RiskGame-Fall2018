package com.risk.controller;
/**
 * @author Bedi	
 * @version 1.0.0
 * @since 25-September-2018
 */

import java.io.*;

/**
 * This class runs the game.
 */

public class GameRunner {

	/**
	 * start and initilization of game
	 * 
	 * @param args,
	 *            arguments passed in the main method
	 * @throws IOException
	 *             if Input Output Exception
	 */
	public static void main(String[] args) throws IOException {
		GameController gameController = new GameController();
		gameController.startGame();
	}
}
