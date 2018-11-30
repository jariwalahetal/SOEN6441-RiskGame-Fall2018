package com.risk.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import com.risk.helper.CardEnum;
import com.risk.helper.Common;
import com.risk.helper.EnumColor;
import com.risk.helper.IOHelper;
import com.risk.helper.InitialPlayerSetup;
import com.risk.model.strategies.PlayerStrategy;

/**
 * Player Class
 * 
 * @author sadgi
 * @version 1.0.0
 * @since 27-September-2018
 */
public class Player implements Serializable {
	private int playerId;
	private String name;
	private EnumColor color;
	private int noOfUnassignedArmies;
	private int noOfReinforcedArmies;
	private int noOfTradedArmies;
	private int tradingCount;
	private ArrayList<Country> assignedCountryList = new ArrayList<Country>();
	private final int MINIMUM_REINFORCEMENT_PLAYERS = 3;
	private Country fromCountry;
	private int noOfArmiesToMove;
	private Country toCountry;
	private Player attackedPlayer;
	private Boolean isConquered = false;
	private ArrayList<CardEnum> playerCards = new ArrayList<>();
	// TODO: implement lost logic in game check whole flow
	private boolean isLost = false;
	private ArrayList<Integer> diceOutComes = new ArrayList<>();
	private Boolean eligibleForCard = false;
	PlayerStrategy playerStrategy;

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
		this.addCardToPlayer(CardEnum.Artillery);
		this.addCardToPlayer(CardEnum.Artillery);
		this.addCardToPlayer(CardEnum.Artillery);
		this.playerId = playerId;
		this.name = name;
		this.color = InitialPlayerSetup.getPlayerColor(playerId);
	}

	public void setPlayerStrategy(PlayerStrategy playerStrategy) {
		this.playerStrategy = playerStrategy;
	}

	public PlayerStrategy getPlayerStrategy() {
		return playerStrategy;
	}

	public Player getAttackedPlayer() {
		return attackedPlayer;
	}

	public void setAttackedPlayer(Player attackedPlayer) {
		this.attackedPlayer = attackedPlayer;
	}

	public int getNoOfArmiesToMove() {
		return noOfArmiesToMove;
	}

	public void setNoOfArmiesToMove(int noOfArmiesToMove) {
		this.noOfArmiesToMove = noOfArmiesToMove;
	}

	public Country getFromCountry() {
		return fromCountry;
	}

	public void setFromCountry(Country fromCountry) {
		this.fromCountry = fromCountry;
	}

	public Country getToCountry() {
		return toCountry;
	}

	public void setToCountry(Country toCountry) {
		this.toCountry = toCountry;
	}

	public ArrayList<Integer> getDiceOutComes() {
		return diceOutComes;
	}

	/**
	 * This method is used to find if a player has conquered any country
	 * 
	 * @return
	 */
	public Boolean isConquered() {
		return isConquered;
	}

	public void setIsConquered(Boolean isConquered) {
		this.isConquered = isConquered;
	}

	/**
	 * This method will tell if a player is eligible for card exchange
	 * 
	 * @return
	 */
	public Boolean isEligibleForCard() {
		return eligibleForCard;
	}

	/**
	 * This method will set a player eligible for card exchange
	 * 
	 * @param eligibleForCard
	 */
	public void setEligibleForCard(Boolean eligibleForCard) {
		this.eligibleForCard = eligibleForCard;
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
	 * Assigns the current country to player
	 * 
	 * @param newCountry,
	 *            Country Object
	 */
	public void assignCountryToPlayer(Country newCountry) {
		assignedCountryList.add(newCountry);
		newCountry.setCountryColor(this.getColor());
//		newCountry.setPlayerId(this.getPlayerId());
		newCountry.setPlayer(this);
	}

	/**
	 * UnAssigns the current country to player
	 * 
	 * @param newCountry,
	 *            Country Object
	 */
	public void unAssignCountryToPlayer(Country newCountry) {
		assignedCountryList.remove(newCountry);
	}

	/**
	 * Add army to the country for startup phase
	 * 
	 * @param countryName,name
	 *            of the country
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
		IOHelper.print("Adding startup army in " + countryName);
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
	public boolean fortificationPhase() {
		return this.playerStrategy.fortify(this);
	}

	/**
	 * Returns true if cards available for trading in reinforcement
	 * 
	 * @return true, if card is available
	 */
	public boolean isCardsAvailableForTradeInReinforcement() {
		if (this.playerCards.size() >= 3)
			return true;
		else
			return false;
	}

	/**
	 * Returns true if setting up reinforcement armies allowed
	 * 
	 * @return true, if reinforcement assignation Armies allowed
	 */
	public boolean isAssigningReinforcementArmiesAllowed() {
		if (this.playerCards.size() >= 4) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Method to set up reinforcement phase
	 * 
	 * @param continents,
	 *            list of continents
	 * @return true, if armies can be set for reinforcement else false
	 */
	public boolean setReinformcementArmies(ArrayList<Continent> continents) {
		if (!isAssigningReinforcementArmiesAllowed()&&
				this.playerStrategy.getStrategyName()=="Human") {          //TO-DO might have to remove this
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

		countriesCount += getNoOfTradedArmies();
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
	public boolean reinforce() {
		return this.playerStrategy.reinforce(this);
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
	 * Method to get neighboring countries of a given country
	 * 
	 * @param sourceCountryName,
	 *            name of the source country of player
	 * @param assignedCountriesName,
	 *            list of assigned countries name
	 * @return ArrayList , returning array list of countries.
	 */
	public ArrayList<String> getNeighbouringCountries(String sourceCountryName,
			ArrayList<String> assignedCountriesName) {
		ArrayList<String> neighborCountriesName = new ArrayList<>();
		for (Country country : assignedCountryList) {
			String countryName = country.getCountryName();
			assignedCountriesName.add(countryName);
			if (country.getCountryName().equals(sourceCountryName)) {
				neighborCountriesName = country.getNeighboursString();
			}
		}
		return neighborCountriesName;
	}

	public ArrayList<Country> getNeighbouringCountries(Country sourceCountry,
			ArrayList<Country> assignedCountries) {
		ArrayList<Country> neighborCountriesName = new ArrayList<>();
		for (Country country : assignedCountryList) {
			assignedCountries.add(country);
			if (country.getCountryName().equals(sourceCountry.getCountryName())) {
				neighborCountriesName = country.getNeighbourCountries();
			}
		}
		return neighborCountriesName;
	}
	
	/**
	 * Method to get neighboring countries of a given country
	 * 
	 * @param sourceCountryName,
	 *            name of the source country of player
	 * @param assignedCountriesName,
	 *            list of assigned countries name
	 * @return ArrayList , returning array list of countries.
	 */
	public ArrayList<String> getConnectedCountriesRecursively(String sourceCountryName,
			ArrayList<String> assignedCountriesName, ArrayList<String> foundCountriesName) {

		ArrayList<String> neighbouringCounties = this.getNeighbouringCountries(sourceCountryName,
				assignedCountriesName);
		if (neighbouringCounties.size() > 0) {
			ArrayList<String> newConnectedNeighbours = new ArrayList<>();
			for (String neigbour : neighbouringCounties) {
				if (!foundCountriesName.contains(neigbour)) {
					foundCountriesName.add(neigbour);
					getConnectedCountriesRecursively(neigbour, assignedCountriesName, foundCountriesName);
				}
			}
		}
		return foundCountriesName;
	}

	public ArrayList<Country> getConnectedCountriesRecursively(Country sourceCountry,
			ArrayList<Country> assignedCountries, ArrayList<Country> foundCountries) {

		ArrayList<Country> neighbouringCounties = this.getNeighbouringCountries(sourceCountry,
				assignedCountries);
		if (neighbouringCounties.size() > 0) {
			ArrayList<String> newConnectedNeighbours = new ArrayList<>();
			for (Country neigbour : neighbouringCounties) {
				if (!foundCountries.contains(neigbour)) {
					foundCountries.add(neigbour);
					getConnectedCountriesRecursively(neigbour, assignedCountries, foundCountries);
				}
			}
		}
		return foundCountries;
	}
	
	/**
	 * Method to get neighboring countries of a given country
	 * 
	 * @param sourceCountryName,
	 *            name of the source country of player
	 * @return ArrayList , returning array list of countries.
	 */
	public ArrayList<String> getAssignedNeighbouringCountries(String sourceCountryName) {
		ArrayList<String> assignedCountriesName = new ArrayList<String>();
		ArrayList<String> neighborCountriesName = this.getConnectedCountriesRecursively(sourceCountryName,
				assignedCountriesName, new ArrayList<String>());

		Iterator<String> it = neighborCountriesName.iterator();
		while (it.hasNext()) {
			String country = it.next();
			if (!assignedCountriesName.contains(country) || country.equals(sourceCountryName)) {
				it.remove();
			}
		}
		return neighborCountriesName;
	}

	/**
	 * Method to get neighboring countries of a given country
	 * 
	 * @param sourceCountryName,
	 *            name of the source country of player
	 * @return ArrayList , returning array list of countries.
	 */
	public ArrayList<String> getUnAssignedNeighbouringCountries(String sourceCountryName) {
		ArrayList<String> neighborCountriesName = new ArrayList<String>();

		ArrayList<Country> neighborCountries = getUnAssignedNeighbouringCountriesObject(sourceCountryName);

		for (Country neighborCountry : neighborCountries) {
			neighborCountriesName.add(neighborCountry.getCountryName());
		}

		return neighborCountriesName;
	}

	public ArrayList<Country> getUnAssignedNeighbouringCountriesObject(String sourceCountryName) {
		ArrayList<Country> neighborCountries = new ArrayList<Country>();
		
		for (Country assignedCountry : assignedCountryList) {
				if (assignedCountry.getCountryName().equals(sourceCountryName)) {
					neighborCountries = assignedCountry.getNeighbourCountries().stream()
										.filter(x -> x.getPlayer().getPlayerId() != this.getPlayerId())
										.collect(Collectors.toCollection(ArrayList::new));
					break;
				}
			}
		return neighborCountries;
	}

	/**
	 * This method will roll a Dice
	 * 
	 * @param diceCount,
	 *            count of the dice
	 */
	public void rollDice(int diceCount) {
		diceOutComes.clear();
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
	 * @return true if successful
	 * @param defenderPlayer,
	 *            Player
	 * @param attackingCountry,
	 *            Attacking country
	 * @param defendingCountry,
	 *            Defending country
	 * @param attackingDiceCount,
	 *            attacking dices count
	 * @param defendingDiceCount,
	 *            defending dices count
	 */
	public void attackPhase() {

		isConquered = false;
		this.playerStrategy.attack(this);
	}

	/**
	 * This method will perform operation required after conquering a country
	 * 
	 * @param defenderPlayer
	 */
	public void conquerCountry(Player defenderPlayer) {

		toCountry.setPlayer(this);
		defenderPlayer.unAssignCountryToPlayer(toCountry);
		assignCountryToPlayer(toCountry);

		// attacker has to put minimum one army defending country (By Game rules)
		fromCountry.decreaseArmyCount(1);
		toCountry.increaseArmyCount(1);
		isConquered = true;
		eligibleForCard = true;
		if (defenderPlayer.getAssignedCountryList().size() == 0) {
			ArrayList<CardEnum> defenderCards = defenderPlayer.getCards();
			for (CardEnum card : defenderCards) {
				this.addCardToPlayer(card);
			}
			defenderPlayer.removeAllCardsFromPlayer();
			defenderPlayer.setLost();
		}
	}

	public boolean moveArmyAfterAttack(int armiesCount) {
		if (isConquered) {
			if (fromCountry == null || toCountry == null) {
				IOHelper.print("Source or destination country is invalid!");
				return false;
			}

			IOHelper.print("Moving " + armiesCount + " armies from " + fromCountry.getCountryName() + " to "
					+ toCountry.getCountryName());
			fromCountry.decreaseArmyCount(armiesCount);
			toCountry.increaseArmyCount(armiesCount);
			isConquered = false;
			return true;
		} else {
			IOHelper.print("Need to conquer country first");
			return false;
		}
	}

	/**
	 * Get number armies allowed to move from attacker to defending country
	 * 
	 * @return Integer
	 */
	public Integer getAllowableArmiesMoveFromAttackerToDefender() {
		if (isConquered) {
			return fromCountry.getnoOfArmies() - 1;
		}
		return -1;
	}

	/**
	 * 
	 * @param country,
	 *            Country Object
	 * @param playerStatus,
	 *            status of the player
	 * @return allowableAttackingArmies
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
	 * @return playerCards,list of cards of player
	 */
	public ArrayList<CardEnum> getCards() {
		return playerCards;
	}

	/**
	 * Remove all cards from player
	 */
	public void removeAllCardsFromPlayer() {
		playerCards.clear();
	}

	/**
	 * Add card to player
	 * 
	 * @param card,
	 *            type of card
	 */
	public void addCardToPlayer(CardEnum card) {
		playerCards.add(card);
		IOHelper.print("Added " + card + " card to player");
	}

	/**
	 * This method will return the countries for which armies count is greater than
	 * one
	 * 
	 * @return countries, list of countries
	 */
	public ArrayList<String> getCountriesWithArmiesGreaterThanOne() {
		ArrayList<String> countryNames = new ArrayList<String>();
		ArrayList<Country> countries = getCountriesObjectWithArmiesGreaterThanOne();
		for (Country country : countries) {
			countryNames.add(country.getCountryName());
		}

		return countryNames;
	}

	public ArrayList<Country> getCountriesObjectWithArmiesGreaterThanOne() {
		ArrayList<Country> countries = new ArrayList<Country>();
		for (Country country : getAssignedCountryList()) {
			if (country.getnoOfArmies() > 1) {
				countries.add(country);
			}
		}
		return countries;
	}

	/***
	 * This method will return true if an Attack move is possible for the current
	 * Player
	 * 
	 * @return true, if attack possible else false
	 */
	public Boolean isAttackPossible() {
		Boolean status = false;
		ArrayList<String> attackingFromCountries = getCountriesWithArmiesGreaterThanOne();
		if (attackingFromCountries.size() == 0) {
			return false;
		} else {
			for (String countryName : attackingFromCountries) {
				ArrayList<String> neighborCountries = getUnAssignedNeighbouringCountries(countryName);
				if (neighborCountries.size() > 0) {
					status = true;
					break;
				}
			}
		}
		return status;
	}
	
	public boolean getIsBot() {
		return this.playerStrategy.getIsBot();
	}
	
	public String getStrategyName() {
		return playerStrategy.getStrategyName();
	}
}
