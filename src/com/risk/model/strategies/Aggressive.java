package com.risk.model.strategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.risk.helper.Common;
import com.risk.helper.IOHelper;
import com.risk.model.Country;
import com.risk.model.Game;
import com.risk.model.Player;

/**
 * An aggressive computer player strategy that focuses on attack (reinforces its
 * strongest country, then always attack with it until it cannot attack anymore,
 * then fortifies in order to maximize aggregation of forces in one country).
 *
 * @author binay
 * @version 1.0.0
 * @since 19-November-2018
 */
public class Aggressive implements PlayerStrategy {
	private String strategyName = "Aggressive";

	public String getStrategyName() {
		return strategyName;
	}

	@Override
	public boolean reinforce(Player player) {
		// TODO Auto-generated method stub
		ArrayList<Country> assignedCountryList = player.getAssignedCountryList();
		System.out.println("Reinforce assignedCountryList.size:"+assignedCountryList.size());
		if (assignedCountryList.size()==0)
		     return true;

		Country country = getStrongestCountry(assignedCountryList,0);						

		if (country != null) {
			IOHelper.print("Adding reinforcement army in " + country.getCountryName());
			int armies = player.getNoOfReinforcedArmies();
			player.setNoOfReinforcedArmies(0);
			country.increaseArmyCount(armies);
		}
		return true;
	}

	@Override
	public void attack(Player attackerPlayer) {
		
		ArrayList<Country> CountriesForAttack;
		//after conquering a country only 1 army is placed by default so new conquered country will not
		//come in this list
		CountriesForAttack = attackerPlayer.getCountriesObjectWithArmiesGreaterThanOne();
		if (CountriesForAttack.size()==0) {		
			IOHelper.print("No country has sufficient armies for Attack");
		}
		 
		ArrayList<Country> CountriesToAttack ;
		 
		while (CountriesForAttack.size()>0) { 
			Country fromCountry = getStrongestCountry(CountriesForAttack,1);	
			
			if(fromCountry == null) {
				IOHelper.print("Can not find attacker fountry");
				break;
			}
			
		    CountriesToAttack = attackerPlayer.getUnAssignedNeighbouringCountriesObject(fromCountry.getCountryName());

		    if (CountriesToAttack.size()==0) {  
		    	CountriesForAttack.remove(fromCountry);
		        System.out.println("Cannot attack from "+fromCountry.getCountryName());
				continue;
			}
		     
		    Country toCountry = getCountryToAttack(attackerPlayer,CountriesToAttack);
				
			if (toCountry == null) {
				CountriesToAttack.remove(toCountry);
				continue;
			}
			
			IOHelper.print(fromCountry.getCountryName()+" is attacking "+toCountry.getCountryName());
						
			while (toCountry.getPlayerId() != attackerPlayer.getPlayerId()) {
				attackOperation(fromCountry, toCountry, attackerPlayer);
				if (fromCountry.getnoOfArmies() == 1) {	
					CountriesForAttack.remove(fromCountry);
				    break;
				}			
			}
		}
	}

	@Override
	public boolean fortify(Player player) {
		// TODO Auto-generated method stub
		ArrayList<Country> assignedCountryList = player.getAssignedCountryList();

		if (assignedCountryList.size()==0)
		    return true;

		Country destinationCountry = getStrongestCountry(assignedCountryList,1);	
		
		if (destinationCountry != null) {
			Country fromCountry = getFromCountryFortification(player,destinationCountry);
			int armies = fromCountry.getnoOfArmies()-1;
			fromCountry.decreaseArmyCount(armies);
			destinationCountry.increaseArmyCount(armies);
		}

		return true;
	}

	private void attackOperation(Country fromCountry, Country toCountry, Player attackerPlayer) {
		int attackerDiceCount = attackerPlayer.getMaximumAllowableDices(fromCountry, "Attacker");
		int defenderDiceCount = attackerPlayer.getMaximumAllowableDices(toCountry, "Defender");
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

	private Country getStrongestCountry(ArrayList<Country> assignedCountryList,int thresholdArmyCount ) {
		Country country = null;
		int armiesCount = thresholdArmyCount;
		for (Country c : assignedCountryList) {
			if (c.getnoOfArmies() > armiesCount) {
				armiesCount = c.getnoOfArmies();
				country = c;
			}
		}
		return country;
	}
	
    private Country getCountryToAttack(Player attackerPlayer, ArrayList<Country> CountriesToAttack){   
    	Country toCountry = null;
		int armies = Integer.MAX_VALUE;
		for (Country neighbourCountry : CountriesToAttack) {
			if ( neighbourCountry.getnoOfArmies() < armies) {
				armies = neighbourCountry.getnoOfArmies();
				toCountry = neighbourCountry;
			}
		}

       return toCountry;
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
    
    private boolean countryVisited(Country country, HashMap<Country,Integer> visitedCountries,Player player)
    { if(country.getPlayerId() == player.getPlayerId()&&
			!visitedCountries.containsKey(country))
    	return true;
    else
    	return false;    	
    }
}
