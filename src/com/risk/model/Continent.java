package com.risk.model;

import java.util.ArrayList;

/**
 * Continent Class
 * 
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
	 * This is a Constructor for Continent Class This constructor sets name and
	 * control value of the class
	 * 
	 * @param contId
	 *            , Id of the continent
	 * @param contName
	 *            , Name of the continent
	 * @param controlValue,
	 *            Control Value of the continent
	 *
	 */
	public Continent(int contId, String contName, int controlValue) {
		super();
		this.contId = contId;
		this.contName = contName;
		this.controlValue = controlValue;
	}

	/**
	 * This function returns the continent ID of the continent.
	 * 
	 * @return int continent ID
	 */
	public int getContId() {
		return contId;
	}

	/**
	 * This function returns the continent name.
	 * 
	 * @return contName
	 */
	public String getContName() {
		return contName;
	}

	/**
	 * This function returns the control value of the continent object.
	 * 
	 * @return int control value
	 */
	public int getControlValue() {
		return controlValue;
	}

	/**
	 * This function adds the country object into the country list.
	 * 
	 * @param country
	 *            , Name of the country
	 */
	public void addCountry(Country country) {
		this.countryList.add(country);
	}

	/**
	 * This function returns the country list.
	 * 
	 * @return ArrayList country object
	 */
	public ArrayList<Country> getCountryList() {
		return countryList;
	}

}
