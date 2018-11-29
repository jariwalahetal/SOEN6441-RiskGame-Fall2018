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

   public boolean getIsBot();
   
   public default void beforeInitialStartupAssignment (Player player) {
	   Thread t = new Thread(new Runnable() {
			@Override
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			t.start();
			try {
				t.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   }
   
   public boolean reinforce(Player player);
   
   public void attack(Player attackerPlayer);
   
   public boolean fortify(Player player);
   
}
