package com.risk.model.strategies;

import java.util.ArrayList;
import java.util.Collections;

import com.risk.helper.Common;
import com.risk.helper.IOHelper;
import com.risk.model.Country;
import com.risk.model.Game;
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
		Country country = countryList.get(randomIndex);
		player.setNoOfReinforcedArmies(0);
		country.increaseArmyCount(armies);
		return true;
	}

	@Override
	public void attack(Player attackerPlayer) {
		// TODO Auto-generated method stub
		//Random Attack Country
		ArrayList<Country> countryList = attackerPlayer.getCountriesObjectWithArmiesGreaterThanOne();
		if(countryList.isEmpty())
			return;
		int randomIndex = Common.getRandomNumberInRange(0, countryList.size());
		Country attackingCountry = countryList.get(randomIndex);
		
		//Random Attacked country
		ArrayList<Country> neighborCountries = attackerPlayer.getUnAssignedNeighbouringCountriesObject(attackingCountry.getCountryName());
		if(neighborCountries.isEmpty())
			return;
		
		randomIndex = Common.getRandomNumberInRange(0, neighborCountries.size());
		Country defendingCountry = neighborCountries.get(randomIndex);
		
		//Random Dice Count
		int attackerDiceCount = attackerPlayer.getMaximumAllowableDices(attackingCountry, "Attacker");
		attackerDiceCount = Common.getRandomNumberInRange(0, attackerDiceCount);
		int defenderDiceCount = attackerPlayer.getMaximumAllowableDices(defendingCountry, "Defender");
		defenderDiceCount = Common.getRandomNumberInRange(0, defenderDiceCount);
					

	    Player defenderPlayer = Game.getPlayerFromID(defendingCountry.getPlayerId());
	    attackerPlayer.setAttackedPlayer(defenderPlayer);
	    attackerPlayer.setFromCountry(attackingCountry);
	    attackerPlayer.setToCountry(defendingCountry);
	    
	   	    
	    attackerPlayer.rollDice(attackerDiceCount);
	    defenderPlayer.rollDice(defenderDiceCount);
	    
		ArrayList<Integer> attackingDices = attackerPlayer.getDiceOutComes();
		ArrayList<Integer> defendingDices = defenderPlayer.getDiceOutComes();

		IOHelper.print("Attacker's dices -- " + attackingDices);
		IOHelper.print("Defender's dices -- " + defendingDices);

		Collections.sort(attackingDices, Collections.reverseOrder());
		Collections.sort(defendingDices, Collections.reverseOrder());

		int totalComparisions = attackingDices.size() < defendingDices.size() ? attackingDices.size()
				: defendingDices.size();

		for (int i = 0; i < totalComparisions; i++) {

			int attackerDice = attackingDices.get(i);
			int defencerDice = defendingDices.get(i);

			IOHelper.print("Attacker dice - " + attackerDice + "  to Defender dice - " + defencerDice);
			Common.PhaseActions.add("Attacker dice - " + attackerDice + "  to Defender dice - " + defencerDice);

			if (attackerDice > defencerDice) {
				IOHelper.print("----> attacker wins for dice " + (i + 1));
				Common.PhaseActions.add("----> attacker wins for dice " + (i + 1));

				defendingCountry.decreaseArmyCount(1);

			} else {
				IOHelper.print("----> defender wins for dice " + (i + 1));
				Common.PhaseActions.add("----> defender wins for dice " + (i + 1));

				attackingCountry.decreaseArmyCount(1);
			}

			if (attackingCountry.getnoOfArmies() == 1) {
				IOHelper.print("----> Attacker not able to Attack ");
				break;
			} else if (defendingCountry.getnoOfArmies() == 0) {
				IOHelper.print("----> Defender lost all armies in " + (i + 1) + " dice roll");
				break;
			}

		}

		if (defendingCountry.getnoOfArmies() == 0) {
	             attackerPlayer.conquerCountry(defenderPlayer);	
		}

		attack(attackerPlayer);
	}

	@Override
	public boolean fortify(Player player) {
		// TODO Auto-generated method stub
		
		ArrayList<Country> countryList = player.getAssignedCountryList();		
        int randomIndex = Common.getRandomNumberInRange(0, countryList.size());
		Country sourceCountry = countryList.get(randomIndex);
		ArrayList<Country> neighbourCountryList = sourceCountry.getNeighbourCountries();
		Country destinationCountry = null;
		int armies = 0;
		for (Country neighbourCountry:neighbourCountryList)
		{if(neighbourCountry.getPlayerId() == player.getPlayerId() &&
		     neighbourCountry.getnoOfArmies()>armies)
	    	{ armies = neighbourCountry.getnoOfArmies();	
	    	destinationCountry = neighbourCountry;
	    	}
			
		}
		if (destinationCountry != null)
		{   sourceCountry.decreaseArmyCount(armies);
			destinationCountry.increaseArmyCount(armies);
		}
		
		return true;
	}

}
