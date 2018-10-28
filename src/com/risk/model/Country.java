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
	 * This is a Constructor for Country class which sets name, continent,
	 * neighbooring countries and xaxis , yaxis and player of the country.
	 * 
	 * @param countryId
	 *            , Id of the country
	 * @param countryName,
	 *            Name of the country
	 * @param noOfArmies,
	 *            Number of armies the country
	 */
	public Country(int countryId, String countryName, int noOfArmies) {
		super();
		this.countryId = countryId;
		this.countryName = countryName;
		this.noOfArmies = noOfArmies;
	}

	/**
	 *  This is a Constructor for Country class which sets name and id
	 * 
	 * @param countryId ,id of the country
	 * @param countryName,name of the country
	 */
	public Country(int countryId, String countryName) {
		this.countryId = countryId;
		this.countryName = countryName;
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
	 * Sets country id
	 * 
	 * @param countryId
	 *            int
	 * 
	 */
	public void setCountryId(int countryId) {
		this.countryId = countryId;
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
	 * Sets country name
	 * 
	 * @param countryName
	 *            string
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
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
	 * Sets X-coordinate position
	 * 
	 * @param xCoordiate
	 *            int
	 * 
	 */
	public void setxCoordiate(int xCoordiate) {
		this.xCoordiate = xCoordiate;
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
	 * Sets Y-coordinate position
	 * 
	 * @param yCoordiate
	 *            int
	 * 
	 */
	public void setyCoordiate(int yCoordiate) {
		this.yCoordiate = yCoordiate;
	}

	/**
	 * Adds name for neighbour string
	 * 
	 * @param newNeighbour
	 *            String
	 */
	public void addNeighboursString(String newNeighbour) {
		if (this.neighboursString.contains(newNeighbour)) {
			// Do nothing
		} else {
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
	 * Increases the army count by one
	 * 
	 */
	public void increaseArmyCount() {
		noOfArmies++;
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
	 * Decreases the army count by one
	 * 
	 */
	public void decreseArmyCount() {
		noOfArmies--;
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
