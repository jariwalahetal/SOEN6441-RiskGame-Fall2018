package com.risk.helper;

/**
 * This class is for the player setup.
 */
public class InitialPlayerSetup {
	/**
	 * This returns the player color.
	 * 
	 * @param playerID
	 *            the id of the player
	 * @return EnumColor,color of the player
	 */
	public static EnumColor getPlayerColor(int playerID) {
		switch (playerID) {
		case 1:
			return EnumColor.BLACK;
		case 2:
			return EnumColor.BLUE;
		case 3:
			return EnumColor.GREEN;
		case 4:
			return EnumColor.RED;
		case 5:
			return EnumColor.GRAY;
		default:
			return EnumColor.GRAY;
		}
	}

	/**
	 * This method computes the initial army count according to the player.
	 * 
	 * @param playerCount
	 *            The total number of players playing the game.
	 * @return count of the initial army
	 */
	public static int getInitialArmyCount(int playerCount) {
		switch (playerCount) {
		case 3:
			return 35;
		case 4:
			return 30;
		case 5:
			return 10;
		default:
			return 20;
		}
	}
}
