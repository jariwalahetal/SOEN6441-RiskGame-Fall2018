package com.risk.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Observable;
import java.util.Random;
import java.util.HashMap;
import com.risk.helper.CardEnum;
import com.risk.helper.Common;
import com.risk.helper.GameMode;
import com.risk.helper.GetArmiesByTrading;
import com.risk.helper.IOHelper;
import com.risk.helper.InitialPlayerSetup;
import com.risk.helper.PhaseEnum;
import com.risk.model.strategies.PlayerStrategy;

/**
 * Game Class
 * 
 * @author Binay
 * @version 1.0.0
 * @since 30-September-2018
 */
public class Game extends Observable implements Serializable {
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private int currentPlayerId;
	private PhaseEnum gamePhase;
	private Map map;
	private ArrayList<CardEnum> gameCards = new ArrayList<>();
	private Boolean isMapConqueredFlag = false;
	private GameMode gameMode;
	private int maxTurnsForTournament;

	/**
	 * This is a constructor of Game class which will initialize the Map
	 * 
	 * @param map, object of the map
	 */
	public Game(Map map) {
		super();
		this.map = map;
		this.setGamePhase(gamePhase.Startup);
	}

	/**
	 * Sets game mode
	 * 
	 * @param gameMode,mode of the Game
	 */
	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

	/**
	 * Returns maxTurnsForTournament
	 * 
	 * @return int, maxTurnsForTournament
	 */
	public int getMaxTurnsForTournament() {
		return this.maxTurnsForTournament;
	}

	/**
	 * Sets MaxTurnsForTournamet
	 * 
	 * @param maxTurns, Maximum Turns
	 */
	public void setMaxTurnsForTournament(int maxTurns) {
		this.maxTurnsForTournament = maxTurns;
	}

	/**
	 * Returns Game mode
	 * 
	 * @return Game Mode,  GameMode object 
	 */
	public GameMode getGameMode() {
		return gameMode;
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
				int totalArmies = country.getnoOfArmies();
				if (returnMap.containsKey(player.getPlayerId())) {
					totalArmies += returnMap.get(player.getPlayerId());
				}
				returnMap.put(player.getPlayerId(), totalArmies);
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
	 * @param gamePhase, name of the game phase
	 */
	public void setGamePhase(PhaseEnum gamePhase) {
		this.gamePhase = gamePhase;
	}

	/**
	 * This function will add the player to the game(playerList)
	 * 
	 * @param player, object of the player
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
	 * Method use to get player from player ID
	 * @param playerID, Id of the Player
	 * @return playerList, ArrayList of players
	 */
	public Player getPlayerFromID(int playerID) {

		Player player = playerList.stream().filter(c -> c.getPlayerId() == playerID).findAny().orElse(null);
		return player;
	}

	/**
	 * Method to get armies assigned to the country
	 * 
	 * @param sourceCountryName,name of the source country of player
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
	 */
	public void notifyObserversLocal() {
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Get country object from country name
	 * 
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
		Common.PhaseActions.clear();
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
		notifyObserversLocal();

	}

	/**
	 * Add army to the country for Reinforcement phase
	 * 
	 * @param countryName, name of the country
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
			Country toCountry = getCountryFromName(countryName);
			getCurrentPlayer().setToCountry(toCountry);
			getCurrentPlayer().reinforce();
		}
		updatePhase();
		if (this.getGameMode() == GameMode.SingleGameMode) {
			singleGameMode();
		}
	}

	/**
	 * Method to update the phase of the game
	 * 
	 */
	public void updatePhase() {
		if (this.phaseCheckValidation(PhaseEnum.Startup)) {
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
			if (getCurrentPlayer().isEligibleForCard()) {
				addCardToCurrentPlayer();
			}
			this.setNextPlayerTurn();
			setGamePhase(PhaseEnum.Reinforcement);
			reinforcementPhaseSetup();

		} else if (this.phaseCheckValidation(PhaseEnum.Attack)) {
			this.setGamePhase(PhaseEnum.Fortification);
		}

		notifyObserversLocal();

	}

	private void executeCurrentPhase() {
		if (phaseCheckValidation(PhaseEnum.Startup)) {
			ArrayList<Country> assignedCountryList = getCurrentPlayer().getAssignedCountryList();
			int randomIndex = 0;
			if (assignedCountryList.isEmpty())
				return;
			else if (assignedCountryList.size() > 1)
				randomIndex = Common.getRandomNumberInRange(0, assignedCountryList.size() - 1);

			Country country = assignedCountryList.get(randomIndex);
			boolean isProcessed = getCurrentPlayer().addArmyToCountryForStartup(country.getCountryName());
			if (isProcessed) {
				setNextPlayerTurn();
			}

		} else if (this.phaseCheckValidation(PhaseEnum.Reinforcement)) {
			System.out.println("Reinforcement");
			this.getCurrentPlayer().reinforce();

		} else if (this.phaseCheckValidation(PhaseEnum.Attack)) {
			System.out.println("Before attack");
			this.getCurrentPlayer().attackPhase();
			if (isMapConquered()) {
				IOHelper.print("Game Over, You win");
				isMapConqueredFlag = true;
			}
			System.out.println("After attack");
			System.out
					.println("Player 1 Countries Count:" + this.getAllPlayers().get(0).getAssignedCountryList().size());
			System.out
					.println("Player 2 Countries Count:" + this.getAllPlayers().get(1).getAssignedCountryList().size());
			System.out
					.println("Player 3 Countries Count:" + this.getAllPlayers().get(2).getAssignedCountryList().size());

		} else if (this.phaseCheckValidation(PhaseEnum.Fortification)) {
			System.out.println("Fortification");
			this.getCurrentPlayer().fortificationPhase();
		}
	}

	/**
	 * Add card to current player from deck
	 */
	private void addCardToCurrentPlayer() {
		CardEnum card = getCardFromDeck();
		if (card == null) {
			IOHelper.print("No card available");
		} else {
			getCurrentPlayer().addCardToPlayer(card);
		}
		getCurrentPlayer().setEligibleForCard(false);
	}

	/**
	 * Returns allowable dices for attacking country
	 * 
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
	 * 
	 * @param attackingCountry, Attacking Country in String
	 * @param defendingCountry, Defending country in String
	 * @param attackingDiceCount, Attacking Dice Count
	 * @param defendingDiceCount, Defending Dice Count
	 * @return true, if attack done
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

		Player attackedPlayer = playerList.stream().filter(p -> p.getAssignedCountryList().contains(defCountry))
				.findAny().orElse(null);

		if (attackedPlayer == null) {
			IOHelper.print("Cannot find defender player");
			return false;
		}

		getCurrentPlayer().setFromCountry(attCountry);
		getCurrentPlayer().setToCountry(defCountry);
		getCurrentPlayer().rollDice(attackingDiceCount);
		attackedPlayer.rollDice(defendingDiceCount);
		getCurrentPlayer().setAttackedPlayer(attackedPlayer);

		getCurrentPlayer().attackPhase();

		if (isMapConquered()) {
			IOHelper.print("Game Over, You win");
			isMapConqueredFlag = true;
		} else if (!getCurrentPlayer().isAttackPossible()) {
			updatePhase();
		}

		notifyObserversLocal();

		return true;
	}

	/**
	 * Method for performing attack phase
	 * 
	 * @param attackingCountry, Attacking Country in String
	 * @param defendingCountry, Defending country in String
	 * @return true, if attack phase out
	 * 
	 */
	public Boolean attackAllOutPhase(String attackingCountry, String defendingCountry) {
		Common.PhaseActions.clear();

		Country attCountry = getCountryFromName(attackingCountry);
		Country defCountry = getCountryFromName(defendingCountry);

		if (attCountry == null || defCountry == null) {
			IOHelper.print("Set attacking and defending countries first");
			return false;
		}

		Player attackedPlayer = playerList.stream().filter(p -> p.getAssignedCountryList().contains(defCountry))
				.findAny().orElse(null);

		if (attackedPlayer == null) {
			IOHelper.print("Cannot find defender player");
			return false;
		}

		getCurrentPlayer().setFromCountry(attCountry);
		getCurrentPlayer().setToCountry(defCountry);
		getCurrentPlayer().setAttackedPlayer(attackedPlayer);
		int attackingDiceCount, defendingDiceCount;

		while (attCountry.getnoOfArmies() > 1) {
			attackingDiceCount = this.getMaximumAllowableDices(attackingCountry, "Attacker");
			defendingDiceCount = this.getMaximumAllowableDices(defendingCountry, "Defender");
			getCurrentPlayer().rollDice(attackingDiceCount);
			attackedPlayer.rollDice(defendingDiceCount);

			getCurrentPlayer().attackPhase();

			if (getCurrentPlayer().isConquered()) {
				break;
			}
		}

		if (!getCurrentPlayer().isAttackPossible()) {
			updatePhase();
		}
		notifyObserversLocal();

		return true;
	}

	/**
	 * Method to perform fortification phase
	 * 
	 * @param sourceCountryName, name of the source country of player
	 * @param destinationCountryName, name of the destination country of the player
	 * @param noOfArmies, number of armies to be moved
	 * @return true if no army need to move and false if source and destination
	 *         countries are null
	 */
	public boolean fortificationPhase(String sourceCountryName, String destinationCountryName, int noOfArmies) {

		Country fromCountry = getCountryFromName(sourceCountryName);
		Country toCountry = getCountryFromName(destinationCountryName);

		getCurrentPlayer().setFromCountry(fromCountry);
		getCurrentPlayer().setToCountry(toCountry);
		getCurrentPlayer().setNoOfArmiesToMove(noOfArmies);

		getCurrentPlayer().fortificationPhase();

		if (getCurrentPlayer().isEligibleForCard()) {
			CardEnum card = getCardFromDeck();
			if (card == null) {
				IOHelper.print("No card available");
			} else {
				getCurrentPlayer().addCardToPlayer(card);
			}
			getCurrentPlayer().setEligibleForCard(false);
		}

		updatePhase();
		if (this.getGameMode() == GameMode.SingleGameMode) {
			singleGameMode();
		}
		return true;
	}

	/**
	 * Set up reinformcement phase
	 */
	public void reinforcementPhaseSetup() {
		ArrayList<Continent> continents = map.getContinentList();
		this.getCurrentPlayer().setReinformcementArmies(continents);
	}

	/**
	 * Move armies after player win in attack
	 * 
	 * @param noOfArmies Integer
	 * @return true , if army moved
	 */
	public boolean moveArmyAfterAttack(int noOfArmies) {
		boolean result = getCurrentPlayer().moveArmyAfterAttack(noOfArmies);
		if (result) {
			notifyObserversLocal();
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
	 * @param card, CardEnum
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
	 * @param cards, list of Cards
	 * @return true, if card traded
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
				notifyObserversLocal();
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
	 * @return true, if map conquered
	 */
	public Boolean isMapConquered() {
		if (map.getCountryList().size() == this.getCurrentPlayer().getAssignedCountryList().size()) {
			this.gamePhase = PhaseEnum.GameWinner;
			return true;
		}

		return false;
	}

	/**
	 * Print status for all player
	 */
	public void printPlayerStatus() {
		for (int i = 0; i < playerList.size(); i++) {
			System.out.println(playerList.get(i).getName() + " player: Countries Count:"
					+ playerList.get(i).getAssignedCountryList().size());
		}
	}

	/**
	 * Runs tournament mode for game
	 */
	public void tournamentMode() {
		Player currentPlayer;
		int turnsCounts = 0;
		
		IOHelper.print("\n");
		IOHelper.print("\n");
		IOHelper.print("===========================================================================================");
		IOHelper.print("=================================TOURNAMENT START==========================================");
		IOHelper.print("===========================================================================================");
		// step 1: assign player to countries and randomly increase countries for player

		// Loop until all armies are assigned for all players
		while (this.phaseCheckValidation(PhaseEnum.Startup)) {
			// Randomly increase army for the country of player
			ArrayList<Country> playerCountries = getCurrentPlayer().getAssignedCountryList();

			int id = Common.getRandomNumberInRange(0, playerCountries.size() - 1);

			addArmyToCountry(playerCountries.get(id).getCountryName());
		}

		while (true) {
			currentPlayer = this.getCurrentPlayer();

			// step 2: reinforce counties
			currentPlayer.reinforce();
			this.updatePhase();

			// step 3: attack phase
			currentPlayer.attackPhase();

			// step 3.1: generate logic to move armies after attack phase

			if (isMapConquered()) {
				IOHelper.print(this.getCurrentPlayer().getName() + " is a winner !!");
				break;
			}

			this.updatePhase();

			// step 4: fortify phase
			currentPlayer.fortificationPhase();
			this.updatePhase();

			// Print status of players
			this.printPlayerStatus();

			turnsCounts++;
			if (turnsCounts >= getMaxTurnsForTournament()) {
				this.setGamePhase(PhaseEnum.GameDraw);
				IOHelper.print("Tournament draw after " + turnsCounts + " turns");
				break;
			}
		}
		IOHelper.print("===========================================================================================");
		notifyObserversLocal();
	}

	public String saveGame() {
		String fileTime = new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date());
		try {
			FileOutputStream fileOut = new FileOutputStream("assets/Saved_Games/" + fileTime);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
     return fileTime;
	}

	public static Game loadGame(String gameTitle) {
		Game game = null;
		try {
			System.out.println("gameTitle:" + gameTitle);
			FileInputStream fileIn = new FileInputStream("assets/Saved_Games/" + gameTitle);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			game = (Game) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
		}
		return game;
	}

	public void continueSinglePlayerMde() {
		while (getCurrentPlayer().getIsBot() && !isMapConquered()) {
			if (phaseCheckValidation(PhaseEnum.Startup) || phaseCheckValidation(PhaseEnum.Reinforcement)) {
				int randomIndex = Common.getRandomNumberInRange(0,
						getCurrentPlayer().getAssignedCountryList().size() - 1);
				Country country = getCurrentPlayer().getAssignedCountryList().get(randomIndex);
				addArmyToCountry(country.getCountryName());
			}
		}
	}

	public void singleGameMode() {
		while (getCurrentPlayer().getIsBot() && !this.isMapConquered()) {
			executeCurrentPhase();
			updatePhase();
		}

		notifyObserversLocal();

	}
}
