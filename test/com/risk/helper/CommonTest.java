package com.risk.helper;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the Common class
 */
public class CommonTest {
	Common c;
	Color colorToTest;
	EnumColor e;
	int max;
	int min;
	
	/**
	 * This sets up method for initialising variables
	 */
	@Before
	public void setUp() {
		c = new Common();
		max = 4;
		min = 1;
	}
	
	@Test
	public void testGetColor() {
		e = null;
		colorToTest = c.getColor(e.BLACK);
		assertEquals(colorToTest, Color.BLACK);
	}
	
	@Test
	public void testGetRandomNumberInRange() {
		int randomInteger = c.getRandomNumberInRange(min, max);
		System.out.print(randomInteger);
		assertTrue(randomInteger > min-1);
		assertTrue(randomInteger < max+1);
	}
}
