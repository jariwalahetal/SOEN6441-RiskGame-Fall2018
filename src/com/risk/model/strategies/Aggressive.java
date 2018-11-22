package com.risk.model.strategies;

import java.util.ArrayList;
import java.util.Collections;

import com.risk.helper.Common;
import com.risk.helper.IOHelper;
import com.risk.model.Country;
import com.risk.model.Game;
import com.risk.model.Player;

/**
 * An aggressive computer player strategy that focuses on attack (reinforces its
*strongest country, then always attack with it until it cannot attack anymore, then
*fortifies in order to maximize aggregation of forces in one country).
*
 * @author binay
 * @version 1.0.0
 * @since 19-November-2018
 */
public class Aggressive implements PlayerStrategy {

	@Override
	public boolean reinforce(Player player) {
		// TODO Auto-generated method stub
		if (player.getNoOfReinforcedArmies() == 0) {
			IOHelper.print("Player " + player.getName() + " doesn't have unassigned armies!");
			return false;
		}

		Country country = player.getStrongestCountry();
		if (country == null) {
			IOHelper.print("Country name - " + country.getCountryName() + " does not exist!");
			return false;
		}

		IOHelper.print("Adding reinforcement army in " + country.getCountryName());
		while(player.getNoOfReinforcedArmies() > 0)
		{player.decreaseReinforcementArmyCount();
		 country.increaseArmyCount(1);
		}
		
		return true;
	}

	@Override
	public void attack(Player attackerPlayer) {
		// TODO Auto-generated method stub
		Country fromCountry = attackerPlayer.getStrongestCountry();
		if (fromCountry==null)  //attack not possible
		{ return;
		}
		ArrayList<Country> neighbourCountryList = fromCountry.getNeighbourCountries();
		Country toCountry = null;
		int armies = Integer.MAX_VALUE;
		for (Country neighbourCountry:neighbourCountryList)
		{if(neighbourCountry.getPlayerId() == attackerPlayer.getPlayerId() &&
		     neighbourCountry.getnoOfArmies() < armies)
	    	{ armies = neighbourCountry.getnoOfArmies();	
	      	  toCountry = neighbourCountry;
	    	}	
		}
		
		if (toCountry == null)
		{ return;
		}
		
		int attackerDiceCount = attackerPlayer.getMaximumAllowableDices(fromCountry, "Attacker");
		int defenderDiceCount = attackerPlayer.getMaximumAllowableDices(toCountry, "Defender");
		defenderDiceCount = Common.getRandomNumberInRange(0, defenderDiceCount);
					

	    Player defenderPlayer = Game.getPlayerFromID(toCountry.getPlayerId());
	    attackerPlayer.setAttackedPlayer(defenderPlayer);
	    attackerPlayer.setFromCountry(fromCountry);
	    attackerPlayer.setToCountry(toCountry);
	    	   	    
	    attackerPlayer.rollDice(attackerDiceCount);
	    defenderPlayer.rollDice(defenderDiceCount);
	    
		ArrayList<Integer> attackingDices = attackerPlayer.getDiceOutComes();
		ArrayList<Integer> defendingDices = defenderPlayer.getDiceOutComes();

		IOHelper.print("Attacker's dices -- " + attackingDices);
		IOHelper.print("Defender's dices -- " + defendingDices);

		Collections.sort(attackingDices, Collections.reverseOrder());
		Collections.sort(defendingDices, Collections.reverseOrder());

		int totalComparisions = attackingDices.size() < defendingDices.size() ? attackingDices.size()
				: defendingDices.size();

		for (int i = 0; i < totalComparisions; i++) {

			int attackerDice = attackingDices.get(i);
			int defencerDice = defendingDices.get(i);

			IOHelper.print("Attacker dice - " + attackerDice + "  to Defender dice - " + defencerDice);
			Common.PhaseActions.add("Attacker dice - " + attackerDice + "  to Defender dice - " + defencerDice);

			if (attackerDice > defencerDice) {
				IOHelper.print("----> attacker wins for dice " + (i + 1));
				Common.PhaseActions.add("----> attacker wins for dice " + (i + 1));

				toCountry.decreaseArmyCount(1);

			} else {
				IOHelper.print("----> defender wins for dice " + (i + 1));
				Common.PhaseActions.add("----> defender wins for dice " + (i + 1));

				fromCountry.decreaseArmyCount(1);
			}

			if (fromCountry.getnoOfArmies() == 1) {
				IOHelper.print("----> Attacker not able to Attack ");
				break;
			} else if (toCountry.getnoOfArmies() == 0) {
				IOHelper.print("----> Defender lost all armies in " + (i + 1) + " dice roll");
				break;
			}

		}

		if (toCountry.getnoOfArmies() == 0) {
	             attackerPlayer.conquerCountry(defenderPlayer);	
		}
   
       attack(attackerPlayer);  //keep attacking
	}

	@Override
	public boolean fortify(Player player) {
		// TODO Auto-generated method stub
		Country sourceCountry = player.getStrongestCountry();
		ArrayList<Country> neighbourCountryList = sourceCountry.getNeighbourCountries();
		Country destinationCountry = null;
		int armies = 0;
		for (Country neighbourCountry:neighbourCountryList)
		{if(neighbourCountry.getPlayerId() == player.getPlayerId() &&
		     neighbourCountry.getnoOfArmies()>armies)
	    	{ armies = neighbourCountry.getnoOfArmies();	
	    	destinationCountry = neighbourCountry;
	    	}	
		}
		if (destinationCountry != null)
		{   sourceCountry.decreaseArmyCount(armies);
			destinationCountry.increaseArmyCount(armies);
		}
		
		return true;
	}

}
