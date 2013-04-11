package quiz;

import io.IO;

import java.util.Scanner;

/**
 * 
 * @author Joshua Preece
 * @version 1.0
 *
 */

public class Menu {
	
	/**
	 * Displays a main menu to the user.
	 * This allows the user to change settings and display current
	 * settings. And finally start the quiz / server
	 * @param handle used to access settings / variables stored within the game class
	 * @param APP_NAME String value with the name of the application
	 * @param VERSION String value with the version number of the application
	 */
	public static void displayMenu(Game handle, String APP_NAME, String VERSION) {
		
		do {
			
			clear_screen();
			
			IO.println(APP_NAME + " " + VERSION + "\n");
			IO.println("Start Game : 1 ");
			IO.println("Settings : 2 ");
			IO.println("Display Settings : 3 ");
			
			int choice = getInput();
			if (choice > 0 && choice <= 3) {
				
				if (choice == 1) {
					handle.set_game_start(true);
				} else if (choice == 2) {
					displaySettingsMenu(handle);
				} else if (choice == 3) {
					displayInfo(handle);
				}
				
			}
			
		} while (handle.get_game_start() == false);
		
	}
	
	/**
	 * Retrieves input from the user
	 * @return int value with the users input
	 */
	private static int getInput() {
		try {
			Scanner scan = new Scanner(System.in);
			int value = scan.nextInt();
			scan.close();
			return value;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * Displays the current settings to the user.
	 * @param handle used to retive the settings from the game class
	 */
	public static void displayInfo(Game handle) {
		
		boolean valid = false;
		do {
			
			clear_screen();
			
			IO.println("**** Info **** \n");
			IO.println("Clients: " + handle.get_Number_of_Clients());
			IO.println("Questions: " + handle.get_Number_of_Questions());
			IO.println("Port: " + handle.get_Port_Number());
			IO.println("Back to Main Menu 1 : ");
			
			int choice = getInput();
			if (choice > 0 && choice <= 1) {
				
				if (choice == 1) {
					valid = true;
				}
			}
			
		} while (valid == false);
	
	}
	
	/**
	 * Displays the settings menu to the user so that they can change some
	 * settings
	 * @param handle 
	 */
	public static void displaySettingsMenu(Game handle) {
		
		boolean valid = false;
		
		do {
			
			clear_screen();
			
			IO.println("Possible Number of Clients : 1 ");
			IO.println("Possible Number of Questions : 2");
			IO.println("Port Number : 3");
			
			int choice = getInput();
			if (choice > 0 && choice <= 3) {
				if (choice == 1) {
					set_Number_of_Clients(handle);
				} else if (choice == 2) {
					set_Number_of_Questions(handle);
				} else if (choice == 3) {
					set_Port_Number(handle);
				}
				
				valid = true;
			}
			
		} while (valid == false);
		
	}
	
	/**
	 * Allows the user to set the number of clients that will be able
	 * to connect and play the quiz.
	 * @param handle used to set the number of clients within the game class
	 */
	private static void set_Number_of_Clients(Game handle) {
		boolean valid = false;
		
		IO.println("Set Number of Clients : ");
		
		do {
			
			int clients = getInput();
			if (clients > 0) {
				handle.set_Number_of_Clients(clients);
				valid = true;
			} else {
				IO.println("Error pease enter a value greater than 0!");
			}
			
		} while (valid == false);
		
		IO.println("Number of Clients Set : " + handle.get_Number_of_Clients());
		
		try {
			// wait and let the user see the message
			Thread.sleep(2000);
		} catch (Exception ex) {}
	}
	
	/**
	 * Allows the user to set the number of questions that will be
	 * available during the quiz
	 * @param handle used to set the number of questions within the game class
	 */
	private static void set_Number_of_Questions(Game handle) {
		boolean valid = false;
		
		IO.println("Set Number of Questions : ");
		
		do {
			
			int questions = getInput();
			if (questions > 0) {
				handle.set_Number_of_Questions(questions);
				valid = true;
			} else {
				IO.println("Error please enter a value greater than 0!");
			}
			
		} while (valid == false);
		
		IO.println("Number of Questions Set : " + handle.get_Number_of_Questions());
		
		try {
			// wait and let the user see the message
			Thread.sleep(2000);
		} catch (Exception ex) {}
	}
	
	/**
	 * Allows the user to set the port number for
	 * clients to connect to.
	 * @param handle used to set the port number within the game class
	 */
	private static void set_Port_Number(Game handle) {
		boolean valid = false;
		
		IO.println("Set Port Number : ");
		
		do {
			
			int port = getInput();
			if (port > 0) {
				handle.set_Port_Number(port);
				valid = true;
			} else {
				IO.print("Error please enter a value greater than 0!");
			}
			
		} while (valid == false);
		
		IO.println("Port number set : " + handle.get_Port_Number());
		
		try {
			// wait and let the user see the message
			Thread.sleep(2000);
		} catch (Exception ex) {}
	}
	
	/**
	 * Clears the screen by printing new lines to the screen
	 * ** Custom hack as java does not have a "cross-platform" friendly
	 *    way to clear the console screen. ** 
	 */
	private static void clear_screen() {
		for (int i = 0; i < 20; i++) {
			IO.println("");
		}
	}
}
