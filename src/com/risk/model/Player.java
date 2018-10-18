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
	 * This is a constructor of Player Class which sets playerId, name, and color.
	 * 
	 * @param playerId
	 * @param name
	 * @param color
	 */
	public Player(int playerId, String name) {
		super();
		this.playerId = playerId;
		this.name = name;
		this.color = InitialPlayerSetup.getPlayerColor(playerId);
	}

	/**
	 * getters and setters of NoOfArmies
	 */

	public int getNoOfUnassignedArmies() {
		return noOfUnassignedArmies;
	}

	public void setNoOfUnassignedArmies(int noOfUnassignedArmies) {
		this.noOfUnassignedArmies = noOfUnassignedArmies;
	}

	public int getNoOfReinforcedArmies ()
	{
		return noOfReinforcedArmies;
	}
	
	public void setNoOfReinforcedArmies(int noOfReinforcedArmies) {
		this.noOfReinforcedArmies = noOfReinforcedArmies;
	}
	
	/**
	 * getters and setters
	 */
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EnumColor getColor() {
		return color;
	}

	public void setColor(EnumColor color) {
		this.color = color;
	}

	public void decreaseUnassignedArmyCount()
	{     if(noOfUnassignedArmies>0)
				noOfUnassignedArmies--;
	}
	
	public void increaseUnassignedArmyCount()
	{
		noOfUnassignedArmies++;
	}
	
	public void decreaseReinforcementArmyCount()
	{     if(noOfReinforcedArmies>0)
			noOfReinforcedArmies--;
	}
	
}
