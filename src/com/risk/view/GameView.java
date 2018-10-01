package com.risk.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Initiate the game view in javaswings
 * @author sadgi
 *
 */
public class GameView {
	
	 private static JPanel gameActionPanel = new JPanel(null);
	  private static JFrame gameFrame = null;
	  private static boolean firstTurn = true;

	  // Map Label
	  private static JLabel mapLabel = new JLabel();
	  private static JScrollPane mapScrollPane = null;

	  // Reinforcement Label
	  private static JLabel reinforcementLabel = new JLabel();
	  private static JLabel playersTurnLabel = new JLabel("Default");
	  private static JLabel unassignedUnit = new JLabel("0");
	  private static JComboBox<String> addUnitToCountryComboBox = new JComboBox<>();
	  private static JButton addUnitButton = new JButton("Add Unit");

	  // Phase View Label
	  private static JLabel phaseViewLabel = new JLabel();


	  // Fortification Label
	  private static JLabel fortificationLabel = new JLabel();
	  private static JComboBox<String> soldierMoveFromComboBox = new JComboBox<>();
	  private static JComboBox<String> soldierMoveToComboBox = new JComboBox<>();
	  private static JComboBox<String> noOfSoldierMoveComboBox = new JComboBox<>();
	  private static JButton fortificationMoveButton = new JButton("Move");

	  // Save Game Button
	  private static JLabel saveButtonLabel = new JLabel();
	  private static JButton saveButton = new JButton("Save Game");



}
