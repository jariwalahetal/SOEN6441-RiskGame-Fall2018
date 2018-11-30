package com.risk.model.strategies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

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
public class Aggressive implements PlayerStrategy, Serializable {
	private String strategyName = "Aggressive";
	private Country attackingCountry;
	
	public String getStrategyName() {
		return strategyName;
	}

	public boolean getIsBot() {
		return true;
	}
	
	@Override
	public boolean reinforce(Player player) {
		ArrayList<Country> assignedCountryList = player.getAssignedCountryList();
		if (assignedCountryList.size()==0)
		     return true;

		attackingCountry = getStrongestCountry(assignedCountryList,0);						

		if (attackingCountry != null) {
			IOHelper.print("Adding reinforcement army in " + attackingCountry.getCountryName() + "("+ attackingCountry.getnoOfArmies()+")");
			int armies = player.getNoOfReinforcedArmies();
			player.setNoOfReinforcedArmies(0);
			attackingCountry.increaseArmyCount(armies);
			IOHelper.print("Added reinforcement army in " + attackingCountry.getCountryName() + "("+ attackingCountry.getnoOfArmies()+")");
		}
		else {
			IOHelper.print("Cannot find any attacking country");
		}
		return true;
	}

	@Override
	public void attack(Player attackerPlayer) {		
		if(attackingCountry == null)
		{   IOHelper.print("No attacking country found so skipping attack");
			return;
		}
		
		IOHelper.print("Aggressive player "+ attackerPlayer.getName() +" - attack - attacking from " + attackingCountry.getCountryName() + "("+  attackingCountry.getnoOfArmies()+")");

		ArrayList<Country> CountriesToAttack = attackerPlayer.getUnAssignedNeighbouringCountriesObject
											(attackingCountry.getCountryName());
		if(CountriesToAttack==null || CountriesToAttack.size() == 0)
		{
			IOHelper.print("Cannot find any counties to attack from this country");
			return;
		}
		
		IOHelper.print("Found " + CountriesToAttack.size() + " country for attacking...Doing attack now..");
		while (CountriesToAttack.size()>0) { 
			
		    Country toCountry = getCountryToAttack(attackerPlayer,CountriesToAttack);
				
			if (toCountry == null) {
				CountriesToAttack.remove(toCountry);
				continue;
			}
			
			
						
			//Perform attack untill country is acquired or the attacking country is lost
			while (toCountry.getPlayer().getPlayerId() != attackerPlayer.getPlayerId()) {
				if(attackingCountry.getnoOfArmies() == 1)
					break;
				attackOperation(attackingCountry, toCountry, attackerPlayer);
			}
			
			if(attackingCountry.getnoOfArmies() == 1)
			{
				//Cannot perform attack now
				break;
			}
			CountriesToAttack.remove(toCountry);
		}
		
		//Perform move operation if the attacking country cannot attack any more
		if(attackingCountry != null && attackingCountry.getnoOfArmies() >1 &&  CountriesToAttack.size() == 0) {
			//if attacking country wins in attack phase then all neigbours are acquired
			//so fortify strongest neighbour of attacking country
			IOHelper.print(attackingCountry.getCountryName() + "("+ attackingCountry.getnoOfArmies()+") cannot attack further so moving country to neigbours");
			Country fromCountry = attackingCountry;
			ArrayList<Country> neighborCountries = attackerPlayer.getConnectedCountriesRecursively(fromCountry,
					(ArrayList<Country>) attackerPlayer.getAssignedCountryList().clone(), 
														new ArrayList<Country>());
			
			neighborCountries.removeIf(x -> x.getCountryName().equals(fromCountry.getCountryName()));
			Country destinationCountry = getStrongestCountry(neighborCountries, 0);
			if(fromCountry != null && destinationCountry != null) 
			{
				int armies = fromCountry.getnoOfArmies()-1;
				IOHelper.print("Aggressive player "+ attackerPlayer.getName() +" - can not attack further with " + fromCountry.getCountryName() + "("+ fromCountry.getnoOfArmies() +") so moving armies to " + destinationCountry.getCountryName() + "("+ destinationCountry.getnoOfArmies()+") with " + armies + " armies");
				fromCountry.decreaseArmyCount(armies);
				destinationCountry.increaseArmyCount(armies);
				IOHelper.print("Finished move armies after attack with destination country " +  destinationCountry.getCountryName() + " ("+ destinationCountry.getnoOfArmies() +")");
			}
			
		}
		IOHelper.print("Finishing attack...");
	}

	@Override
	public boolean fortify(Player player) {
		
		Country fromCountry;
		Country destinationCountry;
		
		ArrayList<Country> assignedCountryList = player.getAssignedCountryList();
		fromCountry = getStrongestCountry(assignedCountryList,0);	
		
		ArrayList<Country> neighborCountries = player.getConnectedCountriesRecursively(fromCountry,
				(ArrayList<Country>) player.getAssignedCountryList().clone(), 
				new ArrayList<Country>());
		
		neighborCountries.removeIf(x -> x.getCountryName().equals(fromCountry.getCountryName()));
		destinationCountry = getStrongestCountry(neighborCountries, 0);
		
		if(fromCountry != null && destinationCountry != null) 
		{   int armies = fromCountry.getnoOfArmies()-1;
			IOHelper.print("Aggressive player "+ player.getName() +" - fortification from " + fromCountry.getCountryName() + "("+ fromCountry.getnoOfArmies() +") to " + destinationCountry.getCountryName() + "("+ destinationCountry.getnoOfArmies()+") with " + armies + " armies");
			fromCountry.decreaseArmyCount(armies);
			destinationCountry.increaseArmyCount(armies);
			IOHelper.print("Finished fortification with destination country " +  destinationCountry.getCountryName() + " ("+ destinationCountry.getnoOfArmies() +")");
			return true;
		}
		else {
			IOHelper.print("Aggressive player " + player.getName() + " cannot find country for fortification");
			return false;
		}
		
	}

	private void attackOperation(Country fromCountry, Country toCountry, Player attackerPlayer) {
		
		IOHelper.print(attackingCountry.getCountryName()+"("+ attackingCountry.getnoOfArmies() + ") is attacking to "+ toCountry.getCountryName() + "("+ toCountry.getnoOfArmies()+")");
		
		Player defenderPlayer = toCountry.getPlayer();
		
		int attackerDiceCount = attackerPlayer.getMaximumAllowableDices(fromCountry, "Attacker");
		int defenderDiceCount = defenderPlayer.getMaximumAllowableDices(toCountry, "Defender"); 
		defenderDiceCount = Common.getRandomNumberInRange(1, defenderDiceCount);

		
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

			if (attackerDice > defencerDice) {
				IOHelper.print("----> attacker wins for dice " + (i + 1));
				Common.PhaseActions.add("----> attacker wins for dice " + (i + 1));

				toCountry.decreaseArmyCount(1);

			} else {
				IOHelper.print("----> defender wins for dice " + (i + 1));

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

	
}
