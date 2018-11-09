package com.risk.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Random;
import java.util.HashMap;
import com.risk.helper.CardEnum;
import com.risk.helper.Common;
import com.risk.helper.GetArmiesByTrading;
import com.risk.helper.IOHelper;
import com.risk.helper.InitialPlayerSetup;
import com.risk.helper.PhaseEnum;

/**
 * Game Class
 * 
 * @author Binay
 * @version 1.0.0
 * @since 30-September-2018
 */
public class Game extends Observable {
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private int currentPlayerId;
	private PhaseEnum gamePhase;
	private Map map;
	private ArrayList<CardEnum> gameCards = new ArrayList<>();
	private Boolean isMapConqueredFlag = false;

	/**
	 * This is a constructor of Game class which will initialize the Map
	 * 
	 * @param map,
	 *            object of the map
	 */
	public Game(Map map) {
		super();
		this.map = map;
		this.setGamePhase(gamePhase.Startup);
	}

	/**
	 * This function returns the current player id.
	 * 
	 * @return currentPlayerId The current player Id.
	 */
	public int getCurrentPlayerId() {
		return currentPlayerId;
	}

	/**
	 * This function switches turn to the next player
	 */
	public void setNextPlayerTurn() {
		currentPlayerId++;

		if (currentPlayerId == playerList.size())
			currentPlayerId = 0;
	}

	/**
	 * This function returns a hash map which contains player id of all the players
	 * with their percentage of map acquired.
	 * 
	 * @return returnMap hash map of player id to percentage of map acquired.
	 */
	public HashMap<Integer, Float> getPercentageOfMapControlledForEachPlayer() {
		HashMap<Integer, Float> returnMap = new HashMap<Integer, Float>();
		float totalCountries = 0;
		ArrayList<Continent> allContinents = this.map.getContinentList();
		for (Continent continent : allContinents) {
			ArrayList<Country> country = continent.getCountryList();
			totalCountries = totalCountries + country.size();
		}
		for (Player player : this.playerList) {
			float playerNumberOfCountries = player.getAssignedCountryList().size();
			float percentage = (playerNumberOfCountries / totalCountries) * 100;
			returnMap.put(player.getPlayerId(), percentage);
		}
		return returnMap;
	}

	/**
	 * This function returns a hash map which contains player id of all the players
	 * with the number of continents they acquire.
	 * 
	 * @return returnMap hash map of player id to total number of continents
	 *         acquired.
	 */
	public HashMap<Integer, Integer> getNumberOfContinentsControlledForEachPlayer() {
		HashMap<Integer, Integer> returnMap = new HashMap<Integer, Integer>();
		ArrayList<Continent> allContinents = this.map.getContinentList();
		for (Player player : this.playerList) {
			boolean goToOuterLoop = false;
			int numberOfContinentsAquired = 0;
			for (Continent continent : allContinents) {
				for (Country country : continent.getCountryList()) {
					if (player.getAssignedCountryList().contains(country)) {
						// nada
					} else {
						goToOuterLoop = true;
						break;
					}
				}
				if (goToOuterLoop) {
					goToOuterLoop = false;
					continue;
				}
				numberOfContinentsAquired++;
			}
			returnMap.put(player.getPlayerId(), numberOfContinentsAquired);
		}
		return returnMap;
	}

	/**
	 * This function returns a hash map which contains player id of all the players
	 * with the number of armies they acquire.
	 * 
	 * @return returnMap hash map of player id to total number of armies acquired.
	 */
	public HashMap<Integer, Integer> getNumberOfArmiesForEachPlayer() {
		HashMap<Integer, Integer> returnMap = new HashMap<Integer, Integer>();
		for (Player player : this.playerList) {
			for (Country country : player.getAssignedCountryList()) {
				returnMap.put(player.getPlayerId(), country.getnoOfArmies());
			}
		}
		return returnMap;
	}

	/**
	 * This function returns the current player object
	 * 
	 * @return currentPlayer The current player object
	 */
	public Player getCurrentPlayer() {
		Player currentPlayer = playerList.get(currentPlayerId);
		return currentPlayer;
	}

	/**
	 * Method to get enum for game phase
	 * 
	 * @return gamePhase
	 */
	public PhaseEnum getGamePhase() {
		return gamePhase;
	}

	/**
	 * Method used to set phase of the game
	 * 
	 * @param gamePhase,
	 *            name of the game phase
	 */
	public void setGamePhase(PhaseEnum gamePhase) {
		this.gamePhase = gamePhase;
	}

	/**
	 * This function will add the player to the game(playerList)
	 * 
	 * @param player,
	 *            object of the player
	 */
	public void addPlayer(Player player) {
		this.playerList.add(player.getPlayerId(), player);
	}

	/**
	 * Method used to get map
	 * 
	 * @return map, object of map
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * Method use to get arraylist of players
	 * 
	 * @return playerList, ArrayList of players
	 */
	public ArrayList<Player> getAllPlayers() {
		return playerList;
	}

	/**
	 * Method to get armies assigned to the country
	 * 
	 * @param sourceCountryName,name
	 *            of the source country of player
	 * @return noOfArmies, number of armies
	 */
	public int getArmiesAssignedToCountry(String sourceCountryName) {
		int noOfArmies = this.getCurrentPlayer().getArmiesAssignedToCountry(sourceCountryName);
		return noOfArmies;
	}

	/**
	 * This function is called to check if correct operation is performed in the
	 * correct phase
	 * 
	 * @param phase, PhaseEnum
	 * @return true if phase is valid, else false
	 */
	private Boolean phaseCheckValidation(PhaseEnum phase) {
		if (phase == this.gamePhase)
			return true;
		else {
			return false;
		}
	}

	/**
	 * Method used to notify observer
	 * 
	 * @param game,
	 *            object of the game
	 */
	private void notifyObserverslocal(Game game) {
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Get country object from country name
	 * @param countryName, String
	 * @return Country
	 */
	public Country getCountryFromName(String countryName) {
		Country country = map.getCountryList().stream().filter(x -> x.getCountryName().equals(countryName)).findAny()
				.orElse(null);

		return country;
	}

	/**
	 * This function will randomly assign Countries to all players and assign one
	 * army to each country for a player
	 * 
	 */
	public void startUpPhase() {

		initilizeCardDeck();

		int noOfInitialArmies = InitialPlayerSetup.getInitialArmyCount(playerList.size());
		for (int i = 0; i < playerList.size(); i++) {
			playerList.get(i).setNoOfUnassignedArmies(noOfInitialArmies);
		}

		int countriesCount = map.getCountryList().size();
		int playerIndex = 0, playerCount = playerList.size();
		ArrayList<Integer> tempList = new ArrayList<>();
		// Here creating the list with indexes
		for (int i = 0; i < countriesCount; i++) {
			tempList.add(i);
		}

		// Shuffling the list for randomness
		Collections.shuffle(tempList, new Random());

		// assigning the shuffled countries from tempList to the players one by
		// one
		for (int i = 0; i < countriesCount; i++) {
			if (playerIndex == playerCount)
				playerIndex = 0;

			Country newCountry = map.getCountryList().get(tempList.get(i));
			playerList.get(playerIndex).assignCountryToPlayer(newCountry);
			playerList.get(playerIndex).decreaseUnassignedArmyCount();
			newCountry.increaseArmyCount(1);
			playerIndex++;
		}
		notifyObserverslocal(this);
	}

	/**
	 * Add army to the country for fortification phase
	 * 
	 * @param countryName,
	 *            name of the country
	 */
	public void addArmyToCountry(String countryName) {
		if (phaseCheckValidation(PhaseEnum.Attack) || phaseCheckValidation(PhaseEnum.Fortification)) {
			IOHelper.print("Cannot add army in attack or fortification phase");
			return;
		}
		if (phaseCheckValidation(PhaseEnum.Startup)) {
			boolean isProcessed = getCurrentPlayer().addArmyToCountryForStartup(countryName);

			if (isProcessed) {
				setNextPlayerTurn();
			}
		} else if (phaseCheckValidation(PhaseEnum.Reinforcement)) {
			getCurrentPlayer().addArmyToCountryForReinforcement(countryName);
		}
		Common.PhaseActions.add("adding army to country");
		updatePhase();
		notifyObserverslocal(this);
		// return true;
	}

	/**
	 * Method to update the phase of the game
	 * 
	 */
	public void updatePhase() {

		if (this.phaseCheckValidation(PhaseEnum.Startup)) {
			// Check all players have assigned armies to country or not
			long pendingPlayersCount = playerList.stream().filter(p -> p.getNoOfUnassignedArmies() > 0).count();

			if (pendingPlayersCount == 0) {
				this.setGamePhase(PhaseEnum.Reinforcement);
				currentPlayerId = 0;
				reinforcementPhaseSetup();

			}
		} else if (this.phaseCheckValidation(PhaseEnum.Reinforcement)) {
			if (getCurrentPlayer().getNoOfReinforcedArmies() == 0) {
				this.setGamePhase(PhaseEnum.Attack);
			}

		} else if (this.phaseCheckValidation(PhaseEnum.Fortification)) {
			this.setNextPlayerTurn();
			setGamePhase(PhaseEnum.Reinforcement);
			reinforcementPhaseSetup();
			notifyObserverslocal(this);

		} else if (this.phaseCheckValidation(PhaseEnum.Attack)) {
			this.setGamePhase(PhaseEnum.Fortification);
			notifyObserverslocal(this);
		}
	}

	/**
	 * Returns allowable dices for attacking country
	 * @param countryName, name of the country in String
	 * @param playerStatus, status of the player in String
	 * 
	 * @return Integer
	 */
	public int getMaximumAllowableDices(String countryName, String playerStatus) {
		int allowableAttackingArmies = 0;
		if (phaseCheckValidation(PhaseEnum.Attack)) {
			// Will also add validation if the attacker is assigned to player or not

			Country c = this.getCountryFromName(countryName);

			if (c != null) {
				allowableAttackingArmies = getCurrentPlayer().getMaximumAllowableDices(c, playerStatus);
			}
		}
		return allowableAttackingArmies;
	}

	/**
	 * Method for performing attack phase
	 */
	public Boolean attackPhase(String attackingCountry, String defendingCountry, int attackingDiceCount,
			int defendingDiceCount) {
		Common.PhaseActions.clear();

		Country attCountry = getCountryFromName(attackingCountry);
		Country defCountry = getCountryFromName(defendingCountry);

		if (attCountry == null || defCountry == null) {
			IOHelper.print("Set attacking and defending countries first");
			return false;
		}

		if (defCountry.getnoOfArmies() < defendingDiceCount) {
			IOHelper.print("Defender has no sufficient armies");
			return false;
		}

		Player defenderPlayer = playerList.stream().filter(p -> p.getAssignedCountryList().contains(defCountry))
				.findAny().orElse(null);

		if (defenderPlayer == null) {
			IOHelper.print("Cannot find defender player");
			return false;
		}

		getCurrentPlayer().attackPhase(defenderPlayer, attCountry, defCountry, attackingDiceCount, defendingDiceCount);

		if (isMapConquered()) {
			IOHelper.print("Game Over, You win");
			isMapConqueredFlag = true;
		} else if (!getCurrentPlayer().isAttackPossible()) {
			updatePhase();
		}

		notifyObserverslocal(this);

		return true;
	}

	/**
	 * Method for performing attack phase
	 */
	public Boolean attackAllOutPhase(String attackingCountry, String defendingCountry) {
		Common.PhaseActions.clear();

		Country attCountry = getCountryFromName(attackingCountry);
		Country defCountry = getCountryFromName(defendingCountry);

		if (attCountry == null || defCountry == null) {
			IOHelper.print("Set attacking and defending countries first");
			return false;
		}

		Player defenderPlayer = playerList.stream().filter(p -> p.getAssignedCountryList().contains(defCountry))
				.findAny().orElse(null);

		if (defenderPlayer == null) {
			IOHelper.print("Cannot find defender player");
			return false;
		}

		while ((!getCurrentPlayer().isConquered) && attCountry.getnoOfArmies() > 1) {
			int attackingDiceCount = this.getMaximumAllowableDices(attackingCountry, "Attacker");
			int defendingDiceCount = this.getMaximumAllowableDices(defendingCountry, "Defender");

			getCurrentPlayer().attackPhase(defenderPlayer, attCountry, defCountry, attackingDiceCount,
					defendingDiceCount);
		}

		if (!getCurrentPlayer().isAttackPossible()) {
			updatePhase();
		}
		notifyObserverslocal(this);

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

		getCurrentPlayer().fortificationPhase(sourceCountryName, destinationCountryName, noOfArmies);

		if (getCurrentPlayer().isEligibleForCard()) {
			CardEnum card = getCardFromDeck();
			if (card == null) {
				IOHelper.print("No card available");
			} else {
				getCurrentPlayer().addCardToPlayer(card);
			}
			getCurrentPlayer().setEligibleForCard(false);
		}

		this.setNextPlayerTurn();
		setGamePhase(PhaseEnum.Reinforcement);
		reinforcementPhaseSetup();
		notifyObserverslocal(this);
		return true;
	}

	/**
	 * Set up reinformcement phase
	 */
	public void reinforcementPhaseSetup() {
		ArrayList<Continent> continents = map.getContinentList();
		this.getCurrentPlayer().setReinformcementArmies(continents);
		notifyObserverslocal(this);
	}

	/**
	 * Move armies after player win in attack
	 * 
	 * @param noOfArmies
	 *            Integer
	 * @return
	 */
	public boolean moveArmyAfterAttack(int noOfArmies) {
		boolean result = getCurrentPlayer().moveArmyAfterAttack(noOfArmies);
		if (result) {
			notifyObserverslocal(this);
		}
		return result;
	}

	/**
	 * To inilize list of cards
	 */
	private void initilizeCardDeck() {
		int countriesCount = map.getCountryList().size();

		gameCards.clear();

		// Here creating the list of cards
		int t = 0;
		for (int i = 0; i < countriesCount; i++) {
			if (t == 0) {
				gameCards.add(CardEnum.Artillery);
			} else if (t == 1) {
				gameCards.add(CardEnum.Cavalry);
			} else if (t == 2) {
				gameCards.add(CardEnum.Infantry);
			}
			t++;
			if (t == 3)
				t = 0;
		}

		// Shuffling the list for randomness
		Collections.shuffle(gameCards, new Random());
	}

	/**
	 * Returns a card from deck (note this function will remove card from deck So
	 * assign to player immediately
	 * 
	 * @return CardEnum
	 */
	private CardEnum getCardFromDeck() {
		if (gameCards.size() > 0) {
			CardEnum card = gameCards.get(0);
			gameCards.remove(card);
			return card;
		}
		return null;
	}

	/**
	 * Adds the given card again to deck at random position
	 * 
	 * @param card
	 */
	private void addCardToDeck(CardEnum card) {
		int random = 0;
		if (gameCards.size() > 0) {
			random = Common.getRandomNumberInRange(0, gameCards.size() - 1);
		}
		gameCards.add(random, card);
	}

	/**
	 * Trade cards to armies
	 * 
	 * @param cards
	 * @return
	 */
	public boolean tradeCards(ArrayList<String> cards) {
		if (cards.size() == 3) {

			CardEnum firstCard = getCurrentPlayer().getCards().stream().filter(x -> x == CardEnum.valueOf(cards.get(0)))
					.findFirst().orElse(null);

			CardEnum secondCard = getCurrentPlayer().getCards().stream()
					.filter(x -> x == CardEnum.valueOf(cards.get(1))).findFirst().orElse(null);

			CardEnum thirdCard = getCurrentPlayer().getCards().stream().filter(x -> x == CardEnum.valueOf(cards.get(2)))
					.findFirst().orElse(null);

			if (firstCard == null || secondCard == null || thirdCard == null) {
				IOHelper.print("One of the card doesn't belong to player");
				return false;
			}

			boolean isAllSameTypeOfCards = (firstCard == secondCard) && (secondCard == thirdCard);
			boolean isAllDifferentTypeOfCars = (firstCard != secondCard) && (secondCard != thirdCard)
					&& (firstCard != thirdCard);

			if (isAllSameTypeOfCards || isAllDifferentTypeOfCars) {
				int tradingCount = getCurrentPlayer().getTradingCount() + 1;
				int tradingArmies = GetArmiesByTrading.getArmies(tradingCount);

				// Remove cards from player
				getCurrentPlayer().getCards().remove(firstCard);
				getCurrentPlayer().getCards().remove(secondCard);
				getCurrentPlayer().getCards().remove(thirdCard);

				// Add cards to deck
				addCardToDeck(firstCard);
				addCardToDeck(secondCard);
				addCardToDeck(thirdCard);

				// set trade armies
				this.getCurrentPlayer().setNoOfTradedArmies(tradingArmies);
				this.getCurrentPlayer().setTradingCount(tradingCount);
				notifyObserverslocal(this);
				return true;
			} else {
				IOHelper.print("Provide either all same type of cards or one of each kind of card");
			}
		} else {
			IOHelper.print("Provide three set of cards");
		}
		return false;
	}

	/**
	 * This method will tell if whole map is being conquered by current Player
	 * 
	 * @return
	 */
	public Boolean isMapConquered() {
		if (map.getCountryList().size() == this.getCurrentPlayer().getAssignedCountryList().size()) {
			return true;
		}

		return false;
	}
}
