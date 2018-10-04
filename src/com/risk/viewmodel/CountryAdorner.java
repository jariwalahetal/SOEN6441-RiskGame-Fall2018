package com.risk.viewmodel;

import com.risk.helper.Color;
import com.risk.model.Country;

public class CountryAdorner extends Country {
	public CountryAdorner(int countryId, String countryName) {
		super(countryId, countryName);
	}

	private Color color;
	private int NoOfArmies;
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public int getNoOfArmies() {
		return NoOfArmies;
	}

	public void setNoOfArmies(int noOfArmies) {
		NoOfArmies = noOfArmies;
	}
}
