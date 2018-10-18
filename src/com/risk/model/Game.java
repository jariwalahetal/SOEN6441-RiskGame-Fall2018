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
	private final int MINIMUM_REINFORCEMENT_PlAYERS = 3;

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
	/**
	 * This function returns the current player id.
	 * 
	 * @return currentPlayerId The current player Id.
	 */
	public int getCurrentPlayerId() {
		return currentPlayerId;
	}
	/**
	 * This function switches turn to the next player
	 */
	private void setNextPlayerTurn() {
			currentPlayerId++;

		if (currentPlayerId == playerList.size())
			currentPlayerId = 0;
		System.out.println("current player ID:" + currentPlayerId);
	}
	/**
	 * This function returns the current player object
	 * 
	 * @return currentPlayer The current player object
	 */
			
	public Player getCurrentPlayer() {

		Player currentPlayer = playerList.get(currentPlayerId);
		return currentPlayer;
	}

	/**
	 * This function will randomly assign Countries to all players and assign
	 * one army to each country for a player
	 * 
	 */
	public void startUpPhase() {

		// Assign countries based on number of players to all players
		for (int i = 0; i < playerList.size(); i++) {
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

			assignCountryToPlayer(playerList.get(playerIndex), newCountry);
			assignFromUnassigned(playerList.get(playerIndex), newCountry);
			playerIndex++;
		}
		notifyObserverslocal(this);
	}

	/**
	 * Assigns the newCountry to selected player
	 * 
	 * @param player
	 * @param newCountry
	 */
	public void assignCountryToPlayer(Player player, Country newCountry) {
		if (playerCountry.containsKey(player)) {
			playerCountry.get(player).add(newCountry);
		} else {
			ArrayList<Country> assignedCountries = new ArrayList<>();
			assignedCountries.add(newCountry);
			playerCountry.put(player, assignedCountries);
		}
		newCountry.setCountryColor(player.getColor());
		newCountry.setPlayerId(player.getPlayerId());
	}

	/**
	 * Increase one army for selected selected player in selected country from unassigned armies
	 * 
	 * @param player
	 * @param country
	 */
	private void assignFromUnassigned(Player player, Country country) {
		player.decreaseUnassignedArmyCount();
		country.increaseArmyCount();
	}
	
	/**
	 * Increase one army for selected selected player in selected country from reinforcement armies
	 * 
	 * @param player
	 * @param country
	 */
	private void assignFromReinforcement(Player player, Country country) {
		player.decreaseReinforcementArmyCount();
		country.increaseArmyCount();
	}

	/**
	 * Decrease army of the player in the country
	 * 
	 * @param player
	 * @param country
	 */
	private void decreasePlayerArmyInCountry(Player player, Country country) {
		player.increaseUnassignedArmyCount();
		country.decreseArmyCount();
	}

	/**
	 * Add army to the country for startup phase
	 * 
	 * @param countryName
	 */
	public boolean addArmyToCountryForStartup(String countryName) {
		if (this.gamePhase != PhaseEnum.Startup) {
			IOHelper.print("Cannot assign army from player to country. Not valid phase");
			return false;
		}

		Player player = this.getCurrentPlayer();

		if (player == null) {
			IOHelper.print("Player id " + currentPlayerId + " does not exist");
			return false;
		}

		if (player.getNoOfUnassignedArmies() == 0) {
			IOHelper.print("Player " + player.getName() + " doesn't have unassigned armies");
			return true;
		}

		Country country = playerCountry.get(player).stream()
				.filter(c -> c.getCountryName().equalsIgnoreCase(countryName)).findAny().orElse(null);
		if (country == null) {
			IOHelper.print("Country name -  " + countryName + " does not exist");
			return false;
		}

		assignFromUnassigned(player, country);
		return true;
	}
	
	/**
	 * Add army to the country for reinforcement phase
	 * 
	 * @param countryName
	 */
	public boolean addArmyToCountryForReinforcement(String countryName) {
		if (this.gamePhase != PhaseEnum.Reinforcement) {
			IOHelper.print("Cannot assign army from player to country. Not valid phase");
			return false;
		}

		Player player = this.getCurrentPlayer();

		if (player == null) {
			IOHelper.print("Player id " + currentPlayerId + " does not exist");
			return false;
		}

		if (player.getNoOfReinforcedArmies() == 0) {
			IOHelper.print("Player " + player.getName() + " doesn't have unassigned armies");
			return false;
		}

		Country country = playerCountry.get(player).stream()
				.filter(c -> c.getCountryName().equalsIgnoreCase(countryName)).findAny().orElse(null);
		if (country == null) {
			IOHelper.print("Country name - " + countryName + " does not exist");
			return false;
		}

		assignFromReinforcement(player, country);
		return true;
	}
	
	/**
	 * Add army to the country for fortification phase
	 * 
	 * @param countryName
	 */
	public void addArmyToCountry(String countryName)
	{
		if(gamePhase == PhaseEnum.Attack || gamePhase == PhaseEnum.Fortification)
		{
			IOHelper.print("Cannot add army in attack or fortification phase");
			return;
		}
		if(gamePhase == PhaseEnum.Startup) 
		{
			boolean isProcessed = addArmyToCountryForStartup(countryName);
			
			if(isProcessed)
			{
				setNextPlayerTurn();
			}
		}
		else if(gamePhase == PhaseEnum.Reinforcement)
		{
			addArmyToCountryForReinforcement(countryName);
		}
		updatePhase();

		notifyObserverslocal(this);
		// return true;
	}

	/**
	 * Method to update the phase of the game
	 * 
	 */
	private void updatePhase() {

		if(this.getGamePhase() == gamePhase.Startup)
		{
			//Check all players have assigned armies to country or not
			long pendingPlayersCount = playerList.stream().filter(p -> p.getNoOfUnassignedArmies() > 0).count();
			
			if (pendingPlayersCount == 0) {
				this.setGamePhase(gamePhase.Reinforcement);
				currentPlayerId = 0;
				reinforcementPhaseSetup();
			}
		}
		else if(this.getGamePhase() == gamePhase.Reinforcement)
		{
			//Check the current player reinforcement armies
			if(getCurrentPlayer().getNoOfReinforcedArmies() == 0)
			{
				// We don't need to implement attack for now
				this.setGamePhase(gamePhase.Attack);
			}
			
		}
		else if(this.getGamePhase() == gamePhase.Fortification)
		{
			this.setGamePhase(gamePhase.Reinforcement);
		}
	}

	/**
	 * Method to set up reinforcement phase
	 */
	public void reinforcementPhaseSetup() {

		// count number of countries owned by player
		Player player = getCurrentPlayer();

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
				boolean hasPlayerAllCountries = assignedCountryIds.containsAll(continentCountryIds);
				if (hasPlayerAllCountries)
					countriesCount += continent.getControlValue();
			}
		}

		countriesCount = countriesCount < MINIMUM_REINFORCEMENT_PlAYERS ? MINIMUM_REINFORCEMENT_PlAYERS
				: countriesCount;
		System.out.println("countriesCount:" + countriesCount);
		player.setNoOfReinforcedArmies(countriesCount);
	}

	/**
	 * Method for performing attack phase 
	 */
	public void attackPhase() {
		setGamePhase(gamePhase.Fortification);
		notifyObserverslocal(this);
	}

	/**
	 * Method to perform fortification phase
	 * 
	 * @param sourceCountryName
	 * @param destinationCountryName
	 * @param noOfArmies
	 */
	public void fortificationPhase(String sourceCountryName, String destinationCountryName, int noOfArmies) {

		Player player = getCurrentPlayer();
		Country sourceCountry = playerCountry.get(player).stream()
				.filter(c -> c.getCountryName().equalsIgnoreCase(sourceCountryName)).findAny().orElse(null);
		Country destinationCountry = playerCountry.get(player).stream()
				.filter(c -> c.getCountryName().equalsIgnoreCase(destinationCountryName)).findAny().orElse(null);

		if(sourceCountry == null || destinationCountry == null)
		{
			IOHelper.print("Source or destination country is invalid");
			return;
		}
		
		if(noOfArmies == 0)
		{
			IOHelper.print("No armies to move");
		}
		sourceCountry.decreaseArmyCount(noOfArmies);
		destinationCountry.increaseArmyCount(noOfArmies);
		this.setNextPlayerTurn();
		setGamePhase(gamePhase.Reinforcement);
		reinforcementPhaseSetup();
		notifyObserverslocal(this);

	}

	/**
	 * Method to find the next player to perform reinforcement
	 */
	public void setNextPlayerReinforcement() {
		if (gamePhase != gamePhase.Fortification) {
			IOHelper.print("Cannot set next player in reinforcement");
		}
		setNextPlayerTurn();
		updatePhase();
		reinforcementPhaseSetup();
	}

	/**
	 * This function will add the player to the game(playerList)
	 * 
	 * @param player
	 */
	public void addPlayer(Player player) {
		this.playerList.add(player.getPlayerId(), player);
	}

	/**
	 * Method to get countries corresponding to players
	 * 
	 * @return ArrayList , returning arraylist of countries.
	 */
	public ArrayList<Country> getPlayerCountries() {
		Player currentPlayer = playerList.get(currentPlayerId);
		return playerCountry.get(currentPlayer);
	}

	/**
	 * Method to get neighboring countries of a given country
	 * 
	 * @param sourceCountryName
	 * @return ArrayList , returning array list of countries.
	 */
	public ArrayList<String> getNeighbouringCountries(String sourceCountryName) {
	
	   Player currentPlayer = this.getCurrentPlayer();
       ArrayList<String> neighborCountriesName = null;
       ArrayList<String> countriesAssignedToPlayer = new ArrayList<String>() ; 
      	   
      for(Country country: playerCountry.get(currentPlayer))
       {  String countryName = country.getCountryName();
    	  countriesAssignedToPlayer.add(countryName);
    	  if(country.getCountryName().equals(sourceCountryName))
    	     { neighborCountriesName = country.getNeighboursString();
    	     }            
       }
             
      Iterator<String> it = neighborCountriesName.iterator();
      while(it.hasNext()) {  
    	  String country = it.next();
    	    if(!countriesAssignedToPlayer.contains(country))
    	    {    it.remove();
    	    }
    	}

      return neighborCountriesName;
  	}
	
	/**
	 * Method to get armies assigned to the country
	 * 
	 * @param sourceCountryName int
	 * @return noOfArmies
	 */
	public int getArmiesAssignedToCountry(String sourceCountryName)
	{
		 Player currentPlayer = this.getCurrentPlayer();
	     int noOfArmies = 0;
	     
	      for(Country country: playerCountry.get(currentPlayer))
	       {  if(country.getCountryName().equals(sourceCountryName))
	    	     { noOfArmies = country.getnoOfArmies();
	    	     }            
	       }
	        
		return noOfArmies;
		
	}
	
	/**
	 * Method used to get map
	 * 
	 * @return map
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * method used to set map
	 * 
	 * @param map
	 */
	public void setMap(Map map) {
		this.map = map;
	}

	/**
	 * Method to get enum for game phase
	 * 
	 * @return gamePhase
	 */
	public PhaseEnum getGamePhase() {
		return gamePhase;
	}

	/**
	 * Method used to set phase of the game
	 * 
	 * @param gamePhase
	 */
	private void setGamePhase(PhaseEnum gamePhase) {
		this.gamePhase = gamePhase;
	}

	/**
	 * Method used to notify observer
	 * 
	 * @param game
	 */
	private void notifyObserverslocal(Game game) {
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Method use to get  arraylist of players
	 * 
	 * @return playerList
	 */
	public ArrayList<Player> getAllPlayers() {
		return playerList;
	}
}
