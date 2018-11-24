package com.risk.model.strategies;

import java.util.ArrayList;
import java.util.Collections;

import com.risk.helper.Common;
import com.risk.helper.IOHelper;
import com.risk.model.Country;
import com.risk.model.Game;
import com.risk.model.Player;

/**
 * A cheater computer player strategy whose reinforce() method doubles the
*number of armies on all its countries, whose attack() method automatically
*conquers all the neighbors of all its countries, and whose fortify() method
*doubles the number of armies on its countries that have neighbors that belong to
*other players. 
 * 
 * @author binay
 * @version 1.0.0
 * @since 19-November-2018
 */
public class Cheater implements PlayerStrategy {
	private String strategyName = "Cheater";
	
	   public String getStrategyName() {
			return strategyName;
		}

	@Override
	public boolean reinforce(Player player) {
		// TODO Auto-generated method stub
	    for (Country country:player.getAssignedCountryList())
	    { IOHelper.print("Adding reinforcement army in " + country.getCountryName());
		  int armies =  country.getnoOfArmies();
	      player.setNoOfReinforcedArmies(0);   //TODO: need to confim it 
	      country.setNoOfArmies(armies*2);
	    }
		return true;	
}

	@Override
	public void attack(Player attackerPlayer) {
		// TODO Auto-generated method stub		
		int armies;
		Player defenderPlayer;
		
		ArrayList<Country> attackerCountries = attackerPlayer.getAssignedCountryList();
		int size = attackerCountries.size();
		for (int i=0;i<size;i++) { 
			Country fromCountry = attackerCountries.get(i);
			for(Country toCountry : fromCountry.getNeighbourCountries()) {  
				if(toCountry.getPlayerId() != attackerPlayer.getPlayerId()) {
					armies = toCountry.getnoOfArmies();
	 	            toCountry.decreaseArmyCount(armies);   	
	                defenderPlayer = Game.getPlayerFromID(toCountry.getPlayerId());
	                
	                attackerPlayer.setAttackedPlayer(defenderPlayer);
	        		attackerPlayer.setFromCountry(fromCountry);
	        		attackerPlayer.setToCountry(toCountry);
	        		
	                attackerPlayer.conquerCountry(defenderPlayer);
				}
	    	}	    	
	    }		
	}

	@Override
	public boolean fortify(Player player) {
		// TODO Auto-generated method stub
        int armiesCount;
		for (Country country:player.getAssignedCountryList())
	    { for(Country neighbourCountry : country.getNeighbourCountries())
	    	{ if (neighbourCountry.getPlayerId()!=player.getPlayerId())
	    	  { armiesCount = country.getnoOfArmies()*2;
	    		country.increaseArmyCount(armiesCount);
	    	  }
	    	}		
	    }
		return true;
	}

}
