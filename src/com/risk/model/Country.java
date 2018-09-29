package com.risk.model;

import java.util.ArrayList;

/**
 * Country Class
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
    private ArrayList<Country> neighbours = new ArrayList<>();
    private ArrayList<String> neighboursString = new ArrayList<>();
    
	/**
	 * This is a Constructor for Country class which sets name, continent, neighbooring countries and
	 * xaxis , yaxis and player of the country.
	 * @param countryId
	 * @param countryName
	 * @param contId
	 * @param playerId
	 * @param xCoordiate
	 * @param yCoordiate
	 * @param neighbours
	 */
	public Country(int countryId, String countryName) {
		super();
		this.countryId = countryId;
		this.countryName = countryName;
	}
	
	/**
	 * getters and setters
	 */
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public int getContId() {
		return contId;
	}
	public void setContId(int contId) {
		this.contId = contId;
	}
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public int getxCoordiate() {
		return xCoordiate;
	}
	public void setxCoordiate(int xCoordiate) {
		this.xCoordiate = xCoordiate;
	}
	public int getyCoordiate() {
		return yCoordiate;
	}
	public void setyCoordiate(int yCoordiate) {
		this.yCoordiate = yCoordiate;
	}
	public ArrayList<Country> getNeighbours() {
		return neighbours;
	}
	public void setNeighbours(ArrayList<Country> neighbours) {
		this.neighbours = neighbours;
	}
	public void addNeighboursString(String newNeighbour)
	{
		this.neighboursString.add(newNeighbour);
	}
	public ArrayList<String> getNeighboursString(){
		return neighboursString;
	}
}
