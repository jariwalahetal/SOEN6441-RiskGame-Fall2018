package com.risk.helper;

import java.util.Scanner;

/**
 * Helper class to print data
 * @author sadgi
 *
 */
public class IOHelper {
	
	public static void print(String string) {
		System.out.println(string);
	}
	
	static Scanner sc = new Scanner(System.in);
    public static String getNextString()
    {
        return sc.nextLine();
    }
    public static int getNextInteger()
    {
    	String s = sc.nextLine();
    	return Integer.parseInt(s);
    }
    
    public static void printException(Exception exception)
    {
    	System.out.println("Exception: " + exception.getMessage());
    }
}
