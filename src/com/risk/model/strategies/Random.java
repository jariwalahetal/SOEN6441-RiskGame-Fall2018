package com.risk.model.strategies;

import com.risk.model.Country;
import com.risk.model.Player;

/**
 * A random computer player strategy that reinforces random a random country,
*attacks a random number of times a random country, and fortifies a random
*country, all following the standard rules for each phase.
 * 
 * @author binay
 * @version 1.0.0
 * @since 19-November-2018
 */
public class Random implements PlayerStrategy {

	@Override
	public boolean reinforce(Player player,String countryName) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void attack(Player attackerPlayer, Player defenderPlayer, Country attackingCountry, Country defendingCountry,
			int attackingDiceCount, int defendingDiceCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean fortify(Player player, String sourceCountryName, String destinationCountryName, int noOfArmies) {
		// TODO Auto-generated method stub
		return true;
	}

}
