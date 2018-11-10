package com.risk.helper;

import java.util.Scanner;

/**
 * Helper class to print data
 * 
 * @author sadgi
 *
 */
public class IOHelper {

	/**
	 * Method to print output on the command line
	 * 
	 * @param string,
	 *            string parameter to print
	 */
	public static void print(String string) {
		System.out.println(string);
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
