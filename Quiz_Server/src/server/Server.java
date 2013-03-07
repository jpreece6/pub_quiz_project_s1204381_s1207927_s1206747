package server;

import io.IO;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import quiz.Game;
/**
 * Server listens to the network for incoming connections
 * from clients wanting to join the quiz.
 * @author Joshua Preece
 * @version 1.0
 */

public class Server implements Runnable {

	private static ExecutorService exe;
	
	private Runnable newClient;
	
	private Socket clientSocket;
	private ServerSocket serverSocket;
	
	private Game game;
	private int current_connected = 0;
	private int num_clients = 0;
	
	private Client[] clientsList;
	
	/**
	 * Creates a new server to listen for connecting clients
	 * @param newGame Game handle
	 * @param clients Number of clients to wait for
	 */
	public Server(Game newGame, int clients) {
		this.game = newGame;
		this.num_clients = clients;
		this.clientsList = new Client[clients];
		exe  = Executors.newFixedThreadPool(clients);
	}
	
	@Override
	public void run() {

		server();
		
	}
	
	public void server() {
		try {
			
			serverSocket = new ServerSocket(2013);
			
			for(;;) {
				
				clientSocket = serverSocket.accept();
				newClient = new Client(clientSocket, game);
				exe.execute(newClient);
				clientsList[clientsList.length - 1] = (Client) newClient;
				
				IO.println("Client Connected");
				IO.println("IP:" + clientSocket.getInetAddress().toString());
				IO.println("Port: " + Integer.toString(clientSocket.getPort()));
				IO.print("");
				
				current_connected++;
				if (current_connected == num_clients) {
					game.all_clients_connected(clientsList);
					break;
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
