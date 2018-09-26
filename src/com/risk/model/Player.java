package com.risk.model;

/**
 * Player Class
 * @author sadgi
 * @version 1.0.0
 * @since 27-September-2018
 */
public class Player {
	private int playerId;
	private String name;
	private String color;

	/**
	 * This is a constructor of Player Class which sets playerId, name, and color.
	 * @param playerId
	 * @param name
	 * @param color
	 */
	public Player(int playerId, String name, String color) {
		super();
		this.playerId = playerId;
		this.name = name;
		this.color = color;
	}

	/**
	 * getters and setters
	 */
	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}



}
