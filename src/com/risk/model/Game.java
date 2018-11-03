package com.risk.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Random;
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
	 * This function returns the current player object
	 * 
	 * @return currentPlayer The current player object
	 */
	public Player getCurrentPlayer() {

		Player currentPlayer = playerList.get(currentPlayerId);
		return currentPlayer;
	}

	/**
	 * This function will randomly assign Countries to all players and assign one
	 * army to each country for a player
	 * 
	 */
	public void startUpPhase() {

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
		if (gamePhase == PhaseEnum.Attack || gamePhase == PhaseEnum.Fortification) {
			IOHelper.print("Cannot add army in attack or fortification phase");
			return;
		}
		if (gamePhase == PhaseEnum.Startup) {
			boolean isProcessed = getCurrentPlayer().addArmyToCountryForStartup(countryName);

			if (isProcessed) {
				setNextPlayerTurn();
			}
		} else if (gamePhase == PhaseEnum.Reinforcement) {
			getCurrentPlayer().addArmyToCountryForReinforcement(countryName);
		}
		updatePhase();

		notifyObserverslocal(this);
		// return true;
	}

	/**
	 * Method to update the phase of the game
	 * 
	 */
	private void updatePhase() {

		if (this.getGamePhase() == gamePhase.Startup) {
			// Check all players have assigned armies to country or not
			long pendingPlayersCount = playerList.stream().filter(p -> p.getNoOfUnassignedArmies() > 0).count();

			if (pendingPlayersCount == 0) {
				this.setGamePhase(gamePhase.Reinforcement);
				currentPlayerId = 0;
				reinforcementPhaseSetup();

			}
		} else if (this.getGamePhase() == gamePhase.Reinforcement) {
			// Check the current player reinforcement armies
			if (getCurrentPlayer().getNoOfReinforcedArmies() == 0) {
				// We don't need to implement attack for now
				this.setGamePhase(gamePhase.Attack);
		//		attackPhase();
			}

		} else if (this.getGamePhase() == gamePhase.Fortification) {
			this.setGamePhase(gamePhase.Reinforcement);
		}
	}

	/**
	 * Method for performing attack phase
	 */
	public void attackPhase() {
		setGamePhase(gamePhase.Fortification);
		notifyObserverslocal(this);
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
		this.setNextPlayerTurn();
		setGamePhase(PhaseEnum.Reinforcement);
		reinforcementPhaseSetup();
		notifyObserverslocal(this);
		return true;
	}

	public void reinforcementPhaseSetup() {
		ArrayList<Continent> continents = map.getContinentList();
		this.getCurrentPlayer().reinforcementPhaseSetup(continents);
	}

	/**
	 * Method to find the next player to perform reinforcement
	 */
	/*
	 * public void setNextPlayerReinforcement() { if (gamePhase !=
	 * gamePhase.Fortification) {
	 * IOHelper.print("Cannot set next player in reinforcement"); }
	 * setNextPlayerTurn(); updatePhase(); Player player = getCurrentPlayer();
	 * player.reinforcementPhaseSetup(); }
	 */
	
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
	 * Method to get countries corresponding to players
	 *
	 * @return ArrayList , returning array list of countries.
	 */
	public ArrayList<Country> getCurrentPlayerCountries() {
		return this.getCurrentPlayer().getAssignedCountryList();
	}

	/**
	 * This method returns the list of countries that the player has.
	 * 
	 * @param currentPlayer
	 *            , object of the player
	 * @return The countries which the player occupies
	 */
	/*
	 * public ArrayList<Country> getPlayersCountry(Player currentPlayer) { return
	 * playerCountry.get(currentPlayer); }
	 */
	
	private Boolean phaseCheckValidation(PhaseEnum phase) {
		if (phase == this.gamePhase)
			return true;
		else
			return false;
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
	 * Method used to get map
	 * 
	 * @return map, object of map
	 */
	public Map getMap() {
		return map;
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
	 * Method use to get arraylist of players
	 * 
	 * @return playerList, ArrayList of players
	 */
	public ArrayList<Player> getAllPlayers() {
		return playerList;
	}

	/**
	 * Method to get neighbouring countries of a given country
	 * 
	 * @param sourceCountryName,
	 *            name of the source country of player
	 * @return ArrayList , returning array list of countries.
	 */
	public ArrayList<String> getNeighbouringCountries(String sourceCountryName) {

		ArrayList<String> neighborCountriesName = this.getCurrentPlayer().getNeighbouringCountries(sourceCountryName);
		return neighborCountriesName;
	}

}
