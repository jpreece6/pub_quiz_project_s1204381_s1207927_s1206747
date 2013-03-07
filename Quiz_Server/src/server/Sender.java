package server;

import io.IO;

import java.io.DataOutputStream;
import java.net.Socket;

import packet.Packet;

/**
 * Sender sends data to its assigned client
 * @author Joshua Preece
 * @version 1.0
 */

public class Sender implements Runnable {

	private Packet data;
	private DataOutputStream toServer;
	private Socket clientSocket;
	
	/**
	 * Creates a new sender to communicate with a client
	 * @param client Client to communicate with
	 * @param dataBuf Data Packet to send to the client
	 */
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
