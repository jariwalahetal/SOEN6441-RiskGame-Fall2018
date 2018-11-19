package com.risk.model.strategies;

import com.risk.model.Country;
import com.risk.model.Player;

/**
 * A cheater computer player strategy whose reinforce() method doubles the
*number of armies on all its countries, whose attack() method automatically
*conquers all the neighbors of all its countries, and whose fortify() method
*doubles the number of armies on its countries that have neighbors that belong to
*other players. 
 * 
 * @author binay
 * @version 1.0.0
 * @since 19-November-2018
 */
public class Cheater implements PlayerStrategy {

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
