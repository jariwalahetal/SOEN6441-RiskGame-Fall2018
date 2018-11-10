package com.risk.helper;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

public class CommonTest {
	Common c;
	Color colorToTest;
	EnumColor e;
	
	@Before
	public void setUp() {
		c = new Common();
	}
	
	@Test
	public void testGetColor() {
		e = null;
		colorToTest = c.getColor(e.BLACK);
		assertEquals(colorToTest, Color.BLACK);
	}
}
