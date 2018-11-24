package com.risk.model.strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.risk.helper.Common;
import com.risk.helper.IOHelper;
import com.risk.model.Country;
import com.risk.model.Player;

/**
 * A benevolent computer player strategy that focuses on protecting its weak
*countries (reinforces its weakest countries, never attacks, then fortifies in order to
*move armies to weaker countries).
*
* @author binay
* @version 1.0.0
* @since 19-November-2018
*/
public class Benevolent implements PlayerStrategy {
	private String strategyName = "Benevolent";
	
	public String getStrategyName() {
			return strategyName;
		}

	@Override
	public boolean reinforce(Player player) {
		// TODO Auto-generated method stub
       
		Country country = getWeakestCountry(player);
		IOHelper.print("Adding reinforcement army in " + country.getCountryName());
		int armies = player.getNoOfReinforcedArmies();
		player.setNoOfReinforcedArmies(0);
		country.increaseArmyCount(armies);
		return true;
}

	@Override
	public void attack(Player attackerPlayer) {
		// TODO Auto-generated method stub
		IOHelper.print("Player's strategy is benevolent so Attack skipped");

	}

	@Override
	public boolean fortify(Player player) {
		// TODO Auto-generated method stub
		Country toCountry = getWeakestCountry(player);
		ArrayList<Country> neighbourCountryList = toCountry.getNeighbourCountries();
		Country fromCountry = getFromCountryFortification(player,toCountry);
		if (toCountry != null)
		{   int armies = fromCountry.getnoOfArmies()-1;
		    fromCountry.decreaseArmyCount(armies);
		    toCountry.increaseArmyCount(armies);
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

    private boolean countryVisited(Country country, HashMap<Country,Integer> visitedCountries,Player player)
    { if(country.getPlayerId() == player.getPlayerId()&&
			!visitedCountries.containsKey(country))
    	return true;
    else
    	return false;    	
    }

    
	private Country getWeakestCountry(Player player) {
		Country country = null;
		ArrayList<Country> assignedCountryList = player.getAssignedCountryList();
		int armiesCount = Integer.MAX_VALUE;
		for (Country c : assignedCountryList) {
			if (c.getnoOfArmies() < armiesCount) {
				armiesCount = c.getnoOfArmies();
				country = c;
			}
		}
		return country;
	}


}
