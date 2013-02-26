package server;

import io.IO;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import packet.Packet;
import packet.PacketHeaders;

public class Client implements Runnable {

	private Socket clientSocket;
	private ObjectOutputStream toClient;
	private ObjectInputStream fromClient;
	private Runnable sender;
	
	private ExecutorService exe = Executors.newSingleThreadExecutor();
	
	public Client(Socket client) {
		this.clientSocket = client;
	}

	@Override
	public void run() {

		/*try {
			
			Packet p = new Packet(11, PacketHeaders.unknown);
			sender = new Sender(clientSocket, p);
			exe.execute(sender);
			
		} catch (Exception ex) {
			
		}*/
		

		listen();
		
	}
	
	public void listen() {
		for (;;) {
			
			try {
				
				Packet receivedPacket;
				fromClient = new ObjectInputStream(clientSocket.getInputStream());
				receivedPacket = (Packet) fromClient.readObject();
				if (receivedPacket.getHeader() == PacketHeaders.unknown) {
					IO.println("Client: " + receivedPacket.getClientId() + "\nError unknown data");
				} else if (receivedPacket.getHeader() == PacketHeaders.command) {
					
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
				IO.println("Error Client Disconnected!");
				break;
			}
		}
	}
}
