package packet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import data.Question;

/**
 *  Packet handles the storage of data to be sent to the servers
 *  clients and conversion of data into and from bytes.
 *  @author Joshua Preece
 *  @version 1.0
 */

public class Packet {
	
	private byte[] byte_id;
	private byte[] byte_header;
	private byte[] byte_data;
	
	private byte[] packetHeader;
	private byte[] packet;
	
	private static final int PACKET_SIZE = 2024;
	
	/**
	 * Creates a new Packet from a byte array.
	 * @param receivedPacket a byte array received from a client
	 */
	public Packet(byte[] receivedPacket) {
		// Split the receivedPacket into multiple byte arrays for access to the data.
		splitBytes(receivedPacket);
	}
	
	/**
	 * Creates a new Packet with the client id, header/data type 
	 * and a single string message.
	 * @param id assigned to a packet to identify the sender
	 * @param header used to identify the type of data being sent 
	 * @param msg a single string message
	 */
	public Packet(int id, PacketHeaders header, String msg) {
		try {
			// Converts id, header and msg into bytes.
			byte_id = ByteBuffer.allocate(4).putInt(id).array();
			byte_header = ByteBuffer.allocate(4).putInt(header.ordinal()).array();
			byte_data = msg.getBytes("ASCII");
		
			// Concatenate all byte arrays into a packet array.
			packetHeader = new byte[byte_id.length + byte_header.length];
			packet = new byte[PACKET_SIZE];
			System.arraycopy(byte_id, 0, packetHeader, 0, byte_id.length);
			System.arraycopy(byte_header, 0, packetHeader, byte_id.length, byte_header.length);
			System.arraycopy(packetHeader, 0, packet, 0, packetHeader.length);
			System.arraycopy(byte_data, 0, packet, packetHeader.length, byte_data.length);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Creates a new Packet with a client id, header/data type
	 * and Question object which contains the questions.
	 * @param id assigned to the packet to identify the sender
	 * @param header used to identify the type of data being sent
	 * @param questions a Question object which contains a string array of questions
	 */
	public Packet(int id, PacketHeaders header, Question questions) {
		try {
			// Converts id, header and questions into bytes.
			byte_id = ByteBuffer.allocate(4).putInt(id).array();
			byte_header = ByteBuffer.allocate(4).putInt(header.ordinal()).array();
			byte_data = convertToByteArray(questions.getQuestionArray(true));
		
			// Concatenate all byte arrays into a packet array.
			packetHeader = new byte[byte_id.length + byte_header.length];
			packet = new byte[PACKET_SIZE];
			System.arraycopy(byte_id, 0, packetHeader, 0, byte_id.length);
			System.arraycopy(byte_header, 0, packetHeader, byte_id.length, byte_header.length);
			System.arraycopy(packetHeader, 0, packet, 0, packetHeader.length);
			System.arraycopy(byte_data, 0, packet, packetHeader.length, byte_data.length);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Creates a new Packet with a client id, header/data type
	 * and a string array of results.
	 * @param id assigned to the packet to identify the sender
	 * @param header used to identify the type of data being sent
	 * @param results a String array of results
	 */
	public Packet(int id, PacketHeaders header, String[] results) {
		try {
			// Converts id, header and results into bytes.
			byte_id = ByteBuffer.allocate(4).putInt(id).array();
			byte_header = ByteBuffer.allocate(4).putInt(header.ordinal()).array();
			byte_data = convertToByteArray(results);
		
			// Concatenate all byte arrays into a packet array.
			packetHeader = new byte[byte_id.length + byte_header.length];
			packet = new byte[PACKET_SIZE];
			System.arraycopy(byte_id, 0, packetHeader, 0, byte_id.length);
			System.arraycopy(byte_header, 0, packetHeader, byte_id.length, byte_header.length);
			System.arraycopy(packetHeader, 0, packet, 0, packetHeader.length);
			System.arraycopy(byte_data, 0, packet, packetHeader.length, byte_data.length);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Converts each element within a 2D string array into bytes.
	 * @param questions a String array of questions to be converted
	 * @return a byte array of data
	 */
	public byte[] convertToByteArray(String[][] questions) {
		ArrayList<byte[]> byteList = new ArrayList<byte[]>();
		
		/*
		 * Convert each element within questions into bytes and
		 * then insert into byteLsit.
		 */
		for (int i = 0; i < questions.length; i++) {
			for (int b = 0; b < 5; b++) {
				byteList.add(questions[i][b].getBytes());
			}
		}
		
		byte[] byte_list = new byte[PACKET_SIZE - 8];
		int currentLength = 0;
		// Append a byteList element onto the end of byte_list.
		for (int i = 0; i < byteList.size(); i++) {
			System.arraycopy(byteList.get(i), 0, byte_list, currentLength, byteList.get(i).length);
			currentLength += byteList.get(i).length;
		}
		
		return byte_list;
	}
	
	/**
	 * Converts each element within a string array into bytes.
	 * @param questions a String array of questions to be converted
	 * @return a byte array of data
	 */
	public byte[] convertToByteArray(String[] questions) {
		ArrayList<byte[]> byteList = new ArrayList<byte[]>();
		
		/*
		 * Convert each element within questions into bytes and
		 * then insert into byteLsit.
		 */
		for (int i = 0; i < questions.length; i++) {
			byteList.add(questions[i].getBytes());
		}
		
		byte[] byte_list = new byte[PACKET_SIZE - 8];
		int currentLength = 0;
		// Append a byteList element onto the end of byte_list.
		for (int i = 0; i < byteList.size(); i++) {
			System.arraycopy(byteList.get(i), 0, byte_list, currentLength, byteList.get(i).length);
			currentLength += byteList.get(i).length;
		}
		
		return byte_list;
	}
	
	/**
	 * Retrieves the packet as a byte array.
	 * @return a byte array which contains id, header and data
	 */
	public byte[] getDataForTransmit() {
		// Returns the packet byte array.
		return packet;
	}
	
	/**
	 * Splits a byte array into separate byte arrays.
	 * @param data to be split into byte_id, byte_header and byte_data
	 */
	public void splitBytes(byte[] data) {
		byte_id = new byte[4];
		byte_header = new byte[4];
		byte_data = new byte[PACKET_SIZE - 8];
		// Split the bytes from data into individual byte arrays for easy access.
		System.arraycopy(data, 0, byte_id, 0, 4);
		System.arraycopy(data, 4, byte_header, 0, 4);
		System.arraycopy(data, 8, byte_data, 0, PACKET_SIZE - 8);
	}
	
	/**
	 * Returns the client id assigned to this packet
	 * @return client id assigned to the packet
	 */
	public int getID() {
		return ByteBuffer.wrap(byte_id).getInt();
	}
	
	/**
	 * Returns the header of this packet
	 * @return header of the packet
	 */
	public int getHeader() {
		return ByteBuffer.wrap(byte_header).getInt();
	}
	
	/**
	 * Returns the data stored within this packet
	 * excluding id and header
	 * @return data stored within the packet
	 */
	public String getData() {
		return trim(byte_data);
	}
	
	/**
	 * Removes all empty bytes from a byte array
	 * @param data Data array to trim
	 * @return Returns a string without empty space
	 */
	public String trim(byte[] data) {
		int space = 0;
		int end_of_data = 0;
		byte[] new_data;
		/**
		 * Integrates through the byte array, 5 continuous
		 * 00s found within the array marks where the data ends.
		 */
		for (int i = 0; i < data.length; i++) {
			if (data[i] == 00) {
				++space;
				if (space >= 5) {
					end_of_data = i - 4;
					break;
				}
			}
		}
		
		new_data = new byte[end_of_data];
		System.arraycopy(data, 0, new_data, 0, end_of_data);
		return new String(new_data);
	}
}
