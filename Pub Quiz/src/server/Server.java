package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	private static final int NUM_CLIENTS = 20;
	private static final ExecutorService exe = Executors.newFixedThreadPool(NUM_CLIENTS);

	private Socket clientSocket;
	private static ServerSocket serverSocket;
	
	public static void main(String[] args) {
		new Server();
	}
	
	public Server() {
		
		for(;;) {
			
			try {
				
				clientSocket = serverSocket.accept();
				
			} catch (Exception ex) {
				
			}
			
		}
	}

}
