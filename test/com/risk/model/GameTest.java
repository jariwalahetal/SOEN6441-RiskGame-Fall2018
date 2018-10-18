package com.risk.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.risk.helper.Common;
import com.risk.helper.IOHelper;
import com.risk.helper.InitialPlayerSetup;
import com.risk.helper.PhaseEnum;

public class GameTest {

	Map map;
	Game game;
	Game game2;

	String mapToTest = "Africa.map";
	Integer playerCount = 5;
	Player p1;
	Country c1;
	Country c2;

	@Before
	public void readMapAndAssignCountries() {
		IOHelper.print("Test: creating maps and generating player randomly");
		map = new Map();
		map.setMapName(mapToTest);
		map.readMap();
		

		game = new Game(map);
		for (int i = 0; i < playerCount; i++) {
			String playerName = "player " + i;
			Player player = new Player(i, playerName);
			game.addPlayer(player);
		}
		game.startUpPhase();

		// Loop until all armies are assigned for all players
		while (game.getGamePhase() == PhaseEnum.Startup) {
			// Randomly increase army for the country of player
			ArrayList<Country> playerCountries = game.getPlayerCountries();

			int id = Common.getRandomNumberInRange(0, playerCountries.size() - 1);

			game.addArmyToCountry(playerCountries.get(id).getCountryId());
		}
	}
	@Test
	public void testCurrentPhaseIsReinforcement() {
		// Phase should be updated directly by model
		assertEquals(PhaseEnum.Reinforcement, game.getGamePhase());
	}
	@Test
	public void testGamePlayRandomTimes() {
		int iterationCount = Common.getRandomNumberInRange(10, 25);

		while (iterationCount > 0) {
			// Generate reinforcement for player
			Player currentPlayer = game.getCurrentPlayer();
			ArrayList<Country> playerCountries = game.getPlayerCountries();
			List<Integer> countryIds = playerCountries.stream().map(c -> c.getCountryId()).collect(Collectors.toList());

			int reinforcementCount = (int) Math.floor(playerCountries.size() / 3);

			for (Continent continent : map.getContinentList()) {
				List<Integer> continentCountryIds = continent.getCountryList().stream().map(c -> c.getCountryId())
						.collect(Collectors.toList());
				boolean hasPlayerAllCountries = countryIds.containsAll(continentCountryIds);
				if (hasPlayerAllCountries)
					reinforcementCount += continent.getControlValue();
			}
			reinforcementCount = reinforcementCount < 3 ? 3 : reinforcementCount;

			// varify generate reinforcement value with actual value
			assertEquals(reinforcementCount, currentPlayer.getNoOfReinforcedArmies());
			
			//place the armies on random countries for the player
			while(currentPlayer.getNoOfReinforcedArmies() > 0)
			{
				game.addArmyToCountry(playerCountries.get(Common.getRandomNumberInRange(0, playerCountries.size()-1)).getCountryId());
			}

			assertEquals(0, currentPlayer.getNoOfUnassignedArmies());
			assertEquals(0, currentPlayer.getNoOfReinforcedArmies());
			
			//Do attack
			game.attackPhase();

			// Randomly select a country to move armies from
			Country fromCountry = playerCountries.get(Common.getRandomNumberInRange(0, playerCountries.size() - 1));
			int previousFromCountryArmiesCount = fromCountry.getnoOfArmies();

			// Randomly select a neighbouring country to move armies in
			ArrayList<Country> neigbouringCountries = game
					.getNeighbouringCountriesForFortification(fromCountry.getCountryId());

			if (neigbouringCountries != null && neigbouringCountries.size() > 0) {
				Country toCountry;
				if (neigbouringCountries.size() == 1) {
					toCountry = neigbouringCountries.get(0);
				} else {
					toCountry = neigbouringCountries
							.get(Common.getRandomNumberInRange(0, neigbouringCountries.size() - 1));
				}
				int previousToCountryCount = toCountry.getnoOfArmies();

				// Randomly generate army count to move
				// Because we don't want to move all countries
				if (previousFromCountryArmiesCount > 1) {
					int armyCountToMove = Common.getRandomNumberInRange(0, previousFromCountryArmiesCount - 1);

					boolean isFortifySuccessFull = game.fortifyCountry(fromCountry.getCountryId(),
							toCountry.getCountryId(), armyCountToMove);
					assertTrue(isFortifySuccessFull);

					assertEquals(previousFromCountryArmiesCount - armyCountToMove, fromCountry.getnoOfArmies());
					assertEquals(previousToCountryCount + armyCountToMove, toCountry.getnoOfArmies());
				}
			}
			game.setNextPlayerReinforcement();
			iterationCount--;
		}
	}
	
	@Test
	public void assignCountryToPlayerTest() {
		
		Map map1 = new Map();
		map1.setMapName(mapToTest);
		map1.readMap();
		
		Game game1 = new Game(map1);
		for (int i = 0; i < 2; i++) {
			String playerName = "Bestplayer " + i;
			Player player = new Player(i, playerName);
			game1.addPlayer(player);
		}
		game1.startUpPhase();
		
		for(Continent c: map1.getContinentList()) {
			for(Country countryToTest :c.getCountryList()) {
				assertEquals(1,countryToTest.getnoOfArmies());
			}
		}
	}
	
//	@Test
//	public void totalArmiesTest(){
//		Map map2 = new Map();
//		map2.setMapName(mapToTest);
//		map2.readMap();
//		
//		Game game2 = new Game(map2);
//		for (int i = 0; i < playerCount; i++) {
//			String playerName = "Bestplayer " + i;
//			Player player = new Player(i, playerName);
//			assert(player.getNoOfUnassignedArmies()+);
//			game2.addPlayer(player);
//		}
//		game2.startUpPhase();
//	}
}
