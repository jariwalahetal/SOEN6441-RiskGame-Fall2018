package com.risk.helper;

public class GetArmiesByTrading {
	
	/**
	 * Get number of armies trade by turn
	 * @param turn Integer
	 * @return Integer
	 */
	public static int getArmies(int turn) {
		if(turn <= 0)
			return 0;
		
		switch(turn) {
		case 1: 
			return 4;
		case 2:
			return 6;
		case 3:
			return 8;
		case 4:
			return 10;
		case 5:
			return 12;
		case 6:
			return 15;
		default:
			int difference = turn - 6;
			return 15 + (difference * 5);
		}
	}
}
