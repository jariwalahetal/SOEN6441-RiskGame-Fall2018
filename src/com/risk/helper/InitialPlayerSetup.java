package com.risk.helper;

public class InitialPlayerSetup {

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
			return EnumColor.YELLOW;
		default:
			return EnumColor.YELLOW;
		}
	}

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
