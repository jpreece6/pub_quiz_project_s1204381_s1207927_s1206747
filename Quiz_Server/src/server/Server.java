package server;

import io.IO;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;
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
	private int port = 2013;
	
	private ArrayList<Client> clientsList;
	
	/**
	 * Creates a new server to listen for connecting clients
	 * @param newGame Game handle
	 * @param clients Number of clients to wait for
	 */
	public Server(Game newGame, int clients, int serverPort) {
		this.game = newGame;
		this.num_clients = clients;
		this.port = serverPort;
		this.clientsList = new ArrayList<Client>();
		exe  = Executors.newFixedThreadPool(clients);
	}
	
	@Override
	public void run() {

		server();
		
	}
	
	/**
	 * Main server thread, this method handles the connection of
	 * new clients connecting to the server. Once connected this thread
	 * creates a new thread to that client can be handle on a thread of its own.
	 */
	public void server() {
		try {
			
			serverSocket = new ServerSocket(port);
			
			for(;;) {
				
				clientSocket = serverSocket.accept();
				clientSocket.setTcpNoDelay(true);
				newClient = new Client(clientSocket, game, gen_client_id());
				exe.execute(newClient);
				clientsList.add((Client) newClient);
	
				
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
	
	/**
	 * Generates a id for a new connected client 
	 * @return client id
	 */
	private int gen_client_id() {
		//UUID uid = UUID.randomUUID();
		return current_connected + 4;
	}
}
