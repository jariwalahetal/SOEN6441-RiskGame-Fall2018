package com.risk.model;

import java.util.ArrayList;

/**
 * Continent Class
 * @author sadgi
 * @version 1.0.0
 * @since 27-September-2018
 */
public class Continent {
	private int contId;
	private String contName;
	private int controlValue;
	private ArrayList<Country> countryList = new ArrayList<>();

	/**
	 * This is a Constructor for Continent Class
	 * This constructor sets name and control value of the class
	 * @param name of continent
	 * @param controlValue of continent
	 */
	public Continent(int contId, String contName, int controlValue) {
		super();
		this.contId = contId;
		this.contName = contName;
		this.controlValue = controlValue;
	}


	/**
	 * getters and setters
	 */
	public int getContId() {
		return contId;
	}

	public void setContId(int contId) {
		this.contId = contId;
	}

	public String getContName() {
		return contName;
	}

	public void setContName(String contName) {
		this.contName = contName;
	}

	public int getControlValue() {
		return controlValue;
	}

	public void setControlValue(int controlValue) {
		this.controlValue = controlValue;
	}
	
	public void addCountry(Country country)
	{
		this.countryList.add(country);
	}
	
	public ArrayList<Country> getCountryList(){
		return countryList;
	}


}
