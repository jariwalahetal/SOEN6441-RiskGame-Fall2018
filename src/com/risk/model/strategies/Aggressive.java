package com.risk.model.strategies;

import com.risk.helper.IOHelper;
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
	public boolean reinforce(Player player) {
		// TODO Auto-generated method stub
		if (player.getNoOfReinforcedArmies() == 0) {
			IOHelper.print("Player " + player.getName() + " doesn't have unassigned armies!");
			return false;
		}

		Country country = player.getStrongestCountry();
		if (country == null) {
			IOHelper.print("Country name - " + country.getCountryName() + " does not exist!");
			return false;
		}

		IOHelper.print("Adding reinforcement army in " + country.getCountryName());
		while(player.getNoOfReinforcedArmies() > 0)
		{player.decreaseReinforcementArmyCount();
		 country.increaseArmyCount(1);
		}
		
		return true;
	}

	@Override
	public void attack(Player attackerPlayer) {
		// TODO Auto-generated method stub
		Country strongestCountry = attackerPlayer.getStrongestCountry();
		
		
	}

	@Override
	public boolean fortify(Player player) {
		// TODO Auto-generated method stub
		Country strongestCountry = player.getStrongestCountry();

		
		return true;
	}

}
