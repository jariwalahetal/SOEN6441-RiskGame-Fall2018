package com.risk.model.strategies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import com.risk.helper.Common;
import com.risk.helper.IOHelper;
import com.risk.model.Country;
import com.risk.model.Player;

/**
 * A human player that requires user interaction to make decisions.
 * 
 * @author binay
 * @version 1.0.0
 * @since 19-November-2018

 */
public class Human implements PlayerStrategy, Serializable {

	private String strategyName = "Human";

    public String getStrategyName() {
			return strategyName;
		}
	
	@Override
	public boolean reinforce(Player player) {
		// TODO Auto-generated method stub
		if (player.getNoOfReinforcedArmies() == 0) {
			IOHelper.print("Player " + player.getName() + " doesn't have unassigned armies!");
			return false;
		}

		Country country = player.getToCountry();
		
		if (country == null) {
			IOHelper.print("Country name - " + country.getCountryName() + " does not exist!");
			return false;
		}

		IOHelper.print("Adding reinforcement army in " + country.getCountryName());
		player.decreaseReinforcementArmyCount();
		country.increaseArmyCount(1);

		return true;
	}

	@Override
	public void attack(Player attackerPlayer) {
		// TODO Auto-generated method stub
		Country attackingCountry = attackerPlayer.getFromCountry();
		Country defendingCountry = 	attackerPlayer.getToCountry();	
	    Player defenderPlayer = attackerPlayer.getAttackedPlayer();
		
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

				defendingCountry.decreaseArmyCount(1);

			} else {
				IOHelper.print("----> defender wins for dice " + (i + 1));
				Common.PhaseActions.add("----> defender wins for dice " + (i + 1));

				attackingCountry.decreaseArmyCount(1);
			}

			if (attackingCountry.getnoOfArmies() == 1) {
				IOHelper.print("----> Attacker not able to Attack ");
				break;
			} else if (defendingCountry.getnoOfArmies() == 0) {
				IOHelper.print("----> Defender lost all armies in " + (i + 1) + " dice roll");
				break;
			}

		}

		if (defendingCountry.getnoOfArmies() == 0) {
	             attackerPlayer.conquerCountry(defenderPlayer);	
		}
	}

	@Override
	public boolean fortify(Player player) {
		// TODO Auto-generated method stub
		
		Country sourceCountry = player.getFromCountry();
		Country destinationCountry = player.getToCountry();
		int noOfArmies = player.getNoOfArmiesToMove();
		
		if (sourceCountry == null || destinationCountry == null) {
			IOHelper.print("Source or destination country is invalid!");
			return false;
		}

		if (noOfArmies == 0) {
			IOHelper.print("No armies to move");
			return true;
		}
		sourceCountry.decreaseArmyCount(noOfArmies);
		destinationCountry.increaseArmyCount(noOfArmies);

		return true;

	}

}
