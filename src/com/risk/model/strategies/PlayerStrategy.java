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

   public String getStrategyName();

   public boolean getIsBoat();
   
   public boolean determineInitialStartupAssignment(Player player);
   
   public boolean reinforce(Player player);
   
   public void attack(Player attackerPlayer);
   
   public boolean fortify(Player player);
   
}
