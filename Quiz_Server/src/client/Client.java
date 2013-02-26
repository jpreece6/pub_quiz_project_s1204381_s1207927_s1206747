package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import packet.Packet;
import packet.PacketHeaders;

public class Client {

	private Socket clientSocket;
	private String serverAddress = "localhost";
	private int serverPort = 2013;
	
	private static final int NUM_THREADS = 2;
	private static final ExecutorService exe = Executors.newFixedThreadPool(NUM_THREADS);
	
	private Runnable listener;
	private Runnable sender;
	
	private Packet packet;
	
	public static void main(String[] args) {
		new Client();
	}
	
	public Client() {
		
		packet = new Packet(10, PacketHeaders.unknown);
		
		try {
			clientSocket = new Socket(serverAddress, serverPort);
			listener = new Listener(clientSocket);
			sender = new Sender(clientSocket, packet);
			exe.execute(listener);
			exe.execute(sender);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void disconnectClient() {
		Packet Discon = new Packet(10, PacketHeaders.command);
		sender = new Sender(clientSocket, Discon);
	}

}
