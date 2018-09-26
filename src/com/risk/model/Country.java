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
	private Continent contId;
	private Player playerId;
    private int xCoordiate;
    private int yCoordiate;
    private ArrayList<Country> neighbours = new ArrayList<>();
    
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
	public Country(int countryId, String countryName, Continent contId, Player playerId, int xCoordiate, int yCoordiate,
			ArrayList<Country> neighbours) {
		super();
		this.countryId = countryId;
		this.countryName = countryName;
		this.contId = contId;
		this.playerId = playerId;
		this.xCoordiate = xCoordiate;
		this.yCoordiate = yCoordiate;
		this.neighbours = neighbours;
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
	public Continent getContId() {
		return contId;
	}
	public void setContId(Continent contId) {
		this.contId = contId;
	}
	public Player getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Player playerId) {
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
   

}
