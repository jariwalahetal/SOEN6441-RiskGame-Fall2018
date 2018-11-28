package com.risk.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
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
import com.risk.model.strategies.PlayerStrategy;

/**
 * Test class for testing game functionality
 *
 */
public class PlayerTest {

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
	 * This is used to test Attack Phase
	 */
	@Test
	public void attackPhaseTest()
	{ 	Player currentPlayer = game.getCurrentPlayer();
	    PlayerStrategy  strategy=game.getCurrentPlayer().getPlayerStrategy();
	    ArrayList<String> attackingCountryList = game.getCurrentPlayer().getCountriesWithArmiesGreaterThanOne();
	    ArrayList<String> attackedCountryList;
	    Country attackingCountry,defendingCountry;
	    int attackingDiceCount,defendingDiceCount, attackingCountryArmyCount, defendingCountryArmyCount;
	    Player defenderPlayer; 
    	    
	    for(String attackingCountryName:attackingCountryList)
	    { attackedCountryList = currentPlayer.getUnAssignedNeighbouringCountries(attackingCountryName);
  	      attackingCountry = game.getCountryFromName(attackingCountryName);
  	      attackingCountryArmyCount = attackingCountry.getnoOfArmies();
	      for(String attackedCountryName : attackedCountryList)
	      { defendingCountry = game.getCountryFromName(attackedCountryName);
	    	
	    	defenderPlayer = game.getAllPlayers().stream().filter(p -> p.getPlayerId()==defendingCountry.getPlayer().getPlayerId())
			.findAny().orElse(null);
				    	  
	    	defendingCountryArmyCount = defendingCountry.getnoOfArmies();
		     
	    	attackingDiceCount = 1;
	        defendingDiceCount = 1;
	        //problem here
	        currentPlayer.attackPhase();
	        
	         
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
	public void moveArmiesAfterAttackTest()
	{ Player currentPlayer = game.getCurrentPlayer(); 
	   ArrayList<String> attackingCountryList = currentPlayer.getCountriesWithArmiesGreaterThanOne();
	    ArrayList<String> attackedCountryList;
	    Country attackingCountry,defendingCountry;
	    int attackingCountryArmyCount, defendingCountryArmyCount;
	    Player defenderPlayer; 
//	    currentPlayer.isConquered = true;	    
  	   for(String attackingCountryName:attackingCountryList)
	    { attackedCountryList = currentPlayer.getUnAssignedNeighbouringCountries(attackingCountryName);
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
	    	currentPlayer.moveArmyAfterAttack(3);
	 	    assertEquals(defendingCountryArmyCount+3, defendingCountry.getnoOfArmies());
		    assertEquals(attackingCountryArmyCount-3, attackingCountry.getnoOfArmies());	
	    	break;	    	
	      }
	      break;
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
		
		int diceCount = player.getMaximumAllowableDices(country, "Attacker");
		assertEquals(3, diceCount);

		country.setNoOfArmies(2);
		diceCount = player.getMaximumAllowableDices(country, "Attacker");
		
		assertEquals(1, diceCount);
     }

/**
 *  This will test getAllowableArmiesMoveFromAttackerToDefender
 */
	@Test
	public void getAllowableArmiesMoveFromAttackerToDefenderTest()
	{  	Player player = game.getCurrentPlayer();
//	    player.isConquered = true;
	    String countryName = player.getCountriesWithArmiesGreaterThanOne().get(0);
	    Country country = game.getCountryFromName(countryName);
	    country.setNoOfArmies(5);
	    player.setFromCountry(country); 
        int armies = player.getAllowableArmiesMoveFromAttackerToDefender();
		assertEquals(4, armies);
		
	}
		
	/**
	 * 
	 */
	@Test
	public void addArmyToCountryForStartup()
	{ Player player = game.getCurrentPlayer();
	  String countryName = player.getCountriesWithArmiesGreaterThanOne().get(0);
	  Country country = game.getCountryFromName(countryName);
	  country.setNoOfArmies(5);
      game.setGamePhase(PhaseEnum.Startup);
      player.setNoOfUnassignedArmies(10);
	  int unassignedArmyCountOld = player.getNoOfUnassignedArmies();
      int countryAssignedArmyCountOld = country.getnoOfArmies();
	  
      Boolean result = player.addArmyToCountryForStartup(countryName);
	  
	  int unassignedArmyCountNew = player.getNoOfUnassignedArmies();
      int countryAssignedArmyCountNew = country.getnoOfArmies();
	  
      assertEquals(result, true);
	  assertEquals(unassignedArmyCountNew, unassignedArmyCountOld-1);
	  assertEquals(countryAssignedArmyCountNew, countryAssignedArmyCountOld+1);
		  
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
	
}