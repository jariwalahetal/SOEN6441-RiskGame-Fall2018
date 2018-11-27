package com.risk.helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Helper class to print data
 * 
 * @author sadgi
 *
 */
public class IOHelper {

	public static Logger logger = Logger.getLogger("IOHelper");
	public static FileHandler fileHandler;
	public static BufferedWriter writeToFile;
	static {
		try {
			String timeStamp = new SimpleDateFormat().format(new Date());
			CustomLogFormatter formatter = new CustomLogFormatter();
			fileHandler = new FileHandler("C:/Users/dell pc/IdeaProjects/SOEN_OCT4/SOEN6441-RiskGame/src/com/risk/helper/Logs.txt");
//			writeToFile = new BufferedWriter(new FileWriter("Logs.txt"));
//			writeToFile.write(timeStamp);
//			writeToFile.close();
			logger.addHandler(fileHandler);
			fileHandler.setFormatter(formatter);
			fileHandler.setLevel(Level.INFO);
			logger.setUseParentHandlers(false);
			logger.info("Log File created - TimeStamp : "+timeStamp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to print output on the command line
	 * 
	 * @param string,
	 *            string parameter to print
	 */
	public static void print(String string) {
		System.out.println(string);
		logger.setUseParentHandlers(false);
		//logger.info(string);
		logger.log(Level.INFO,string);

		Common.PhaseActions.add(string);
	}

	static Scanner sc = new Scanner(System.in);

	/**
	 * Method to take string input from user on command line
	 * 
	 * @return string input
	 */
	public static String getNextString() {
		return sc.nextLine();
	}

	/**
	 * Method to take Integer input from user on command line
	 * 
	 * @return int, next integer
	 */
	public static int getNextInteger() {
		String s = sc.nextLine();
		return Integer.parseInt(s);
	}

	/**
	 * Method to print and handle Exceptions
	 * 
	 * @param exception,object
	 *            of exception class
	 */
	public static void printException(Exception exception) {
		System.out.println("Exception: " + exception.getMessage());
	}
}
