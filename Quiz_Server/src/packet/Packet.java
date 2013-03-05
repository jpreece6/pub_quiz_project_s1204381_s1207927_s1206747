package packet;

import io.IO;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import data.Question;

public class Packet {
	
	private byte[] byte_id;
	private byte[] byte_header;
	private byte[] byte_data;
	
	private byte[] packetHeader;
	private byte[] packet;
	
	
	public Packet(int id, PacketHeaders header, String msg) {
		try {
			byte_id = ByteBuffer.allocate(4).putInt(id).array();
			byte_header = ByteBuffer.allocate(4).putInt(header.ordinal()).array();
			byte_data = msg.getBytes("ASCII");
		
			packetHeader = new byte[byte_id.length + byte_header.length];
			packet = new byte[packetHeader.length + byte_data.length];
			System.arraycopy(byte_id, 0, packetHeader, 0, byte_id.length);
			System.arraycopy(byte_header, 0, packetHeader, byte_id.length, byte_header.length);
			System.arraycopy(packetHeader, 0, packet, 0, packetHeader.length);
			System.arraycopy(byte_data, 0, packet, packetHeader.length, byte_data.length);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public Packet(int id, PacketHeaders header, Question questions) {
		try {
			byte_id = ByteBuffer.allocate(4).putInt(id).array();
			byte_header = ByteBuffer.allocate(4).putInt(header.ordinal()).array();
			byte_data = convertToByteArray(questions.getQuestionArray());
		
			packetHeader = new byte[byte_id.length + byte_header.length];
			packet = new byte[packetHeader.length + 1010];
			System.arraycopy(byte_id, 0, packetHeader, 0, byte_id.length);
			System.arraycopy(byte_header, 0, packetHeader, byte_id.length, byte_header.length);
			System.arraycopy(packetHeader, 0, packet, 0, packetHeader.length);
			System.arraycopy(byte_data, 0, packet, packetHeader.length, byte_data.length);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public byte[] convertToByteArray(String[] questions) {
		ArrayList<byte[]> byteList = new ArrayList<byte[]>();
		
		for (int i = 0; i < questions.length; i++) {
			byteList.add(questions[i].getBytes());
		}
		
		byte[] byte_list = new byte[1010];
		int currentLength = 0;
		for (int i = 0; i < byteList.size(); i++) {
			/*if (i != 0) {
				System.arraycopy(byteList.get(i), 0, byte_list, currentLength, byteList.get(i).length);
			} else {
				System.arraycopy(byteList.get(i), 0, byte_list, currentLength, byteList.get(i).length);
			}*/
			System.arraycopy(byteList.get(i), 0, byte_list, currentLength, byteList.get(i).length);
			currentLength += byteList.get(i).length;
			//System.arraycopy(src, srcPos, dest, destPos, length)
		}
		
		//IO.println(byte_list.toString());
		
		return byte_list;
	}
	
	public byte[] getDataForTransmit() {
		return packet;
	}
}
