package com.risk.viewmodel;

import java.util.ArrayList;

import com.risk.helper.EnumColor;
import com.risk.model.Country;
import com.risk.model.Player;

public class PlayerAdorner extends Player {

	private ArrayList<CountryAdorner> countries;
	
	public void addCountry(Country c)
	{
		countries.add(new CountryAdorner(c));
	}
	
	public PlayerAdorner(Player p, ArrayList<Country> countries) {
		super(p.getPlayerId(), p.getName(), p.getColor());
		countries = new ArrayList<>();
		if(countries!= null)
		{
			for(int i=0;i<countries.size();i++)
			{
				addCountry(countries.get(i));
			}
		}
		
	}

	public ArrayList<CountryAdorner> getCountries() {
		return countries;
	}

	public void setCountries(ArrayList<CountryAdorner> countries) {
		this.countries = countries;
	}
	
}
