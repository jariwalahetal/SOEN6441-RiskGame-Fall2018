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
	public boolean reinforce(Player player) {
		// TODO Auto-generated method stub

		Country country = player.getWeakestCountry();
		IOHelper.print("Adding reinforcement army in " + country.getCountryName());
		player.decreaseReinforcementArmyCount();
		country.increaseArmyCount(1);

		return true;
}

	@Override
	public void attack(Player attackerPlayer) {
		// TODO Auto-generated method stub
		IOHelper.print("Player's strategy is benevolent so Attack skipped");

	}

	@Override
	public boolean fortify(Player player) {
		// TODO Auto-generated method stub
		return true;
	}

}
