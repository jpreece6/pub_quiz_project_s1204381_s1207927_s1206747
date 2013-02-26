package server;

import io.IO;

import java.io.ObjectOutputStream;
import java.net.Socket;

import packet.Packet;
import packet.PacketHeaders;

public class Sender implements Runnable {

	private Packet data;
	private ObjectOutputStream toServer;
	private Socket clientSocket;
	
	public Sender(Socket client, Packet dataBuf) {
		this.clientSocket = client;
		this.data = dataBuf;
	}

	@Override
	public void run() {

		try {
			
			toServer = new ObjectOutputStream(clientSocket.getOutputStream());
			Packet packet = new Packet(10, PacketHeaders.unknown);
			toServer.writeObject(packet);
			
			IO.println("Packet Sent!");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
}
