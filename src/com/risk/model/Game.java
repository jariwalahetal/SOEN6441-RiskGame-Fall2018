package com.risk.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import com.risk.helper.InitialPlayerSetup;

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
	 * This is a constructor of Game class which will initialise the Map
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
	 * @param map
	 */
	public void assignCountriesToPlayer() {
		//For each country in map, assign player to them
		int countriesCount = map.getCountryList().size();
		int playerIndex = 0, playerCount = playerList.size();
		ArrayList<Integer> tempList = new ArrayList<>();
		for(int i=0;i<countriesCount ;i++)
		{
			tempList.add(i);
		}
		Collections.shuffle(tempList,new Random());
		for(int i=0;i<countriesCount;i++)
		{
			if(playerIndex == playerCount)
				playerIndex = 0;

			//Increase assigned armies for country
			assignCountryToPlayer(playerList.get(playerIndex), map.getCountryList().get(tempList.get(i))); 
			playerIndex++;		
		}
	}

	/**
	 * This function will assign armies to all players
	 */
	/*
	 * public void initialArmyAssignment() {
	 * System.out.println("initialArmyAssignment called"); }
	 */
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
	 * @return
	 */
	public Boolean isGameOn() {
		return false;
	}

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
		newCountry.increaseArmyCount();
	}
}
