package com.risk.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import com.risk.helper.CardEnum;
import com.risk.helper.Common;
import com.risk.helper.EnumColor;
import com.risk.helper.IOHelper;
import com.risk.helper.InitialPlayerSetup;

/**
 * Player Class
 * 
 * @author sadgi
 * @version 1.0.0
 * @since 27-September-2018
 */
public class Player {
	private int playerId;
	private String name;
	private EnumColor color;
	private int noOfUnassignedArmies;
	private int noOfReinforcedArmies;
	private int noOfTradedArmies;
	private int tradingCount;
	private ArrayList<Country> assignedCountryList = new ArrayList<Country>();
	private final int MINIMUM_REINFORCEMENT_PLAYERS = 3;
	Country attackingCountry;
	Country attackedCountry;
	Boolean isConquered = false;
	private ArrayList<CardEnum> playerCards = new ArrayList<>();
	// TODO: implement lost logic in game check whole flow
	private boolean isLost = false;
	private ArrayList<Integer> diceOutComes = new ArrayList<>();
	private Boolean eligibleForCard = false;

	public Boolean isEligibleForCard() {
		return eligibleForCard;
	}

	public void setEligibleForCard(Boolean eligibleForCard) {
		this.eligibleForCard = eligibleForCard;
	}

	/**
	 * This is a constructor of Player Class which sets playerId, name, and color.
	 * 
	 * @param playerId,id
	 *            of the player
	 * @param name,name
	 *            of the player
	 */
	public Player(int playerId, String name) {
		super();
		this.playerId = playerId;
		this.name = name;
		this.color = InitialPlayerSetup.getPlayerColor(playerId);

		// TODO: Remove after development
		this.playerCards.add(CardEnum.Artillery);
		this.playerCards.add(CardEnum.Artillery);
		this.playerCards.add(CardEnum.Artillery);
	}

	/**
	 * This method returns the Countries assigned to the particular Player
	 * 
	 * @return assignedCountryList
	 */
	public ArrayList<Country> getAssignedCountryList() {
		return assignedCountryList;
	}

	/**
	 * This method returns the number of unassigned army unit
	 * 
	 * @return noOfUnassignedArmies int
	 */
	public int getNoOfUnassignedArmies() {
		return noOfUnassignedArmies;
	}

	/**
	 * This method set the number of unassigned army unit
	 * 
	 * @param noOfUnassignedArmies,
	 *            number of unassigned armies
	 */
	public void setNoOfUnassignedArmies(int noOfUnassignedArmies) {
		this.noOfUnassignedArmies = noOfUnassignedArmies;
	}

	/**
	 * This method returns the number of reinforcement army units
	 * 
	 * @return noOfReinforcedArmies int
	 */
	public int getNoOfReinforcedArmies() {
		return noOfReinforcedArmies;
	}

	/**
	 * Mark player as lost
	 */
	public void setLost() {
		isLost = true;
	}

	/**
	 * Gets is player is lost
	 * 
	 * @return isLost boolean
	 */
	public boolean getIsLost() {
		return isLost;
	}

	/**
	 * Returns trading count
	 * 
	 * @return tradingCount Integer
	 */
	public int getTradingCount() {
		return tradingCount;
	}

	/**
	 * Sets trading count
	 * 
	 * @param tradingCount
	 *            Integer
	 */
	public void setTradingCount(int tradingCount) {
		this.tradingCount = tradingCount;
	}

	/**
	 * Gets no of traded armies
	 * 
	 * @return noOfTradedArmies Integer
	 */
	public int getNoOfTradedArmies() {
		return noOfTradedArmies;
	}

	/**
	 * Set no of traded armies
	 * 
	 * @param nofOfTradedArmies
	 *            Integer
	 */
	public void setNoOfTradedArmies(int nofOfTradedArmies) {
		this.noOfTradedArmies = nofOfTradedArmies;
	}

	/**
	 * Gets number of countries defended in current turn
	 * 
	 * @return countryDefendedInCurrentTurn Integer
	 */
//	public int GetCountryDefendedInCurrentTurn() {
//		return countryDefendedInCurrentTurn;
//	}

	/**
	 * Resets number of countries defended in current turn
	 * 
	 * @return
	 */
//	public void ResetCountryDefendedInCurrentTurn() {
//		countryDefendedInCurrentTurn = 0;
//	}

	/**
	 * This method set the number of reinforcement army units
	 * 
	 * @param noOfReinforcedArmies
	 *            int
	 */
	public void setNoOfReinforcedArmies(int noOfReinforcedArmies) {
		this.noOfReinforcedArmies = noOfReinforcedArmies;
	}

	/**
	 * This method return the id of the player
	 * 
	 * @return playerId int
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * This method return the name of the player
	 * 
	 * @return name String
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method return color of the EnumColor class
	 * 
	 * @return color EnumColor
	 */
	public EnumColor getColor() {
		return color;
	}

	/**
	 * This method decreases unassigned army count
	 */
	public void decreaseUnassignedArmyCount() {
		if (noOfUnassignedArmies > 0)
			noOfUnassignedArmies--;
	}

	/**
	 * This method increases unassigned army count
	 */
	public void increaseUnassignedArmyCount() {
		noOfUnassignedArmies++;
	}

	/**
	 * This method decreases reinforcement link
	 * 
	 */
	public void decreaseReinforcementArmyCount() {
		if (noOfReinforcedArmies > 0)
			noOfReinforcedArmies--;
	}

	/**
	 * Assigns the current coutry to player
	 * 
	 * @param newCountry
	 */
	public void assignCountryToPlayer(Country newCountry) {
		assignedCountryList.add(newCountry);
		newCountry.setCountryColor(this.getColor());
		newCountry.setPlayerId(this.getPlayerId());
	}

	/**
	 * UnAssigns the current coutry to player
	 * 
	 * @param newCountry
	 */
	public void unAssignCountryToPlayer(Country newCountry) {
		assignedCountryList.remove(newCountry);
	}

	/**
	 * Add army to the country for startup phase
	 * 
	 * @param countryName,name
	 *            of thr country
	 * @return false, if phase is not valid otherwise return true
	 */
	public boolean addArmyToCountryForStartup(String countryName) {

		if (getNoOfUnassignedArmies() == 0) {
			IOHelper.print("Player " + getName() + " doesn't have unassigned armies!");
			return true;
		}

		Country country = assignedCountryList.stream().filter(c -> c.getCountryName().equalsIgnoreCase(countryName))
				.findAny().orElse(null);
		if (country == null) {
			IOHelper.print("Country name -  " + countryName + " does not exist!");
			return false;
		}

		decreaseUnassignedArmyCount();
		country.increaseArmyCount(1);
		return true;
	}

	/**
	 * Method to perform fortification phase
	 * 
	 * @param sourceCountryName,
	 *            name of the source country of player
	 * @param destinationCountryName,
	 *            name of the destination country of the player
	 * @param noOfArmies,
	 *            number of armies to be moved
	 * @return true if no army need to move and false if source and destination
	 *         countries are null
	 */
	public boolean fortificationPhase(String sourceCountryName, String destinationCountryName, int noOfArmies) {

		Country sourceCountry = assignedCountryList.stream()
				.filter(c -> c.getCountryName().equalsIgnoreCase(sourceCountryName)).findAny().orElse(null);
		Country destinationCountry = assignedCountryList.stream()
				.filter(c -> c.getCountryName().equalsIgnoreCase(destinationCountryName)).findAny().orElse(null);

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

	/**
	 * Returns true if cards available for trading in reinforcement
	 * 
	 * @return
	 */
	public boolean IsCardsAvailableForTradeInReinforcement() {
		if (this.playerCards.size() >= 3)
			return true;
		else
			return false;
	}

	/**
	 * Returns true if setting up reinforcement armies allowed
	 * 
	 * @return
	 */
	public boolean IsAssigningReinforcementArmiesAllowed() {
		if (this.playerCards.size() >= 4) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Method to set up reinforcement phase
	 */
	public boolean setReinformcementArmies(ArrayList<Continent> continents) {
		if (!IsAssigningReinforcementArmiesAllowed()) {
			IOHelper.print("Cannot set reinforcement armies. Trade your cards first");
			return false;
		}
		// get reinforcement country count based on countries owned by player
		int countriesCount = (int) Math.floor(assignedCountryList.size() / 3);

		List<Integer> assignedCountryIds = assignedCountryList.stream().map(c -> c.getCountryId())
				.collect(Collectors.toList());

		for (Continent continent : continents) {
			List<Integer> continentCountryIds = continent.getCountryList().stream().map(c -> c.getCountryId())
					.collect(Collectors.toList());
			boolean hasPlayerAllCountries = assignedCountryIds.containsAll(continentCountryIds);
			if (hasPlayerAllCountries)
				countriesCount += continent.getControlValue();
		}

		countriesCount = countriesCount < MINIMUM_REINFORCEMENT_PLAYERS ? MINIMUM_REINFORCEMENT_PLAYERS
				: countriesCount;
		setNoOfReinforcedArmies(countriesCount);
		return true;
	}

	/**
	 * Add army to the country for reinforcement phase
	 * 
	 * @param countryName,name
	 *            of the country
	 * @return false, if phase is not valid otherwise return true
	 */
	public boolean addArmyToCountryForReinforcement(String countryName) {

		if (getNoOfReinforcedArmies() == 0) {
			IOHelper.print("Player " + getName() + " doesn't have unassigned armies!");
			return false;
		}

		Country country = assignedCountryList.stream().filter(c -> c.getCountryName().equalsIgnoreCase(countryName))
				.findAny().orElse(null);
		if (country == null) {
			IOHelper.print("Country name - " + countryName + " does not exist!");
			return false;
		}

		decreaseReinforcementArmyCount();
		country.increaseArmyCount(1);

		return true;
	}

	/**
	 * Method to get armies assigned to the country
	 * 
	 * @param sourceCountryName,name
	 *            of the source country of player
	 * @return noOfArmies, number of armies
	 */
	public int getArmiesAssignedToCountry(String sourceCountryName) {
		int noOfArmies = 0;

		for (Country country : this.assignedCountryList) {
			if (country.getCountryName().equals(sourceCountryName)) {
				noOfArmies = country.getnoOfArmies();
			}
		}
		return noOfArmies;
	}

	/**
	 * Method to get neighbouring countries of a given country
	 * 
	 * @param sourceCountryName,
	 *            name of the source country of player
	 * @return ArrayList , returning array list of countries.
	 */
	public ArrayList<String> getNeighbouringCountries(String sourceCountryName,
			ArrayList<String> assignedCountriesName) {
		ArrayList<String> neighborCountriesName = null;
		for (Country country : assignedCountryList) {
			String countryName = country.getCountryName();
			assignedCountriesName.add(countryName);
			if (country.getCountryName().equals(sourceCountryName)) {
				neighborCountriesName = country.getNeighboursString();
			}
		}
		return neighborCountriesName;
	}

	/**
	 * Method to get neighbouring countries of a given country
	 * 
	 * @param sourceCountryName,
	 *            name of the source country of player
	 * @return ArrayList , returning array list of countries.
	 */
	public ArrayList<String> getAssignedNeighbouringCountries(String sourceCountryName) {
		ArrayList<String> assignedCountriesName = new ArrayList<String>();
		ArrayList<String> neighborCountriesName = this.getNeighbouringCountries(sourceCountryName,
				assignedCountriesName);

		Iterator<String> it = neighborCountriesName.iterator();
		while (it.hasNext()) {
			String country = it.next();
			if (!assignedCountriesName.contains(country)) {
				it.remove();
			}
		}
		return neighborCountriesName;
	}

	/**
	 * Method to get neighbouring countries of a given country
	 * 
	 * @param sourceCountryName,
	 *            name of the source country of player
	 * @return ArrayList , returning array list of countries.
	 */
	public ArrayList<String> getUnAssignedNeighbouringCountries(String sourceCountryName) {
		ArrayList<String> assignedCountriesName = new ArrayList<String>();
		ArrayList<String> neighborCountriesName = this.getNeighbouringCountries(sourceCountryName,
				assignedCountriesName);

		Iterator<String> it = neighborCountriesName.iterator();
		while (it.hasNext()) {
			String country = it.next();
			if (assignedCountriesName.contains(country)) {
				it.remove();
			}
		}
		return neighborCountriesName;
	}

/**
 * This method will roll a Dice	
 * @param diceCount
 * @return
 */
	private void rollDice(int diceCount) {
		for (int i = 0; i < diceCount; i++) {
		   diceOutComes.add(Common.getRandomNumberInRange(1, 6));
		}
	}
	
	/**
	 * This method will process attack on given player
	 * 
	 * @param defenderPlayer
	 *            Player
	 * @param attackingCountry
	 *            Attacking country
	 * @param defendingCountry
	 *            Defending country
	 * @param attackingDices
	 *            attacking dices
	 * @param denfendingDices
	 *            defending dices
	 * @return true if suceessful
	 */
	public void attackPhase(Player defenderPlayer, Country attackingCountry, Country defendingCountry,
			int attackingDiceCount, int defendingDiceCount) {
		
		rollDice(attackingDiceCount);
		defenderPlayer.rollDice(defendingDiceCount);
		ArrayList<Integer> attackingDices = diceOutComes;		
		ArrayList<Integer> defendingDices = defenderPlayer.diceOutComes;
		
		IOHelper.print("Attacker's dices -- " + attackingDices);
		Common.PhaseActions.add("Attacker's dices -- " + attackingDices);

		IOHelper.print("Defender's dices -- " + defendingDices);
		Common.PhaseActions.add("Defender's dices -- " + defendingDices);

		this.attackingCountry = attackingCountry;
		this.attackedCountry = defendingCountry;

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

			if (defendingCountry.getnoOfArmies() == 0 )
			{	IOHelper.print("----> Attacker lost all armies in " + (i + 1)+" dice roll");
			  break;	
			}
			else if(attackingCountry.getnoOfArmies() ==0) {
				{	IOHelper.print("----> Defender lost all armies in " + (i + 1)+" dice roll");
				  break;
				}
			}
			
			
		}

		// Check if defending armies are 0 then acquire the country with cards
		if (defendingCountry.getnoOfArmies() == 0) {
			defendingCountry.setPlayerId(playerId);
			defenderPlayer.unAssignCountryToPlayer(defendingCountry);
			assignCountryToPlayer(defendingCountry);

			// attacker has to put minimum one army defending country (By Game rules)
			attackingCountry.decreaseArmyCount(1);
			defendingCountry.increaseArmyCount(1);
			isConquered = true;
			eligibleForCard = true;
			if (defenderPlayer.getAssignedCountryList().size() == 0) {
				ArrayList<CardEnum> defenderCards = defenderPlayer.getCards();
				for (CardEnum card : defenderCards) {
					this.addCardToPlayer(card);
				}

				defenderPlayer.RemoveAllCardsFromPlayer();

				defenderPlayer.setLost();

			}
		}
		
		
	}

	public boolean MoveArmyAfterAttack(int armiesCount) {
		if(isConquered)
		{	if (attackingCountry == null || attackedCountry == null) {
				IOHelper.print("Source or destination country is invalid!");
				return false;
			}

			attackingCountry.decreaseArmyCount(armiesCount);
			attackedCountry.increaseArmyCount(armiesCount);
			isConquered = false;
			return true;}
		else 
		{		IOHelper.print("Need to conquer country first");
			return false;
		}
	}

	/**
	 * Get number armies allowed to move from attacker to defending country
	 * 
	 * @return Integer
	 */
	public Integer GetAllowableArmiesMoveFromAttackerToDefender() {
		if (isConquered) {
			return attackingCountry.getnoOfArmies() - 1;
		}
		return -1;
	}

	/**
	 * 
	 * @param country
	 * @param playerStatus
	 * @return
	 */
	public int getMaximumAllowableDices(Country country, String playerStatus) {
		int allowableAttackingArmies = 0;
		int maximumDiceCount = 0;
		if (playerStatus.equals("Attacker")) {
			allowableAttackingArmies = country.getnoOfArmies() - 1;
			maximumDiceCount = 3;
		} else {
			allowableAttackingArmies = country.getnoOfArmies();
			maximumDiceCount = 2;
		}
		if (allowableAttackingArmies >= maximumDiceCount)
			allowableAttackingArmies = maximumDiceCount;

		return allowableAttackingArmies;
	}

	/**
	 * Get player cards
	 * 
	 * @return playerCars ArrayList<CardEnum>
	 */
	public ArrayList<CardEnum> getCards() {
		return playerCards;
	}

	/**
	 * Remove all cards from player
	 */
	public void RemoveAllCardsFromPlayer() {
		playerCards.clear();
	}

	/**
	 * Add card to player
	 * 
	 * @param card
	 */
	public void addCardToPlayer(CardEnum card) {
		playerCards.add(card);
		IOHelper.print("Added " + card + " card to player");
		Common.PhaseActions.add("Added " + card + " card to player");
	}
}
