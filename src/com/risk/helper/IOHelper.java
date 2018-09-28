package com.risk.helper;

import java.io.PrintStream;
import java.util.Scanner;

public class IOHelper {
	
	public static PrintStream print(String string) {
		return System.out.printf(string);
	}
	
	static Scanner sc = new Scanner(System.in);
    public static String getNextString()
    {
        return sc.nextLine();
    }
    public static int getNextInteger()
    {
    	return sc.nextInt();
    }
}
