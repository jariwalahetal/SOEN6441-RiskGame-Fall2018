package com.risk.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import com.risk.helper.IOHelper;
import com.risk.helper.InitialPlayerSetup;
import com.risk.viewmodel.CountryAdorner;
import com.risk.viewmodel.PlayerAdorner;

/**
 * Game Class
 * 
 * @author Binay
 * @version 1.0.0
 * @since 30-September-2018
 */
public class Game {

	// Country and the army assigned to it
	private HashMap<Player, ArrayList<Country> > playerCountry = new HashMap<>();
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private static int currentPlayerId;
	private Map map;

	/**
	 * This is a constructor of Game class which will initialize the Map
	 * 
	 * @param map
	 */
	public Game(Map map) {
		super();
		this.map = map;
	}

	/**
	 * This function will randomly assign Countries to all players and assign one army to each country for a player
	 * 
	 */
	public void assignCountriesToPlayer() {
		
		int countriesCount = map.getCountryList().size();
		int playerIndex = 0, playerCount = playerList.size();
		ArrayList<Integer> tempList = new ArrayList<>();
		//Here creating the list with indexes
		for(int i=0;i<countriesCount ;i++)
		{
			tempList.add(i);
		}
		
		//Shuffling the list for randomness
		Collections.shuffle(tempList,new Random());
		
		//assigning the shuffled countries from tempList to the players one by one
		for(int i=0;i<countriesCount;i++)
		{
			if(playerIndex == playerCount)
				playerIndex = 0;

			Country newCountry = map.getCountryList().get(tempList.get(i));
			//Adding the country to the player
			assignCountryToPlayer(playerList.get(playerIndex), newCountry);
			
			//Assigning one initial army to the country
			incresePlayerArmyInCountry(playerList.get(playerIndex), newCountry);
			
			playerIndex++;		
		}
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
	public void assignCountryToPlayer(Player player, Country newCountry)
	{
		if (playerCountry.containsKey(player))
		{
			ArrayList<Country> assignedCountries = playerCountry.get(player);
			assignedCountries.add(newCountry);
		}
		else
		{
			ArrayList<Country> assignedCountries = new ArrayList<>();
			assignedCountries.add(newCountry);
			playerCountry.put(player, assignedCountries);
		}
	}

	/**
	 * Increase one army for selected selected player in selected country
	 * 
	 * @param player
	 * @param country
	 */
	public void incresePlayerArmyInCountry(Player player, Country country)
	{
		player.decreaseUnassignedArmyCount();
		country.increaseArmyCount();
	}
	
	/**
	 * Unassign the country from selected player
	 * @param player
	 * @param country
	 */
	public void unassignCountryFromPlayer(Player player, Country country)
	{
		if(playerCountry.containsKey(player))
		{
			ArrayList<Country> assignedCountries = playerCountry.get(player);
			assignedCountries.remove(player);
		}
	}
		
	public ArrayList<CountryAdorner> getMapViewData()
	{
		ArrayList<CountryAdorner> list = new ArrayList<>();
		
		for(java.util.Map.Entry<Player, ArrayList<Country>> e: playerCountry.entrySet()){
		    Player key = e.getKey();
		    ArrayList<Country> countries = e.getValue();
		    for(int i=0;i<countries.size();i++)
			{
				CountryAdorner _newItem = new CountryAdorner(countries.get(i));
			
				_newItem.setPlayerColor(key.getColor());
				list.add(_newItem);
			}
		    
		}
		return list;
	}
	
	public PlayerAdorner getNextPlayer()
	{
		if(currentPlayerId == playerList.size())
			currentPlayerId = 0;
		
		Player p = playerList.get(currentPlayerId);
		PlayerAdorner currentPlayer = new  PlayerAdorner(p,playerCountry.get(p)) ;
		currentPlayerId++;
		return currentPlayer;
	}
	
	public boolean addArmyToCountry(int playerId, int countryId)
	{
		Player player = playerList.stream()
				  .filter(p -> playerId == p.getPlayerId())
				  .findAny()
				  .orElse(null);
		if(player == null)
		{
			IOHelper.print("Player id " + playerId + " does not exist");
			return false;
		}
		Country country = playerCountry.get(player).stream()
				.filter(c -> c.getCountryId() == countryId)
				.findAny()
				.orElse(null);
		if(country == null)
		{
			IOHelper.print("Country id " + countryId + " does not exist");
			return false;
		}
		incresePlayerArmyInCountry(player, country);
		return true;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
}
