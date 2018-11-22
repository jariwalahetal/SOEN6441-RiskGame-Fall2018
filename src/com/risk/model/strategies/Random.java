package com.risk.model.strategies;

import java.util.ArrayList;

import com.risk.helper.Common;
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
	public boolean reinforce(Player player) {
		// TODO Auto-generated method stub
		ArrayList<Country> countryList = player.getAssignedCountryList();		
        int randomIndex = Common.getRandomNumberInRange(0, countryList.size());
		int armies = player.getNoOfReinforcedArmies();
		player.setNoOfReinforcedArmies(0);
		countryList.get(randomIndex).increaseArmyCount(armies);
		return true;
	}

	@Override
	public void attack(Player attackerPlayer) {
		// TODO Auto-generated method stub
		ArrayList<String> countryList = attackerPlayer.getCountriesWithArmiesGreaterThanOne();
		int randomIndex = Common.getRandomNumberInRange(0, countryList.size());
		attackerPlayer.getUnAssignedNeighbouringCountries(countryList.get(randomIndex));
				

	}

	@Override
	public boolean fortify(Player player) {
		// TODO Auto-generated method stub
		return true;
	}

}
