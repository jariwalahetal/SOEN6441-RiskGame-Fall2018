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

/**
 * Test class for testing game functionality
 * 
 * @author jasraj
 *
 */
public class GameTest {

	Map map;
	Game game;
	Game game2;

	String mapToTest = "Africa.map";
	Integer playerCount = 5;
	Player p1;
	Country c1;
	Country c2;

	/**
	 * Test Method for assign countries to player after reading the map
	 */
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
			ArrayList<Country> playerCountries = game.getCurrentPlayerCountries();

			int id = Common.getRandomNumberInRange(0, playerCountries.size() - 1);

			game.addArmyToCountry(playerCountries.get(id).getCountryName());
		}
	}

	/**
	 * Test method for checking current reinforcement phase
	 */
	@Test
	public void testCurrentPhaseIsReinforcement() {
		// Phase should be updated directly by model
		assertEquals(PhaseEnum.Reinforcement, game.getGamePhase());
	}

	/**
	 * Test Method for game play functionality
	 */

	@Test
	public void testGamePlay() {
		int iterationCount = 15;

		while (iterationCount > 0) {
			// Generate reinforcement for player
			Player currentPlayer = game.getCurrentPlayer();
			ArrayList<Country> playerCountries = game.getCurrentPlayerCountries();
			List<Integer> countryIds = playerCountries.stream().map(c -> c.getCountryId()).collect(Collectors.toList());
			boolean isPhaseUpdated = false;
			int reinforcementCount = (int) Math.floor(playerCountries.size() / 3);

			for (Continent continent : map.getContinentList()) {
				List<Integer> continentCountryIds = continent.getCountryList().stream().map(c -> c.getCountryId())
						.collect(Collectors.toList());
				boolean hasPlayerAllCountries = countryIds.containsAll(continentCountryIds);
				if (hasPlayerAllCountries)
					reinforcementCount += continent.getControlValue();
			}
			reinforcementCount = reinforcementCount < 3 ? 3 : reinforcementCount;

			// place the armies on random countries for the player
			while (currentPlayer.getNoOfReinforcedArmies() > 0) {
				game.addArmyToCountry(playerCountries.get(Common.getRandomNumberInRange(0, playerCountries.size() - 1))
						.getCountryName());
			}

			assertEquals(0, currentPlayer.getNoOfUnassignedArmies());
			assertEquals(0, currentPlayer.getNoOfReinforcedArmies());

			// Do attack
			game.attackPhase();

			// Randomly select a country to move armies from
			Country fromCountry = playerCountries.get(Common.getRandomNumberInRange(0, playerCountries.size() - 1));
			int previousFromCountryArmiesCount = fromCountry.getnoOfArmies();

			// Randomly select a neighboring country to move armies in
			ArrayList<String> neigbouringCountries = game.getNeighbouringCountries(fromCountry.getCountryName());

			if (neigbouringCountries != null && neigbouringCountries.size() > 0) {
				String toCountryName;
				if (neigbouringCountries.size() == 1) {
					toCountryName = neigbouringCountries.get(0);
				} else {
					toCountryName = neigbouringCountries
							.get(Common.getRandomNumberInRange(0, neigbouringCountries.size() - 1));
				}

				// Randomly generate army count to move
				// Because we don't want to move all countries
				if (previousFromCountryArmiesCount > 1) {
					int armyCountToMove = Common.getRandomNumberInRange(0, previousFromCountryArmiesCount - 1);

					boolean isFortifySuccessFull = game.fortificationPhase(fromCountry.getCountryName(), toCountryName,
							armyCountToMove);
					assertTrue(isFortifySuccessFull);

					assertEquals(previousFromCountryArmiesCount - armyCountToMove, fromCountry.getnoOfArmies());
					isPhaseUpdated = isFortifySuccessFull;
				}
			}
			iterationCount--;
			if (!isPhaseUpdated) {
				game.setGamePhase(PhaseEnum.Reinforcement);
				game.reinforcementPhaseSetup();
			}
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

		for (Continent c : map1.getContinentList()) {
			for (Country countryToTest : c.getCountryList()) {
				assertEquals(1, countryToTest.getnoOfArmies());
			}
		}
	}

	@Test
	public void totalArmiesTest() {
		Map map2 = new Map();
		InitialPlayerSetup setup = new InitialPlayerSetup();
		map2.setMapName(mapToTest);
		map2.readMap();
		int totalArmies = 0;

		Game game2 = new Game(map2);
		for (int i = 0; i < playerCount; i++) {
			String playerName = "Bestplayer " + i;
			Player player = new Player(i, playerName);

			game2.addPlayer(player);
		}
		game2.startUpPhase();
		ArrayList<Player> players = game2.getAllPlayers();
		for (Player player : players) {
			totalArmies += player.getNoOfUnassignedArmies();
			ArrayList<Country> playersCountries = game2.getPlayersCountry(player);
			for (Country singleCountry : playersCountries) {
				totalArmies += singleCountry.getnoOfArmies();
			}
			assertEquals(totalArmies, InitialPlayerSetup.getInitialArmyCount(playerCount));
			totalArmies = 0;
		}
	}

	@Test
	public void getNeighbouringCountriesTest() {
		Map map3 = new Map();
		map3.setMapName(mapToTest);
		map3.readMap();

		Game game3 = new Game(map3);
		for (int i = 0; i < 2; i++) {
			String playerName = "Bestplayer " + i;
			Player player = new Player(i, playerName);
			game3.addPlayer(player);
		}
		game3.startUpPhase();
		// ArrayList<String> neighCountries = game3.getNeighbouringCountries("Morocco");
		Country country = game3.getMap().getCountryList().stream()
				.filter(x -> x.getCountryName().equalsIgnoreCase("Morocco")).findAny().orElse(null);
		if (country != null) {
			ArrayList<String> neighCountries = country.getNeighboursString();
			ArrayList<String> actualNeigboursList = new ArrayList<String>();
			actualNeigboursList.add("Western Sahara");
			actualNeigboursList.add("Algeria");
			assertTrue(isTwoArrayListsWithSameValues(neighCountries, actualNeigboursList));
		}
	}

	public boolean isTwoArrayListsWithSameValues(ArrayList<String> list1, ArrayList<String> list2) {
		if (list1 == null && list2 == null)
			return true;
		if ((list1 == null && list2 != null) || (list1 != null && list2 == null))
			return false;

		if (list1.size() != list2.size())
			return false;
		for (String itemList1 : list1) {
			if (!list2.contains(itemList1))
				return false;
		}
		return true;
	}
}
