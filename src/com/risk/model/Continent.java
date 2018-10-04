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
	 * @param name
	 *            of continent
	 * @param controlValue
	 *            of continent
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
	 * This function sets the continent ID of the continent.
	 * @param int continent ID
	 */
	public void setContId(int contId) {
		this.contId = contId;
	}
	/**
	 * This function returns the continent name.
	 * @param string continent name
	 */
	public String getContName() {
		return contName;
	}
	/**
	 * This function sets the continent name.
	 * @param string continent name
	 */
	public void setContName(String contName) {
		this.contName = contName;
	}
	/**
	 * This function returns the control value of the continent object.
	 * @return int control value
	 */
	public int getControlValue() {
		return controlValue;
	}
	/**
	 * This function sets the control value of the continent object.
	 * @param int control value
	 */
	public void setControlValue(int controlValue) {
		this.controlValue = controlValue;
	}
	/**
	 * This function adds the country object into the country list.
	 * @param Country country object
	 */
	public void addCountry(Country country) {
		this.countryList.add(country);
	}
	/**
	 * This function returns the country list.
	 * @return ArrayList country object
	 */
	public ArrayList<Country> getCountryList() {
		return countryList;
	}

}
