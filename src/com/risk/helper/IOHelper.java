package com.risk.helper;

import java.util.Scanner;

/**
 * Helper class to print data
 * @author sadgi
 *
 */
public class IOHelper {
	
	/**
	 * Method to print output on the command line
	 * @param string
	 */
	public static void print(String string)
	{
		System.out.println(string);
	}
	
	static Scanner sc = new Scanner(System.in);
	

	/**
	 * Method to take string input from user on command line
	 * @param string
	 */
    public static String getNextString()
    {
        return sc.nextLine();
    }
    
    /**
	 * Method to take Integer input from user on command line
	 * 
	 */
    public static int getNextInteger()
    {
    	String s = sc.nextLine();
    	return Integer.parseInt(s);
    }
    
    /**
  	 * Method to print and handle Exceptions
  	 * @param Exception Object
  	 */
    public static void printException(Exception exception)
    {
    	System.out.println("Exception: " + exception.getMessage());
    }
}
