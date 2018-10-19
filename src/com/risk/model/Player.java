package com.risk.model;

import com.risk.helper.EnumColor;
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
	
	/**
	 * This is a constructor of Player Class which sets playerId, name, and
	 * color.
	 * 
	 * @param playerId
	 * @param name
	 */
	public Player(int playerId, String name) {
		super();
		this.playerId = playerId;
		this.name = name;
		this.color = InitialPlayerSetup.getPlayerColor(playerId);
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
	 * @param noOfUnassignedArmies
	 */
	public void setNoOfUnassignedArmies(int noOfUnassignedArmies) {
		this.noOfUnassignedArmies = noOfUnassignedArmies;
	}

	/**
	 * This method returns the number of reinforcement army units
	 * 
	 * @return noOfReinforcedArmies int
	 */
	public int getNoOfReinforcedArmies ()
	{
		return noOfReinforcedArmies;
	}
	
	/**
	 * This method set the number of reinforcement army units
	 * 
	 * @param noOfReinforcedArmies int
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
	 * This method set the id of the player
	 * 
	 * @param playerId int
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
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
	 * This method set name of the player
	 * 
	 * @param name String
	 */
	public void setName(String name) {
		this.name = name;
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
	 * This method set color of the EnumColor class
	 * 
	 * @param color EnumColor
	 */
	public void setColor(EnumColor color) {
		this.color = color;
	}

	/**
	 * This method decreases unassigned army count 
	 */
	public void decreaseUnassignedArmyCount()
	{     if(noOfUnassignedArmies>0)
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
	public void decreaseReinforcementArmyCount()
	{     if(noOfReinforcedArmies>0)
			noOfReinforcedArmies--;
	}
	
}
