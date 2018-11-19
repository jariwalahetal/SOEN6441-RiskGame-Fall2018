package com.risk.helper;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * Testing armies
 *
 */
public class GetArmiesByTradingTest {
	/**
	 * testing getarmies
	 */
	@Test
	public void  GetArmiesByTrading() {
		GetArmiesByTrading g = new GetArmiesByTrading();
		assertEquals(g.getArmies(2), 6);
	}
}
