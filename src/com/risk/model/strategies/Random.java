package com.risk.model.strategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

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
		// TODO: Change this logic to randomly reinforce different countries
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
		ArrayList<Country> countryList = attackerPlayer.getCountriesObjectWithArmiesGreaterThanOne();
		int randomIndex = 0;
		if(countryList.isEmpty())
			return;
		else if(countryList.size()>1)
			randomIndex = Common.getRandomNumberInRange(0, countryList.size()-1);
			
		Country fromCountry = countryList.get(randomIndex);
		ArrayList<Country> neighborCountries = attackerPlayer.getUnAssignedNeighbouringCountriesObject(fromCountry.getCountryName());
		
		if(neighborCountries.isEmpty())
			return;
		else if(neighborCountries.size()==1)
		   randomIndex = 0;			
		else
			randomIndex = Common.getRandomNumberInRange(0, neighborCountries.size()-1);
			
		Country toCountry = neighborCountries.get(randomIndex);
		
		attackOperation( fromCountry,  toCountry,  attackerPlayer);
		
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
		Country destinationCountry = getFromCountryFortification(player,sourceCountry);

		if (destinationCountry != null)
		{   int armies = sourceCountry.getnoOfArmies()-1;
			sourceCountry.decreaseArmyCount(armies);
			destinationCountry.increaseArmyCount(armies);
		}
		
		return true;
	}

	private Country getFromCountryFortification(Player player, Country sourceCountry){   
    	ArrayList<Country> neighbourCountryList = sourceCountry.getNeighbourCountries();
		HashMap<Country,Integer> visitedCountries = new HashMap<Country,Integer>();
		visitedCountries.put(sourceCountry, sourceCountry.getnoOfArmies());
		Country neighbourCountry;
		for (int i=0;i<neighbourCountryList.size();i++) {
			neighbourCountry = neighbourCountryList.get(i);
			if (countryVisited(neighbourCountry,visitedCountries,player)) {
				visitedCountries.put(neighbourCountry, neighbourCountry.getnoOfArmies());
				ArrayList<Country> neighbourCountryListTemp = neighbourCountry.getNeighbourCountries();
				for (int j=0;j<neighbourCountryListTemp.size();j++) {
					if (countryVisited(neighbourCountry,visitedCountries,player)) {
						visitedCountries.put(neighbourCountryListTemp.get(j), neighbourCountryListTemp.get(j).getnoOfArmies());					
					}
				}			
			}
		}
		
		Country toCountry = null;
		int armies = 0;
		Iterator it = visitedCountries.entrySet().iterator();
	    while (it.hasNext()) {
	        HashMap.Entry<Country,Integer> pair = (HashMap.Entry<Country,Integer>)it.next();
	        if (armies < pair.getValue()) {
	        	armies = pair.getValue();
	        	toCountry = pair.getKey();
	        }
	    }		
       return toCountry;
    }

	
	private void attackOperation(Country fromCountry, Country toCountry, Player attackerPlayer) {
		
		int attackerDiceCount = attackerPlayer.getMaximumAllowableDices(fromCountry, "Attacker");
		if (attackerDiceCount >0)
		  attackerDiceCount = Common.getRandomNumberInRange(1, attackerDiceCount);
		
		int defenderDiceCount = attackerPlayer.getMaximumAllowableDices(toCountry, "Defender");
		if (defenderDiceCount >0)
		defenderDiceCount = Common.getRandomNumberInRange(1, defenderDiceCount);
					
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

    private boolean countryVisited(Country country, HashMap<Country,Integer> visitedCountries,Player player)
    { if(country.getPlayerId() == player.getPlayerId()&&
			!visitedCountries.containsKey(country))
    	return true;
    else
    	return false;    	
    }



}
