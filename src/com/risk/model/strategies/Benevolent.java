package com.risk.model.strategies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.risk.helper.Common;
import com.risk.helper.IOHelper;
import com.risk.model.Country;
import com.risk.model.Player;

/**
 * A benevolent computer player strategy that focuses on protecting its weak
 * countries (reinforces its weakest countries, never attacks, then fortifies in
 * order to move armies to weaker countries).
 *
 * @author binay
 * @version 1.0.0
 * @since 19-November-2018
 */
public class Benevolent implements PlayerStrategy, Serializable {
	private String strategyName = "Benevolent";

	public String getStrategyName() {
		return strategyName;
	}

	public boolean getIsBot() {
		return true;
	}
	
	@Override
	public boolean reinforce(Player player) {
		int mimumArmies = getMinimumArmies(player);
		List<Country> weakestCountries = player.getAssignedCountryList().stream()
				.filter(x -> x.getnoOfArmies() == mimumArmies).collect(Collectors.toList());
		if (weakestCountries != null) {
			IOHelper.print("Found " + weakestCountries.size() + " weakest counties. Now assigning "
					+ player.getNoOfReinforcedArmies() + " armies");
			int index = 0;
			while (player.getNoOfReinforcedArmies() > 0) {
				Country c = weakestCountries.get(index);
				int armies = player.getNoOfReinforcedArmies();
				player.decreaseReinforcementArmyCount();
				c.increaseArmyCount(1);
				IOHelper.print("Added reinforcement army in " + c.getCountryName() + "(" + c.getnoOfArmies() + ")");
				index++;
				if (index == weakestCountries.size())
					index = 0;
			}
		} else {
			IOHelper.print("Cannot find any weakest country");
		}
		return true;
	}

	@Override
	public void attack(Player attackerPlayer) {
		IOHelper.print("Player's strategy is benevolent so Attack skipped");
	}

	@Override
	public boolean fortify(Player player) {
		// TODO Auto-generated method stub
		
		ArrayList<Country> sortedList = player.getAssignedCountryList().stream()
											.sorted(Comparator.comparing(Country::getnoOfArmies).reversed())
											.collect(Collectors.toCollection(ArrayList::new));
		
		for(Country fromCountry : sortedList) {
			
			if(fromCountry == null)
				break;
			IOHelper.print("Found strongest country " + fromCountry.getCountryName() + ". Now finding weakest link...");
			ArrayList<Country> neighborCountries = player.getConnectedCountriesRecursively(fromCountry,
					(ArrayList<Country>) player.getAssignedCountryList().clone(), 
														new ArrayList<Country>());
			if(neighborCountries!= null && neighborCountries.size() > 0)
			{
				Country toCountry = getWeakestCountry(neighborCountries);
				if(fromCountry !=null && toCountry !=null && toCountry.getnoOfArmies() < fromCountry.getnoOfArmies() )
				{
					//fortify weakest country
					int armies = (fromCountry.getnoOfArmies() - toCountry.getnoOfArmies()) / 2;
					IOHelper.print("Benevolent player "+ player.getName() +" - fortification from " + fromCountry.getCountryName() + "("+ fromCountry.getnoOfArmies() +") to " + toCountry.getCountryName() + "("+ toCountry.getnoOfArmies()+") with " + armies + " armies");
					fromCountry.decreaseArmyCount(armies);
					toCountry.increaseArmyCount(armies);
					IOHelper.print("Finished fortification with destination country " +  toCountry.getCountryName() + " ("+ toCountry.getnoOfArmies() +")");
					break;
				}
			}
			IOHelper.print("Cannot find any neighbouring weaker country");
		}
		return true;
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

	private Country getWeakestCountry(ArrayList<Country> countries) {
		Country country = null;
		int armiesCount = Integer.MAX_VALUE;
		for (Country c : countries) {
			if (c.getnoOfArmies() < armiesCount) {
				armiesCount = c.getnoOfArmies();
				country = c;
			}
		}
		return country;
	}

	private int getMinimumArmies(Player player) {
		int returnVal = Integer.MAX_VALUE;
		ArrayList<Country> assignedCountryList = player.getAssignedCountryList();
		for (Country c : assignedCountryList) {
			if (c.getnoOfArmies() < returnVal)
				returnVal = c.getnoOfArmies();
		}
		return returnVal;
	}

	private Country getStrongestCounty(Player player) {
		Country country = null;
		ArrayList<Country> assignedCountryList = player.getAssignedCountryList();
		int armiesCount = 0;
		for (Country c : assignedCountryList) {
			if (c.getnoOfArmies() > armiesCount) {
				armiesCount = c.getnoOfArmies();
				country = c;
			}
		}
		return country;
	}

}
