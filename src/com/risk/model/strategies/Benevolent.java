package com.risk.model.strategies;
import com.risk.helper.IOHelper;
import com.risk.model.Country;
import com.risk.model.Player;

/**
 * A benevolent computer player strategy that focuses on protecting its weak
*countries (reinforces its weakest countries, never attacks, then fortifies in order to
*move armies to weaker countries).
*
* @author binay
* @version 1.0.0
* @since 19-November-2018
*/
public class Benevolent implements PlayerStrategy {

	@Override
	public boolean reinforce(Player player,String countryName) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void attack(Player attackerPlayer, Player defenderPlayer, Country attackingCountry, Country defendingCountry,
			int attackingDiceCount, int defendingDiceCount) {
		// TODO Auto-generated method stub
		IOHelper.print("Player's strategy is benevolent so Attack skipped");

	}

	@Override
	public boolean fortify(Player player, String sourceCountryName, String destinationCountryName, int noOfArmies) {
		// TODO Auto-generated method stub
		return true;
	}

}
