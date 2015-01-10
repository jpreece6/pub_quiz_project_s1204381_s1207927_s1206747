package io;

import java.io.File;
import java.util.Scanner;

/**
 * IO contains methods to print to the console in a simple fashion,
 * removes the need to type 'System.out.print(ln)'.
 * @author Joshua Preece
 * @version 1.0
 */

public class IO {
	
	/**
	 * Prints to the console
	 * @param text Text to print
	 */
	public static void print(String text) {
		System.out.print(text);
	}
	
	/**
	 * Prints a line to the console
	 * @param text Text o print
	 */
	public static void println(String text) {
		System.out.println(text);
	}
	
	public static String readFile(String path) {
		
		String input = "";
		
		try(Scanner reader = new Scanner(new File(path))) {
			
			while(reader.hasNextLine()) {

				input = input + reader.nextLine();
				
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return input;
	}
	
}
