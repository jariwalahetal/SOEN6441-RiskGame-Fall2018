package com.risk.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.risk.helper.InitialPlayerSetup;

/**
 * Game Class
 * @author Binay
 * @version 1.0.0
 * @since 30-September-2018
 */
public class Game {

	//Country and the army assigned to it
		private HashMap<Country,Integer> countryArmy = new  HashMap<Country,Integer>();
		private ArrayList<Player> playerList = new ArrayList<Player>();
		private Map map;
		
	/**
	 * This is a constructor of Game class which will initialise the Map	
	 * @param map
	 */
    public Game(Map map) {
		super();
		this.map = map;
	}

	/**
	 * This function will randomly assign Countries to all players
	 * @param map
	 */
	public void assignCountryToPlayer()
	{ //divide the countries in Map object to the players in the playerList
	  System.out.println("assignCountryToPlayer called");
	}
	
	/**
	 * This function will assign armies to all players 
	 */
	/*	public void initialArmyAssignment()
	{ System.out.println("initialArmyAssignment called");		
	}
	*/
	/**
	 * This function will add the player to the game(playerList)
	 * @param player
	 */
	public void addPlayer(Player player)
	{
		this.playerList.add(player);
	}

	/**
	 * 
	 * @return
	 */
	public Boolean isGameOn()
	{
		return false;
	}
}
