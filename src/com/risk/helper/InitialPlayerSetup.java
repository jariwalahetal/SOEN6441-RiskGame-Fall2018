package com.risk.helper;

public class InitialPlayerSetup {

	public static Color getPlayerColor(int playerID) {
		switch (playerID) {
		case 1:
			return Color.BLACK;
		case 2:
			return Color.BLUE;
		case 3:
			return Color.GREEN;
		case 4:
			return Color.RED;
		case 5:
			return Color.YELLOW;
		default:
			return Color.YELLOW;
		}
	}

	public static int getInitialArmyCount(int playerCount) {
		switch (playerCount) {
		case 3:
			return 35;
		case 4:
			return 30;
		case 5:
			return 25;
		default:
			return 20;
		}
	}
}
