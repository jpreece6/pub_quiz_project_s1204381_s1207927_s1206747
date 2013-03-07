package client;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import packet.Packet;
import packet.PacketHeaders;
import data.Question;

public class Client {

	private Socket clientSocket;
	private String serverAddress = "192.168.1.64";
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
		
		Question q = new Question(1);
		packet = new Packet(10, PacketHeaders.command, q);
		
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
		Question q = new Question(1);
		Packet Discon = new Packet(10, PacketHeaders.unknown, q);
		sender = new Sender(clientSocket, Discon);
	}

}
