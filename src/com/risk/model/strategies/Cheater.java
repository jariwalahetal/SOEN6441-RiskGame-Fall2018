package com.risk.model.strategies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.risk.helper.Common;
import com.risk.helper.IOHelper;
import com.risk.model.Country;
import com.risk.model.Game;
import com.risk.model.Player;

/**
 * A cheater computer player strategy whose reinforce() method doubles the
 * number of armies on all its countries, whose attack() method automatically
 * conquers all the neighbors of all its countries, and whose fortify() method
 * doubles the number of armies on its countries that have neighbors that belong
 * to other players.
 * 
 * @author binay
 * @version 1.0.0
 * @since 19-November-2018
 */
public class Cheater implements PlayerStrategy, Serializable {
	private String strategyName = "Cheater";

	public String getStrategyName() {
		return strategyName;
	}

	public boolean getIsBoat() {
		return true;
	}
	
	@Override
	public boolean reinforce(Player player) {
	    for (Country country:player.getAssignedCountryList()) { 
	    	IOHelper.print("Adding reinforcement army in " + country.getCountryName() + "("+ country.getnoOfArmies()+")");
	    	int armies =  country.getnoOfArmies();
	    	player.setNoOfReinforcedArmies(0);
	    	country.setNoOfArmies(armies*2);
			IOHelper.print("Added reinforcement army in " + country.getCountryName() + "("+ country.getnoOfArmies()+")");
	    }
		return true;	
}

	@Override
	public void attack(Player attackerPlayer) {
		int armies;
		Player defenderPlayer;
		
		ArrayList<Integer> attackerCountryIds = attackerPlayer.getAssignedCountryList()
											.stream().map(c -> c.getCountryId())
											.collect(Collectors.toCollection(ArrayList::new));
		IOHelper.print("Cheater player " + attackerPlayer.getName() + " found " + attackerCountryIds.size() +" countries for attack. Doing attack now..");
		for (int i=0;i<attackerCountryIds.size();i++) { 
			int attackerCountryId = attackerCountryIds.get(i);
			Country fromCountry = attackerPlayer.getAssignedCountryList()
									.stream().filter(x -> x.getCountryId() == attackerCountryId)
									.findFirst().orElse(null);
			
			if(fromCountry == null) {
				continue;
			}
			ArrayList<Country> CountriesToAttack = attackerPlayer.getUnAssignedNeighbouringCountriesObject
					(fromCountry.getCountryName());
			if(CountriesToAttack==null || CountriesToAttack.size() == 0)
			{
				continue;
			}
			for(Country toCountry : CountriesToAttack) {  
				if(toCountry.getPlayer().getPlayerId() == attackerPlayer.getPlayerId())
					continue;
				
				IOHelper.print(fromCountry.getCountryName() + " is attacking ("+ fromCountry.getnoOfArmies()+")" + toCountry.getCountryName() + "("+ toCountry.getnoOfArmies()+ ")");
				armies = toCountry.getnoOfArmies();
				toCountry.decreaseArmyCount(armies);   	
	            defenderPlayer = toCountry.getPlayer();
	            
	            attackerPlayer.setAttackedPlayer(defenderPlayer);
	            attackerPlayer.setFromCountry(fromCountry);
	            attackerPlayer.setToCountry(toCountry);
	        	
	            attackerPlayer.conquerCountry(defenderPlayer);
	            
	            // reverting minimum army rule to overcome - armies count
	    		fromCountry.increaseArmyCount(1);
	    		toCountry.decreaseArmyCount(1);
	    	}	    	
	    }	
		IOHelper.print("Finishing attack...");	
	}

	@Override
	public boolean fortify(Player player) {
        int armiesCount;
		for (Country country:player.getAssignedCountryList()) { 
			IOHelper.print("Cheater player " + player.getName() +" is trying to fortify " + country.getCountryName() + "("+ country.getnoOfArmies() +")");
			ArrayList<Country> neighbouringList = player.getUnAssignedNeighbouringCountriesObject(country.getCountryName());
			
			if(neighbouringList != null || neighbouringList.size() == 0) {
				IOHelper.print("-- Cannot fortify as there is no neigbouring county found from other player");
			}
			else {
				armiesCount = country.getnoOfArmies()*2;
				country.increaseArmyCount(armiesCount);
				IOHelper.print("-- Finished fortification with country " +  country.getCountryName() + " ("+ country.getnoOfArmies() +")");
			}	
	    }
		return true;
	}

	@Override
	public boolean determineInitialStartupAssignment(Player player) {
		IOHelper.print("This is boat player so processing step automatically");
		Country country = player.getAssignedCountryList().get(0);
		return player.addArmyToCountryForStartup(country.getCountryName());
	}

}
