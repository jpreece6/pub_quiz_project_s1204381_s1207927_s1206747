package client;

import io.IO;

import java.io.DataInputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Listener implements Runnable {

	private Socket clientSocket;
	
	private DataInputStream fromServer;
	
	public Listener(Socket client) {
		this.clientSocket = client;
	}

	@Override
	public void run() {
		
		for (;;) {
			
			try {
			
				byte[] receivedPacket = new byte[1024];
				fromServer = new DataInputStream(clientSocket.getInputStream());
				fromServer.read(receivedPacket);
				
				int idPak = ByteBuffer.wrap(receivedPacket, 0, 4).getInt();
				IO.println(Integer.toString(idPak));
				
				/*if (receivedPacket.getHeader() == PacketHeaders.unknown) {
					IO.println("Client: " + receivedPacket.getClientId() + "\nError unknown data");
				}*/
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		}
		
	}

}
