package com.risk.model.strategies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import com.risk.helper.Common;
import com.risk.helper.IOHelper;
import com.risk.model.Country;
import com.risk.model.Game;
import com.risk.model.Player;

/**
 * A random computer player strategy that reinforces random a random country,
 * attacks a random number of times a random country, and fortifies a random
 * country, all following the standard rules for each phase.
 * 
 * @author binay
 * @version 1.0.0
 * @since 19-November-2018
 */
public class Random implements PlayerStrategy, Serializable {

	private String strategyName = "Random";

	public String getStrategyName() {
		return strategyName;
	}

	public boolean getIsBot() {
		return true;
	}
	
	@Override
	public boolean reinforce(Player player) {
		ArrayList<Country> countryList = player.getAssignedCountryList();
		int randomIndex = 0;
		if (countryList.isEmpty())
			return true;
		else if (countryList.size() > 1)
			randomIndex = Common.getRandomNumberInRange(0, countryList.size() - 1);
		int armies = player.getNoOfReinforcedArmies();
		Country country = countryList.get(randomIndex);
		IOHelper.print("Randomly selected " + country.getCountryName() + "(" + country.getnoOfArmies()
				+ ") for reinforcement");

		IOHelper.print(
				"Adding reinforcement army in " + country.getCountryName() + "(" + country.getnoOfArmies() + ")");
		player.setNoOfReinforcedArmies(0);
		country.increaseArmyCount(armies);
		IOHelper.print("Added reinforcement army in " + country.getCountryName() + "(" + country.getnoOfArmies() + ")");
		return true;
	}

	@Override
	public void attack(Player attackerPlayer) {
		int totalAttack = Common.getRandomNumberInRange(1, 10);

		IOHelper.print("Total " + totalAttack + " random attacks are generated");
		for (int i = 0; i < totalAttack; i++) {
			ArrayList<Country> countryList = attackerPlayer.getCountriesObjectWithArmiesGreaterThanOne();
			int randomIndex = 0;
			if (countryList == null || countryList.size() == 0)
				break;
			else if (countryList.size() > 1)
				randomIndex = Common.getRandomNumberInRange(0, countryList.size() - 1);

			Country fromCountry = countryList.get(randomIndex);
			IOHelper.print("Randomly selectd " + fromCountry.getCountryName() + " (" + fromCountry.getnoOfArmies()
					+ ") for attack");

			ArrayList<Country> neighborCountries = attackerPlayer
					.getUnAssignedNeighbouringCountriesObject(fromCountry.getCountryName());

			if (neighborCountries.isEmpty()) {
				IOHelper.print("No neighbour found as a defender");
				continue;
			} else if (neighborCountries.size() == 1)
				randomIndex = 0;
			else
				randomIndex = Common.getRandomNumberInRange(0, neighborCountries.size() - 1);

			Country toCountry = neighborCountries.get(randomIndex);
			IOHelper.print("Randomly selectd " + toCountry.getCountryName() + " (" + toCountry.getnoOfArmies()
					+ ") as a defender");

			attackOperation(fromCountry, toCountry, attackerPlayer);
		}

	}

	@Override
	public boolean fortify(Player player) {
		ArrayList<Country> countryList = player.getCountriesObjectWithArmiesGreaterThanOne();
		int randomIndex = 0;

		if (countryList.isEmpty())
			return false;
		else if (countryList.size() > 1)
			randomIndex = Common.getRandomNumberInRange(0, countryList.size() - 1);

		Country sourceCountry = countryList.get(randomIndex);
		IOHelper.print("Randomly select " + sourceCountry.getCountryName() + "(" + sourceCountry.getnoOfArmies()
				+ ") country for fortification");
		ArrayList<Country> neigbouringCountries = player.getConnectedCountriesRecursively(sourceCountry,
				(ArrayList<Country>) player.getAssignedCountryList().clone(), new ArrayList<Country>());

		if (neigbouringCountries != null && neigbouringCountries.size() > 0) {
			int destinationRandomIndex = Common.getRandomNumberInRange(0, neigbouringCountries.size() - 1);
			Country destinationCountry = neigbouringCountries.get(destinationRandomIndex);
			IOHelper.print("Randomly select " + destinationCountry.getCountryName() + "("
					+ destinationCountry.getnoOfArmies() + ") country for fortification");
			if (destinationCountry != null) {
				int armies = Common.getRandomNumberInRange(0, sourceCountry.getnoOfArmies() - 1);
				sourceCountry.decreaseArmyCount(armies);
				destinationCountry.increaseArmyCount(armies);
			}
			IOHelper.print("Finished fortification with destination country " + destinationCountry.getCountryName()
					+ " (" + destinationCountry.getnoOfArmies() + ")");
		}

		return true;
	}

	private void attackOperation(Country fromCountry, Country toCountry, Player attackerPlayer) {

		int attackerDiceCount = attackerPlayer.getMaximumAllowableDices(fromCountry, "Attacker");
		if (attackerDiceCount > 0)
			attackerDiceCount = Common.getRandomNumberInRange(1, attackerDiceCount);

		int defenderDiceCount = attackerPlayer.getMaximumAllowableDices(toCountry, "Defender");
		if (defenderDiceCount > 0)
			defenderDiceCount = Common.getRandomNumberInRange(1, defenderDiceCount);

		Player defenderPlayer = toCountry.getPlayer();
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

			if (attackerDice > defencerDice) {
				IOHelper.print("----> attacker wins for dice " + (i + 1));
				Common.PhaseActions.add("----> attacker wins for dice " + (i + 1));

				toCountry.decreaseArmyCount(1);

			} else {
				IOHelper.print("----> defender wins for dice " + (i + 1));

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
	}

	private boolean countryVisited(Country country, HashMap<Country, Integer> visitedCountries, Player player) {
		if (country.getPlayer().getPlayerId() == player.getPlayerId() && !visitedCountries.containsKey(country))
			return true;
		else
			return false;
	}

}
