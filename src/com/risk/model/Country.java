package com.risk.model;

import java.util.ArrayList;

import com.risk.helper.EnumColor;

/**
 * Country Class
 * 
 * @author sadgi
 * @version 1.0.0
 * @since 27-September-2018
 */
public class Country {
	private int countryId;
	private String countryName;
	private int contId;
	private int playerId;
	private int xCoordiate;
	private int yCoordiate;
	private ArrayList<String> neighboursString = new ArrayList<>();
	private int noOfArmies;

	private EnumColor countryColor;

	/**
	 *  This is a Constructor for Country class which sets name and id
	 * 
	 * @param countryId ,id of the country
	 * @param countryName,name of the country
	 * @param xCoordiate, x co-ordinate of the country
	 * @param yCoordiate, y co-ordinate of the country
	 * 
	 */
	public Country(int countryId, String countryName, int xCoordiate, int yCoordiate ) {
		this.countryId = countryId;
		this.countryName = countryName;
		this.xCoordiate = xCoordiate;
		this.yCoordiate = yCoordiate;
	}
	
	/**
	 * Method to get color of the country
	 * 
	 * @return countryColor,color of the country
	 */
	public EnumColor getCountryColor() {
		return countryColor;
	}

	/**
	 * Method to set color of the country
	 * 
	 * @param countryColor, color of the country
	 */
	public void setCountryColor(EnumColor countryColor) {
		this.countryColor = countryColor;
	}

	/**
	 * Get country id
	 * 
	 * @return countryId int
	 */
	public int getCountryId() {
		return countryId;
	}

	/**
	 * Gets country name
	 * 
	 * @return countryName string
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * Gets continent id
	 * 
	 * @return continentId int
	 */
	public int getContId() {
		return contId;
	}

	/**
	 * Sets continent id
	 * 
	 * @param contId,,
	 *            Id of the continent
	 */
	public void setContId(int contId) {
		this.contId = contId;
	}

	/**
	 * Gets player id
	 * 
	 * @return playeId int
	 */

	public int getPlayerId() {
		return playerId;
	}

	/**
	 * Sets player id
	 * 
	 * @param playerId
	 *            int
	 * 
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	/**
	 * Gets X-coordiante position
	 * 
	 * @return xCoordiate int
	 */
	public int getxCoordiate() {
		return xCoordiate;
	}

	/**
	 * Gets Y-coordinate position
	 * 
	 * @return yCoordiate int
	 */
	public int getyCoordiate() {
		return yCoordiate;
	}

	/**
	 * Adds name for neighbour string
	 * 
	 * @param newNeighbour
	 *            String
	 */
	public void addNeighboursString(String newNeighbour) {
		if (!this.neighboursString.contains(newNeighbour)) {
			this.neighboursString.add(newNeighbour);
		}
	}

	/**
	 * Gets list of neighbours stings
	 * 
	 * @return neighboursString
	 */
	public ArrayList<String> getNeighboursString() {
		return neighboursString;
	}

	/**
	 * Gets army count for the country
	 * 
	 * @return noOfArmies int
	 */
	public int getnoOfArmies() {
		return noOfArmies;
	}

	/**
	 * Sets army count for the country
	 * @param noOfArmies, int
	 * 
	 */
	public void setNoOfArmies(int noOfArmies) {
		this.noOfArmies = noOfArmies;
	}
	
	/**
	 * Increases the army count by given value
	 * 
	 * @param count
	 *            int
	 * 
	 */
	public void increaseArmyCount(int count) {
		noOfArmies += count;
	}


	/**
	 * Decreases the army count by given value
	 * 
	 * @param count
	 *            int
	 * 
	 */
	public void decreaseArmyCount(int count) {
		noOfArmies -= count;
	}
}
