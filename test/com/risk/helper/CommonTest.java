package com.risk.helper;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.After;
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
	 * This sets up method for initializing variables
	 */
	@Before
	public void setUp() {
		c = new Common();
		max = 4;
		min = 1;
	}
	/**
	 * This method tests the color.
	 */
	@Test
	public void testGetColor() {
		e = null;
		colorToTest = c.getColor(e.BLACK);
		assertEquals(colorToTest, Color.BLACK);
	}
	/**
	 * This method tests testGetRandomNumberInRange.
	 */
	@Test
	public void testGetRandomNumberInRange() {
		int randomInteger = c.getRandomNumberInRange(min, max);
		System.out.print(randomInteger);
		assertTrue(randomInteger > min-1);
		assertTrue(randomInteger < max+1);
	}
	/**
	 * This method tears down.
	 */
	@After
	public void tearDown() {
		Common c = null;
		Color colorToTest = null;
		EnumColor e =null;
		int max = 0;
		int min = 0;
	}
}
