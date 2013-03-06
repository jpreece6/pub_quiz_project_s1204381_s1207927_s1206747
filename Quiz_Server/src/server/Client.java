package server;

import io.IO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import packet.Packet;
import packet.PacketHeaders;
import quiz.Game;
import data.Question;

public class Client implements Runnable {

	private Socket clientSocket;
	private DataOutputStream toClient;
	private DataInputStream fromClient;
	private Runnable sender;
	
	private ExecutorService exe = Executors.newSingleThreadExecutor();
	
	private Game game;
	private boolean resultsReady = false;
	
	public Client(Socket client, Game newGame) {
		this.clientSocket = client;
		this.game = newGame;
	}

	@Override
	public void run() {

		listen();
		
	}
	
	public void listen() {
		for (;;) {
			
			try {
				
				byte[] receivedPacket = new byte[1024];
				fromClient = new DataInputStream(clientSocket.getInputStream());
				fromClient.read(receivedPacket);
				Packet packet = new Packet(receivedPacket);
				process_data(packet);
				
			} catch (Exception ex) {
				IO.println("Client Disconnected!");
				break;
			}
		}
	}
	
	public void process_data(Packet packet) {
		if (packet.getHeader() == PacketHeaders.unknown.ordinal()) {
			IO.println("Error: client " + packet.getID() + " : has sent unknown data");
		} else if (packet.getHeader() == PacketHeaders.command.ordinal()) {
			
		} else if (packet.getHeader() == PacketHeaders.questions.ordinal()) {
			
		} else if (packet.getHeader() == PacketHeaders.results.ordinal()) {
			
		} else if (packet.getHeader() == PacketHeaders.team.ordinal()) {
			
		}
	}
	
	public void sendPacket(Packet send) {
		try {
			sender = new Sender(clientSocket, send);
			exe.execute(sender);
			
		} catch (Exception ex) {
			
		}
	}
	
	public void ResultsReady(boolean isReady) {
		this.resultsReady = isReady;
	}
}
