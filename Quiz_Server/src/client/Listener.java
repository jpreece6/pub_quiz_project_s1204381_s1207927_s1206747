package client;

import io.IO;

import java.io.ObjectInputStream;
import java.net.Socket;

import packet.Packet;
import packet.PacketHeaders;

public class Listener implements Runnable {

	private Socket clientSocket;
	
	private ObjectInputStream fromServer;
	
	public Listener(Socket client) {
		this.clientSocket = client;
	}

	@Override
	public void run() {
		
		for (;;) {
			
			try {
			
				Packet receivedPacket;
				fromServer = new ObjectInputStream(clientSocket.getInputStream());
				receivedPacket = (Packet) fromServer.readObject();
				if (receivedPacket.getHeader() == PacketHeaders.unknown) {
					IO.println("Client: " + receivedPacket.getClientId() + "\nError unknown data");
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		}
		
	}

}
