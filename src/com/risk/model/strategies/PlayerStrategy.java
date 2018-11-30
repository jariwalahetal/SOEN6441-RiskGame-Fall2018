package com.risk.model.strategies;

import com.risk.model.Country;
import com.risk.model.Player;

/**
 * It contains declaration of the reinforce, attack and fortify methods
 * 
 * @author binay
 * @version 1.0.0
 * @since 19-November-2018
 *
 */
public interface PlayerStrategy {

	/**
	 * This method will return strategy Name
	 * 
	 * @return strategy name
	 */
	public String getStrategyName();

	/**
	 * This method will return true if strategy is a bot
	 * 
	 * @return true or false
	 */
	public boolean getIsBot();

	/**
	 * This method will execute reinforce method for the Strategy
	 * 
	 * @param player
	 *            Input Player
	 * @return true or false
	 */
	public boolean reinforce(Player player);

	/**
	 * This method will execute attack method for the Strategy
	 * 
	 * @param attackerPlayer
	 *            Player Object
	 */
	public void attack(Player attackerPlayer);

	/**
	 * This method will execute fortify method for the Strategy
	 * 
	 * @param player
	 *            Player Object
	 * @return true or false
	 */
	public boolean fortify(Player player);

}
