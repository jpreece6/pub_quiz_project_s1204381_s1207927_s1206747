package quiz;

import io.IO;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import packet.Packet;
import packet.PacketHeaders;
import server.Client;
import server.Server;
import data.Question;

/**
 * Game is the main class which handles each stage/step of the server.
 * @author Joshua Preece
 * @version 1.0
 *
 */

public class Game {

	private int num_clients = 1;
	private int num_questions = 2;
	private ArrayList<String[]> results = new ArrayList<String[]>();
	private String[] readyResults;
	
	private Client[] clientsList;
	private Question question;
	
	private boolean resultsReady = false;
	private boolean all_connected = false;
	
	private ExecutorService serverThread = Executors.newSingleThreadExecutor();
	private Runnable server;
	
	public static void main(String[] args) {
		new Game();
	}
	
	public Game() {
		
		// menu get num clients and num questions
		server = new Server(this, num_clients);
		serverThread.execute(server);
		clientsList = new Client[num_clients];
		question = new Question(num_questions);
		wait_for_connecting_clients();
		send_questions(question);
		wait_for_results();
		process_results();
		
	}
	
	/**
	 * Waits for all clients specified in num_clients to connect.
	 */
	public void wait_for_connecting_clients() {
		IO.println("Waiting for all clients to connect!");
		while (all_connected == false) {
			try {
				// wait 500ms to update all_connected variable
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		IO.println("All clients connected!\n\nStarting Quiz...");
	}
	
	/**
	 * Loads and sends the questions to all clients connected
	 * @param questions Question object containing all questions
	 */
	public void send_questions(Question questions) {
		Packet questionPacket = new Packet(1, PacketHeaders.questions, questions);
		// Loops through all connected clients and sends the packet.
		for (int i = 0; i < clientsList.length; i++) {
			clientsList[i].sendPacket(questionPacket);
		}
	}
	
	/**
	 * Waits for all clients to return their results
	 */
	public void wait_for_results() {
		while (resultsReady == false){
			if (results.size() == num_clients) {
				resultsReady = true;
			}
		}
	}
	
	/**
	 * Adds a new result to the results array
	 * @param newResults an array of results to add to the results array
	 */
	public void addResult(String[] newResults) {
		results.add(newResults);
	}
	
	/**
	 * Sends the compiled results to all connected clients
	 */
	public void sendResults() {
		Packet resultsPacket = new Packet(1, PacketHeaders.results, readyResults);
		// Loops through all connected clients and sends the packet.
		for (int i = 0; i < clientsList.length; i++) {
			clientsList[i].sendPacket(resultsPacket);
		}
	}
	
	/**
	 * Checks if all results are ready
	 * @return boolean resultsReady
	 */
	public boolean areResultsReady() {
		return resultsReady;
	}

	/**
	 * Checks if all clients have connected to the server and match the num_clients variable.
	 * and sets all_connected = true
	 * @param clientHandleList List of clients that are connected to the server
	 */
	public void all_clients_connected(Client[] clientHandleList) {
		/*
		 *  Loops through all connected clients adds them to the clientsList
		 *  and sets all_connected.
		 */
		for (int i = 0; i < clientHandleList.length; i++) {
			clientsList[clientsList.length - 1] = clientHandleList[i]; 
		}
		this.all_connected = true;
	}
	
	/**
	 * 
	 * @return
	 */
	public String[][] process_results() {
		String[][] quizResults = new String[results.size()][num_questions];
		for (int i = 0; i < results.size(); i++) {
			quizResults[i] = results.get(i);
		}
		
		return quizResults;
	}
}
