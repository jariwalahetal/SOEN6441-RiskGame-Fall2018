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
	
	private String strategyName = "Random";

   public String getStrategyName() {
		return strategyName;
	}
   
	@Override
	public boolean reinforce(Player player) {
		// TODO Auto-generated method stub
		ArrayList<Country> countryList = player.getAssignedCountryList();		
		int randomIndex = 0;
		if(countryList.isEmpty())
			return true;
		else if(countryList.size()>1)
			randomIndex = Common.getRandomNumberInRange(0, countryList.size()-1);
    	int armies = player.getNoOfReinforcedArmies();
		Country country = countryList.get(randomIndex);
		player.setNoOfReinforcedArmies(0);
		country.increaseArmyCount(armies);
		return true;
	}

	@Override
	public void attack(Player attackerPlayer) {
		// TODO Auto-generated method stub
		System.out.println("attack 1");
		//Random Attack Country
		ArrayList<Country> countryList = attackerPlayer.getCountriesObjectWithArmiesGreaterThanOne();
		int randomIndex = 0;
		if(countryList.isEmpty())
			return;
		else if(countryList.size()>1)
			randomIndex = Common.getRandomNumberInRange(0, countryList.size()-1);
			
		System.out.println("attack 2");

		Country fromCountry = countryList.get(randomIndex);
		System.out.println("attack 2a:fromCountry: "+fromCountry.getCountryName());

		//Random Attacked country
		ArrayList<Country> neighborCountries = attackerPlayer.getUnAssignedNeighbouringCountriesObject(fromCountry.getCountryName());
		System.out.println("attack 2b");

		if(neighborCountries.isEmpty())
			return;
		else if(neighborCountries.size()==1)
		   randomIndex = 0;			
		else
			randomIndex = Common.getRandomNumberInRange(0, neighborCountries.size()-1);
			
		System.out.println("attack 3::size"+neighborCountries.size());

		Country toCountry = neighborCountries.get(randomIndex);
		System.out.println("attack 4");

		attackOperation( fromCountry,  toCountry,  attackerPlayer);
		System.out.println("attack 5");

	//	attack(attackerPlayer);
	}

	@Override
	public boolean fortify(Player player) {
		// TODO Auto-generated method stub
		System.out.println("fortify 1");
		
		ArrayList<Country> countryList = player.getAssignedCountryList();		
         int    randomIndex = 0;			

         if(countryList.isEmpty())
			return true;
		else if(countryList.size()>1)
		   	randomIndex = Common.getRandomNumberInRange(0, countryList.size()-1);
     	System.out.println("fortify 1::randomIndex::"+randomIndex);
  	
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

	private void attackOperation(Country fromCountry, Country toCountry, Player attackerPlayer) {
		
		int attackerDiceCount = attackerPlayer.getMaximumAllowableDices(fromCountry, "Attacker");
		if (attackerDiceCount >0)
		  attackerDiceCount = Common.getRandomNumberInRange(0, attackerDiceCount);
		
		int defenderDiceCount = attackerPlayer.getMaximumAllowableDices(toCountry, "Defender");
		if (defenderDiceCount >0)
		defenderDiceCount = Common.getRandomNumberInRange(0, defenderDiceCount);
					
	    Player defenderPlayer = Game.getPlayerFromID(toCountry.getPlayerId());
	    attackerPlayer.setAttackedPlayer(defenderPlayer);
	    attackerPlayer.setFromCountry(fromCountry);
	    attackerPlayer.setToCountry(toCountry);
	    
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

				toCountry.decreaseArmyCount(1);

			} else {
				IOHelper.print("----> defender wins for dice " + (i + 1));
				Common.PhaseActions.add("----> defender wins for dice " + (i + 1));

				fromCountry.decreaseArmyCount(1);
			}

			if (fromCountry.getnoOfArmies() == 1) {
				IOHelper.print("----> Attacker not able to Attack ");
				break;
			} else if (toCountry.getnoOfArmies() == 0) {
				IOHelper.print("----> Defender lost all armies in " + (i + 1) + " dice roll");
				break;
			}

		}

		if (toCountry.getnoOfArmies() == 0) {
	             attackerPlayer.conquerCountry(defenderPlayer);	
		}
	}
}
