package com.risk.model;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
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
		map = new Map(mapToTest);
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
			ArrayList<Country> playerCountries = game.getCurrentPlayer().getAssignedCountryList();

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
	 * Test Method for calculation for reinforcement armies
	 */
	@Test
	public void testReinforcementPhase() {
	
		// Generate reinforcement for player
		Player currentPlayer = game.getCurrentPlayer();
		if(!currentPlayer.isCardsAvailableForTradeInReinforcement()) {
		ArrayList<Country> playerCountries = game.getCurrentPlayer().getAssignedCountryList();
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

		assertEquals(reinforcementCount, currentPlayer.getNoOfReinforcedArmies());
		}
	}
	
	/**
	 * Test Method for calculation for assignment of armies and phase should be shifted to attack at end
	 */
	@Test
	public void testReinforcementAndArmiesAssignment() {
	
		// Generate reinforcement for player
		Player currentPlayer = game.getCurrentPlayer();
		if(!currentPlayer.isCardsAvailableForTradeInReinforcement()) {
			ArrayList<Country> playerCountries = game.getCurrentPlayer().getAssignedCountryList();
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
	
			assertEquals(reinforcementCount, currentPlayer.getNoOfReinforcedArmies());
			
			// place the armies on random countries for the player
			while (currentPlayer.getNoOfReinforcedArmies() > 0) {
				game.addArmyToCountry(playerCountries.get(Common.getRandomNumberInRange(0, playerCountries.size() - 1))
						.getCountryName());
			}
	
			assertEquals(0, currentPlayer.getNoOfUnassignedArmies());
			assertEquals(0, currentPlayer.getNoOfReinforcedArmies());
			assertEquals(PhaseEnum.Attack, game.getGamePhase());
		}
	}
	
	/**
	 * This is used to test Attack Phase
	 */
	@Test
	public void testAttackPhase()
	{ 	Player currentPlayer = game.getCurrentPlayer();
	    ArrayList<String> attackingCountryList = game.getCurrentPlayer().getCountriesWithArmiesGreaterThanOne();
	    ArrayList<String> attackedCountryList;
	    Country attackingCountry,defendingCountry;
	    
	    int attackingDiceCount,defendingDiceCount, attackingCountryArmyCount, defendingCountryArmyCount;
	    Player defenderPlayer; 
    	    
	    for(String attackingCountryName:attackingCountryList)
	    { attackedCountryList = game.getCurrentPlayer().getUnAssignedNeighbouringCountries(attackingCountryName);
  	      attackingCountry = game.getCountryFromName(attackingCountryName);
  	      attackingCountryArmyCount = attackingCountry.getnoOfArmies();
	      for(String attackedCountryName : attackedCountryList)
	      { defenderPlayer = game.getAllPlayers().stream().filter(p -> p.getAssignedCountryList().contains(attackedCountryName))
			.findAny().orElse(null);
				    	  
	    	defendingCountry = game.getCountryFromName(attackedCountryName);
	    	defendingCountryArmyCount = defendingCountry.getnoOfArmies();
		     
	    	attackingDiceCount = 1;
	        defendingDiceCount = 1;
	        
	        game.attackPhase(attackingCountryName, attackedCountryName, attackingDiceCount, defendingDiceCount);
	 	   
	        if (defendingCountryArmyCount>defendingCountry.getnoOfArmies())
	        {   assertEquals(defendingCountryArmyCount, defendingCountry.getnoOfArmies()+1);
	    		assertEquals(attackingCountryArmyCount, attackingCountry.getnoOfArmies());
	        }
	        else if ( attackingCountryArmyCount > attackingCountry.getnoOfArmies())
	        {   if(currentPlayer.isConquered())
	             {  assertEquals(defendingCountry.getnoOfArmies(),1);
		    		assertEquals(attackingCountryArmyCount, attackingCountry.getnoOfArmies()+1);	
		    		assertEquals(defendingCountry.getPlayer().getPlayerId(),currentPlayer.getPlayerId());
	             }
	            else 
	             { assertEquals(defendingCountryArmyCount, defendingCountry.getnoOfArmies());
		    		assertEquals(attackingCountryArmyCount, attackingCountry.getnoOfArmies()+1);	
	             }
	        	
	        }
	        break;
	    }
	      break;
	    }
	    
	}
	
	/**
	 * This is used to test Move armies after attack
	 */
	@Test
	public void testMoveArmiesAfterAttack()
	{ Player currentPlayer = game.getCurrentPlayer(); 
	   ArrayList<String> attackingCountryList = game.getCurrentPlayer().getCountriesWithArmiesGreaterThanOne();
	    ArrayList<String> attackedCountryList;
	    Country attackingCountry,defendingCountry;
	    int attackingCountryArmyCount, defendingCountryArmyCount;
	    Player defenderPlayer; 
//	    currentPlayer.isConquered() = true;	    
  	   for(String attackingCountryName:attackingCountryList)
	    { attackedCountryList = game.getCurrentPlayer().getUnAssignedNeighbouringCountries(attackingCountryName);
	      attackingCountry = game.getCountryFromName(attackingCountryName);
	      attackingCountry.setNoOfArmies(5);
	      attackingCountryArmyCount = attackingCountry.getnoOfArmies();
	      currentPlayer.setFromCountry(attackingCountry);
	  	    		  
	      for(String attackedCountryName : attackedCountryList)
	      { defenderPlayer = game.getAllPlayers().stream().filter(p -> p.getAssignedCountryList().contains(attackedCountryName))
			.findAny().orElse(null);				    	  
	    	defendingCountry = game.getCountryFromName(attackedCountryName);
	    	defendingCountry.setNoOfArmies(1);
	    	defendingCountryArmyCount = defendingCountry.getnoOfArmies();
		    currentPlayer.setToCountry(defendingCountry);
	    	game.moveArmyAfterAttack(3);
	 	    assertEquals(defendingCountryArmyCount+3, defendingCountry.getnoOfArmies());
		    assertEquals(attackingCountryArmyCount-3, attackingCountry.getnoOfArmies());	
	    	break;	    	
	      }
	      break;
	      }	    
		
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
			ArrayList<Country> playerCountries = game.getCurrentPlayer().getAssignedCountryList();
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
			//TODO: change this
			//game.attackPhase();

			// Randomly select a country to move armies from
			Country fromCountry = playerCountries.get(Common.getRandomNumberInRange(0, playerCountries.size() - 1));
			int previousFromCountryArmiesCount = fromCountry.getnoOfArmies();

			// Randomly select a neighboring country to move armies in
			ArrayList<String> neigbouringCountries = game.getCurrentPlayer().getAssignedNeighbouringCountries(fromCountry.getCountryName());

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
	
	/**
	 * This method tests if country is assigned to the player or not.
	 */
	@Test
	public void assignCountryToPlayerTest() {

		Map map1 = new Map(mapToTest);
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
	
	/**
	 * This will test getMaximumAllowableDices
	 */
	@Test
	public void getMaximumAllowableDicesTest()
	{
		Player player = game.getCurrentPlayer();
		String countryName = player.getCountriesWithArmiesGreaterThanOne().get(0);
		Country country = game.getCountryFromName(countryName);
		country.setNoOfArmies(5);
        game.setGamePhase(PhaseEnum.Attack);
		
		int diceCount = game.getMaximumAllowableDices(countryName, "Attacker");
		assertEquals(3, diceCount);

		country.setNoOfArmies(2);
		diceCount = game.getMaximumAllowableDices(countryName, "Attacker");
		
		assertEquals(1, diceCount);
     }
	
	/**
	 * This method tests the total armies.
	 */
	@Test
	public void totalArmiesTest() {
		Map map2 = new Map(mapToTest);
		InitialPlayerSetup setup = new InitialPlayerSetup();
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
			ArrayList<Country> playersCountries = player.getAssignedCountryList();
			//getPlayersCountry(player);
			for (Country singleCountry : playersCountries) {
				totalArmies += singleCountry.getnoOfArmies();			
			}
			
			assertEquals(totalArmies, InitialPlayerSetup.getInitialArmyCount(playerCount));
			totalArmies = 0;
		}
	}
	
	/**
	 * This Junit is used to test getCountryFromName function
	 */
	@Test
	public void getCountryFromNameTest()
	{  for(Country c:game.getMap().getCountryList())
		  {Country countryName =  game.getCountryFromName(c.getCountryName());
			assertEquals(countryName.getCountryName(), c.getCountryName());
		  }
	}
		
	/**
	 * This method tests the neighboring countries in the startup phase
	 */
	@Test
	public void getNeighbouringCountriesTest() {
		Map map3 = new Map(mapToTest);
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
	
	/**
	 * This will test isMapConquered
	 */
	@Test
	public void isMapConqueredTest()
	{ Player player = game.getCurrentPlayer();
	   
		for(Country country:map.getCountryList())
	    { if (country.getPlayer().getPlayerId()!=player.getPlayerId())
			player.assignCountryToPlayer(country);
	    }		
			
		Boolean result = game.isMapConquered();
	    assertEquals(result, true);
	
	}
	
	/**
	 * This will test game is saved or not
	 */
	@Test
	public void isGameSaved()
	{
		try {
			game.saveGame();
			String fileTime = new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date());
			FileInputStream fileInput = new FileInputStream("assets/Saved_Games/" + fileTime);
			ObjectInputStream in =new ObjectInputStream(fileInput);
			Game testObject=(Game) in.readObject();
			in.close();
			fileInput.close();
			assertEquals(game, testObject);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void isGameLoad()
	{
		try {
			Game gameLoad=null;
			String gameTitle="Single";
			game.saveGame();
			gameLoad=game.loadGame(gameTitle);	
			assertEquals(game, gameLoad);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will tear down variables.
	 */
	@After
	public void tearDown() {
		map = null;
		game = null;
		game2 = null;
		mapToTest = null;
		playerCount = null;
		p1 = null;
		c1 = null;
		c2 = null;

	}
	/**
	 * This method tests the total armies.
	 * @param list1 array list number 1
	 * @param list2 array list number 2
	 * @return boolean
	 */
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
