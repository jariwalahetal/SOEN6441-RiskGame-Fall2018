package com.risk.controller;
/**
 * @author Bedi
 * @version 1.0.0
 * @since 25-September-2018
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.risk.view.MapEditorView;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class GameRunner{
	
     /**
     * start and initilization of game 
     * @param args
     * @throws IOException
     */
    public static void main(String []args) throws IOException
     {
    	 MapController mapController = new MapController();
    	 mapController.startGame();
     }
}

