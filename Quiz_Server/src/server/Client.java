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
import data.Question;

public class Client implements Runnable {

	private Socket clientSocket;
	private DataOutputStream toClient;
	private DataInputStream fromClient;
	private Runnable sender;
	
	private ExecutorService exe = Executors.newSingleThreadExecutor();
	
	public Client(Socket client) {
		this.clientSocket = client;
	}

	@Override
	public void run() {

		try {
			
			Question q = new Question("hello");
			Packet p = new Packet(11, PacketHeaders.command, q);
			sender = new Sender(clientSocket, p);
			exe.execute(sender);
			
		} catch (Exception ex) {
			
		}
		

		listen();
		
	}
	
	public void listen() {
		for (;;) {
			
			try {
				
				byte[] receivedPacket = new byte[1024];
				fromClient = new DataInputStream(clientSocket.getInputStream());
				fromClient.read(receivedPacket);
			
				int idPak = ByteBuffer.wrap(receivedPacket, 4, 4).getInt();
				IO.println(Integer.toString(idPak));

				
				/*if (receivedPacket.getHeader() == PacketHeaders.unknown) {
					IO.println("Client: " + receivedPacket.getClientId() + "\nError unknown data");
				} else if (receivedPacket.getHeader() == PacketHeaders.command) {
					IO.println("Client: " + receivedPacket.getMsgData());
				}*/
				
			} catch (Exception ex) {
				ex.printStackTrace();
				IO.println("Error Client Disconnected!");
				break;
			}
		}
	}
}
