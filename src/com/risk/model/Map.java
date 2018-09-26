package com.risk.model;

/**
 * Map Class
 * @author sadgi
 * @version 1.0.0
 * @since 27-September-2018
 */
public class Map {
	private String mapId;
	private String mapName;
	
	/**
	 * This is a constructor of Map Class which sets mapId and mapName.
	 * @param mapId
	 * @param mapName
	 */
	public Map(String mapId, String mapName) {
		super();
		this.mapId = mapId;
		this.mapName = mapName;
	}
	
	/**
	 * getters and setters
	 */
	public String getMapId() {
		return mapId;
	}
	public void setMapId(String mapId) {
		this.mapId = mapId;
	}
	public String getMapName() {
		return mapName;
	}
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
}
