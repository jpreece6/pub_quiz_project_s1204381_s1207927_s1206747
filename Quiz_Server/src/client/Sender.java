package client;

import io.IO;

import java.io.DataOutputStream;
import java.net.Socket;

import packet.Packet;

public class Sender implements Runnable {

	private Packet data;
	private DataOutputStream toServer;
	private Socket clientSocket;
	
	public Sender(Socket client, Packet dataBuf) {
		this.clientSocket = client;
		this.data = dataBuf;
	}

	@Override
	public void run() {

		try {
			
			toServer = new DataOutputStream(clientSocket.getOutputStream());
			Packet packet = data;
			toServer.write(packet.getDataForTransmit());
			
			IO.println("Packet Sent!");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
}
