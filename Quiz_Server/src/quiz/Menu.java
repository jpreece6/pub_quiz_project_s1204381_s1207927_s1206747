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
					
				} else if (choice == 2) {
					displaySettingsMenu(handle);
				} else if (choice == 3) {
					displayInfo(handle);
				}
				
			}
			
		} while (handle.get_game_start() == false);
		
	}
	
	private void startQuiz(Game handle) {
		
	}
	
	private static int getInput() {
		try {
			Scanner scan = new Scanner(System.in);
			return scan.nextInt();
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	
	public static void displayInfo(Game handle) {
		IO.println("**** Info **** \n");
		IO.println("Clients: " + handle.get_Number_of_Clients());
		IO.println("Questions: " + handle.get_Number_of_Questions());
		IO.println("Port: ");
	}
	
	public static void displaySettingsMenu(Game handle) {
		
		boolean valid = false;
		
		do {
			
			clear_screen();
			
			IO.println("Set Number of Clients : 1 ");
			IO.println("Set Number of Questions : 2");
			IO.println("Set Port Number : 3");
			
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
	
	private static void set_Number_of_Clients(Game handle) {
		boolean valid = false;
		do {
			
			int clients = getInput();
			if (clients > 0) {
				handle.set_Number_of_Clients(clients);
				valid = true;
			}
			
		} while (valid == false);
		
		IO.println("Number of Clients Set : " + handle.get_Number_of_Clients());
	}
	
	private static void set_Number_of_Questions(Game handle) {
		boolean valid = false;
		do {
			
			int questions = getInput();
			if (questions > 0) {
				handle.set_Number_of_Questions(questions);
				valid = true;
			}
			
		} while (valid == false);
		
		IO.println("Number of Questions Set : " + handle.get_Number_of_Questions());
	}
	
	private static void set_Port_Number(Game handle) {
		boolean valid = false;
		do {
			
			int port = getInput();
			if (port > 0) {
				handle.set_Port_Number(port);
				valid = true;
			}
			
		} while (valid == false);
		
		IO.println("Port number set : " + handle.get_Port_Number());
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
