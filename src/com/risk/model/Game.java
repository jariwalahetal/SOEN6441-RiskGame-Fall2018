package com.risk.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import com.risk.helper.InitialPlayerSetup;
import com.risk.viewmodel.PlayerCountryAdorner;

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
	 * This function will randomly assign Countries to all players
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
			newCountry.increaseArmyCount();
			
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
		
	public ArrayList<PlayerCountryAdorner> getMapViewData()
	{
		ArrayList<PlayerCountryAdorner> list = new ArrayList<>();
		
		playerCountry.forEach((k,v) -> {
			for(int i=0;i<v.size();i++)
			{
				PlayerCountryAdorner _newItem = new PlayerCountryAdorner
						(v.get(i).getCountryId(), v.get(i).getCountryName());
				_newItem.setColor(k.getColor());
				_newItem.setNoOfArmies(k.getNoOfArmies());
				
			}
		});
		
		return list;
	}
}
