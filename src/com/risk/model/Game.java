package com.risk.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Random;
import java.util.stream.Collectors;

import java.util.List;
import com.risk.helper.EnumColor;
import com.risk.helper.IOHelper;
import com.risk.helper.InitialPlayerSetup;
import com.risk.helper.PhaseEnum;
/**
 * Game Class
 * 
 * @author Binay
 * @version 1.0.0
 * @since 30-September-2018
 */
public class Game extends Observable {

	// Country and the army assigned to it
	private HashMap<Player, ArrayList<Country>> playerCountry = new HashMap<>();
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private int currentPlayerId;
	private PhaseEnum gamePhase;
	private Map map;
	private final int MINIMUM_REINFORCEMENT_PlALYERS = 3;

	/**
	 * This is a constructor of Game class which will initialize the Map
	 * 
	 * @param map
	 */
	public Game(Map map) {
		super();
		this.map = map;
		this.setGamePhase(gamePhase.Startup);
	}

	public int getCurrentPlayerId() {
		return currentPlayerId;
	}

	private void setCurrentPlayerId() {
		if (currentPlayerId == playerList.size() - 1)
			currentPlayerId = 0;
		else
			currentPlayerId++;
		System.out.println("current player ID:" + currentPlayerId);
	}

	public Player getCurrentPlayer() {
		Player currentPlayer = null;
		for (Player player : playerList) {
			if (player.getPlayerId() == currentPlayerId)
				currentPlayer = player;
		}
		return currentPlayer;
	}

	/**
	 * This function will randomly assign Countries to all players and assign
	 * one army to each country for a player
	 * 
	 */
	public void assignCountriesToPlayer() {

		//Assign countries based on number of players to all players
		for(int i=0;i<playerList.size();i++)
		{
			playerList.get(i).setNoOfUnassignedArmies(InitialPlayerSetup.getInitialArmyCount(playerList.size()));	
		}
		
		int countriesCount = map.getCountryList().size();
		int playerIndex = 0, playerCount = playerList.size();
		ArrayList<Integer> tempList = new ArrayList<>();
		// Here creating the list with indexes
		for (int i = 0; i < countriesCount; i++) {
			tempList.add(i);
		}

		// Shuffling the list for randomness
		Collections.shuffle(tempList, new Random());

		// assigning the shuffled countries from tempList to the players one by
		// one
		for (int i = 0; i < countriesCount; i++) {
			if (playerIndex == playerCount)
				playerIndex = 0;

			Country newCountry = map.getCountryList().get(tempList.get(i));
			// Adding the country to the player
			assignCountryToPlayer(playerList.get(playerIndex), newCountry);

			// Assigning one initial army to the country
			incresePlayerArmyInCountry(playerList.get(playerIndex), newCountry);

			playerIndex++;
		}
		setCurrentPlayerId();
		setChanged();
		notifyObservers(this);
	}

	/**
	 * This function will add the player to the game(playerList)
	 * 
	 * @param player
	 */
	public void addPlayer(Player player) {
		this.playerList.add(player);
	}

	/**
	 * 
	 * @return Boolean
	 */
	public Boolean isGameOn() {
		return false;
	}

	/**
	 * Assigns the newCountry to selected player
	 * 
	 * @param player
	 * @param newCountry
	 */
	public void assignCountryToPlayer(Player player, Country newCountry) {
		if (playerCountry.containsKey(player)) {
			ArrayList<Country> assignedCountries = playerCountry.get(player);
			assignedCountries.add(newCountry);
		} else {
			ArrayList<Country> assignedCountries = new ArrayList<>();
			assignedCountries.add(newCountry);
			playerCountry.put(player, assignedCountries);
		}
		newCountry.setCountryColor(player.getColor());
	}

	/**
	 * Increase one army for selected selected player in selected country
	 * 
	 * @param player
	 * @param country
	 */
	private void incresePlayerArmyInCountry(Player player, Country country) {
		player.decreaseUnassignedArmyCount();
		country.increaseArmyCount();
	}

	/**
	 * Unassign the country from selected player
	 * 
	 * @param player
	 * @param country
	 */
	public void unassignCountryFromPlayer(Player player, Country country) {
		if (playerCountry.containsKey(player)) {
			ArrayList<Country> assignedCountries = playerCountry.get(player);
			assignedCountries.remove(player);
		}
	}

	/*
	 * public ArrayList<CountryAdorner> getMapViewData() {
	 * ArrayList<CountryAdorner> list = new ArrayList<>();
	 * 
	 * for (java.util.Map.Entry<Player, ArrayList<Country>> e :
	 * playerCountry.entrySet()) { Player key = e.getKey(); ArrayList<Country>
	 * countries = e.getValue(); /* for(int i=0;i<countries.size();i++) {
	 * CountryAdorner _newItem = new CountryAdorner(countries.get(i));
	 * 
	 * _newItem.setPlayerColor(key.getColor()); list.add(_newItem); }
	 */

	/*
	 * } return list; }
	 */

	private void setNextPlayer() {
		if (currentPlayerId == playerList.size()-1)
			currentPlayerId = 0;
		else
			currentPlayerId++;
	}

	public ArrayList<Country> getPlayerCountries() {
		System.out.println("Hetal -- " + currentPlayerId);
		Player currentPlayer = playerList.get(currentPlayerId);
		return playerCountry.get(currentPlayer);
	}

	public ArrayList<Country> getNeighbouringCountriesForFortification(int countryId)
	{
		Country country = map.getCountryList().stream().filter(c -> c.getCountryId() == countryId).findAny()
				.orElse(null);
		Player currentPlayer = playerList.get(currentPlayerId);
		
		if(country == null || currentPlayer == null)
		{
			IOHelper.print("Country id or player id is not valid");
			return null;
		}
		
	    ArrayList<Country> neighbhbouringCountries = new ArrayList<>();
	    
	    for(Country pCounty : playerCountry.get(currentPlayer))
	    {
	    	Country matchedCountry = country.getNeighbours().stream().filter(c -> c.equals(pCounty))
	    							.findAny().orElse(null);
	    	if(matchedCountry != null)
	    	{
	    		neighbhbouringCountries.add(matchedCountry);
	    	}
	    }
	    return neighbhbouringCountries;
	}
	
	public boolean fortifyCountry(int fromCountryId, int toCountryId, int fortifyArmiesCount)
	{
		Player currentPlayer = playerList.get(currentPlayerId);
		
		Country fromCountry = playerCountry.get(currentPlayer).stream().filter(c -> c.getCountryId() == fromCountryId)
								.findAny().orElse(null);
		if(fromCountry == null)
		{
			IOHelper.print("From country doesn't belong to current player");
			return false;
		}
		
		Country toCountry = playerCountry.get(currentPlayer).stream().filter(c -> c.getCountryId() == toCountryId)
				.findAny().orElse(null);
		if(toCountry == null)
		{
			IOHelper.print("From country doesn't belong to current player");
			return false;
		}
		
		fromCountry.decreaseArmyCount(fortifyArmiesCount);
		toCountry.increaseArmyCount(fortifyArmiesCount);
		setNextPlayer();
		return true;
	}
	
	public boolean addArmyToCountry(int countryId) {
		System.out.println("Inside addArmyToCountry and value of gamePhase: "+gamePhase);
		if (this.gamePhase == PhaseEnum.Attack || this.gamePhase == PhaseEnum.Fortification) {
			IOHelper.print("Cannot assign army from player to country. Not valid phase");
			return false;
		}

		Player player = playerList.stream().filter(p -> currentPlayerId == p.getPlayerId()).findAny().orElse(null);
		if (player == null) {
			IOHelper.print("Player id " + currentPlayerId + " does not exist");
			return false;
		}

		if (player.getNoOfUnassignedArmies() == 0) {
			IOHelper.print("Player " + player.getName() + " doesn't have unassigned armies");
			return false;
		}

		for(int i=0;i<playerCountry.get(player).size();i++)
		{
			System.out.println(playerCountry.get(player).get(i).getCountryId() + "--" + playerCountry.get(player).get(i).getCountryName());
		}
		Country country = playerCountry.get(player).stream().filter(c -> c.getCountryId() == countryId).findAny()
				.orElse(null);
		if (country == null) {
			IOHelper.print("Country id " + player.getName() + "--"+ countryId + " does not exist");
			return false;
		}
		
		incresePlayerArmyInCountry(player, country);
		updatePhase();
		setNextPlayer();

		setChanged();
		notifyObservers(this);
		
		return true;
	}

	private void updatePhase() {
		// check if all player has unassigned armies as 0 then update phase
		long pendingPlayersCount = playerList.stream().filter(p -> p.getNoOfUnassignedArmies() > 0).count();

		if (pendingPlayersCount == 0) {
			// Check if in startup phase then update to reinforcement
			if (this.getGamePhase() == gamePhase.Startup) {
				this.setGamePhase(gamePhase.Reinforcement);
				currentPlayerId = 0;
				calculateReinforcement();
			} else if (this.getGamePhase() == gamePhase.Reinforcement) {
				// We don't need to implement attack for now
				this.setGamePhase(gamePhase.Attack);
				
			}
		}
	}

	public void doAttack() {
		// Nothing to do for Build 1
		this.setGamePhase(gamePhase.Fortification);
	}

	public void calculateReinforcement() {

		// count number of countries owned by player
		Player player = playerList.stream().filter(p -> currentPlayerId == p.getPlayerId()).findAny().orElse(null);

		// get reinforcement country count based on countries owned by player
		int countriesCount = (int) Math.floor(playerCountry.get(player).stream().count() / 3);

		// Check if player owns any of the continent
		if (playerCountry.containsKey(player)) {
			ArrayList<Country> assignedCountries = playerCountry.get(player);
			List<Integer> assignedCountryIds = assignedCountries.stream().map(c -> c.getCountryId())
					.collect(Collectors.toList());

			ArrayList<Continent> continents = map.getContinentList();
			for (Continent continent : continents) {
				List<Integer> continentCountryIds = continent.getCountryList().stream().map(c -> c.getCountryId())
						.collect(Collectors.toList());
				boolean hasPlayerAllCountries = continentCountryIds.containsAll(assignedCountryIds);
				countriesCount += continent.getControlValue();
			}
		}

		countriesCount = countriesCount < MINIMUM_REINFORCEMENT_PlALYERS ? MINIMUM_REINFORCEMENT_PlALYERS
				: countriesCount;
		player.setNoOfUnassignedArmies(countriesCount);
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public PhaseEnum getGamePhase() {
		return gamePhase;
	}

	private void setGamePhase(PhaseEnum gamePhase) {
		this.gamePhase = gamePhase;
	}
	
	public ArrayList<Player> getAllPlayers()
	{
		return playerList;
	}
}
