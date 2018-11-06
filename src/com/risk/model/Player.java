package com.risk.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.risk.helper.Common;
import com.risk.helper.EnumColor;
import com.risk.helper.IOHelper;
import com.risk.helper.InitialPlayerSetup;

/**
 * Player Class
 * 
 * @author sadgi
 * @version 1.0.0
 * @since 27-September-2018
 */
public class Player {
	private int playerId;
	private String name;
	private EnumColor color;
	private int noOfUnassignedArmies;
	private int noOfReinforcedArmies;
	private ArrayList<Country> assignedCountryList = new ArrayList<Country>();
	private final int MINIMUM_REINFORCEMENT_PLAYERS = 3;

	/**
	 * This is a constructor of Player Class which sets playerId, name, and color.
	 * 
	 * @param playerId,id
	 *            of the player
	 * @param name,name
	 *            of the player
	 */
	public Player(int playerId, String name) {
		super();
		this.playerId = playerId;
		this.name = name;
		this.color = InitialPlayerSetup.getPlayerColor(playerId);
	}

	/**
	 * This method returns the Countries assigned to the particular Player
	 * 
	 * @return assignedCountryList
	 */
	public ArrayList<Country> getAssignedCountryList() {
		return assignedCountryList;
	}

	/**
	 * This method returns the number of unassigned army unit
	 * 
	 * @return noOfUnassignedArmies int
	 */
	public int getNoOfUnassignedArmies() {
		return noOfUnassignedArmies;
	}

	/**
	 * This method set the number of unassigned army unit
	 * 
	 * @param noOfUnassignedArmies,
	 *            number of unassigned armies
	 */
	public void setNoOfUnassignedArmies(int noOfUnassignedArmies) {
		this.noOfUnassignedArmies = noOfUnassignedArmies;
	}

	/**
	 * This method returns the number of reinforcement army units
	 * 
	 * @return noOfReinforcedArmies int
	 */
	public int getNoOfReinforcedArmies() {
		return noOfReinforcedArmies;
	}

	/**
	 * This method set the number of reinforcement army units
	 * 
	 * @param noOfReinforcedArmies
	 *            int
	 */
	public void setNoOfReinforcedArmies(int noOfReinforcedArmies) {
		this.noOfReinforcedArmies = noOfReinforcedArmies;
	}

	/**
	 * This method return the id of the player
	 * 
	 * @return playerId int
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * This method return the name of the player
	 * 
	 * @return name String
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method return color of the EnumColor class
	 * 
	 * @return color EnumColor
	 */
	public EnumColor getColor() {
		return color;
	}

	/**
	 * This method decreases unassigned army count
	 */
	public void decreaseUnassignedArmyCount() {
		if (noOfUnassignedArmies > 0)
			noOfUnassignedArmies--;
	}

	/**
	 * This method increases unassigned army count
	 */
	public void increaseUnassignedArmyCount() {
		noOfUnassignedArmies++;
	}

	/**
	 * This method decreases reinforcement link
	 * 
	 */
	public void decreaseReinforcementArmyCount() {
		if (noOfReinforcedArmies > 0)
			noOfReinforcedArmies--;
	}

	/**
	 * Assigns the current coutry to player
	 * @param newCountry
	 */
	public void assignCountryToPlayer(Country newCountry) {
		assignedCountryList.add(newCountry);
		newCountry.setCountryColor(this.getColor());
		newCountry.setPlayerId(this.getPlayerId());
	}

	/**
	 * UnAssigns the current coutry to player
	 * @param newCountry
	 */
	public void unAssignCountryToPlayer(Country newCountry) {
		assignedCountryList.remove(newCountry);
	}
	
	/**
	 * Add army to the country for startup phase
	 * 
	 * @param countryName,name
	 *            of thr country
	 * @return false, if phase is not valid otherwise return true
	 */
	public boolean addArmyToCountryForStartup(String countryName) {

		if (getNoOfUnassignedArmies() == 0) {
			IOHelper.print("Player " + getName() + " doesn't have unassigned armies!");
			return true;
		}

		Country country = assignedCountryList.stream().filter(c -> c.getCountryName().equalsIgnoreCase(countryName))
				.findAny().orElse(null);
		if (country == null) {
			IOHelper.print("Country name -  " + countryName + " does not exist!");
			return false;
		}

		decreaseUnassignedArmyCount();
		country.increaseArmyCount(1);
		return true;
	}

	/**
	 * Method to perform fortification phase
	 * 
	 * @param sourceCountryName,
	 *            name of the source country of player
	 * @param destinationCountryName,
	 *            name of the destination country of the player
	 * @param noOfArmies,
	 *            number of armies to be moved
	 * @return true if no army need to move and false if source and destination
	 *         countries are null
	 */
	public boolean fortificationPhase(String sourceCountryName, String destinationCountryName, int noOfArmies) {

		Country sourceCountry = assignedCountryList.stream()
				.filter(c -> c.getCountryName().equalsIgnoreCase(sourceCountryName)).findAny().orElse(null);
		Country destinationCountry = assignedCountryList.stream()
				.filter(c -> c.getCountryName().equalsIgnoreCase(destinationCountryName)).findAny().orElse(null);

		if (sourceCountry == null || destinationCountry == null) {
			IOHelper.print("Source or destination country is invalid!");
			return false;
		}

		if (noOfArmies == 0) {
			IOHelper.print("No armies to move");
			return true;
		}
		sourceCountry.decreaseArmyCount(noOfArmies);
		destinationCountry.increaseArmyCount(noOfArmies);
		return true;

	}

	/**
	 * Method to set up reinforcement phase
	 */
	public void reinforcementPhaseSetup(ArrayList<Continent> continents) {

		// get reinforcement country count based on countries owned by player
		int countriesCount = (int) Math.floor(assignedCountryList.size() / 3);

		List<Integer> assignedCountryIds = assignedCountryList.stream().map(c -> c.getCountryId())
				.collect(Collectors.toList());

		for (Continent continent : continents) {
			List<Integer> continentCountryIds = continent.getCountryList().stream().map(c -> c.getCountryId())
					.collect(Collectors.toList());
			boolean hasPlayerAllCountries = assignedCountryIds.containsAll(continentCountryIds);
			if (hasPlayerAllCountries)
				countriesCount += continent.getControlValue();
		}

		countriesCount = countriesCount < MINIMUM_REINFORCEMENT_PLAYERS ? MINIMUM_REINFORCEMENT_PLAYERS
				: countriesCount;
		setNoOfReinforcedArmies(countriesCount);
	}

	/**
	 * Add army to the country for reinforcement phase
	 * 
	 * @param countryName,name
	 *            of the country
	 * @return false, if phase is not valid otherwise return true
	 */
	public boolean addArmyToCountryForReinforcement(String countryName) {

		if (getNoOfReinforcedArmies() == 0) {
			IOHelper.print("Player " + getName() + " doesn't have unassigned armies!");
			return false;
		}

		Country country = assignedCountryList.stream().filter(c -> c.getCountryName().equalsIgnoreCase(countryName))
				.findAny().orElse(null);
		if (country == null) {
			IOHelper.print("Country name - " + countryName + " does not exist!");
			return false;
		}

		decreaseReinforcementArmyCount();
		country.increaseArmyCount(1);

		return true;
	}

	/**
	 * Method to get armies assigned to the country
	 * 
	 * @param sourceCountryName,name
	 *            of the source country of player
	 * @return noOfArmies, number of armies
	 */
	public int getArmiesAssignedToCountry(String sourceCountryName) {
		int noOfArmies = 0;

		for (Country country : this.assignedCountryList) {
			if (country.getCountryName().equals(sourceCountryName)) {
				noOfArmies = country.getnoOfArmies();
			}
		}
		return noOfArmies;
	}


	/**
	 * Method to get neighbouring countries of a given country
	 * 
	 * @param sourceCountryName,
	 *            name of the source country of player
	 * @return ArrayList , returning array list of countries.
	 */
	public ArrayList<String> getNeighbouringCountries(String sourceCountryName,ArrayList<String> assignedCountriesName) {
		ArrayList<String> neighborCountriesName = null;
		for (Country country : assignedCountryList) {
			String countryName = country.getCountryName();
			assignedCountriesName.add(countryName);
			if (country.getCountryName().equals(sourceCountryName)) {
				neighborCountriesName = country.getNeighboursString();
			}
		}
		return neighborCountriesName;
	}

	
	
	/**
	 * Method to get neighbouring countries of a given country
	 * 
	 * @param sourceCountryName,
	 *            name of the source country of player
	 * @return ArrayList , returning array list of countries.
	 */
	public ArrayList<String> getAssignedNeighbouringCountries(String sourceCountryName) {
		ArrayList<String> assignedCountriesName =  new ArrayList<String>();
		ArrayList<String> neighborCountriesName = this.getNeighbouringCountries(sourceCountryName, assignedCountriesName);
		
		Iterator<String> it = neighborCountriesName.iterator();
		while (it.hasNext()) {
			String country = it.next();
			if (!assignedCountriesName.contains(country)) {
				it.remove();
			}
		}
		return neighborCountriesName;
	}

	/**
	 * Method to get neighbouring countries of a given country
	 * 
	 * @param sourceCountryName,
	 *            name of the source country of player
	 * @return ArrayList , returning array list of countries.
	 */
	public ArrayList<String> getUnAssignedNeighbouringCountries(String sourceCountryName) {
		ArrayList<String> assignedCountriesName =  new ArrayList<String>();
		ArrayList<String> neighborCountriesName = this.getNeighbouringCountries(sourceCountryName, assignedCountriesName);
		
		Iterator<String> it = neighborCountriesName.iterator();
		while (it.hasNext()) {
			String country = it.next();
			if (assignedCountriesName.contains(country)) {
				it.remove();
			}
		}
		return neighborCountriesName;
	}	
	
	/**
	 * This method will process attack on given player 
	 * @param defenderPlayer Player
	 * @param attackingCountry Attacking country
	 * @param defendingCuntry Defending country
	 * @param attackingDices attacking dices
	 * @param denfendingDices defending dices
	 * @return true if suceessful
	 */
	public boolean ProcessAttack(Player defenderPlayer, Country attackingCountry,Country defendingCuntry,
								ArrayList<Integer> attackingDices, ArrayList<Integer> denfendingDices)
	{
		IOHelper.print("Attacker's dices -- " + attackingDices);
		Common.PhaseActions.add("Attacker's dices -- " + attackingDices);
		
		IOHelper.print("Defender's dices -- " + denfendingDices);
		Common.PhaseActions.add("Defender's dices -- " + denfendingDices);
		
		Collections.sort(attackingDices, Collections.reverseOrder());
		Collections.sort(denfendingDices, Collections.reverseOrder());
		
		int totalComparisions = attackingDices.size() < denfendingDices.size() ? attackingDices.size() : denfendingDices.size();
		
		for(int i=0;i<totalComparisions;i++) {
			
			int attackerDice = attackingDices.get(i);
			int defencerDice = denfendingDices.get(i);
			
			IOHelper.print("Attacker dice - " + attackerDice + "  to Defender dice - " + defencerDice);
			Common.PhaseActions.add("Attacker dice - " + attackerDice + "  to Defender dice - " + defencerDice);
			
			if(attackerDice > defencerDice) {
				IOHelper.print("----> attacker wins for dice " + (i+1));
				Common.PhaseActions.add("----> attacker wins for dice " + (i+1));
				
				
				//Decrease one army from defender by one
				defendingCuntry.decreaseArmyCount(1);
				
			}
			else {
				IOHelper.print("----> defender wins for dice " + (i+1));
				Common.PhaseActions.add("----> defender wins for dice " + (i+1));
				
				//Decrese one amy from attacker
				attackingCountry.decreaseArmyCount(1);
			}
			
		}
		
		//Check if defending armies are 0 then acquire the country
		if(defendingCuntry.getnoOfArmies() == 0)
		{
			defendingCuntry.setPlayerId(playerId);
			defenderPlayer.unAssignCountryToPlayer(defendingCuntry);
			this.assignCountryToPlayer(defendingCuntry);
			defendingCuntry.setPlayerId(playerId);
			attackingCountry.decreaseArmyCount(1);
			defendingCuntry.increaseArmyCount(1);
		}
		return true;
	}
	
	public boolean MoveArmyAfterAttack(Country sourceCountry, Country destinationCountry, int armiesCount) {
		
		if (sourceCountry == null || destinationCountry == null) {
			IOHelper.print("Source or destination country is invalid!");
			return false;
		}
		
		if(armiesCount >= sourceCountry.getnoOfArmies()) {
			IOHelper.print("Cannot move " + armiesCount + " armies from " + sourceCountry.getnoOfArmies() + " armies");
			return false;
		}
		sourceCountry.decreaseArmyCount(armiesCount);
		destinationCountry.increaseArmyCount(armiesCount);
		return true;
	}
}
