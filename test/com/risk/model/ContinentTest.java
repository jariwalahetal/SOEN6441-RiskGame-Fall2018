package com.risk.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.stream.Collectors;
import org.junit.*;

/**
 * This class will test the Continent methods.
 */
public class ContinentTest {
	Country c1;
	Continent cont1;
	String countryName = "TheBestCountry";
	ArrayList<Country> listOfCountries;
	String countryNameInList = "";

	/**
	 * This method will setup the variables for the test methods.
	 */
	@Before
	public void setUp() {
		c1 = new Country(2, countryName, 2);
		cont1 = new Continent(12, "BestContinent", 2);
	}

	/**
	 * This method tests if the addcountry method actually adds the country into
	 * the list or not.
	 */
	@Test
	public void checkAddCountry() {
		cont1.addCountry(c1);
		listOfCountries = cont1.getCountryList();
		countryNameInList = listOfCountries.stream().filter(x -> x.getCountryName() == countryName)
				.map(x -> x.getCountryName()).collect(Collectors.joining());
		assertEquals(countryNameInList, countryName);
	}
}
