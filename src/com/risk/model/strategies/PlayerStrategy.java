package com.risk.model.strategies;

import com.risk.model.Country;
import com.risk.model.Player;

/**
 * It contains declaration of the reinforce, attack and fortify methods
 * @author binay
 * @version 1.0.0
 * @since 19-November-2018
 *
 */
public interface PlayerStrategy {

   public boolean reinforce(Player player,String countryName);
   
   public void attack(Player attackerPlayer,Player defenderPlayer, Country attackingCountry, Country defendingCountry,
			int attackingDiceCount, int defendingDiceCount);
   
   public boolean fortify(Player player, String sourceCountryName, String destinationCountryName, int noOfArmies);
   
}
