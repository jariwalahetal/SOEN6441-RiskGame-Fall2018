package com.risk.model.strategies;

import com.risk.model.Country;
import com.risk.model.Player;

/**
 * An aggressive computer player strategy that focuses on attack (reinforces its
*strongest country, then always attack with it until it cannot attack anymore, then
*fortifies in order to maximize aggregation of forces in one country).
*
 * @author binay
 * @version 1.0.0
 * @since 19-November-2018
 */
public class Aggressive implements PlayerStrategy {

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
