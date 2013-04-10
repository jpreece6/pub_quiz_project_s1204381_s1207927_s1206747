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
	
	private ArrayList<Client> clientsList;
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
		clientsList = new ArrayList<Client>();
		question = new Question(num_questions);
		wait_for_connecting_clients();
		send_questions(question);
		wait_for_results();
		
		
		process_results();
		sendResults();
		
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
		serverThread.shutdown();
	}
	
	/**
	 * Loads and sends the questions to all clients connected
	 * @param questions Question object containing all questions
	 */
	public void send_questions(Question questions) {
		Packet questionPacket = new Packet(1, PacketHeaders.questions, questions);
		// Loops through all connected clients and sends the packet.
		
		try {
			// Wait 1 second for last client to be established
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < clientsList.size(); i++) {
			clientsList.get(i).sendPacket(questionPacket);
		}
	}
	
	/**
	 * Waits for all clients to return their results
	 */
	public void wait_for_results() {
		while (resultsReady == false){
			if (results.size() == num_clients) {
				resultsReady = true;
				IO.println("All clients have returned their results!");
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
		Packet resultsPacket = new Packet(1, PacketHeaders.results, "4");
		// Loops through all connected clients and sends the packet.
		for (int i = 0; i < clientsList.size(); i++) {
			clientsList.get(i).sendPacket(resultsPacket);
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
	public void all_clients_connected(ArrayList<Client> clientHandleList) {
		/*
		 *  Loops through all connected clients adds them to the clientsList
		 *  and sets all_connected.
		 */
		for (int i = 0; i < clientHandleList.size(); i++) {
			clientsList.add(clientHandleList.get(i)); 
		}
		this.all_connected = true;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<String> process_results() {
//		String[][] quizResults = new String[results.size()][num_questions];
//		for (int i = 0; i < results.size(); i++) {
//			quizResults[i] = results.get(i);
//		}
//		
//		return quizResults;
		
		ArrayList<String> winners = new ArrayList<String>() ;
		ArrayList<String> answers = new ArrayList<String>();
		ArrayList<String[]> processed_answers = new ArrayList<String[]>();
		
		answers = question.getAnswers();
		
		for (int i = 0; i < results.size(); i++) {
			
			String[] one_result = new String[2];
			int num_correct = 0;
			
			for (int b = 0; b < num_questions; b++) {
				if (b != 0) {
					if (results.get(i)[b].equals(answers.get(b - 1))) {
						num_correct++;
					}
				}
			}
			
			one_result[0] = results.get(i)[0];
			one_result[1] = Integer.toString(num_correct);
			processed_answers.add(one_result);
		}
		
		int current = 0;
		for (int i = 0; i < processed_answers.size(); i++) {
			 current = Math.max(current, Integer.parseInt((processed_answers.get(i)[1])));
		}
		
		
		for (int i = 0; i < processed_answers.size(); i++) {
			if (processed_answers.get(i)[1].equals(Integer.toString(current))) {
				winners.add(processed_answers.get(i)[0]);
			}
		}
		
		return winners;
	}
	
	public void DisconectClient() {
		Packet packet = new Packet(1, PacketHeaders.disconnect, "Quiz Finished!");
		for (int i = 0; i < clientsList.size(); i++) {
			clientsList.get(i).sendPacket(packet);
		}
	}
}
